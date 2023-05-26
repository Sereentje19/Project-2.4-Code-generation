<template>
    <headerNavigation />

    <body class="bodyStructure">
        <div class="structure">
            <div class="headInfo">
                <div class="accountNumber">
                    <p>rekeningnummer....</p>
                </div>
                <div class="groupOptions">
                    <div class="option">
                        <button class="btn">
                            Transaction
                        </button>
                    </div>
                    <div class="option"></div>

                    <div class="option">
                        <input id="inputField" type="text">
                    </div>
                </div>
            </div>
            <div id="extraPadding">
                <div class="bodyInfo">
                    <a href="/employee/viewTransaction/1">
                        <div v-for="trans in transactions" class="transaction">
                            <h1>blabla ... {{ this.transactions.id }}</h1>
                        </div>
                    </a>
                </div>
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
        },
    },
    name: "transactions",
    props: {
        id: Number,
    },
    data() {
        return {
            transactions: [],
        };
    },
    mounted() {
        this.getAll();
    },
    methods: {
        getAll() {
            axios
            //     .get('users/' + this.id, {
            //         headers: {
            //             Authorization: "Bearer " + localStorage.getItem("jwt")
            //         }
            //     })
            //     .then((res) => {
            //         this.transactions = res.data;

            //         console.log(res.data)
            //         console.log(this.transactions.id)
            //     })
            //     .catch(error => console.log(error))

            // axios
            //     .get('bankaccounts/' + this.id)
            //     .then((res) => {
            //         this.transactions = res.data;

            //         console.log(res.data)
            //         console.log(this.transactions.id)
            //     })
            //     .catch(error => console.log(error))

            axios
                .get('transactions/' + this.id, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.transactions = res.data;

                    // console.log(res.data)
                    // console.log(this.transactions.id)
                })
                .catch(error => console.log(error))

        },
    },
};
</script>

<style>
@import '../../assets/css/transaction.css';
</style>
