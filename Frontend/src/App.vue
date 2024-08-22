<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useUserStore } from './stores/user-store'
import router from './router'
const store = useUserStore()
const date = new Date()

// Extract current month and year
const months = [
  'January',
  'February',
  'March',
  'April',
  'May',
  'June',
  'July',
  'August',
  'September',
  'October',
  'November',
  'December'
]
const currentMonth = ref(months[date.getMonth()])
const currentYear = ref(date.getFullYear())
onMounted(() => {
  store.user = localStorage.getItem('username')
})
const signOut = () => {
  localStorage.clear()
  store.user = null
  router.push('/')
}
</script>

<template>
  <header class="">
    <div v-if="store.user" class="flex items-center gap-4">
      <div class="relative w-10 h-10 overflow-hidden bg-gray-100 rounded-full dark:bg-gray-600">
        <svg
          class="absolute w-12 h-12 text-gray-400 -left-1"
          fill="currentColor"
          viewBox="0 0 20 20"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            fill-rule="evenodd"
            d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z"
            clip-rule="evenodd"
          ></path>
        </svg>
      </div>
      <div class="font-mediu">
        <div>{{ store.user }}</div>
        <div class="text-sm text-gray-500 dark:text-gray-400">
          Joined in {{ currentMonth }} {{ currentYear }}
        </div>
      </div>
      <div class="absolute right-10">
        <button
          @click="signOut"
          type="button"
          class="focus:outline-none text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 font-semibold rounded-lg px-4 py-2 me-2 mb-2 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-900"
        >
          Sign out
        </button>
      </div>
    </div>
    <div v-else>
      <h1>Hi fellas!</h1>
      <br />Username is: admin@mail.com <br />Password is: dodge
    </div>
  </header>

  <RouterView />
</template>
