<template>
    <div class="container">
        <h2>All Bank Accounts</h2>
        <input type="text" v-model="searchQuery" placeholder="Search..." />
        <div v-for="account in filteredAccounts" :key="account.id" @click="selectAccount(account)">
            <span>{{ account.accountNumber }}</span>
            <span v-if="selectedAccount && selectedAccount.id === account.id">- Owner: {{ selectedAccount.ownerName
            }}</span>
        </div>
    </div>
</template>

<script>
import headerNavigation from '../main/Header.vue'
import footerNavigation from '../main/Footer.vue';
import axios from '../../axios-auth.js';

export default {
    header: {
        name: "header",
        components: {
            headerNavigation
        }
    },
    footer: {
        name: "footer",
        components: {
            footerNavigation
        },
    },
    data() {
        return {
            accounts: [],
            searchQuery: '',
            selectedAccount: null,
        };
    },
    mounted() {
        this.fetchAccounts();
    },
    computed: {
        filteredAccounts() {
            // Filter accounts based on the search query
            return this.accounts.filter((account) =>
                account.accountNumber.includes(this.searchQuery)
            );
        },
    },
    methods: {
        fetchAccounts() {
            // Make an API call to fetch all bank accounts
            axios
                .get('/bankaccounts') // Use the correct API endpoint
                .then((response) => {
                    // Handle the response and update the accounts data
                    this.accounts = response.data;
                })
                .catch((error) => {
                    console.error('Error fetching accounts:', error);
                    // Handle the error case if necessary
                });
        },
        selectAccount(account) {
            // Set the selected account for displaying owner's name
            this.selectedAccount = { ...account }; // Create a copy of the account object
        },
    },
};
</script>

<style>
@import '../../assets/css/allAccounts.css';
</style>
