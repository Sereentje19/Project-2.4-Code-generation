<template>
  <headerNavigation />

  <div class="container">
    <h2>Personal Details</h2>
    <div>
      <label>First Name:</label>
      <span v-if="!editMode">{{ customer.firstName }}</span>
      <input type="text" v-else v-model="editedUser.firstName" />
    </div>
    <div>
      <label>Last Name:</label>
      <span v-if="!editMode">{{ customer.lastName }}</span>
      <input type="text" v-else v-model="editedUser.lastName" />
    </div>
    <div>
      <label>Phone Number:</label>
      <span v-if="!editMode">{{ customer.phoneNumber }}</span>
      <input type="text" v-else v-model="editedUser.phoneNumber" />
    </div>
    <div>
      <label>Email Address:</label>
      <span v-if="!editMode">{{ customer.email }}</span>
      <input type="text" v-else v-model="editedUser.email" />
    </div>
    <div>
      <label>Postal Code:</label>
      <span v-if="!editMode">{{ customer.postalCode }}</span>
      <input type="text" v-else v-model="editedUser.postalCode" />
    </div>
    <div>
      <label>City:</label>
      <span v-if="!editMode">{{ customer.city }}</span>
      <input type="text" v-else v-model="editedUser.city" />
    </div>
    <div>
      <label>Street:</label>
      <span v-if="!editMode">{{ customer.street }}</span>
      <input type="text" v-else v-model="editedUser.street" />
    </div>
    <div>
      <label>House Number:</label>
      <span v-if="!editMode">{{ customer.houseNumber }}</span>
      <input type="text" v-else v-model="editedUser.houseNumber" />
    </div>
    <div v-if="!editMode">
      <div v-if="customer.bankAccountList.length > 0">
      <label>Account Status:</label>
      <span>{{ customer.bankAccountList[0].disabled ? 'Inactive' : 'Active' }}</span>
      <label>IBAN:</label>
      <span>{{ customer.bankAccountList[0].iban }}</span>
    </div>
    </div>

    <div v-else>
      <label>Account Status:</label>
      <select v-model="editedUser.accountStatus">
        <option value="active">Active</option>
        <option value="inactive">Inactive</option>
      </select>
    </div>
    <div>
      <button v-if="!editMode" @click="editMode = true">Edit Info</button>
      <button id="btnUpdate" v-else @click="cancelEdit">Cancel</button>
      <button v-if="editMode" @click="updateInfo">Save Changes</button>
    </div>
  </div>
  <footerNavigation />
</template>

<style>
#btnUpdate {
  margin-right: 150px;
}
</style>

<script>
import headerNavigation from '../main/Header.vue'
import footerNavigation from '../main/Footer.vue';
import axios from '../../axios-auth.js';

const headerToken = {
    headers: {
        Authorization: "Bearer " + localStorage.getItem("jwt")
    }
};

export default {
  components: {
    headerNavigation,
    footerNavigation
  },
  data() {
    return {
      customer: {
        id: 0,
        username: "",
        password: "",
        firstName: "",
        lastName: "",
        phoneNumber: "",
        email: "",
        street: "",
        houseNumber: "",
        postalCode: "",
        city: "",
        bankAccountList: [],
      },
      editedUser: {},
      editMode: false
    };
  },
  mounted() {
    this.getUser();
  },
  methods: {
    getUser() {
      axios
        .get('/users/current', {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("jwt")
          }
        })
        .then((res) => {
          this.customer = res.data;
          this.editedUser = { ...this.customer };
          this.getBankAccounts();
        })
        .catch(error => console.log(error));
    },
    getBankAccounts() {
      // Iterate over the bank accounts and fetch their details
      for (let i = 0; i < this.customer.bankAccountList.length; i++) {
        const accountId = this.customer.bankAccountList[i];
        axios
          .get(`/bankaccounts/` + accountId, {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("jwt")
            }
          })
          .then((res) => {
            this.customer.bankAccountList[i] = res.data;
            this.customer.bankAccountList[i].amount = res.data.balance;
            this.customer.bankAccountList[i].iban = res.data.iban;
            this.customer.bankAccountList[i].accountType = res.data.accountType;            
          })
          .catch(error => console.log(error));
      }
    },
    updateInfo() {
      console.log(this.editedUser);
      console.log(this.customer);
      console.log(this.customer.id);
      console.log(this.editedUser.id);
      console.log(this.editedUser.accountStatus);
      console.log(this.headerToken);
      axios
        .put('/users/' + this.customer.id, this.editedUser, headerToken)
        .then((response) => {
          console.log('User information updated successfully!');
          this.customer = { ...this.editedUser };
          this.editMode = false;
          console.log(this.response )
        })
        .catch((error) => {
          console.error('Error updating user information:', error);
        });
    },
    cancelEdit() {
      this.editMode = false;
    },
  }
};
</script>

<style>
@import '../../assets/css/accountInfoForEmployee.css';
</style>
