<template>
    <headerNavigation />

    <body class="bodyStructure">
        <div class="structure">
            <div class="headInfo">
                <div class="accountNumber">
                    <p>{{ this.iban }}</p>
                </div>
                <div class="groupOptions">
                    <div class="option">
                        <button class="btn" @click="goToWithDrawOrDeposit()">
                            Deposit
                        </button>
                    </div>
                    <div class="option">
                        <button class="btn" @click="goToWithDrawOrDeposit()">
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
                    <div v-for="list in this.transactions" class="transaction" @click="goToViewTransactions(list.id)">
                        <div id="transactionInfo">
                            <div>
                                <h1 v-if="list.bankAccountTo == this.iban">{{ list.bankAccountFrom }}</h1>
                                <h1 v-else-if="list.bankAccountFrom == this.iban">{{ list.bankAccountTo }}</h1>
                            </div>
                            <div>
                                <h1>{{ list.amount }}</h1>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <footerNavigation />
</template>

<style>
#transactionInfo {
    display: flex;
    justify-content: space-between;
    padding: 25px;
}
</style>


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
        iban: String,
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
        };
    },
    mounted() {
        this.getUser();
    },
    methods: {
        goToWithDrawOrDeposit(iban) {
            this.$router.push("/customer/withdrawOrDeposit/" + iban);
        },
        goToViewTransactions(id) {
            this.$router.push("/customer/viewTransaction/" + this.iban + "/" + id);
        },
        getUser() {
            axios
                .get('users/login', headerToken)
                .then((res) => {
                    this.user = res.data;
                    this.getTransactions();
                })
                .catch(error => console.log(error));
        },
        getTransactions() {
            axios
                .get('transactions/' + this.iban, headerToken)
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
