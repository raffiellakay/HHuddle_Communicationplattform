<script setup>
import { useRoute, useRouter } from 'vue-router'
import { ref,computed } from 'vue'
import AdminPostForm from '@/components/Admin/AdminPostForm.vue';



const router = useRouter(); //Gibt Router Instanz zurück
const route = useRoute(); //Gibt aktuelle Route zurück 


//showDrawer Konstante ist per default auf false
const showDrawer = ref(false);

//Aufrufen der Methode setzt showDrawer value auf true, dadurch wird v-navigation drawer sichtbar
const toggleDrawer = () => {
  showDrawer.value = !showDrawer.value;
}

//Checkt ob die aktuelle Seite Haus Seite ist indem es mit items im items array abgeglichen wird
//Ist true wenn auf einer HausSeite und false wenn nicht
const isHousePage = computed(() =>{
  return route.path.includes('/admin/house');
})


const showForm = ref(false); 

//Debugging
console.log(route.path);
console.log(showForm.value);


</script>

<template>

  <!--Navbar-->
  <v-app-bar :elevation="2" rounded color="secondary">
    <template v-slot:prepend>
      <!--Bei Klick auf bar-nav-icon wird toggleDrawer Methode aufgerufen-->
      <v-app-bar-nav-icon @click="toggleDrawer"></v-app-bar-nav-icon>
    </template>
    <v-app-bar-title>Admin Navbar</v-app-bar-title>
  

  <template v-if="isHousePage">
      <v-btn icon @click="showForm = true">
        <v-icon class="plus-icon"> mdi-plus-circle</v-icon>
      </v-btn>
<!-- Öffnen der PostForm Komponent in einem Dialog -->
   
      <v-dialog v-model="showForm" max-width="500">
        <template v-slot:default="{close}">
          <AdminPostForm @close="close"></AdminPostForm>
        </template>

      </v-dialog>

      </template>

      </v-app-bar>



  <!--Linkes Seitenmenü ausklappbar-->
  <v-navigation-drawer v-if="showDrawer" style="width:100%" app color="grey-darken-2" temporary width="100%">



    <!-- Inhalte des Navigation Drawers -->
    <v-list>
      <v-list-item :to="{ path: '/admin/houses'}">
        <v-list-item-title>Häuser</v-list-item-title>
      </v-list-item>    
      <v-list-item>Über Uns</v-list-item>
      <v-list-item>Kontakt</v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>


<style scoped></style>