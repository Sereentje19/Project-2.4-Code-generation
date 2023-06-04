<template>
    <div class="container">
        <h2>Personal Details</h2>
        <div>
            <label>Iban number:</label>
            <input type="text" v-model="this.iban" />
        </div>
        <div>
            <label>Account type:</label>
            <select v-model="this.accountType">
                <option value="Current">Current</option>
                <option value="Savings">Savings</option>
            </select>
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
            iban: '',
            accountType: '',
        };
    },
    mounted() {

    },
    methods: {
        cancel() {
            this.$router.push("/employee/question");
        },

    },
};
</script>
  
<style>
@import '../../assets/css/addAccount.css';
</style>
  