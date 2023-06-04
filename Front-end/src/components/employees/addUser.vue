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
            <label>Password:</label>
            <input type="text" v-model="this.password" />
        </div>
        <div>
            <label>Pincode:</label>
            <input type="text" v-model="this.pincode" />
        </div>
        <div>
            <button id="btn2" class="btnUpdate" @click="cancel()">Cancel</button>
            <button id="btn2" @click="updateInfo()">Save Changes</button>
        </div>
    </div>
</template>
  
<script>
import axios from '../../axios-auth.js';

export default {

    data() {
        return {
            user: {},
            password: '',
            pincode: 0
        };
    },
    mounted() {
        this.generatePassword();
        this.generatePincode();
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
            this.password = password;
        },
        generatePincode() {
            const length = 4;
            let pincode = '';
            for (let i = 0; i < length; i++) {
                const digit = Math.floor(Math.random() * 10); // Generate a random number between 0 and 9
                pincode += digit;
            }
            this.pincode = pincode;
        },
        cancel() {
            this.$router.push("/employee/question");
        },
    },
};
</script>
  
<style>
@import '../../assets/css/addAccount.css';
</style>
  