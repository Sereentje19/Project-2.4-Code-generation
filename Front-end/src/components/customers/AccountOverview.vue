<template>
  <div>
    <headerNavigation />

    <div class="container">
      <div class="user-info-button">
        <h2>{{ user.firstName }} {{ user.lastName }}</h2>
        <button @click="goToUserInfo">User Info</button>
      </div>

      <div v-if="user.bankAccountList.length > 0">
        <h2>IBAN: {{ user.bankAccountList[0].iban }}</h2>

        <h3>Bank Accounts</h3>
        <div v-for="account in user.bankAccountList" :key="account.id" @click="goToTransactions(account)">
          <div v-for="accType in account.accountType">
            <span class="wide-field">{{ accType }} Є{{ account.amount }}</span>
          </div>
        </div>
        <h3>Total: Є{{ totalAmount }}</h3>
      </div>

      <div v-else>
        <p>You have no accounts.</p>
      </div>
    </div>

    <footerNavigation />
  </div>
</template>


<style>
.user-info-button {
  display: flex;
  justify-content: flex-end;
}

.user-info-button button {
  margin-left: auto;
}
</style>

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
        bankAccountList: []
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
      this.bankacc = this.user.bankAccountList[1];
      console.log(this.bankacc);
    },
    goToUserInfo() {
      this.$router.push(`/accountInfo`);
    },
    goToTransactions(account) {

      const username = this.user.username;
      this.$router.push("/transactions/" + btoa(account.id));
    },
  },
};
</script>

<style>
@import '../../assets/css/accountOverview.css';
</style>