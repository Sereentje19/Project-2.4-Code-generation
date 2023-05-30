<template>
  <div class="container">
    <h2>Personal Details</h2>
    <div>
      <label>First Name:</label>
      <span>{{ customer.firstName }}</span>
    </div>
    <div>
      <label>Last Name:</label>
      <span>{{ customer.lastName }}</span>
    </div>
    <div>
      <label>Phone Number:</label>
      <span>{{ customer.phoneNumber }}</span>
    </div>
    <div>
      <label>Email Address:</label>
      <span>{{ customer.emailAddress }}</span>
    </div>
    <div>
      <label>Postal Code:</label>
      <span>{{ customer.postalCode }}</span>
    </div>
    <div>
      <label>City:</label>
      <span>{{ customer.city }}</span>
    </div>
    <div>
      <label>Street:</label>
      <span>{{ customer.street }}</span>
    </div>
    <div>
      <label>Street Number:</label>
      <span>{{ customer.streetNumber }}</span>
    </div>
    <div v-if="!editMode">
      <label>Account Status:</label>
      <span>{{ customer.accountStatus }}</span>
    </div>
    <div v-else>
      <label>Account Status:</label>
      <select v-model="editedCustomer.accountStatus">
        <option value="active">Active</option>
        <option value="inactive">Inactive</option>
      </select>
      <br />
      <label>First Name:</label>
      <input type="text" v-model="editedCustomer.firstName" />
      <br />
      <label>Last Name:</label>
      <input type="text" v-model="editedCustomer.lastName" />
      <br />
      <label>Phone Number:</label>
      <input type="text" v-model="editedCustomer.phoneNumber" />
      <br />
      <label>Email Address:</label>
      <input type="text" v-model="editedCustomer.emailAddress" />
      <br />
      <label>Postal Code:</label>
      <input type="text" v-model="editedCustomer.postalCode" />
      <br />
      <label>City:</label>
      <input type="text" v-model="editedCustomer.city" />
      <br />
      <label>Street:</label>
      <input type="text" v-model="editedCustomer.street" />
      <br />
      <label>Street Number:</label>
      <input type="text" v-model="editedCustomer.streetNumber" />
      <br />
    </div>
    <button v-if="!editMode" @click="editMode = true">Edit Info</button>
    <div v-else>
      <button @click="updateInfo">Change</button>
      <button @click="cancelEdit">Cancel</button>
    </div>
  </div>
</template>
  
<script>
import axios from '../../axios-auth.js';

export default {
  data() {
    return {
      customer: {
        firstName: 'John',
        lastName: 'Doe',
        phoneNumber: '1234567890',
        emailAddress: 'johndoe@example.com',
        postalCode: '12345',
        city: 'City',
        street: 'Street',
        streetNumber: '123',
        accountStatus: 'active', // Default account status
      },
      editedCustomer: {},
      editMode: false,
    };
  },
  methods: {
    updateInfo() {
      // Make a PUT request to update the customer's information
      axios
        .put('/api/customer', this.editedCustomer)
        .then((response) => {
          // Handle the response after updating the information
          console.log('Customer information updated successfully!');
          // Update the original customer object with the edited values
          this.customer = { ...this.editedCustomer };
          this.editMode = false; // Exit edit mode
        })
        .catch((error) => {
          console.error('Error updating customer information:', error);
          // Handle the error case if necessary
        });
    },
    cancelEdit() {
      this.editMode = false; // Cancel the editing and exit edit mode
    },
  },
};
</script>
  
<style>
@import '../../assets/css/accountInfoForEmployee.css';
</style>
  