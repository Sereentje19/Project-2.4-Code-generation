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
                        <div v-if="this.user.employeeRole != 'EMPLOYEE'" class="option">
                            <button class="btn" @click="WithDrawOrDeposit()">
                                Deposit
                            </button>
                        </div>
                        <div v-if="this.user.employeeRole != 'EMPLOYEE'" class="option">
                            <button class="btn" @click="WithDrawOrDeposit()">
                                Withdraw
                            </button>
                        </div>
                        <div v-if="this.user.employeeRole == 'EMPLOYEE'" class="option"></div>
                        <div v-if="this.user.employeeRole == 'EMPLOYEE'" class="option"></div>
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
                        <input type="date" v-model="startDate" @change="getTransactions()" />
                    </div>
                    <div class="options" id="datepicker">
                        <h4>To</h4> &nbsp;&nbsp;
                        <input type="date" v-model="endDate"  @change="getTransactions()"/>
                    </div>
                    <div class="options">
                        <select id="inputField" v-model="this.operator"  @change="getTransactions()">
                            <option value="<">Less than</option>
                            <option value="==">Equal to</option>
                            <option value=">">Greater than</option>
                        </select>
                    </div>
                    <div v-if="this.operator === '==' || this.operator === '<' || this.operator === '>'" class="options">
                        <input type="text" id="inputField" placeholder="Balance" v-model="this.searchField" @change="getTransactions()"/>
                    </div>
                    <div v-else class="options">
                        <input type="text" id="inputField" placeholder="Search" v-model="this.searchField" @change="getTransactions()">
                    </div>
                </div>
            </div>
            <div id="extraPadding">
                <div class="bodyInfo">
                    <div v-for="list in this.transactions" class="transaction" @click="ViewTransactions(list.id)">
                        <div id="transactionInfo">
                            <div id="bankAccount">
                                <h1 >{{ list.iban }}</h1>
                            </div>
                            <div>
                                <div id="currencies">
                                    <div> {{ this.bankAccount.currencies }}</div>
                                </div>
                                <div id="amount">
                                    <h1> {{ list.amount.toFixed(2) }}</h1>
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
            
            startDate: "0001-01-01",
            endDate: "9999-09-09",
            operator: "",
            searchField: 0,
            roleUser: localStorage.getItem("role"),
            transactions: [
                {
                    id: 0,
                    amount: 0,
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
            },
            user:{
                employeeRole: []
            }
        };
    },
    mounted() {
        this.getBankAccount();
        this.getUserRoles();
    },
    methods: {
        getUserRoles() {
            axios
                .get('users/role',  {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.user = res.data;
                }).catch((error) => {
                    alert(error.response.data);
                });
        },
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
                    alert("There are no transactions with current used filters");
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
};
</script>

<style>
@import '../../assets/css/transaction.css';
</style>
