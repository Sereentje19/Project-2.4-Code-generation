<template>
    <div class="container">
        <h2>Personal Details</h2>
        <div>
            <label>Iban number:</label>
            <input type="text" v-model="this.newBankAccount.iban" />
        </div>
        <div>
            <label>Account type:</label>
            <select v-model="this.selectedAccountType">
                <option v-for="accountType in this.accountTypes" :value="accountType">{{ accountType }}</option>
            </select>
        </div>
        <div>
            <button id="btn2" class="btnUpdate" @click="cancel()">Cancel</button>
            <button id="btn2" @click="checkIbanExists()">Save Changes</button>
        </div>
    </div>
</template>

  
<script>
import axios from '../../axios-auth.js';

const headerToken = {
    headers: {
        Authorization: "Bearer " + localStorage.getItem("jwt")
    }
};

export default {

    data() {
        return {
            ibanExists: false,
            bankAccounts: [],
            accountTypes: ['CURRENT', 'SAVINGS'],
            selectedAccountType: '',
            newBankAccount:
            {
                id: 0,
                iban: '',
                balance: 0,
                userId: 0,
                disabled: false,
                currencies: "EUR",
                accountType: [],
            },
        };
    },
    mounted() {

    },
    methods: {
        cancel() {
            this.$router.push("/employee/question");
        },
        checkIbanExists() {
            axios
                .get('/bankaccounts', headerToken)
                .then((res) => {
                    this.bankAccounts = res.data;

                    for (const element of this.bankAccounts) {
                        if (element.iban == this.newBankAccount.iban) {
                            this.ibanExists = true;
                            this.newBankAccount.userId = element.userId;
                            break;
                        }
                        this.ibanExists = false;
                    }

                    if (!this.ibanExists) {
                        alert("IBAN doesn't exists");
                        return;
                    }
                    this.addBankAccount();

                }).catch((error) => console.log(error));
        },
        addBankAccount() {
            this.newBankAccount.accountType = [];
            this.newBankAccount.accountType.push(this.selectedAccountType);
            console.log(this.newBankAccount)

            axios
                .post('bankaccounts', this.newBankAccount, headerToken)
                .then((res) => {

                    console.log(res.data)
                    // this.$router.push("/");
                })
                .catch((error) => console.log(error));
        },

    },
};
</script>
  
<style>
@import '../../assets/css/addAccount.css';
</style>
  