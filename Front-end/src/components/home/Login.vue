<template>
    <div id="logIn" width="100%">
        <div class="container">
            <div class="divBtns">
                <h2 class="">Sign in</h2>
            </div><br>
            <label ><b>Username</b></label>
            <input v-model="username" class="input" type="text" placeholder="Enter username" required><br>
            
            <label ><b>Password</b></label>
            <input v-model="password" class="input" type="password" placeholder="Enter password" required>

            <div class="divBtn">
                <button @click="login()" class="loginBtn btn" id="" type="button">Sign in</button>
            </div>
        </div>
    </div>5
</template>

<script>
import axios from '../../axios-auth.js';

export default {
    // data() {
    //     return {
    //         user: [
    //             {
    //                 username: '',
    //                 email: '',
    //                 password: '',
    //             }
    //         ]
    //     };
    // },
    methods: {
        login() {
            axios.get("users", {
                // username: this.username,
                // password: this.password,
            }).then((res) => {
                // axios.defaults.headers.common['Authorization'] = "Bearer " + res.data.jwt;
                // localStorage.setItem("jwt", res.data.jwt)
                this.$router.push("/home");
                // console.log(res.data.jwt);
            }).catch((error) => {
                console.log(error);
                alert("Error logging in");
            });
        }
    }
}
</script>

<style>
@import '../../assets/css/login.css';
</style>
