<script setup>
import { useRoute, useRouter } from 'vue-router'
import { ref,computed } from 'vue'
import AdminPostForm from '@/components/Admin/AdminPostForm.vue';



const router = useRouter(); //Gibt Router Instanz zurück
const route = useRoute(); //Gibt aktuelle Route zurück 

//AdminPostForm bekommt houseId entweder als prop oder aus der route 
const houseId = computed(() => Number(route.params.houseId) || null);


//showDrawer Konstante ist per default auf false
const showDrawer = ref(false);

//Aufrufen der Methode setzt showDrawer value auf true, dadurch wird v-navigation drawer sichtbar
const toggleDrawer = () => {
  showDrawer.value = !showDrawer.value;
}

//Checkt ob die aktuelle Seite Haus Seite ist
//Ist true wenn auf einer HausSeite und false wenn nicht
const isHousePage = computed(() =>{
  return route.path.includes('/admin/house');
})


const showAdminPostForm = ref(false); 

//Debugging
console.log(route.path);
console.log(showAdminPostForm.value);



const handleLogout = () => {
  authStore.logout();
  router.push('/');
}

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
      <v-btn icon @click="showAdminPostForm = true">
        <v-icon class="plus-icon"> mdi-plus-circle</v-icon>
      </v-btn>
<!-- Öffnen der PostForm Komponent in einem Dialog -->
   
      <v-dialog v-model="showAdminPostForm" max-width="500">
        
          <AdminPostForm 
          :houseId="houseId" 
          @adminPost-added="refreshPosts" 
          @close="showAdminPostForm=false"></AdminPostForm>
      

      </v-dialog>

      </template>

      </v-app-bar>



  <!--Linkes Seitenmenü ausklappbar-->
  <v-navigation-drawer v-if="showDrawer" style="width:100%" app color="grey-darken-2" temporary width="100%">



    <!-- Inhalte des Navigation Drawers -->
    <v-list>
      <v-list-item :to="{ path: '/admin/home'}">
        <v-list-item-title>Häuser</v-list-item-title>
      </v-list-item>    
      <v-list-item>Über Uns</v-list-item>
      <v-list-item>Kontakt</v-list-item>
      <v-list-item class="logout-icon">
      <v-btn icon @click="handleLogout" >
        <v-icon color="red">mdi-logout</v-icon>
      </v-btn>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>


<style scoped>

.logout-icon {
  padding-top: 500px; 
}



</style>