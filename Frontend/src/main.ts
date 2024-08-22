import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import PrimeVue from 'primevue/config'
import Aura from '@primevue/themes/aura'
import ToastService from 'primevue/toastservice'

import './assets/tailwind.scss'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(ToastService)

app.mount('#app')

app.use(PrimeVue, {
  theme: {
    preset: Aura,
    options: {
      prefix: 'p',
      darkModeSelector: 'off',
      cssLayer: false
    }
  }
})
