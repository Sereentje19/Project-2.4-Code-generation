<template>
  <Transition name="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">
          <div class="modal-header">
            <slot name="header">Please enter your pincode</slot>
          </div>

          <div class="modal-body">
            <input type="text" class="input">
          </div>

          <div class="modal-footer">
            <slot name="footer">
                <button
                class="modal-default-button"
                @click="$emit('close')"
              >close</button>
              <button
                class="modal-default-button"
                @click="$emit('close')"
              >OK</button>
            </slot>
          </div>
        </div>
      </div>
    </div>
  </Transition>
</template>

<style>
.input{
    max-width: 100%;
    border: 1px solid black !important;
}

/* .modal-mask {
    display: none;
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
} */

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
<!-- <template>
    <headerNavigation />

    <body class="bodyStructure">
        <div class="structure small">
            <h1>Vul uw pincode in</h1>

            <input type="passcode" placeholder="pincode invullen" class="input">

            <div class="group">
                <button>cancel</button>
                <button>verstuur</button>
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
    name: "object",
    props: {
        object: Object,
    },
    
    
    data() {
        return {
            transactions: {
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
           this.transactions = localStorage.getItem("transactionData");
           console.log(localStorage.getItem("transactionData"))
        },
    },
};
</script>

<style>
@import '../../assets/css/transaction.css';
</style> -->
