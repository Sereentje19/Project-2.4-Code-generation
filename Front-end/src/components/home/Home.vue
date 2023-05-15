<template>
  <headerNavigation />

  <body>
    <div id="homeText">
      <!-- <a href="/transactions/"> -->
        <div class="d-flex p-2 mb-3 bg-primary">
          <div>
            <h4>{ rekening nummer }</h4>
            <p>{ rekening type}</p>
          </div>
          <h4 class="align-baseline">{ saldo }</h4>

        </div>
      <!-- </a> -->
      
      <div>

        <div class="d-flex p-2 mb-1 bg-secondary">
          <div>
            <h4>{ rekening nummer }</h4>
            <p>{ rekening type}</p>
          </div>
          <h4 class="align-baseline">{ saldo }</h4>
        </div>

        <div class="d-flex p-2 bg-secondary">
          <div>
            <h4>{ rekening nummer }</h4>
            <p>{ rekening type}</p>
          </div>
          <h4 class="align-baseline">{ saldo }</h4>
        </div>

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