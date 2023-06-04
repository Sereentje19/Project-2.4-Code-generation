<template>
  <div>
    <headerNavigation />

    <div class="container">
      <h2>Personal Info</h2>
      <button @click="goToUserInfo">User Info</button>
      <h3>Bank Accounts</h3>
      <div v-for="account in user.bankAccountList" :key="account.id" @click="goToTransactions(account)">
        <span class="wide-field">{{ account.id }} {{ account.accountType }} {{ account.amount }}</span>
      </div>
    </div>

    <footerNavigation />
  </div>
</template>

<script>
import headerNavigation from '../main/Header.vue'
import footerNavigation from '../main/Footer.vue';
import axios from '../../axios-auth.js';

export default {
  components: {
    headerNavigation,
    footerNavigation
  },
  data() {
    return {
      user: {
        id: 0,
        username: "",
        password: "",
        firstName: "",
        lastName: "",
        phoneNumber: "",
        email: "",
        street: "",
        houseNumber: "",
        postalCode: "",
        city: "",
        bankAccountList: [],
      },
    };
  },
  mounted() {
    this.getAll();
  },
  methods: {
    getAll() {
      axios
        .get('users/login', {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("jwt")
          }
        })
        .then((res) => {
          this.user = res.data;
          console.log(res.data)
          console.log(this.user.id)
        })
        .catch(error => console.log(error))
    },
    goToUserInfo() {
      this.$router.push('/personal-details');
    },
    goToTransactions(account) {
      this.$router.push({ name: 'transactions', params: { accountId: account.id } });
    },
  },
};
</script>

<style>
@import '../../assets/css/accountOverview.css';
</style>
