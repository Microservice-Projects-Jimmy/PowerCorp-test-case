import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/code',
      name: 'code',
      component: () => import('../views/CodeView.vue')
    },
    {
      path: '/clicks',
      name: 'clicks',
      component: () => import('../views/ClickView.vue')
    }
  ]
})

export default router
