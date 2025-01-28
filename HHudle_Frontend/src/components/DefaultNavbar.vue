<script setup>

import { ref } from 'vue'





//showDrawer Konstante ist per default auf false
const showDrawer = ref(false);

//Aufrufen der Methode setzt showDrawer value auf true, dadurch wird v-navigation drawer sichtbar
const toggleDrawer = () => {
  showDrawer.value = !showDrawer.value;
}

</script>

<template>

  <!--Navbar-->
  <v-app-bar :elevation="2" rounded color="secondary">
    <template v-slot:prepend>
      <!--Bei Klick auf bar-nav-icon wird toggleDrawer Methode aufgerufen-->
      <v-app-bar-nav-icon @click="toggleDrawer"></v-app-bar-nav-icon>
    </template>
    <v-app-bar-title>Willkommen bei HHuddle</v-app-bar-title>
      </v-app-bar>



  <!--Linkes Seitenmenü ausklappbar-->
  <v-navigation-drawer v-if="showDrawer" style="width:100%" app color="grey-darken-2" temporary width="100%">

  <!-- Inhalte des Navigation Drawers -->
    <v-list>
      <v-list-item :to="{ path: '/'}">Login</v-list-item>   
      <v-list-item :to="{ path: '/aboutUs'}">Über Uns</v-list-item>
      <v-list-item :to="{ path: '/contact'}">Kontakt</v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>


<style scoped></style>