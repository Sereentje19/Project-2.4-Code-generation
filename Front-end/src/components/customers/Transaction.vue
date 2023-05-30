<template>
    <headerNavigation />

    <body class="bodyStructure">
        <div class="structure">
            <div class="headInfo">
                <div class="accountNumber">
                    <p>{{ this.user.rekeningnummer }}</p>
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
                    <a href="/customer/viewTransaction/1">
                        <div v-for="u in user" class="transaction">
                            <h1>blabla ... {{ this.user.username }}</h1>
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
            transactions: [
                {
                    // id: '',
                    // description: '',
                    // amount: '',
                    // accountFromtype: '',
                    // accountTotype: '',
                    // bankAccountFrom: '',
                    // bankAccountTo: '',
                }
            ],
            user: [
                // {
                //     id: 0,
                //     username: '',
                //     password: '',
                //     firstName: '',
                //     lastName: '',
                //     phoneNumber: '',
                //     email: '',
                //     street: '',
                //     houseNumber: '',
                //     postalCode: '',
                //     city: '',
                //     bankAccountList: [],

                // }
            ],
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

            // axios
            //     .get('bankaccounts/' + this.user.bankAccountList)
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
