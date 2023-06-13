<template>
  <div>
    <headerNavigation />

    <div class="container">
<<<<<<< Updated upstream
      <h2>Personal Info</h2>
      <button @click="goToUserInfo">User Info</button>
      <h3>Bank Accounts</h3>
      <div v-for="account in user.bankAccountList" :key="account.id" @click="goToTransactions(account)">
        <span class="wide-field">{{ account.id }} {{ account.accountType }} {{ account.amount }}</span>
=======
      <div class="user-info-button">
        <h2>{{ user.firstName }} {{ user.lastName }}</h2>
        <button @click="goToUserInfo">User Info</button>
      </div>

      <div v-if="user.bankAccountList.length > 0">
        <h2>IBAN: {{ user.bankAccountList[0].iban }}</h2>


        <h3>Bank Accounts</h3>
        <div v-for="account in this.user.bankAccountList" :key="account.id" @click="goToTransactions(account)">
          <div v-for="accType in account.accountType">
            <span class="wide-field">{{ accType }} Є{{ account.amount }}</span>
          </div>
        </div>
        <h3>Total: Є{{ totalAmount }}</h3>
      </div>

      <div v-else>
        <p>You have no accounts.</p>
>>>>>>> Stashed changes
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
        .get('users/current', {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("jwt")
          }
        })
        .then((res) => {
          console.log(res.data);
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
