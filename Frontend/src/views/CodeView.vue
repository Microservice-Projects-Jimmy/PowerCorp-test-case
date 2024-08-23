<script setup lang="ts">
import router from '@/router'
import axios from 'axios'
import { onMounted, ref } from 'vue'
import Toast from 'primevue/toast'
import { useToast } from 'primevue/usetoast'
const toast = useToast()

const code = ref<Number>()
const codeFromServer = ref()
const previosCodes = ref()
const BASE_URL = import.meta.env.VITE_API_URL
const progressBarWidth = ref(100)
const duration = 60000
let intervalId: number
const otp = ref(Array(4).fill(''))
const totalPages = ref(0)
const showingOf = ref('')
const totalSize = ref(0)
let currPage = 0
let size = 10

const confirmCode = () => {
  code.value = Number(otp.value.join(''))
  console.log(code.value)
  axios
    .post(
      BASE_URL + '/code/confirm',
      {
        username: localStorage.getItem('username'),
        code: code.value
      },
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      }
    )
    .then((res) => {
      console.log(res)
      router.push('/clicks')
    })
    .catch((err) => {
      toast.add({
        severity: 'error',
        summary: 'Info',
        detail: err.response.data.message,
        life: 3000
      })

      codeFromServer.value = null
      otp.value = Array(4).fill('')
      updateCodes()
    })
}
const startProgress = () => {
  const intervalTime = 100
  const decrement = 100 / (duration / intervalTime)

  intervalId = window.setInterval(() => {
    progressBarWidth.value -= decrement
    if (progressBarWidth.value <= 0) {
      clearInterval(intervalId)
      codeFromServer.value = null
    }
  }, intervalTime)
}
const updateCodes = (page = 0, size = 10) => {
  page = Math.max(0, page)
  currPage = page
  axios
    .get(`${BASE_URL}/code/get-all/${localStorage.getItem('username')}?page=${page}&size=${size}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })
    .then((res) => {
      previosCodes.value = res.data.content
      totalSize.value = res.data.totalElements
      totalPages.value = res.data.totalPages
      showingOf.value = page * size + '-' + (page + 1) * size
    })
}
onMounted(() => {
  if (localStorage.getItem('token') == null) router.push('/')
  updateCodes()
})

const getCode = () => {
  axios
    .get(BASE_URL + '/code/get-code/' + localStorage.getItem('username'), {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })
    .then((res) => {
      codeFromServer.value = res.data.code
      console.log(codeFromServer.value)
    })
  progressBarWidth.value = 100
  focusInput(0)
  startProgress()
  updateCodes()
}

// OPT UI UX starts here

const handleKeyDown = (e: KeyboardEvent) => {
  if (
    !/^[0-9]{1}$/.test(e.key) &&
    e.key !== 'Backspace' &&
    e.key !== 'Delete' &&
    e.key !== 'Tab' &&
    !e.metaKey
  ) {
    e.preventDefault()
  }
  if (e.key === 'Enter') {
    confirmCode()
  }
  if (e.key === 'Delete' || e.key === 'Backspace') {
    const index = otp.value.indexOf('')
    if (index > 0) {
      otp.value.splice(index - 1, 1, '')
      focusInput(index - 1)
    }
  }
}

const handleInput = (index: number, event: Event) => {
  const target = event.target as HTMLInputElement
  const value = target.value
  otp.value.splice(index, 1, value)
  if (value && index < otp.value.length - 1) {
    focusInput(index + 1)
  }
}

const handleFocus = (event: FocusEvent) => {
  const target = event.target as HTMLInputElement
  target.select()
}

const focusInput = (index: number) => {
  const input = document.getElementById(`input-${index}`) as HTMLInputElement
  input?.focus()
}
const resetOtp = () => {
  otp.value = Array(4).fill('')
  focusInput(0) // Focus on the first input field after reset
}
// OPT UI UX ends here
</script>

<template>
  <div>
    <Toast />
    <section class="flex justify-center bg-white py-10 dark:bg-dark">
      <div class="container">
        <form id="otp-form" class="flex flex-col items-center gap-4" @submit.prevent="confirmCode">
          <div class="flex gap-2">
            <input
              v-for="(digit, index) in otp"
              :id="`input-` + index"
              :key="index"
              type="text"
              maxlength="1"
              class="shadow-xs flex w-[64px] items-center justify-center rounded-lg border border-stroke bg-white p-2 text-center text-2xl font-medium text-gray-5 outline-none sm:text-4xl dark:border-dark-3 dark:bg-white/5"
              :value="digit"
              @input="(event) => handleInput(index, event)"
              @keydown="handleKeyDown($event)"
              @focus="handleFocus"
            />
          </div>
          <button
            type="submit"
            class="mt-4 w-full max-w-xs rounded-lg bg-blue-500 p-2 text-white font-semibold hover:bg-blue-600"
          >
            Confirm Code
          </button>
        </form>
        <div class="flex flex-col items-center">
          <button
            @click="resetOtp"
            class="mt-4 w-full max-w-xs rounded-lg bg-red-700 p-2 text-white font-semibold hover:bg-red-800"
          >
            Reset
          </button>
          <button
            @click="getCode"
            class="mt-4 w-full max-w-xs rounded-lg bg-green-800 p-2 text-white font-semibold hover:bg-green-700"
          >
            Get Code
          </button>
        </div>
        <div class="flex justify-center mt-6" v-if="codeFromServer">
          <div
            id="toast-simple"
            class="flex relative items-center w-full max-w-xs p-4 rtl:space-x-reverse text-gray-500 bg-white divide-x rtl:divide-x-reverse divide-gray-200 rounded-lg shadow dark:text-gray-400 dark:divide-gray-700 dark:bg-gray-800"
            role="alert"
          >
            <svg
              class="w-5 h-5 text-blue-600 dark:text-blue-500 rotate-45"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 18 20"
            >
              <path
                stroke="currentColor"
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="m9 17 8 2L9 1 1 19l8-2Zm0 0V9"
              />
            </svg>
            <div class="ps-4 text-lg font-normal">Type code {{ codeFromServer }}</div>
            <div
              class="absolute bottom-0 left-0 h-1 bg-white"
              :style="{ width: progressBarWidth + '%' }"
            ></div>
          </div>
        </div>
      </div>
    </section>
    <section class="ml-10">
      <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
        <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
          <caption
            class="p-5 text-lg font-semibold text-left rtl:text-right text-gray-900 bg-white dark:text-white dark:bg-gray-800"
          >
            History
          </caption>
          <thead
            class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400"
          >
            <tr>
              <th scope="col" class="p-4">
                <div class="flex items-center">
                  <input
                    id="checkbox-all-search"
                    type="checkbox"
                    class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                  />
                  <label for="checkbox-all-search" class="sr-only">checkbox</label>
                </div>
              </th>
              <th scope="col" class="px-6 py-3">Name</th>
              <th scope="col" class="px-6 py-3">Code</th>
              <th scope="col" class="px-6 py-3">Action</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(code, index) in previosCodes"
              :key="index"
              class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600"
            >
              <td class="w-4 p-4">
                <div class="flex items-center">
                  <input
                    id="checkbox-table-search-1"
                    type="checkbox"
                    class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                  />
                  <label for="checkbox-table-search-1" class="sr-only">checkbox</label>
                </div>
              </td>
              <th
                scope="row"
                class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
              >
                {{ code.username }}
              </th>
              <td class="px-6 py-4">
                {{ code.code }}
              </td>
              <td class="px-6 py-4">
                <a href="#" class="font-medium text-gray-900 whitespace-nowrap dark:text-white">{{
                  code.createdAt
                }}</a>
              </td>
            </tr>
          </tbody>
        </table>
        <nav
          ref="excludedArea"
          class="flex items-center flex-column flex-wrap md:flex-row justify-between pt-4"
          aria-label="Table navigation"
        >
          <span
            class="text-sm font-normal text-gray-900 mb-4 md:mb-0 p-3 block w-full md:inline md:w-auto"
            >Showing <span class="font-semibold text-gray-900">{{ showingOf }}</span> of
            <span class="font-semibold text-gray-900">{{ totalSize }}</span></span
          >
          <ul class="inline-flex -space-x-px rtl:space-x-reverse text-sm h-8">
            <li class="cursor-pointer">
              <a
                @click="updateCodes(--currPage, size)"
                class="flex items-center justify-center px-3 h-8 ms-0 leading-tight text-gray-500 bg-white border border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                >Previous</a
              >
            </li>
            <li class="cursor-pointer" v-for="n in totalPages > 5 ? 5 : totalPages" :key="n">
              <a
                @click="updateCodes(n - 1, size)"
                v-if="n - 1 != currPage"
                class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                >{{ n }}</a
              >
              <a
                v-else
                @click="updateCodes(n - 1, size)"
                aria-current="page"
                class="flex items-center justify-center px-3 h-8 text-blue-600 border border-gray-300 bg-blue-50 hover:bg-blue-100 hover:text-blue-700 dark:border-gray-700 dark:bg-gray-700 dark:text-white"
                >{{ n }}</a
              >
            </li>

            <li>
              <a
                class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                >...</a
              >
            </li>

            <li class="cursor-pointer">
              <a
                @click="updateCodes(++currPage, size)"
                class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                >Next</a
              >
            </li>
          </ul>
        </nav>
      </div>
    </section>
  </div>
</template>
