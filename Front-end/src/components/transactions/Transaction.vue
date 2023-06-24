<template>
    <headerNavigation />

    <body class="bodyStructure">
        <div class="structure">
            <div class="contain">
                <div id="rowAbove">
                    <div class="accountNumber">
                        <p>{{ this.bankAccount.iban }}</p>
                    </div>
                    <div class="groupOptions">
                        <div v-if="this.roleUser == 'CUSTOMER'" class="option">
                            <button class="btn" @click="WithDrawOrDeposit()">
                                Deposit
                            </button>
                        </div>
                        <div v-if="this.roleUser == 'CUSTOMER'" class="option">
                            <button class="btn" @click="WithDrawOrDeposit()">
                                Withdraw
                            </button>
                        </div>
                        <div v-if="this.roleUser == 'EMPLOYEE'" class="option"></div>
                        <div v-if="this.roleUser == 'EMPLOYEE'" class="option"></div>
                        <div class="option">
                            <button class="btn" @click="createTransaction()">
                                Transaction
                            </button>
                        </div>
                    </div>
                </div>

                <div id="rowBelow">
                    <div class="options" id="datepicker">
                        <h4>From</h4>&nbsp;&nbsp;
                        <input type="date" v-model="startDate" />
                    </div>
                    <div class="options" id="datepicker">
                        <h4>To</h4> &nbsp;&nbsp;
                        <input type="date" v-model="endDate" />
                    </div>
                    <div class="options">
                        <select id="inputField" v-model="this.operator">
                            <option value="<">Less than</option>
                            <option value="==">Equal to</option>
                            <option value=">">Greater than</option>
                        </select>
                    </div>
                    <div v-if="this.operator === '==' || this.operator === '<' || this.operator === '>'" class="options">
                        <input type="number" id="inputField" placeholder="Balance" v-model="searchField" />
                    </div>
                    <div v-else class="options">
                        <input type="text" id="inputField" placeholder="Search" v-model="searchField">
                    </div>
                </div>
            </div>
            <div id="extraPadding">
                <div class="bodyInfo">
                    <div v-for="list in this.transactions" class="transaction" @click="ViewTransactions(list.id)">
                        <div id="transactionInfo">
                            <div id="bankAccount">
                                <h1 v-if="list.bankAccountTo == this.bankAccount.iban">{{ list.bankAccountFrom }}</h1>
                                <h1 v-else-if="list.bankAccountFrom == this.bankAccount.iban">{{ list.bankAccountTo }}</h1>
                            </div>

                            <div>
                                <div id="currencies">
                                    <div>{{ this.bankAccount.currencies }}</div>
                                </div>
                                <div id="amount">
                                    <h1>{{ list.amount }}</h1>
                                    <!-- list.amount.toFixed(2) -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <footerNavigation />
</template>

<script>
import headerNavigation from '../main/Header.vue'
import footerNavigation from '../main/Footer.vue';
import axios from '../../axios-auth.js';

export default {
    header: {
        name: "header",
        components: {
            headerNavigation
        }
    },
    footer: {
        name: "footer",
        components: {
            footerNavigation
        },
    },
    name: "transactions",
    props: {
        id: Number,
    },
    data() {
        return {
            startDate: "2020-06-22",
            endDate: "2025-01-01",
            operator: "",
            searchField: 0,
            roleUser: localStorage.getItem("role"),
            transactions: [
                {
                    id: 0,
                    amount: '',
                    bankAccountFrom: '',
                    bankAccountTo: '',
                    date: '',
                }
            ],
            bankAccount:
            {
                id: 0,
                iban: '',
                currencies: '',
                accountType: [],
            },
            balanceFilter:
            {
                comparison: '',
                value: null,
            },
            searchQuery: '',
        };
    },
    mounted() {
        this.getBankAccount();
    },
    methods: {
        getBankAccount() {
            const decodedId = atob(this.id)
            axios
                .get('/bankaccounts/info/' + decodedId, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.bankAccount = res.data;
                    this.getTransactions();
                }).catch((error) => {
                    alert(error.response.data);
                });
        },
        getTransactions() {
            console.log(this.bankAccount.id)
            axios
                .get('transactions/account/' + this.bankAccount.id, {
                    params: {
                        startDate: this.startDate,
                        endDate: this.endDate,
                        operator: this.operator,
                        searchField: this.searchField
                    },
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.transactions = res.data;
                    console.log(res.data)
                }).catch((error) => {
                    // alert(error.response.data);
                });
        },
        WithDrawOrDeposit() {
            this.$router.push("/customer/withdrawOrDeposit/" + btoa(this.bankAccount.id));
        },
        createTransaction() {
            this.$router.push("/customer/createtransactions/" + btoa(this.bankAccount.id));
        },
        ViewTransactions(id) {
            this.$router.push("/viewTransaction/" + btoa(this.bankAccount.iban) + "/" + btoa(id));
        },
    },
    // computed: {
    //     filteredTransactions() {
    //         const searchQuery = this.searchQuery.toLowerCase();
    //         const fromDate = this.fromDate;
    //         const toDate = this.toDate;
    //         const balanceFilter = this.balanceFilter;

    //         return this.transactions.filter((transaction) => {
    //             const fieldsToCheck = ['amount', 'bankAccountFrom', 'bankAccountTo'];

    //             const isInDateRange =
    //                 (!fromDate || transaction.date >= fromDate) &&
    //                 (!toDate || transaction.date <= toDate);

    //             const matchesSearchQuery = fieldsToCheck.some((field) => {
    //                 const fieldValue = transaction[field];
    //                 return (
    //                     fieldValue && fieldValue.toString().toLowerCase().includes(searchQuery)
    //                 );
    //             });

    //             let matchesBalanceFilter = true;
    //             if (balanceFilter.comparison === '<') {
    //                 matchesBalanceFilter = parseFloat(transaction.amount) < balanceFilter.value;
    //             } else if (balanceFilter.comparison === '==') {
    //                 matchesBalanceFilter = parseFloat(transaction.amount) === balanceFilter.value;
    //             } else if (balanceFilter.comparison === '>') {
    //                 matchesBalanceFilter = parseFloat(transaction.amount) > balanceFilter.value;
    //             }

    //             return isInDateRange && matchesSearchQuery && matchesBalanceFilter;
    //         });
    //     },
    // },
};
</script>

<style>
@import '../../assets/css/transaction.css';
</style>
