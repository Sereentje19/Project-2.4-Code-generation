import { createRouter, createWebHistory } from 'vue-router'

import Login from '../components/main/Login.vue';
import Home from '../components/main/Home.vue';
import CustomerTransactions from '../components/customers/Transaction.vue';
import EmployeeTransactions from '../components/employees/Transaction.vue';
import CustomerViewTransaction from '../components/customers/ViewTransactions.vue';
import EmployeeViewTransaction from '../components/employees/ViewTransactions.vue';
import Accounts from '../components/employees/Accounts.vue';
import AccountInfo from '../components/customers/AccountInfo.vue';
import CustomerAccountOverview from '../components/customers/AccountOverview.vue';
import AllAccounts from '../components/employees/AllAccounts.vue';
import AccountInfoforEmployee from '../components/employees/AccountInfo.vue';

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
    { path: '/customer/accountInfo/:id' , component: AccountInfo, props: true},
    { path: '/customer/customerAccountOverview/:id' , component: CustomerAccountOverview, props: true},
    { path: '/employee/allAccounts' , component: AllAccounts, props: true},
    { path: '/emplyee/accountInfoforEmployee/:id' , component: AccountInfoforEmployee, props: true},
  ]
})

export default router
 