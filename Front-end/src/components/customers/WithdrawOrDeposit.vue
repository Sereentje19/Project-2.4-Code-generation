<template>
    <headerNavigation />

    <body class="bodyStructure">
        <div class="structure">
            <div class="headInfo">
                <div class="accountNumber">
                    <p>van rekeningnummer....</p>
                </div>
            </div>
            <div class="body">
                <div class="other">
                    <p>bedrag</p>
                </div>
                <div class="other">
                    mededeling
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
        iban: String,
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
