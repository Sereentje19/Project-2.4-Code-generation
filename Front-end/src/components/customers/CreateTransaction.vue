<template>
    <headerNavigation />

    <body class="bodyStructure">
        <div class="structure">
            <div class="headInfo">
                <div class="accountNumber">
                    <input type="text" class="input" placeholder="rekening van" v-model="transaction.bankAccountFrom">
                </div>
                <div class="accountNumber mr-2">
                    <input type="text" class="input" placeholder="rekening naar" v-model="transaction.bankAccountTo">

                </div>
            </div>
            <div class="body">
                <div class="other">
                    <input type="text" class="input" placeholder="bedrag" v-model="transaction.amount">
                </div>
                <div class="other">
                    <input type="text" class="input" placeholder="description" v-model="transaction.description">
                </div>
                <div class="other">
                    <input type="text" class="input" placeholder="betalingskenmerk" v-model="transaction.betalingskenmerk">
                </div>
            </div>
            <button @click="showPincode()">Create transaction</button>

        </div>
    </body>
    <footerNavigation />

    <Transition name="modal">
        <div class="modal-mask" id="test">
            <div class="modal-wrapper">
                <div class="modal-container">
                    <div class="modal-header">
                        <slot name="header">Please enter your pincode</slot>
                    </div>

                    <div class="modal-body">
                        <input type="text" class="input" v-model="pincode">
                    </div>

                    <div class="modal-footer">
                        <slot name="footer">
                            <button class="modal-default-button" @click="closePincode()">close</button>
                            <button class="modal-default-button" @click="checkPincode()">OK</button>
                        </slot>
                    </div>
                </div>
            </div>
        </div>
    </Transition>
</template>
  
<style>
.input {
    max-width: 100%;
    border: 1px solid black !important;
}

.modal-mask {
    display: none;
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    transition: opacity 0.3s ease;
}

.modal-wrapper {
    display: table-cell;
    vertical-align: middle;
}

.modal-container {
    width: 300px;
    margin: 0px auto;
    padding: 20px 30px;
    background-color: #fff;
    border-radius: 2px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
    transition: all 0.3s ease;
}

.modal-header h3 {
    margin-top: 0;
    color: #42b983;
}

.modal-body {
    margin: 20px 0;
}

.modal-default-button {
    float: right;
}

/*
   * The following styles are auto-applied to elements with
   * transition="modal" when their visibility is toggled
   * by Vue.js.
   *
   * You can easily play with the modal transition by editing
   * these styles.
   */

.modal-enter-from {
    opacity: 0;
}

.modal-leave-to {
    opacity: 0;
}

.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
}
</style>

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
            transaction:
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
            pincode: "",
            bankaccount : {
                id: 0,
                iban: "",
                balance: 0,
                userId: 0,
                disabled: false,
                currencies: [],
                accountType:[],
            }
        };
    },
    mounted() {
        this.getAll();
    },
    methods: {
        getAll() {
            // axios
            //     .get('bankaccounts/info/' + this.id, {
            //         headers: {
            //             Authorization: "Bearer " + localStorage.getItem("jwt")
            //         }
            //     })
            //     .then((res) => {
            //         this.bankaccount = res.data;
            //     })
            //     .catch(error => console.log(error))
        },


        showPincode() {
            document.getElementById("test").style.display = "table";
        },
        closePincode() {
            document.getElementById("test").style.display = "none";
        },
        postTransaction(){
          console.log(this.transaction)
          axios
                .post('transactions',this.transaction, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    console.log(res.data)
                    this.$router.push("/customer/transactions/" + this.id);
                })
                .catch((error) => console.log(error));
        },
        checkPincode() {
            axios
                .get('users/pincode/' + this.pincode, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    console.log(res.data)
                    this.postTransaction();
                })
                .catch((error) => console.log(error));

        },
        

    },
};

</script>

<style>
@import '../../assets/css/transaction.css';
</style>
