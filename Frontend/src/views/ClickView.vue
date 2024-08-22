<script setup lang="ts">
import axios from 'axios'

import { onBeforeUnmount, onMounted, ref } from 'vue'
const coordinateHistory = ref()
const BASE_URL = import.meta.env.VITE_API_URL
const totalPages = ref(0)
const showingOf = ref('')
const totalSize = ref(0)
let currPage = 0
let size = 10
const excludedArea = ref<HTMLElement | null>(null)

const handleClick = (event: MouseEvent) => {
  if (excludedArea.value && excludedArea.value.contains(event.target as Node)) {
    return
  }
  axios
    .post(BASE_URL + '/click/save', {
      username: localStorage.getItem('username'),
      XCoordinate: event.clientX,
      YCoordinate: event.clientY
    })
    .then(() => {
      updateCoordinatesHistory()
    })
}

const updateCoordinatesHistory = (page = 0, size = 10) => {
  currPage = Math.max(0, page)
  axios
    .get(`${BASE_URL}/click/get-all/${localStorage.getItem('username')}?page=${page}&size=${size}`)
    .then((res) => {
      coordinateHistory.value = res.data.content
      totalSize.value = res.data.totalElements
      showingOf.value = page * size + '-' + (page + 1) * size
      totalPages.value = res.data.totalPages
    })
}

onMounted(() => {
  updateCoordinatesHistory()
  window.addEventListener('click', handleClick)
})
onBeforeUnmount(() => {
  window.removeEventListener('click', handleClick)
})
</script>

<template>
  <div class="flex flex-col items-center">
    <h1>Welcome to clicks page</h1>
    <section class="ml-10 w-full">
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
              <th scope="col" class="px-6 py-3">Date</th>
              <th scope="col" class="px-6 py-3">X coordinate</th>
              <th scope="col" class="px-6 py-3">Y coordinate</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(coordinate, index) in coordinateHistory"
              :key="index"
              class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600"
            >
              <th
                scope="row"
                class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
              >
                {{ coordinate.clickedAt }}
              </th>
              <td class="px-6 py-4">
                {{ coordinate.x }}
              </td>
              <td class="px-6 py-4">
                {{ coordinate.y }}
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
                @click="updateCoordinatesHistory(--currPage, size)"
                class="flex items-center justify-center px-3 h-8 ms-0 leading-tight text-gray-500 bg-white border border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                >Previous</a
              >
            </li>
            <li class="cursor-pointer" v-for="n in totalPages > 5 ? 5 : totalPages" :key="n">
              <a
                @click="updateCoordinatesHistory(n, size)"
                class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
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
                @click="updateCoordinatesHistory(++currPage, size)"
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
