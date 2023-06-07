<template>
    <div class="container">
        <h2>Personal Details</h2>
        <div>
            <label>First Name:</label>
            <input type="text" v-model="user.firstName" />
        </div>
        <div>
            <label>Last Name:</label>
            <input type="text" v-model="user.lastName" />
        </div>
        <div>
            <label>Phone Number:</label>
            <input type="text" v-model="user.phoneNumber" />
        </div>
        <div>
            <label>Email Address:</label>
            <input type="text" v-model="user.email" />
        </div>
        <div>
            <label>Postal Code:</label>
            <input type="text" v-model="user.postalCode" />
        </div>
        <div>
            <label>City:</label>
            <input type="text" v-model="user.city" />
        </div>
        <div>
            <label>Street:</label>
            <input type="text" v-model="user.street" />
        </div>
        <div>
            <label>House Number:</label>
            <input type="text" v-model="user.houseNumber" />
        </div>
        <div>
            <label>Account type:</label>
            <select v-model="this.selectedAccountType">
                <option v-for="accountType in this.accountTypes" :value="accountType">{{ accountType }}</option>
            </select>
        </div>
        <div>
            <label>Password:</label>
            <input type="text" v-model="this.generatedPassword" />
        </div>
        <div>
            <label>Pincode:</label>
            <input type="text" v-model="this.generatedPincode" />
        </div>
        <div>
            <button id="btn2" class="btnUpdate" @click="cancel()">Cancel</button>
            <button id="btn2" @click="addUser()">Save Changes</button>
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
            user: {},
            generatedPassword: '',
            generatedPincode: 0,
            generatedIban: '',
            ibanExists: false,
            bankAccount: [],
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
        this.generatePassword();
        this.generatePincode();
        this.generateIBAN();
    },
    methods: {
        generatePassword() {
            const length = 10;
            const charset = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*';
            let password = '';
            for (let i = 0; i < length; i++) {
                const randomIndex = Math.floor(Math.random() * charset.length);
                password += charset[randomIndex];
            }
            this.generatedPassword = password;
        },
        generatePincode() {
            const length = 4;
            let pincode = '';
            for (let i = 0; i < length; i++) {
                const digit = Math.floor(Math.random() * 10);
                pincode += digit;
            }
            this.generatedPincode = pincode;
        },
        generateIBAN() {
            const countryCode = 'NL';
            const additionalDigits = Math.floor(Math.random() * 100).toString().padStart(2, '0');
            const bankCode = 'INHO';
            const accountNumber = Math.floor(Math.random() * 10000000000).toString().padStart(10, '0');

            this.generatedIban = `${countryCode}${additionalDigits}${bankCode}0${accountNumber}`;
            this.checkIbanExists();
        },
        cancel() {
            this.$router.push("/employee/question");
        },
        checkIbanExists() {
            axios
                .get('/bankaccounts', headerToken)
                .then((res) => {
                    this.bankAccount = res.data;

                    for (const element of this.bankAccount) {
                        if (element.iban == this.generatedIban) {
                            this.generateIBAN();
                        }
                    }
                    
                }).catch((error) => console.log(error));
        },
        addUser() {
            this.user.iban = this.generatedIban;
            this.user.password = this.generatedPassword;
            this.user.pincode = this.generatedPincode;
            this.user.username = this.user.firstName;

            axios
                .post('users', this.user, headerToken)
                .then((res) => {
                    this.addBankAccount(res.data.id);
                    // this.$router.push("/");
                })
                .catch((error) => console.log(error));
        },
        addBankAccount(userId) {
            this.newBankAccount.iban = this.generatedIban;
            this.newBankAccount.userId = userId;
            this.newBankAccount.accountType.push(this.selectedAccountType);

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
  