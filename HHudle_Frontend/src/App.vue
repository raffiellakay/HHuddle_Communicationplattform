<script setup>
import { RouterLink, RouterView } from 'vue-router';
import Navbar from './components/Navbar.vue';

</script>

<template>
  <v-app>
    <div id="app" data-app>
      <RouterView></RouterView>
    </div>

    <Navbar />
  </v-app>


</template>

<style scoped></style>
