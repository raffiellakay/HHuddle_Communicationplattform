<script setup>
import { routerKey, useRoute, useRouter } from 'vue-router'
import { ref } from 'vue'
import { computed } from 'vue';



const router = useRouter(); //Gibt Router Instanz zurück
const route = useRoute(); // Gibt aktuelle Route zurück 



//showDrawer Konstante ist per default auf false
const showDrawer = ref(false);


//Zustand des aktiven Items im Untermenü von Boards 
const activeItem = ref(null);

//Liste an Unteritems in Array
const items = ref([
  {title: "Gemeinschaftsräume", route: "/user/board/commonrooms"},
  {title:"Schwarzes Brett", route: "/user/board/blackboard"},
  {title: "Paketfinder", route: "/user/board/packagefinder"},
  {title: "Suche - Biete - Tausche", route: "/user/board/search&find"}
]);


//Aufrufen der Methode setzt showDrawer value auf true, dadurch wird v-navigation drawer sichtbar
const toggleDrawer = () => {
  showDrawer.value = !showDrawer.value;
}

//Ruft setActiveItem Methode auf, und übernimmt item als Parameter, setzt den value von activeItem auf den Titel des übergebenen Items.
//Navigiert danach zur entsprechenden route des Items auf @click
const setActiveItem = (item) => {
  activeItem.value = item.title;
  router.push(item.route);
  showDrawer.value = false;
}

//Checkt ob die aktuelle Seite eine Board Seite ist indem es mit items im items array abgeglichen wird
//Ist true wenn auf einer BoardSeite und false wenn nicht
const isBoardPage = computed(() =>{
  const boardRoutes = items.value.map(item => item.route);
  return boardRoutes.includes(route.path);
})

const handlePlusClick = () => {
  console.log("Plus icon clicked!");
  // Add your desired functionality here
};





</script>

<template>

  <!--Navbar-->
  <v-app-bar :elevation="2" rounded color="primary">
    <template v-slot:prepend>
      <!--Bei Klick auf bar-nav-icon wird toggleDrawer Methode aufgerufen-->
      <v-app-bar-nav-icon @click="toggleDrawer"></v-app-bar-nav-icon>
    </template>
    <v-app-bar-title>Menü</v-app-bar-title>
    
    <template v-if="isBoardPage">
      <v-btn icon @click="handlePlusClick">
        <v-icon class="plus-icon"> mdi-plus-circle</v-icon>
      </v-btn>
    </template>
  </v-app-bar>
  <!--Linkes Seitenmenü ausklappbar-->
  <v-navigation-drawer 
    v-if="showDrawer" 
    app
    :style="{ width: width }" 
    color="grey-darken-2" 
    class="custom-drawer">



    <!-- Inhalte des Navigation Drawers -->
    <v-list>
      <v-list-item :to="{ path: '/user/home'}">
        <v-list-item-title>Startseite</v-list-item-title>
      </v-list-item>


      <v-list-group value="Boards">
        <template v-slot:activator="{props}">
        <v-list-item v-bind="props" title="Boards"></v-list-item>
        </template>


        <template v-slot>
          <v-list-item v-for="item in items"
          :key="item.title"
          :title="item.title"
          :class="{'v-list-item--active': activeItem === item.title}"
          @click="setActiveItem(item)">
        </v-list-item>
          
        </template>
      </v-list-group>
      <v-list-item>Chat</v-list-item>
      <v-list-item>Über Uns</v-list-item>
      <v-list-item>Kontakt</v-list-item>


    </v-list>
  </v-navigation-drawer>
</template>


<style scoped>

.v-navigation-drawer {
  padding: 0; 
  margin: 0;
}

/*
.custom-drawer {
  width: 100%;
  max-width: 100%;
}*/

@media (min-width: 1024px) {
  .custom-drawer {
    width: 30%;
  }
}

@media (max-width: 400px) {
  .custom-drawer {
    width: 100%;
    transform: none !important;
  }
}

.plus-icon {
  font-size: 40px;
  padding-right: 20px
}

</style>