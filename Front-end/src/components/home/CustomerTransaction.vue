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
                        <button>
                            Deposit
                        </button>
                    </div>
                    <div class="option">
                        <button>
                            Withdraw
                        </button>
                    </div>
                    <div class="option">
                        <button>
                            Transaction
                        </button>
                    </div>
                </div>
            </div>
            <div class="bodyInfo">
                <!-- Hier komen de transacties -->

                <div v-for="trans in transactions" class="transaction">
                    <h1>blabla ... {{ trans.id }}</h1>
                </div>

            </div>
        </div>
    </body>
    <footerNavigation />
</template>

<script>
import headerNavigation from './Header.vue'
import footerNavigation from './Footer.vue';

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
        }
    },
    name: "customerTransactions",
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