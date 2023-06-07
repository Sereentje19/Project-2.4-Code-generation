<template>
    <headerNavigation />

    <div class="container">
        <h2>All Users</h2>
        <input type="text" v-model="searchQuery" placeholder="Search..." />
        <!-- <div v-for="user in user.bankAccountList" :key="user.id"> -->
            <div v-for="account in filteredUsers" :key="account.id" @click="selectUser(account)">
                <span>{{ account.firstName }} {{ account.lastName }} - IBAN: {{ account.bankAccountList }}</span>
                <span v-if="selectedUser && selectedUser.id === account.id">- Email: {{ selectedUser.email }}</span>
            </div>
        </div>
    <!-- </div> -->
    <footerNavigation />
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
            users: [],
            selectedUser: null,
            searchQuery: ''
        };
    },
    mounted() {
        this.getUsers();
    },
    computed: {
        filteredUsers() {
            if (this.searchQuery) {
                const query = this.searchQuery.toLowerCase();
                return this.users.filter(user => {
                    const fullName = `${user.firstName} ${user.lastName}`.toLowerCase();
                    return fullName.includes(query);
                });
            }
            return this.users;
        }
    },
    methods: {
        getUsers() {
            axios
                .get('/users', {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.users = res.data;
                    this.getBankAccounts();
                })
                .catch(error => console.log(error));
        },
        getBankAccounts() {
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
                    })
                    .catch(error => console.log(error));
            }
        },
        selectUser(user) {
            this.selectedUser = user;
        }
    }
};
</script>
  
<style>
@import '../../assets/css/allAccounts.css';
</style>
  