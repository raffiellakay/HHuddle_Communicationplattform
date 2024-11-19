<script setup>
import { useRouter } from 'vue-router'
import {ref} from 'vue'



const router = useRouter()

const showDrawer = ref(false);


const toggleDrawer = () => {
    showDrawer.value = !showDrawer.value;
}


</script>

<template>
    <v-layout>

        <!--Navbar-->
        <v-app-bar :elevation="2" rounded color="primary">
            <template v-slot:prepend>
                <v-app-bar-nav-icon @click="toggleDrawer"></v-app-bar-nav-icon>
            </template>
            <v-app-bar-title>Menü</v-app-bar-title>
        </v-app-bar>
         <!--Linkes Seitenmenü ausklappbar-->
         <v-navigation-drawer 
           
            v-if="showDrawer"
            app
            color="grey-darken-2"
            temporary
            width="100vw"
            >

    

        <!-- Inhalte des Navigation Drawers -->
      <v-list>
        <v-list-item >
          <v-list-item-content>
            <v-list-item-title>Menü</v-list-item-title>
            <v-list-item-title>Boards</v-list-item-title>
            <v-list-item-title>Über Uns</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
        <v-container>
           
        </v-container>
 </v-layout>
</template>


<style scoped>





</style>