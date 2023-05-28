<template>
  <div class="container">
    <h2>Personal Info</h2>
    <button @click="goToUserInfo">User Info</button>
    <h3>Bank Accounts</h3>
    <div v-for="account in accounts" :key="account.id" @click="goToTransactions(account)">
      <span class="wide-field">{{ account.accountNumber }}</span>
    </div>
  </div>
</template>

<script>
import axios from '../../axios-auth.js';

export default {
  data() {
    return {
      accounts: [],
    };
  },
  mounted() {
    this.fetchAccounts();
  },
  methods: {
    goToUserInfo() {
      // Redirect to the personal details page (user info)
      this.$router.push('/personal-details');
    },
    goToTransactions(account) {
      // Redirect to the transactions page with the selected account
      this.$router.push({ name: 'transactions', params: { accountId: account.id } });
    },
    fetchAccounts() {
      // Make an API call to fetch the user's bank accounts
      axios
        .get('/api/accounts')
        .then((response) => {
          // Handle the response and update the accounts data
          this.accounts = response.data;
        })
        .catch((error) => {
          console.error('Error fetching accounts:', error);
          // Handle the error case if necessary
        });
    },
  },
};
</script>

<style>
@import '../../assets/css/accountOverview.css';
</style>
