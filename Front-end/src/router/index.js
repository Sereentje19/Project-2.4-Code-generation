import { createRouter, createWebHistory } from 'vue-router'

import Login from '../components/home/Login.vue';
import Home from '../components/home/Home.vue';
import Transaction from '../components/home/CustomerTransaction.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', component: Login },
    { path: '/home', component: Home },
    { path: '/customer/transaction' , component: Transaction }
  ]
})

export default router
 