<template>
    <headerNavigation />

    <body class="bodyStructure">
        <div class="structure">
            <div class="headInfo">
                <div class="accountNumber">
                    <p>{{ this.bankAccount.iban }}</p>
                </div>
                <div class="groupOptions">
                    <div class="option">
                        <button class="btn" @click="WithDrawOrDeposit()">
                            Deposit
                        </button>
                    </div>
                    <div class="option">
                        <button class="btn" @click="WithDrawOrDeposit()">
                            Withdraw
                        </button>
                    </div>
                    <div class="option">
                        <button class="btn" @click="createTransaction()">
                            Transaction
                        </button>
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
                                    <div v-for="curr in this.bankAccount.currencies">{{ curr }}</div>
                                </div>
                                <div id="amount">
                                    <h1>{{ list.amount }}</h1>
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

const headerToken = {
    headers: {
        Authorization: "Bearer " + localStorage.getItem("jwt")
    }
};

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
                    accountTypeFrom: '',
                    accountTypeTo: '',
                    bankAccountFrom: '',
                    bankAccountTo: '',
                }
            ],
            bankAccount:
            {
                id: 0,
                iban: '',
                balance: '',
                userId: 0,
                disabled: '',
                currencies: [],
                accountType: [],
            },
        };
    },
    mounted() {
        this.getBankAccount();
    },
    methods: {
        WithDrawOrDeposit() {
            this.$router.push("/customer/withdrawOrDeposit/" + this.bankAccount.iban);
        },
        createTransaction() {
            this.$router.push("/customer/createtransactions/" + this.id);
        },
        ViewTransactions(id) {
            this.$router.push("/customer/viewTransaction/" + this.bankAccount.iban + "/" + id);
        },
        getBankAccount() {
            axios
                .get('/bankaccounts/info/' + this.id, headerToken)
                .then((res) => {
                    this.bankAccount = res.data;
                    this.getTransactions();
                })
                .catch(error => console.log(error))
        },
        getTransactions() {
            axios
                .get('transactions/' + this.bankAccount.iban, headerToken)
                .then((res) => {
                    this.transactions = res.data;
                })
                .catch(error => console.log(error))
        },
    },
};
</script>

<style>
@import '../../assets/css/transaction.css';
</style>
