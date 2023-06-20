<template>
    <div class="containerAddUser">
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
        <div v-if="this.currentUser == 'EMPLOYEE'">
            <label>Account type:</label>
            <select v-model="this.selectedAccountType">
                <option v-for="accountType in this.accountTypes" :value="accountType">{{ accountType }}</option>
            </select>
        </div>
        <div>
            <label>username:</label>
            <input type="text" v-model="user.username" />
        </div>
        <div>
            <label>Password:</label>
            <input type="text" v-model="user.password" />
        </div>
        <div>
            <label>Pincode:</label>
            <input type="text" v-model="user.pincode" />
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
            user: {
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
                pincode: '',
                roles: [],
                bankAccountList: [],
            },
            currentUser: '',
            generatedPassword: '',
            generatedPincode: '',
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
        this.checkUser();
    },
    methods: {
        // checkFieldsNotEmpty() {
        //     if (!this.user.password || this.user.password.length < 8) {
        //         alert("Password has to be at least 8 characters.");
        //         return false;
        //     }
        //     else if (!this.user.username || this.user.username.length < 5) {
        //         alert("Username has to be at least 5 characters.");
        //         return false;
        //     }
        //     else if (!this.user.pincode || !/^\d{4}$/.test(this.user.pincode)) {
        //         alert("Pincode has to be exactly 4 numbers.");
        //         return false;
        //     }
        //     else if (!this.user.email.includes('@')) {
        //         alert("Please enter a valid email.");
        //         return false;
        //     }
        //     else if (!/^\d{10,}$/.test(this.user.phoneNumber)) {
        //         alert("Phonenumber has to be at least 10 numbers");
        //         return false;
        //     }
        //     else if (!this.user.firstName || !this.user.lastName
        //         || !this.user.postalCode || !this.user.city
        //         || !this.user.street || !/^\d+$/.test(this.user.houseNumber)) {
        //         alert("Please fill al fields before savings all changes");
        //         return false;
        //     }
        //     else if (this.currentUser == 'EMPLOYEE' && !this.user.accountType) {
        //         alert("No accounttype is entered");
        //         return false;
        //     }
        //     return true
        // },
        checkUser() {
            if (localStorage.getItem("jwt") !== null) {
                this.currentUser = "EMPLOYEE";
            } else {
                this.currentUser = "CUSTOMER";
            }
        },
        cancel() {
            this.$router.go(-1);
        },
        addUser() {
            this.user.accountType = this.selectedAccountType;
            this.user.roles.push("CUSTOMER");

            if (this.currentUser == "CUSTOMER") {

                console.log(this.user)
                axios
                    .post('users/register', this.user)
                    .then((res) => {
                        this.$router.go(-1);
                    }).catch((error) => console.log(error));
            }
            else {
                axios
                    .post('users', this.user, headerToken)
                    .then((res) => {
                        this.addBankAccount(res.data.id);
                        this.$router.push("/allAccounts");
                    }).catch((error) => console.log(error));
            }
        },
        addBankAccount(userId) {
            this.newBankAccount.userId = userId;
            this.newBankAccount.iban = this.generatedIban;
            this.newBankAccount.accountType.push(this.selectedAccountType);

            axios
                .post('bankaccounts', this.newBankAccount, headerToken)
                .then((res) => {
                    console.log(res.data)
                    this.updateUserBankList(res.data.id, res.data.userId);
                    this.$router.push("/allAccounts");
                }).catch((error) => console.log(error));
        },
        updateUserBankList(id, userId) {
            this.user.bankAccountList.push(id)

            axios
                .put('users/' + userId, this.user, headerToken)
                .then((res) => {
                    console.log(res.data)
                })
                .catch((error) => console.log(error));
        },
    },
};
</script>
  
<style>
@import '../../assets/css/addAccount.css';
</style>
  
