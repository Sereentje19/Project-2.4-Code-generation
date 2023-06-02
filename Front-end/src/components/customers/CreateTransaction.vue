<template>
    <headerNavigation />

    <body class="bodyStructure">
        <div class="structure">
            <div class="headInfo">
                <div class="accountNumber">
                    <input type="text" class="input" placeholder="rekening van" v-model="transactions.bankAccountFrom">
                </div>
                <div class="accountNumber mr-2">
                    <input type="text" class="input" placeholder="rekening naar" v-model="transactions.bankAccountTo">

                </div>
            </div>
            <div class="body">
                <div class="other">
                    <input type="text" class="input" placeholder="bedrag" v-model="transactions.amount">
                </div>
                <div class="other">
                    <input type="text" class="input" placeholder="description" v-model="transactions.description">
                </div>
                <div class="other">
                    <input type="text" class="input" placeholder="betalingskenmerk" v-model="transactions.betalingskenmerk">
                </div>
            </div>
            <button @click="createTransaction()">Create transaction</button>

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
    name: "createtransactions",
    props: {
        id: Number,
    },
    data() {
        return {
            user:
            {
                id: 0,
                username: "",
                password: "",
                fistName: "",
                lastName: "",
                phoneNumber: "",
                email: "",
                street: "",
                houseNumber: "",
                postalCode: "",
                city: "",
                bankAccountList: [
                    // {
                    //     id: 0,
                    //     accountNumber: "BE00 0000 0000 0000",
                    //     saldo: 1000,
                    //     firstName: "test",
                    //     lastName: "test",
                    //     email: "test",
                    // }
                ],
            },
            transactions:
            {
                id: 0,
                description: "",
                amount: 0,
                accountFromtype: "",
                accountTotype: "",
                bankAccountFrom: "",
                bankAccountTo: "",
                betalingskenmerk: "",
            },
        };
    },
    mounted() {
        this.getAll();
    },
    methods: {
        getAll() {
            axios
                .get('users/login', {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.user = res.data;

                    console.log(res.data)
                    console.log(this.user.id)
                })
                .catch(error => console.log(error))
        },


        createTransaction() {
            console.log("test");
            console.log(this.transactions)

            axios
                .post('transactions/post', this.transactions, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    console.log(res.data)
                    this.$refs.form.reset();
                    this.$router.push("/home");
                })
                .catch((error) => console.log(error));
        },


        // axios
        //     .get('bankaccounts/' + this.id)
        //     .then((res) => {
        //         this.transactions = res.data;

        //         console.log(res.data)
        //         console.log(this.transactions.id)
        //     })
        //     .catch(error => console.log(error))


    },
};

</script>

<style>
@import '../../assets/css/transaction.css';
</style>
