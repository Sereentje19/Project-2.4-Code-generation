<template>
    <headerNavigation />

    <body class="bodyStructure">
        <div class="structure">
            <div class="headInfo">
                <div class="accountNumber">
                    <p>rekeningnummer....</p>
                </div>
                <div class="groupOptions">
                    <div class="option"></div>
                    <div class="option"></div>
                    <div class="option">
                        <button class="btn">
                            delete
                        </button>
                    </div>
                </div>
            </div>
            <div id="extraPadding">
                <div class="bodyInfo">
                    <div class="bodyInfoText"> transaction number: {{ this.transactions.id }}</div>
                    <div class="bodyInfoText"> amount: {{ this.transactions.amount }}</div>
                    <div class="bodyInfoText"> description: {{ this.transactions.description }}</div>
                    <div class="bodyInfoText"> type: {{ this.transactions.type }}</div>
                    <div class="bodyInfoText"> bankAccountFrom: {{ this.transactions.bankAccountFrom }}</div>
                    <div class="bodyInfoText"> bankAccountTo: {{ this.transactions.bankAccountTo }}</div>
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
            transactions:
            {
                id: 0,
                amount: '',
                description: '',
                type: '',
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
                .get('transactions/' + this.id, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.transactions = res.data;

                    console.log(res.data)
                    console.log(this.transactions.id)
                })
                .catch(error => console.log(error))

        },
    },
};
</script>

<style>
@import '../../assets/css/transaction.css';
</style>
