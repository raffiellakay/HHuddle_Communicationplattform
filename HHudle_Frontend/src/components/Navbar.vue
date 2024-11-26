<script setup>
import { useRouter } from 'vue-router'
import { ref } from 'vue'



const router = useRouter()

//showDrawer Konstante ist per default auf false
const showDrawer = ref(false);

//Zustand des aktiven Items im Untermenü von Boards 
const activeItem = ref(null);

//Liste an Unteritems in Array
const items = ref([
  "Gemeinschaftsräume",
  "Schwarzes Brett",
  "Paketfinder",
  "Suche - Biete - Tausche",
]);


//Aufrufen der Methode setzt showDrawer value auf true, dadurch wird v-navigation drawer sichtbar
const toggleDrawer = () => {
  showDrawer.value = !showDrawer.value;
}

//Ruft setActiveItem Methode auf, und übernimmt item als Parameter, setzt den value von activeItem auf das übergebene item
const setActiveItem = (item) => {
  activeItem.value = item;
}


</script>

<template>

  <!--Navbar-->
  <v-app-bar :elevation="2" rounded color="primary">
    <template v-slot:prepend>
      <!--Bei Klick auf bar-nav-icon wird toggleDrawer Methode aufgerufen-->
      <v-app-bar-nav-icon @click="toggleDrawer"></v-app-bar-nav-icon>
    </template>
    <v-app-bar-title>Menü</v-app-bar-title>
  </v-app-bar>
  <!--Linkes Seitenmenü ausklappbar-->
  <v-navigation-drawer v-if="showDrawer" style="width:100%" app color="grey-darken-2" temporary width="100%">



    <!-- Inhalte des Navigation Drawers -->
    <v-list>
      <v-list-item>Startseite</v-list-item>
      <v-list-group value="Boards">
        <template v-slot:activator="{props}">
        <v-list-item v-bind="props" title="Boards"></v-list-item>
        </template>


        <template v-slot>
          <v-list-item v-for="item in items"
          :key="item"
          :title="item"
          :class="{'v-list-item--active': activeItem === item}"
          @click="setActiveItem(item)"
          v-bind="props">
        </v-list-item>
          
        </template>
      </v-list-group>
      <v-list-item>Chat</v-list-item>
      <v-list-item>Über Uns</v-list-item>
      <v-list-item>Kontakt</v-list-item>


    </v-list>
  </v-navigation-drawer>
</template>


<style scoped></style>