<script setup>
import {RouterLink, RouterView} from 'vue-router';
import Navbar from './components/Navbar.vue';

</script>

<template>

    <div id="app" data-app>
    <RouterView></RouterView>
  </div>

      <Navbar/>
 

  
</template>

<style scoped>

</style>
