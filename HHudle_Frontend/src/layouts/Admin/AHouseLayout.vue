<script setup>
import Navbar from '@/components/Admin/Navbar.vue';



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