<script setup>

import { computed, onMounted, ref, watch } from "vue";
import { useAdminPostStore } from "@/stores/Admin/adminPostStore";
import { useRoute } from "vue-router";
import DeleteButton from "@/components/Icons/DeleteButton.vue";



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


//Formatiert Datum auf DD.MM.YYYY
const formatToGermanDate = (date) => {
  if (!date) return ""; // Rückgabe eines leeren Strings, wenn kein Datum vorhanden ist
  const d = new Date(date);
  return d.toLocaleDateString("de-DE", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  });
};


//Formatiert Datum auf DD.MM.YYYY
const formatToDateTime = (date) => {
  if (!date) return ""; // Rückgabe eines leeren Strings, wenn kein Datum vorhanden ist
  const d = new Date(date);
  return d.toLocaleDateString("de-DE", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
    hour12: false,

  });
};


</script>


<template>






    <v-row align="center" justify="center">
      <v-col v-for="adminPost in adminPosts" :key="adminPost.id" cols="12" md="6">
        <v-card 
        class="mx-auto"
        elevation="5"
        >
         <v-card-item>
            <v-card-title>{{ adminPost.title }}</v-card-title> 
            <DeleteButton/> 
          
          <v-card-subtitle> {{ formatToDateTime(adminPost.timestamp) }}</v-card-subtitle>
        </v-card-item>
          <v-card-text> {{ adminPost.text }}</v-card-text>
          <v-row>
            
            <v-card-text> {{ adminPost.user.username }}</v-card-text>
          </v-row>
        </v-card>
      </v-col>
    </v-row>




</template>

<style scoped>









</style>
