<template>
    <headerNavigation />

    <div class="container">
        <h2>All Users</h2>
        <div id="inputAndBtn">
            <input id="inputfield" type="text" v-model="searchQuery" placeholder="Search" />
            <button id="buttonAdd" class="add-user-button" @click="goToAddUser">Add User</button>
        </div>

        <!-- <div v-for="user in user.bankAccountList" :key="user.id"> -->
        <div v-for="account in filteredUsers" :key="account.id" @click="selectUser(account)">
            <span>{{ account.firstName }} {{ account.lastName }}</span>
        </div>
    </div>
    <!-- </div> -->
    <footerNavigation />
</template>

<style>
#inputAndBtn {
    display: flex;
    justify-content: space-between;
    color: none;
}

#buttonAdd {
    height: 40px;
    margin-bottom: 10px;
}

#inputfield{
    height: 40px;
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
                })
                .catch(error => console.log(error));
        },
        goToAddUser() {
            this.$router.push('/employee/question');
        },
        selectUser(user) {
            this.$router.push(`/employee/accounts/` + btoa(user.id));
        }
    }
};
</script>
  
<style>
@import '../../assets/css/allAccounts.css';
</style>
  