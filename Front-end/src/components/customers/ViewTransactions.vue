<template>
    <headerNavigation />

    <body class="bodyStructure">
        <div class="structure">
            <div class="headInfo">
                <div class="accountNumber">
                    <p><b>{{this.iban}}</b></p>
                </div>
                <div class="groupOptions">
                    <div class="option"></div>
                    <div class="option"></div>
                    <div class="option">
                        <button class="btn">
                            Delete
                        </button>
                    </div>
                </div>
            </div>
            <div id="extraPadding">
                <div class="bodyInfo">
                    <div class="bodyInfoText"> <b>Transaction number:</b> {{ this.transaction.id }}</div>
                    <div class="bodyInfoText"> <b>Amount:</b> {{ this.transaction.amount }}</div>
                    <div class="bodyInfoText"> <b>Description:</b> {{ this.transaction.description }}</div>
                    <div class="bodyInfoText"> <b>Bank account from:</b> {{ this.transaction.bankAccountFrom }}</div>
                    <div v-for="accFrom in this.transaction.accountTypeFrom" class="bodyInfoText"> <b>Account type from:</b> {{ accFrom }} </div>
                    <div class="bodyInfoText"> <b>Bank account to:</b> {{ this.transaction.bankAccountTo }}</div>
                    <div v-for="accTo in this.transaction.accountTypeTo" class="bodyInfoText"> <b>Account type to:</b> {{ accTo }}</div>
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
        iban: String,
    },
    data() {
        return {
            transaction:
            {
                id: 0,
                amount: '',
                description: '',
                accountTypeFrom: [],
                accountTypeTo: [],
                bankAccountFrom: '',
                bankAccountTo: ''
            }
        };
    },
    mounted() {
        this.getAll();
    },
    methods: {
        getAll() {
            axios
                .get('transactions/info/' + this.id, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.transaction = res.data;

                    console.log(res.data)
                    console.log(this.transaction.id)
                })
                .catch(error => console.log(error))

        },
    },
};
</script>

<style>
@import '../../assets/css/transaction.css';
</style>
