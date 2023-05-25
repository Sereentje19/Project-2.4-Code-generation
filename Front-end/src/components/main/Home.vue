<template>
  <headerNavigation />

  <body>
    <div id="homeText">
      <h1> Welcome to SOT Bank</h1>
      <div>
        text text text text text text text text text text text text 
        text text text text text text text text text text text text 
        text text text text text text text text text text text text text text 
      </div>
      <br><a href="/customer/transactions/1" class="btn">transactions</a>
    </div>

  </body>
  <footerNavigation />
</template>

<script>
import headerNavigation from './Header.vue'
import footerNavigation from './Footer.vue';

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
    }
  }
};
</script>

<style>
@import '../../assets/css/home.css';
</style>