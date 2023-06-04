<template>
    <headerNavigation />

    <body class="bodyStructure">
        <div class="structure">
            <div class="headInfo">
                <div class="accountNumber">
                    <input type="text" class="input" placeholder="van rekeningnummer...." v-model="rekening">
                </div>
            </div>
            <div class="body">
                <div class="other">
                    <input type="number" class="input" placeholder="bedrag" v-model="bedrag">
                </div>
                <div class="other">
                    <input type="text" class="input" placeholder="description" v-model="omscrijving">
                </div>
                <div class="other">
                    <select name="choice" v-model="choice">
                        <option value="withdraw">withdraw</option>
                        <option value="deposit">deposit</option>
                    </select>
                </div>
            </div>
            <button @click="showPincode()">Create transaction</button>
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
            rekening: "",
            bedrag: 0,
            omscrijving: "",
            choice: "",
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

.structure{
    max-width: 90%;
}
</style>
