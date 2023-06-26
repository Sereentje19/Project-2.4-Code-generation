<template>
    <headerNavigation />

    <body class="bodyStructure">
        <div class="structure">
            <div class="headInfo">
                <div class="accountNumber">
                    <input type="text" class="input" placeholder="van rekeningnummer...." :value=this.bankaccountdto.iban
                        id="fromInput">
                </div>
            </div>
            <div class="body">
                <div class="other">
                    <input type="number" class="input" placeholder="bedrag" v-model="this.WithdrawOrDeposit.bedrag">
                </div>
                <div class="other">
                    <input type="text" class="input" placeholder="description" v-model="this.WithdrawOrDeposit.omscrijving">
                </div>
                <div class="other">
                    <select name="choice" v-model="this.WithdrawOrDeposit.choise">
                        <option value="withdraw">withdraw</option>
                        <option value="deposit">deposit</option>
                    </select>
                </div>
            </div>
            <button @click="showPincode()">Submit</button>
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
                        <input type="password" class="input" v-model="pincode">
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
    name: "withdrawOrDeposit",
    props: {
        id: Number,
    },
    data() {
        return {
            WithdrawOrDeposit:{
                id:0,
                bankAccountId: 0,
                bedrag: 0,
                omscrijving: "",
                choise: "",
                performedByUser: [],
            },
            bankaccountdto : {
                accountType: [],
                iban: "",
                id: 0,
                name: "",
            },
            pincode: "",
        };
    },
    mounted() {
        this.getUser();
        this.getBankAccount();

    },
    methods: {
        fillfield() {
            let fromInput = document.getElementById("fromInput");
            if (this.WithdrawOrDeposit.performedByUser.roles[0] == "CUSTOMER") {
                fromInput.setAttribute('readonly', true);
            }
        },
        getUser() {
            axios
                .get('users/current', {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.WithdrawOrDeposit.performedByUser = res.data;
                    this.fillfield();
                })
                .catch(error => console.log(error))
        },
        getBankAccount() {
            const decodedId = atob(this.id)
            axios
                .get('/bankaccounts/dto/' + decodedId, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.bankaccountdto = res.data[0];
                    this.WithdrawOrDeposit.bankAccountId = this.bankaccountdto.id
                    
                })
                .catch(error => console.log(error))
        },
        showPincode() {
            document.getElementById("test").style.display = "table";
        },
        closePincode() {
            document.getElementById("test").style.display = "none";
        },
        checkPincode() {
            axios
                .get('users/pincode/' + this.pincode, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.postWithdrawOrDeposit(this.WithdrawOrDeposit);
                })
                .catch((error) => {
                        alert(error.response.data);
                    });
        },
        postWithdrawOrDeposit(WithdrawOrDeposit){
            axios
                    .post('transactions/withdrawOrDeposit',WithdrawOrDeposit, {
                        headers: {
                            Authorization: "Bearer " + localStorage.getItem("jwt")
                        }
                    })
                    .then((res) => {
                        this.$router.push("/transactions/" + this.id);
                    })
                    .catch((error) => {
                        alert(error.response.data);
                    });
        },
    },
};
</script>

<style>
@import '../../assets/css/transaction.css';

.structure {
    max-width: 90%;
}
</style>
