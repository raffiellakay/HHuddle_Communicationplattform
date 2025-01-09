<script setup>

import { computed, onMounted, ref, watch } from "vue";
import { useAdminPostStore } from "@/stores/Admin/adminPostStore";
import { useRoute } from "vue-router";


const props = defineProps({
  postId: Number,
});


const adminPostStore = useAdminPostStore();


// Holt den richtigen Post aus dem Store
const adminPost = computed(() => 
  adminPostStore.adminPosts.find(post => post.Id === props.postId)
);

//Löschen des AdminPosts
async function deleteAdminPost(postId) {
  await adminPostStore.deleteAdminPost(postId);
  //Eventuell kommt hier ein router.push je nachdem wo nach dem Löschen hinnavigiert werden soll, wahrscheinlich aber wieder auf diese Seite
}




</script>


<template>

 

<v-container>
<h1>Admin Posts für Haus {{ houseId }}</h1>

<v-row>
      <v-col v-if="adminPost">
        <v-card>
          <v-card-title>{{ adminPost.title }}</v-card-title>
          <v-card-text>{{ adminPost.text }}</v-card-text>
        </v-card>
      </v-col>
    </v-row>
</v-container>






  
      <v-card>
      
      <v-card-title> Titel </v-card-title>
      <v-card-text> Beschreibung </v-card-text>

    </v-card>
    
</template>
