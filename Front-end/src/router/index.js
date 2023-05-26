import { createRouter, createWebHistory } from 'vue-router'

import Login from '../components/main/Login.vue';
import Home from '../components/main/Home.vue';
import CustomerTransactions from '../components/customers/Transaction.vue';
import EmployeeTransactions from '../components/employees/Transaction.vue';
import CustomerViewTransaction from '../components/customers/ViewTransactions.vue';
import EmployeeViewTransaction from '../components/employees/ViewTransactions.vue';
import Accounts from '../components/employees/Accounts.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', component: Login },
    { path: '/home', component: Home },
    { path: '/customer/transactions/:id' , component: CustomerTransactions, props: true },
    { path: '/employee/transactions/:id' , component: EmployeeTransactions, props: true },
    { path: '/customer/viewTransaction/:id' , component: CustomerViewTransaction, props: true },
    { path: '/employee/viewTransaction/:id' , component: EmployeeViewTransaction, props: true },
    { path: '/employee/accounts/:id' , component: Accounts, props: true },
  ]
})

export default router
 