<template>
    <headerNavigation />

    <body class="bodyStructure">
        <div class="structure">
            <div class="headInfo">
                <div class="accountNumber">
                    <p>{{ this.bankAccount.iban }}</p>
                </div>
                <div v-for="role in this.user.roles" class="groupOptions">
                    <div v-if="role == 'EMPLOYEE'" class="option">
                        <button class="btn" @click="WithDrawOrDeposit()">
                            Deposit
                        </button>
                    </div>
                    <div v-if="role == 'EMPLOYEE'" class="option">
                        <button class="btn" @click="WithDrawOrDeposit()">
                            Withdraw
                        </button>
                    </div>
                    <div v-if="role == 'EMPLOYEE'" class="option">
                        <button class="btn" @click="createTransaction()">
                            Transaction
                        </button>
                    </div>
                    <div v-if="role == 'CUSTOMER'" class="option"></div>
                    <div v-if="role == 'CUSTOMER'" class="option"></div>
                    <div v-if="role == 'CUSTOMER'" class="option">
                        <input type="text" id="inputField" placeholder="Search" v-model="searchQuery">
                    </div>
                </div>
            </div>
            <div id="extraPadding">
                <div class="bodyInfo">
                    <div v-for="list in filteredTransactions" class="transaction" @click="ViewTransactions(list.id)">
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
                                    <h1>{{ list.amount.toFixed(2) }}</h1>
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


<style>
#inputField {
    border-color: black;
    border-style: solid;
    border-width: 2px;
    border-radius: 10px;
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
                roles: [],
            },
            searchQuery: ''
        };
    },
    mounted() {
        this.getUser();
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
            this.$router.push("/viewTransaction/" + this.bankAccount.iban + "/" + id);
        },
        getUser() {
            axios
                .get('users/current', headerToken)
                .then((res) => {
                    this.user = res.data;
                })
                .catch(error => console.log(error));
        },
        getBankAccount() {
            axios
                .get('/bankaccounts/' + this.id, headerToken)
                .then((res) => {
                    this.bankAccount = res.data;
                    this.getTransactions();
                })
                .catch(error => console.log(error))
        },
        getTransactions() {
            axios
                .get('transactions/account/' + this.bankAccount.iban + "/" + this.bankAccount.accountType[0], headerToken)
                .then((res) => {
                    this.transactions = res.data;
                })
                .catch(error => console.log(error))
        },
    },
    computed: {
        filteredTransactions() {
            const searchQuery = this.searchQuery.toLowerCase();

            return this.transactions.filter(transaction => {
                const fieldsToCheck = [
                    'amount',
                    'bankAccountFrom',
                    'bankAccountTo'
                ];

                return fieldsToCheck.some(field => {
                    const fieldValue = transaction[field];
                    return fieldValue && fieldValue.toString().toLowerCase().includes(searchQuery);
                });
            });
        }
    },

};
</script>

<style>
@import '../../assets/css/transaction.css';
</style>
