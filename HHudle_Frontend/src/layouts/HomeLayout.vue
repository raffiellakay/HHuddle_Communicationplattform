<script setup>

import Navbar from '../components/Navbar.vue';
import { useRouter } from 'vue-router';

const router = useRouter();

</script>




<template> 

<v-layout>
    <Navbar />
    <v-main>
    <div id="app" data-app>
        <RouterView></RouterView>
    </div>
  </v-main>

  </v-layout>
</template>




<style scoped>

</style>
