<template>
    <headerNavigation />
  
    <div class="container">
      <h2>All Users</h2>
      <div id="inputAndBtn">
        <input id="inputfield" type="text" v-model="searchQuery" placeholder="Search" />
        <button id="buttonAdd" class="add-user-button" @click="goToAddUser">Add User</button>
      </div>
  
      <div v-if="!showUsersWithNoAccounts">
        <button class="filter-button" @click="filterUsersWithNoAccounts">No Accounts</button>
      </div>
  
      <div v-else>
        <button class="filter-button" @click="resetFilter">Show All</button>
      </div>
  
      <div v-for="account in filteredUsers" :key="account.id" @click="selectUser(account)">
        <span>{{ account.firstName }} {{ account.lastName }}</span>
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
        users: [],
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
        if (this.showUsersWithNoAccounts) {
          return this.users.filter(user => !user.bankAccountList || user.bankAccountList.length === 0);
        }
  
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
        this.$router.push('/AddUser');
      },
      selectUser(user) {
        this.$router.push(`/employee/accounts/` + btoa(user.id));
      },
      filterUsersWithNoAccounts() {
        this.showUsersWithNoAccounts = true;
      },
      resetFilter() {
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

<<<<<<< HEAD
#buttonFilter {
    height: 40px;
    margin-left: 10px;
}
=======
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
>>>>>>> e9e4c7c0bf8e179c2f11f9e88ef20c53cb951af8
</style>
  