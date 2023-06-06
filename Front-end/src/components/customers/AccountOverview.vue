<template>
  <div>
    <headerNavigation />

    <div class="container">
      <h2>{{ user.firstName }} {{ user.lastName }}</h2>
      <button @click="goToUserInfo">User Info</button>
      <div>
        <!-- <h2>IBAN: {{ user.bankAccountList[1].iban }}</h2> -->
        
      </div>
      <h3>Total: Є{{ totalAmount }}</h3>
      <h3>Bank Accounts</h3>
      <div v-for="account in user.bankAccountList" :key="account.id" @click="goToTransactions(account)">
        <span class="wide-field">{{ account.accountType }} Є{{ account.amount }}</span>
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
  computed: {
    totalAmount() {
      return this.user.bankAccountList.reduce((total, account) => total + account.amount, 0);
    }
  },
  mounted() {
    console.log(this.user.bankAccountList);
    this.getAll();
  },
  methods: {
    getAll() {
      axios
        .get('/users/current', {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("jwt")
          }
        })
        .then((res) => {
          this.user = res.data;
          console.log(this.user.bankAccountList);
          this.getBankAccounts();
        })
        .catch(error => console.log(error))
    },
    getBankAccounts() {
      // Iterate over the bank accounts and fetch their details
      for (let i = 0; i < this.user.bankAccountList.length; i++) {
        const accountId = this.user.bankAccountList[i];
        axios
          .get(`/bankaccounts/` + accountId, {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("jwt")
            }
          })
          .then((res) => {
            this.user.bankAccountList[i] = res.data;
            this.user.bankAccountList[i].amount = res.data.balance;
            this.user.bankAccountList[i].iban = res.data.iban;
            this.user.bankAccountList[i].accountType = res.data.accountType;
          })
          .catch(error => console.log(error));
      }
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
