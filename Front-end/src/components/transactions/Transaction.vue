<template>
    <headerNavigation />

    <body class="bodyStructure">
        <div class="structure">
            <div class="headInfo">
                <div class="accountNumber">
                    <p>{{ this.bankAccount.iban }}</p>
                </div>
                <div class="option">
                    <button class="btn" @click="createTransaction()">Create Transaction</button>
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
                    <div v-if="role == 'CUSTOMER'" class="option" id="datepicker">
                        <h4>From</h4>
                        <input type="date" v-model="fromDate" />
                    </div>
                    <div v-if="role == 'CUSTOMER'" class="option" id="datepicker">
                        <h4>To</h4>
                        <input type="date" v-model="toDate" />
                    </div>
                    <div v-if="role == 'CUSTOMER'" class="option">
                        <select v-model="balanceFilter.comparison">
                            <option value="<">Less than</option>
                            <option value="==">Equal to</option>
                            <option value=">">Greater than</option>
                        </select>
                    </div>
                    <div v-if="role == 'CUSTOMER' && balanceFilter.comparison === '==' || balanceFilter.comparison === '<' || balanceFilter.comparison === '>'"
                        class="option">
                        <input type="number" id="inputField" placeholder="Balance" v-model="balanceFilter.value" />
                    </div>
                    <div v-else class="option">
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
    width: 150px;
    right: 0;
}

#datepicker {
    display: flex;
    flex-direction: column;
    float: left;
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
            balanceFilter: {
                comparison: '',
                value: null,
            },
            transactions: [
                {
                    id: '',
                    description: '',
                    amount: '',
                    accountTypeFrom: '',
                    accountTypeTo: '',
                    bankAccountFrom: '',
                    bankAccountTo: '',
                    date: '',
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
            searchQuery: '',
            fromDate: null,
            toDate: null,
            testArray: [],
        };
    },
    mounted() {
        this.getUser();
        this.getBankAccount();
    },
    methods: {
        test() {
            this.transactions.forEach(element => {
                // console.log(element)
                if (parseInt(element.amount) > 100) {
                    this.testArray.push(element);
                }
            });
            console.log(this.testArray)
        },
        WithDrawOrDeposit() {
            this.$router.push("/customer/withdrawOrDeposit/" + this.bankAccount.iban);
        },
        createTransaction() {
            this.$router.push("/customer/createtransactions/" + this.user.username);
        },
        ViewTransactions(id) {
            this.$router.push("/viewTransaction/" + this.bankAccount.iban + "/" + this.user.username + "/" + id);
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
                    this.test();

                })
                .catch(error => console.log(error))
        },
    },
    computed: {
        filteredTransactions() {
            const searchQuery = this.searchQuery.toLowerCase();
            const fromDate = this.fromDate;
            const toDate = this.toDate;
            const balanceFilter = this.balanceFilter;

            return this.transactions.filter((transaction) => {
                const fieldsToCheck = ['amount', 'bankAccountFrom', 'bankAccountTo'];

                const isInDateRange =
                    (!fromDate || transaction.date >= fromDate) &&
                    (!toDate || transaction.date <= toDate);

                const matchesSearchQuery = fieldsToCheck.some((field) => {
                    const fieldValue = transaction[field];
                    return (
                        fieldValue && fieldValue.toString().toLowerCase().includes(searchQuery)
                    );
                });

                let matchesBalanceFilter = true;
                if (balanceFilter.comparison === '<') {
                    matchesBalanceFilter = parseFloat(transaction.amount) < balanceFilter.value;
                } else if (balanceFilter.comparison === '==') {
                    matchesBalanceFilter = parseFloat(transaction.amount) === balanceFilter.value;
                } else if (balanceFilter.comparison === '>') {
                    matchesBalanceFilter = parseFloat(transaction.amount) > balanceFilter.value;
                }

                return isInDateRange && matchesSearchQuery && matchesBalanceFilter;
            });
        },
    },
};
</script>

<style>
@import '../../assets/css/transaction.css';
</style>
