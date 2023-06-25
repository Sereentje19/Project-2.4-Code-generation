<template>
    <headerNavigation />

    <body class="bodyStructure">
        <div class="structure">
            <div class="headInfo">
                <div class="accountNumber">
                    <input type="text" class="input" placeholder="rekening van" :value="this.bankaccountdto.iban" id="fromInput">
                </div>
                <div class="accountNumber mr-2">
                    <input  type="text" class="input" placeholder="rekening naar" v-model="ibanTo" list="ibanList" id="bankaccountTo" >
                    <datalist id="ibanList" >
                        <option v-for="nameAndDto in this.nameAndDtoList" :value="nameAndDto.iban">{{ nameAndDto.name }} | {{ nameAndDto.iban }} ({{ nameAndDto.accountType }}) </option>
                    </datalist>

                </div>
            </div>
            <div class="body">
                <div class="other" id="dropdown" style="display: none;">
                    <input  type="text" class="input" placeholder="rekening naar" list="ibanLists" v-model="this.accountID">
                    <datalist id="ibanLists" >
                        <option v-for="bankaccount in this.usersBankList" :value="bankaccount.id"> 
                            <div v-for="accType in bankaccount.accountType">
                            {{ bankaccount.iban }} ({{ accType }}) 
                            </div>
                        </option>
                    </datalist>
                </div>
                <div class="other">
                    <input type="number" min="0" class="input" placeholder="bedrag" v-model="transactionDTO.amount">
                </div>
                <div class="other">
                    <input type="text" class="input" placeholder="description" v-model="transactionDTO.description">
                </div>
                <div class="other">
                    <input type="text" class="input" placeholder="betalingskenmerk" v-model="transactionDTO.paymentReference">
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
import { routerKey } from 'vue-router';

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
            accountID: 0,
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
                bankAccountList: [],
                roles: [],
                dailyLimit: 0,
                transactionLimit: 0,
            },
            ibanTo: "",
            transactionDTO:
            {
                id: 1,//check
                description: '',//check
                amount: 0,//check
                accountIdFrom: 0,//check
                accountIdTo: 0,//check
                paymentReference: '',//check
                date: '',//check
                performedByUser: [],//check
            },
            pincode: "",
            bankaccountdto : {
                accountType: [],
                iban: "",
                id: 0,
                name: "",
            },
            bankaccounttodto : {
                id: 0,
                Name: "",
                Iban: "",
                accountType: [],
            },
            nameAndDtoList: {
                id: 0,
                Name: "",
                Iban: "",
                accountType: [],
            },
            usersBankList : [],
            decodedId: atob(this.id),
        };
    },
    mounted() {
        this.getUser();
        this.getBankAccount();
        this.getNameAndDtoList();
        

        document.getElementById("bankaccountTo").addEventListener("change", () => {
            this.checkAccountIban();
        });
    },
    methods: {
        checkAccountIban(){
            if(this.bankaccountdto.iban == this.ibanTo){
                document.getElementById("dropdown").style.display = "block";
            }else {
                document.getElementById("dropdown").style.display = "none";
            }
        },
        getNameAndDtoList(){
            axios
                .get('bankaccounts/All', {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.nameAndDtoList = res.data;
                })
                .catch(error => console.log(error))
        },
        getUser() {
            axios
                .get('users/currentUser', {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.transactionDTO.performedByUser = res.data;
                    this.user = res.data;
                    this.fillfield();
                })
                .catch(error => console.log(error))
        },
        getBankAccount() {
            axios
                .get('/bankaccounts/dto/' + this.decodedId, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.bankaccountdto = res.data[0];
                    this.transactionDTO.accountIdFrom = this.bankaccountdto.id;
                    this.getBankAccountByUserId(this.user.id);
                    
                })
                .catch(error => console.log(error))
        },
        //done
        showPincode() {
            document.getElementById("test").style.display = "table";
        },
        closePincode() {
            document.getElementById("test").style.display = "none";
        },
        getBankAccountByIban(){
            if(this.ibanTo == " " || this.ibanTo == "" || this.ibanTo == null){
                this.ibanTo = "0";
            }
            axios
                .get('bankaccounts/iban/'+this.ibanTo,{
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    if(res.data == null || res.data == undefined || res.data == ""){
                            alert("this account doesn't exist");
                            location.reload();
                        }
                    this.bankaccounttodto = res.data[0];
                    this.transactionDTO.accountIdTo = this.bankaccounttodto.id;
                    this.postTransaction();
                })
                .catch(error =>{
                    alert(error.response.data);
                })
        },
        getOtherBankAccount(){
            if(this.accountID == 0){
                this.getBankAccountByIban();
            }else{
                axios
                .get('/bankaccounts/dto/' + this.accountID, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    if(res.data == null || res.data == undefined || res.data == ""){
                            alert("this account doesn't exist");
                            location.reload();
                        }
                    this.bankaccounttodto = res.data[0];
                    this.transactionDTO.accountIdTo = this.bankaccounttodto.id;
                    this.postTransaction();
                })
                .catch(error => console.log(error))
            }
        },
        fillfield(){
            let fromInput = document.getElementById("fromInput");
            if(this.user.roles[0] == "CUSTOMER"){
                fromInput.setAttribute( 'readonly', true );
            }
        },
        getBankAccountByUserId(id) {
            axios
                .get('/bankaccounts/userID/' + id, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.usersBankList = res.data;
                })
                .catch(error => console.log(error))
        },
        checkPincode() {
            axios
                .get('users/pincode/' + this.pincode, {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt")
                    }
                })
                .then((res) => {
                    this.getOtherBankAccount();
                })
                .catch((error) => {
                        alert(error.response.data);
                    });
        },
        postTransaction(){
          this.transactionDTO.date = new Date();
          console.log(this.transactionDTO);
          axios
                    .post('transactions',this.transactionDTO, {
                        headers: {
                            Authorization: "Bearer " + localStorage.getItem("jwt")
                        }
                    })
                    .then((res) => {
                        console.log(res.data);
                        this.$router.push("/transactions/" + btoa(this.decodedId));
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
</style>
