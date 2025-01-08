<script setup>

import { computed, onMounted, ref, watch } from "vue";
import { useAdminPostStore } from "@/stores/Admin/adminPostStore";
import { useRouter } from "vue-router";

//Initalisieren des AdminPostStore
const adminPostStore = useAdminPostStore();
const adminPosts = computed(() => adminPostStore.adminPosts);
const router = useRouter();

onMounted(async () => {
  await adminPostStore.getAdminPostsByHouseId(houseId);
});

//Löschen des AdminPosts
async function deleteAdminPost(postId) {
  await adminPostStore.deleteAdminPost(postId);
  //Eventuell kommt hier ein router.push je nachdem wo nach dem Löschen hinnavigiert werden soll, wahrscheinlich aber wieder auf diese Seite
}
</script>

<template>
  <!--Card layout without store implementation-->
 

  <v-row align="center" justify="center" dense>
    <v-col cols="12" md="6">
      <v-card 
      v-for="adminPost in adminPosts" 
      :key="adminPost.id">
      
      <v-card-title>{{ adminPost.title }}></v-card-title>
      <v-card-text>{{ adminPost.description }}</v-card-text>

    </v-card>
    </v-col>
  </v-row>
</template>
