<template>
    <div class="container">
        <h2>Personal Details</h2>
        <div>
            <label>User:</label>
            <select v-model="this.selectedUser" @change="checkAccountType()">
                <option v-for="user in this.users" :value="user">{{ user.username }}</option>
            </select>
        </div>
        <div>
            <div v-if="this.accountTypes != null">
                <label>Account type:</label>
                <select v-model="this.selectedAccountType">
                    <option v-for="accountType in this.accountTypes" :value="accountType">{{ accountType }}</option>
                </select>
            </div>
            <div v-else>
                This user already has the maximum of bankaccounts possible.
            </div>
        </div>
        <div>
            <button id="btn2" class="btnUpdate" @click="cancel()">Cancel</button>
            <button id="btn2" @click="addBankAccount()">Save Changes</button>
        </div>
    </div>
</template>

  
<script>
import axios from '../../axios-auth.js';

export default {

    data() {
        return {
            users: [],
            selectedUser: {},
            generatedIban: '',
            bankAccounts: [],
            accountTypes: ['CURRENT', 'SAVINGS'],
            selectedAccountType: '',
            newBankAccount:
            {
                balance: 0,
                disabled: false,
                currencies: "EUR",
                accountType: [],
            },
        };
    },
    mounted() {
        this.getUsers();
    },
    methods: {
        cancel() {
            this.$router.push("/employee/question");
        },
        getUsers() {
            axios
                .get('/users', {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.users = res.data;
                })
                .catch(error => console.log(error));
        },
        checkAccountType() {
            axios
                .get('bankaccounts', {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.bankAccount = res.data;
                    let savingsAccountsCount = 5;
                    let currentsAccountsCount = 1;

                    for (const element of this.bankAccount) {
                        if (element.userId == this.selectedUser.id) {

                            if (element.accountType == 'CURRENT') {
                                currentsAccountsCount--;
                            }
                            else if (element.accountType == 'SAVINGS') {
                                savingsAccountsCount--;
                            }
                        }
                    }

                    if (savingsAccountsCount <= 0 && currentsAccountsCount <= 0) {
                        this.accountTypes = null
                    }
                    else if (savingsAccountsCount <= 0) {
                        this.accountTypes = ['CURRENT']
                    }
                    else if (currentsAccountsCount <= 0) {
                        this.accountTypes = ['SAVINGS']
                    }

                }).catch((error) => console.log(error));
        },
        addBankAccount() {
            this.newBankAccount.accountType = [];
            this.newBankAccount.accountType.push(this.selectedAccountType);
            this.newBankAccount.userId = this.selectedUser.id;

            axios
                .post('bankaccounts', this.newBankAccount, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.$router.push("/allAccounts");
                })
                .catch((error) => console.log(error));

        },

    },
};
</script>
  
<style>
@import '../../assets/css/addAccount.css';
</style>
  