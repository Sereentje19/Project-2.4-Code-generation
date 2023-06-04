<template>
  <div class="container">
    <h2>Personal Details</h2>
    <div>
      <label>First Name:</label>
      <span v-if="!editMode">{{ user.firstName }}</span>
      <input type="text" v-else v-model="editedUser.firstName" />
    </div>
    <div>
      <label>Last Name:</label>
      <span v-if="!editMode">{{ user.lastName }}</span>
      <input type="text" v-else v-model="editedUser.lastName" />
    </div>
    <div>
      <label>Phone Number:</label>
      <span v-if="!editMode">{{ user.phoneNumber }}</span>
      <input type="text" v-else v-model="editedUser.phoneNumber" />
    </div>
    <div>
      <label>Email Address:</label>
      <span v-if="!editMode">{{ user.email }}</span>
      <input type="text" v-else v-model="editedUser.email" />
    </div>
    <div>
      <label>Postal Code:</label>
      <span v-if="!editMode">{{ user.postalCode }}</span>
      <input type="text" v-else v-model="editedUser.postalCode" />
    </div>
    <div>
      <label>City:</label>
      <span v-if="!editMode">{{ user.city }}</span>
      <input type="text" v-else v-model="editedUser.city" />
    </div>
    <div>
      <label>Street:</label>
      <span v-if="!editMode">{{ user.street }}</span>
      <input type="text" v-else v-model="editedUser.street" />
    </div>
    <div>
      <label>House Number:</label>
      <span v-if="!editMode">{{ user.houseNumber }}</span>
      <input type="text" v-else v-model="editedUser.houseNumber" />
    </div>
    <div v-if="!editMode">
      <label>Account Status:</label>
      <span>{{ user.accountStatus }}</span>
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
      <button v-else @click="updateInfo">Save Changes</button>
      <button v-if="editMode" @click="cancelEdit">Cancel</button>
    </div>
  </div>
</template>

<script>
import axios from '../../axios-auth.js';

export default {
  
  data() {
    return {
      user: {},
      editedUser: {},
      editMode: false,
    };
  },
  methods: {
    fetchUser() {
      axios
        .get('/users/login')
        .then((response) => {
          this.user = response.data;
          this.editedUser = { ...this.user };
        })
        .catch((error) => {
          console.error('Error fetching user information:', error);
        });
    },
    updateInfo() {
      axios
        .put('/users/' + this.user.id, this.editedUser)
        .then((response) => {
          console.log('User information updated successfully!');
          this.user = { ...this.editedUser };
          this.editMode = false;
        })
        .catch((error) => {
          console.error('Error updating user information:', error);
        });
    },
    cancelEdit() {
      this.editMode = false;
    },
  },
  mounted() {
    this.fetchUser();
  },
};
</script>

<style>
@import '../../assets/css/accountInfoForEmployee.css';
</style>
