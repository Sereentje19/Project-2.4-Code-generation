<template>
    <div class="container">
        <h2>Personal Details</h2>
        <div>
            <label>User:</label>
            <select v-model="this.newBankAccount.userId" @change="getAccountType()">
                <option v-for="user in this.users" :value="user.id">{{ user.firstName + " " + user.lastName }}</option>
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
            accountTypes: [],
            selectedAccountType: "CURRENT",
            newBankAccount:
            {
                userId: 0,
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
                .get('/users/dropdown', {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.users = res.data;
                }).catch((error) => {
                    alert(error.response.data);
                });
        },
        getAccountType() {
            axios
                .get('bankaccounts/accountType/' + this.newBankAccount.userId, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.accountTypes = res.data;
                    this.selectedAccountType = res.data[0];
                }).catch((error) => {
                    alert(error.response.data);
                });
        },
        addBankAccount() {
            this.newBankAccount.accountType = [this.selectedAccountType];

            axios
                .post('bankaccounts', this.newBankAccount, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.$router.push("/allAccounts");
                })
                .catch((error) => {
                    alert(error.response.data);
                });
        },

    },
};
</script>
  
<style>
@import '../../assets/css/addAccount.css';
</style>
  