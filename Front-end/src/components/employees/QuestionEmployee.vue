<template>
    <headerNavigation />

    <body>
        <div id="container">
            <h1>How do you want to login?</h1>

            <div id="btns">
                <button id="btn" @click="addBankAccount()">Employee</button>
                <button id="btn" @click="addUser()">Customer</button>
            </div>
        </div>
    </body>
    <footerNavigation />
</template>
  
<script>
import headerNavigation from '../main/Header.vue'
import footerNavigation from '../main/Footer.vue';

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
    methods: {
        addUser() {
            this.$router.push("/AddUser");
        },
        addBankAccount() {
            this.$router.push("/employee/AddBankAccount");
        },
    },
    
};
</script>
  
<style>
@import '../../assets/css/questionEmployee.css';
</style>