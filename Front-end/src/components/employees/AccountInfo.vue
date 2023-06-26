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
    <div>
      <label>Status:</label>
      <span v-if="!editMode">{{ customer.inActive ? 'Inactive' : 'Active' }}</span>
      <select v-else v-model="editedUser.inActive">
        <option :value="true">Inactive</option>
        <option :value="false">Active</option>
      </select>
    </div>
    <div>
      <button v-if="!editMode" @click="editMode = true">Edit Info</button>
      <button v-if="!editMode" @click="goBack">Back</button>
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
        inActive: false,
        city: ""
      },
      editedUser: {},
      editMode: false
    };
  },
  mounted() {
    const encodedId = this.$route.params.id;
    const decodedId = atob(encodedId);
    this.getUserInfo(decodedId);
  },
  methods: {
    getUserInfo(userId) {
      axios
        .get(`/users/${userId}`, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("jwt")
          }
        })
        .then((res) => {
          this.customer = res.data;
          this.editedUser = { ...this.customer }; // Initialize editedUser with customer data
        })
        .catch((error) => console.log(error));
    },
    updateInfo() {
      axios
        .put(`/users/${this.customer.id}`, this.editedUser, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("jwt")
          }
        })
        .then((response) => {
          console.log('User information updated successfully!');
          this.customer = { ...this.editedUser }; // Update customer with editedUser data
          this.editMode = false;
          console.log(response);
        })
        .catch((error) => {
          alert(error.response.data);
        });
    },
    cancelEdit() {
      this.editMode = false;
      this.editedUser = { ...this.customer }; // Reset editedUser to customer data
    },
    goBack() {
      this.$router.go(-1); // Go back to the previous page
    }
  }
};
</script>

<style>
@import '../../assets/css/accountInfoForEmployee.css';
</style>
