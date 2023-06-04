import { createRouter, createWebHistory } from 'vue-router'

import Login from '../components/main/Login.vue';
import Home from '../components/main/Home.vue';
import Transactions from '../components/transactions/Transaction.vue';
import ViewTransaction from '../components/transactions/ViewTransactions.vue';
import Accounts from '../components/employees/Accounts.vue';
import AccountInfo from '../components/customers/AccountInfo.vue';
import CustomerAccountOverview from '../components/customers/AccountOverview.vue';
import AllAccounts from '../components/employees/AllAccounts.vue';
import AccountInfoforEmployee from '../components/employees/AccountInfo.vue';
import CustomerCreateTransactions from '../components/customers/CreateTransaction.vue';
import CustomerWithdrawOrDeposit from '../components/customers/WithdrawOrDeposit.vue';
import pincode from '../components/customers/pincode.vue';


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', component: Login },
    { path: '/home', component: Home },
    { path: '/transactions/:id' , component: Transactions, props: true },
    { path: '/viewTransaction/:iban/:id' , component: ViewTransaction, props: true },
    { path: '/employee/accounts/:id' , component: Accounts, props: true },
    { path: '/customer/accountInfo/:id' , component: AccountInfo, props: true},
    { path: '/customer/customerAccountOverview/:id' , component: CustomerAccountOverview, props: true},
    { path: '/allAccounts/:id' , component: AllAccounts, props: true},
    { path: '/accountInfoforEmployee/:id' , component: AccountInfoforEmployee, props: true},
    { path: '/customer/createtransactions/:iban', component: CustomerCreateTransactions, props: true},
    { path: '/customer/withdrawOrDeposit/:iban', component: CustomerWithdrawOrDeposit, props: true},
    { path: '/customer/pincode/', component: pincode, props: true},
  ]
})

export default router
 