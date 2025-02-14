<script setup>

import { ref } from 'vue'
import hhuddle from '@/assets/Pictures/hhuddle.png';




//showDrawer Konstante ist per default auf false
const showDrawer = ref(false);

//Aufrufen der Methode setzt showDrawer value auf true, dadurch wird v-navigation drawer sichtbar
const toggleDrawer = () => {
  showDrawer.value = !showDrawer.value;
}

</script>

<template>

  <!--Navbar-->
  <v-app-bar class="nav" :elevation="2" >
    <template v-slot:prepend>
      <!--Bei Klick auf bar-nav-icon wird toggleDrawer Methode aufgerufen-->
      <v-app-bar-nav-icon @click="toggleDrawer"></v-app-bar-nav-icon>
    </template>
    <v-app-bar-title >Willkommen bei </v-app-bar-title>
    <v-spacer></v-spacer>

                <!-- Zentriertes Bild -->
                <div class="navbar-image-container">
                <v-img 
                :src="hhuddle" 
                class="navbar-image"
                width="200"
                height="50"
                ></v-img>
                </div>

            <v-spacer></v-spacer>
   
      </v-app-bar>



  <!--Linkes Seitenmenü ausklappbar-->
  <v-navigation-drawer class="drawer" v-if="showDrawer"  >

  <!-- Inhalte des Navigation Drawers -->
    <v-list>
      <v-list-item :to="{ path: '/'}">Login</v-list-item>   
      <v-list-item :to="{ path: '/aboutUs'}">Über Uns</v-list-item>
      <v-list-item :to="{ path: '/contact'}">Kontakt</v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>


<style scoped>
.nav {
  background-color: #D8C3A5;
}


.navbar-image-container {
  position: absolute;
  margin-left: 50%;
  transform: translateX(-50%);
        
}

.drawer {
  background-color:#fff0db;
}
</style>