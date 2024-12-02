<script setup>

import Navbar from '@/components/User/Navbar.vue';
import { useRouter } from 'vue-router';

const router = useRouter();

</script>




<template>

  <RouterView></RouterView>
  <v-app>
    <b-btn icon>


      <v-icon>mdi-plus-circle</v-icon>
    </b-btn>
  </v-app>

</template>




<style scoped>

</style>