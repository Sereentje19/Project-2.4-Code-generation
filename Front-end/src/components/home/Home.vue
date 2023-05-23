<template>
  <headerNavigation />

  <body>
    <div id="homeText">
      <h1> Welcome to SOT Bank</h1>
      <div>
        Welcome to SOT Bank, your trusted partner for financial success. With personalized service, advanced technology,
        and unwavering security, we're here to help you achieve your goals. Join us and experience the difference today.
      </div>
      <br><a href="/Quizes" id="homeBtn" class="mainButtonReverse btn">Go to Quizes</a>
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