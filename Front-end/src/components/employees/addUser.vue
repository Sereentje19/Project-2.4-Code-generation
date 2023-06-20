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

export default {

    data() {
        return {
            currentUser: localStorage.getItem("role"),
            bankAccount: [],
            accountTypes: ['CURRENT', 'SAVINGS'],
            selectedAccountType: '',
            user: {
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
                roles: ["CUSTOMER"],
                bankAccountList: [],
            },
            newBankAccount: {
                balance: 0,
                disabled: false,
                currencies: "EUR",
                accountType: [],
            },
        };
    },
    methods: {
        cancel() {
            this.$router.go(-1);
        },
        addUser() {
            if (this.currentUser == "CUSTOMER") {
                axios
                    .post('users/register', this.user)
                    .then((res) => {
                        this.$router.go(-1);
                    }).catch((error) => {
                        alert(error.response.data);
                    });
            }
            else {
                axios
                    .post('users', this.user, {
                        headers: {
                            Authorization: "Bearer " + localStorage.getItem("jwt")
                        }
                    })
                    .then((res) => {
                        this.addBankAccount(res.data.id);
                    }).catch((error) => {
                        alert(error.response.data);
                    });
            }
        },
        addBankAccount(userId) {
            this.newBankAccount.userId = userId;
            this.newBankAccount.accountType.push(this.selectedAccountType);
            console.log(this.newBankAccount)

            axios
                .post('bankaccounts', this.newBankAccount, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.$router.push("/allAccounts");
                }).catch((error) => {
                    alert(error.response.data.token);
                });
        },
    },
};
</script>
  
<style>
@import '../../assets/css/addAccount.css';
</style>
  
