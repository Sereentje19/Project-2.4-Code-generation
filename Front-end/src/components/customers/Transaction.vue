<template>
    <headerNavigation />

    <body class="bodyStructure">
        <div class="structure">
            <div class="headInfo">
                <div class="accountNumber">
                    <p>{{ this.user.bankAccountList[0] }}</p>
                </div>
                <div class="groupOptions">
                    <div class="option">
                        <button class="btn">
                            Deposit
                        </button>
                    </div>
                    <div class="option">
                        <button class="btn" @click="testje()">
                            Withdraw
                        </button>
                    </div>
                    <div class="option">
                        <button class="btn">
                            Transaction
                        </button>
                    </div>
                </div>
            </div>
            <div id="extraPadding">
                <div class="bodyInfo">
                    <a href="/customer/viewTransaction/1">
                        <div v-for="list in this.user.bankAccountList" class="transaction">
                            <h1>{{ list }}</h1>
                        </div>
                    </a>
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
            transactions: [
                {
                    id: '',
                    description: '',
                    amount: '',
                    accountFromtype: '',
                    accountTotype: '',
                    bankAccountFrom: '',
                    bankAccountTo: '',
                }
            ],
            user:
            {
                id: 0,
                username: '',
                password: '',
                firstName: '',
                lastName: '',
                phoneNumber: '',
                email: '',
                street: '',
                houseNumber: '',
                postalCode: '',
                city: '',
                bankAccountList: [],
            },
            bankAccounts: [
                {
                    id: 0,
                    iban: '',
                    accountType: '',
                    currency: '',
                    balance: '',
                    userId: '',
                    disabled: '',
                }
            ],
        };
    },

    mounted() {
        this.getUser();
    },
    methods: {
        getUser() {
            axios
                .get('users/login', {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.user = res.data;
                    this.getBankAccounts();
                    this.getTransactions();
                })
                .catch(error => console.log(error));
        },
        getBankAccounts() {
            for (let i = 0; i < this.user.bankAccountList.length; i++) {
                console.log(i + "hoi")

                axios
                    .get('bankaccounts/user/' + this.user.bankAccountList[i], {
                        headers: {
                            Authorization: "Bearer " + localStorage.getItem("jwt")
                        }
                    })
                    .then((res) => {
                        this.bankAccounts = res.data;

                        console.log(res.data)
                        console.log(bankAccounts)
                    })
                    .catch(error => console.log(error))
            }
        },
        getTransactions() {
            axios
                .get('transactions/user/' + this.id, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.transactions = res.data;

                    console.log(res.data)
                    // console.log(this.transactions.id)
                })
                .catch(error => console.log(error))
        },
    },
};
</script>

<style>
@import '../../assets/css/transaction.css';
</style>
