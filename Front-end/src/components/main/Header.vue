<template>
  <div id="header">
    <!-- <a href="/"><img id="logo" src="../../assets/images/logo.png" alt="Image is not shown"></a> -->

    <div class="navbar">
      <a @click="logout()" class="grey btn" href="/">logout</a>
      <a class="grey btn" href="/">Login</a>

    </div>
  </div>
  <br><br><br><br><br><br>
</template>

<script>
export default {
  name: "header",

  methods: {
  logout(){
    
  }
},
};


</script>
<style>
@import '../../assets/css/header.css';

.grey{
  color: black;
  background-color: rgb(136, 136, 136);
  margin-left: 20px;
}
</style>
