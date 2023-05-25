import { createRouter, createWebHistory } from 'vue-router'

import Login from '../components/main/Login.vue';
import Home from '../components/main/Home.vue';
import Transaction from '../components/customers/Transaction.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', component: Login },
    { path: '/home', component: Home },
    { path: '/customer/transaction/:id' , component: Transaction, props: true }
  ]
})

export default router
 