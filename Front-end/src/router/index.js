import { createRouter, createWebHistory } from 'vue-router'

import Login from '../components/main/Login.vue';
import Home from '../components/main/Home.vue';
import Transactions from '../components/customers/Transaction.vue';
import ViewTransaction from '../components/customers/ViewTransactions.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', component: Login },
    { path: '/home', component: Home },
    { path: '/customer/transactions/:id' , component: Transactions, props: true },
    { path: '/customer/viewTransaction/:id' , component: ViewTransaction, props: true },
  ]
})

export default router
 