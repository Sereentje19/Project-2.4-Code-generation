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
                            Deposit
                        </button>
                    </div>
                    <div class="option">
                        <button class="btn">
                            Withdraw
                        </button>
                    </div>
                    <div class="option">
                        <button class="btn">
                            Transaction
                        </button>
                    </div>
                </div>
            </div>
            <div id="extraPadding">
                <div class="bodyInfo">
                    <!-- Hier komen de transacties -->

                    <div v-for="trans in transactions" class="transaction">
                        <h1>blabla ... {{ trans.id }}</h1>
                    </div>

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
            transactions: [
                {
                    id: 0,
                    description: '',
                    amount: '',
                    type: '',
                    bankAccountFrom: '',
                    bankAccountTo: '',
                }
            ],
        };
    },
    mounted() {
        this.getAll();
    },
    methods: {
        getAll() {
            axios
                .get('transactions/' + this.id, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.transactions = res.data;
                })
                .catch(error => console.log(error))
        },
    },
};
</script>

<style>
@import '../../assets/css/customerTransaction.css';
</style>