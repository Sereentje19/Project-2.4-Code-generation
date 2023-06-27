<template>
    <headerNavigation />

    <div class="container">
        <h2>All Users</h2>
        <div id="inputAndBtn">
            <input id="inputfield" type="text" v-model="searchQuery" placeholder="Search" @input="searchUsers" />
            <button id="buttonAdd" class="add-user-button" @click="goToAddUser">Add Bankaccount</button>
        </div>

        <div v-if="!showUsersWithNoAccounts">
            <button class="filter-button" @click="filterUsersWithNoAccounts">show users without Accounts</button>
        </div>

        <div v-else>
            <button class="filter-button" @click="resetFilter">Show all users</button>
        </div>

        <div v-for="user in filteredUsers" :key="user.id" @click="selectUser(user)">
            <span>{{ user.firstName }} {{ user.lastName }}</span>
        </div>
    </div>

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
            users: [
                {
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
                }
            ],
            selectedUser: null,
            searchQuery: '',
            showUsersWithNoAccounts: false
        };
    },
    mounted() {
        this.getUsers();
    },
    computed: {
        filteredUsers() {
            return this.users.filter(user => !user.inActive);
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
        searchUsers() {
            axios
                .get('/users/search', {
                    params: {
                        query: this.searchQuery
                    },
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('jwt')
                    }
                })
                .then((res) => {
                    this.users = res.data;
                })
                .catch((error) => console.log(error));
        },
        filterUsersWithNoAccounts() {
            axios
                .get('/users/without-accounts', {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('jwt')
                    }
                })
                .then((res) => {
                    this.users = res.data;
                    this.showUsersWithNoAccounts = true;
                })
                .catch((error) => console.log(error));
        },
        goToAddUser() {
            this.$router.push('/employee/question');
        },
        selectUser(user) {
            this.$router.push(`/employee/accounts/` + btoa(user.id));
        },
        resetFilter() {
            this.getUsers();
            this.showUsersWithNoAccounts = false;
        }
    }
};
</script>


  
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

#inputfield {
    height: 40px;
}


#buttonFilter {
    height: 40px;
    margin-left: 10px;
    margin-bottom: 10px;
}

.listField {
    height: 40px;
    margin-bottom: 1px;
    margin-top: 2px;
    /* Add margin-top for spacing */
}
</style>
