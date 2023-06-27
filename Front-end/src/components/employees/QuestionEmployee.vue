<template>
    <headerNavigation />

    <body>
        <div id="container">
            <h1>How do you want to login?</h1>

            <div id="btns">
                <button id="btn" @click="goToEmployee()">Employee</button>
                <button id="btn" @click="goToCustomer()">Customer</button>
            </div>
        </div>
    </body>
    <footerNavigation />
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
        }
    },
    name: "QuestionEmployee",
    props: {
        id: Number,
    },
    data() {
        return {
            user:{
                employeeRole: [],
            }
        };
    },
    methods: {
        updateUserRole(role) {
            this.user.employeeRole = [];
            this.user.employeeRole.push(role);

            axios
                .put('users/role/' + this.id , this.user, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                }).catch((error) => {
                    alert(error.response.data);
                });
        },
        goToEmployee() {
            this.updateUserRole("EMPLOYEE");
            this.$router.push(`/allAccounts`);
        },
        goToCustomer() {
            this.updateUserRole("CUSTOMER");
            this.$router.push(`/customerAccountOverview`);
        },
    },
    
};
</script>
  
<style>
@import '../../assets/css/questionEmployee.css';
</style>