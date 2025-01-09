<script setup>

import { computed, onMounted, ref, watch } from "vue";
import { useAdminPostStore } from "@/stores/Admin/adminPostStore";
import { useRoute } from "vue-router";


const route = useRoute();

const props = defineProps({
  postId: Number,
});


const adminPostStore = useAdminPostStore();


// HouseId dynamisch aus der Route holen
const houseId = computed(() => route.params.houseId);

// AdminPosts aus dem Store holen
const adminPosts = computed(() => adminPostStore.adminPosts);

onMounted(async () => {
  if (houseId.value) {
    console.log("Fetching posts for houseId:", houseId.value); // 🟢 Debugging
    await adminPostStore.getAdminPostsByHouseId(houseId.value);
  }
});




</script>


<template>

 

<v-container>


<v-row>
      <v-col v-for="adminPost in adminPosts" :key="adminPost.id" cols="12" md="6">
        <v-card>
          <v-card-title>{{ adminPost.title }}</v-card-title>
          <v-card-text>{{ adminPost.text }}</v-card-text>
          <v-card-text> {{ adminPost.timestamp }}</v-card-text>
          <v-card-text> {{ adminPost.user.username }}</v-card-text>
        </v-card>
      </v-col>
    </v-row>
</v-container>


    
</template>
