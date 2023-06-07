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

};


</script>
<style>
@import '../../assets/css/header.css';

</style>
