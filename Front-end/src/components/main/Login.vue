<template>
    <div id="logIn" width="100%">
        <div class="containerLogin">
            <div class="divBtns">
                <h2 class="">Sign in</h2>
            </div><br>
            <label><b>Username</b></label>
            <input v-model="username" class="input" type="text" placeholder="Enter username" required><br>

            <label><b>Password</b></label>
            <input v-model="password" class="input" type="password" placeholder="Enter password" required>

            <div class="divBtn">
                <button @click="login()" class="loginBtn btn" id="" type="button">Sign in</button>
            </div>
            <div id="register">
                <a href="/AddUser">Register</a>
            </div>
        </div>
    </div>
</template>

<script>
import axios from '../../axios-auth.js';

export default {
    data() {
        return {
            user:
            {
                id: 0,
                username: '',
                password: '',
                employeeRole: [],
                roles: []
            },
        };
    },
    mounted() {
        delete axios.defaults.headers.common['Authorization'];
        localStorage.removeItem("jwt");
    },
    methods: {
        login() {
            axios.post("login", {
                username: this.username,
                password: this.password,
            }).then((res) => {
                axios.defaults.headers.common['Authorization'] = "Bearer " + res.data.token;
                localStorage.setItem("jwt", res.data.token);
                console.log(res.data.token);

                this.getUserRoles(res.data.token);
            }).catch((error) => {
                alert(error.response.data);
            });
        },
        getUserRoles(token) {
            axios
                .get('users/role', {
                    headers: {
                        Authorization: "Bearer " + token
                    }
                })
                .then((res) => {
                    this.user = res.data;

                    if (this.user.roles[0] == "EMPLOYEE") {
                        this.$router.push(`/question/` + this.user.id);
                    }
                    else {
                        this.$router.push(`/customerAccountOverview`);
                    }
                }).catch((error) => {
                    alert(error.response.data);
                });
        },
    }
}
</script>

<style>
@import '../../assets/css/login.css';
</style>
