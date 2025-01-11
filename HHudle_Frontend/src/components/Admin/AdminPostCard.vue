<script setup>

import { computed, onMounted, ref, watch } from "vue";
import { useAdminPostStore } from "@/stores/Admin/adminPostStore";
import { useRoute } from "vue-router";
import DeleteButton from "@/components/Icons/DeleteButton.vue";
import ConfirmDeleteCheck from "@/components/ConfirmDeleteCheck.vue";



const emits = defineEmits(['delete-adminPost']);
const route = useRoute();
const adminPostStore = useAdminPostStore();
//HouseId dynamisch aus der Route holen
const houseId = computed(() => route.params.houseId);
//AdminPosts aus dem Store holen
const adminPosts = computed(() => adminPostStore.adminPosts);

const props = defineProps({
  postId: Number,
});

const showDeleteChecker = ref(false);
const postToDelete = ref(null);






onMounted(async () => {
  if (houseId.value) {
    console.log("Fetching posts for houseId:", houseId.value); //Debugging
    await adminPostStore.getAdminPostsByHouseId(houseId.value);
  }
});

//Öffnen des DeleteCheckers
const openDeleteChecker = (adminPost) => {
  console.log("Post zum Löschen: ", adminPost) //Debugging
  postToDelete.value = {...adminPost};
  showDeleteChecker.value = true;
}

//Schließen des DeleteCheckers
const closeDeleteChecker = (adminPost) => {
  postToDelete.value = null; 
  showDeleteChecker.value = false;
}


//AdminPost Löschen
const confirmDelete = async () => {
  if (postToDelete.value) {
    try {
      console.log(`Post mit ID ${postToDelete.value.id} wird gelöscht`);
      await adminPostStore.deleteAdminPost(postToDelete.value.id);

      await adminPostStore.getAdminPostsByHouseId(houseId.value);

      showDeleteChecker.value = false; 
      postToDelete.value = null;
    } catch (error) {
      console.error("Folgender Fehler beim Löschen aufgetreten: ", error)
    }
  } 
}

//Sortiert adminPosts nach Zeit und Erstellungsdatum 
const sortedAdminPostsByTimeCreated = computed(() => {
  return [...adminPosts.value].sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp));
});


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

  <v-container>

    <v-row align="center" justify="center">
      <v-col v-for="adminPost in sortedAdminPostsByTimeCreated" :key="adminPost.id" cols="12" md="6">
        <v-card 
        class="mx-auto"
        elevation="5"
        >
         <v-card-item>
          <v-card-title>{{ adminPost.title }}</v-card-title> 
          <template v-slot:append >
            <DeleteButton @click="openDeleteChecker(adminPost)" @delete-success="$emit('adminPost-deleted')" class="delete-button"/> 
          </template>
            
          <v-card-subtitle> {{ formatToDateTime(adminPost.timestamp) }}</v-card-subtitle>
        </v-card-item>
          <v-card-text> {{ adminPost.text }}</v-card-text>
          <v-row>
            
            <v-card-text> {{ adminPost.user.username }}</v-card-text>
          </v-row>
        </v-card>
      </v-col>
    </v-row>

    <ConfirmDeleteCheck
    :show="showDeleteChecker"
    :itemName="'den Post'"
    @confirm="confirmDelete"
    @close="closeDeleteChecker"/>
  </v-container>

</template>

<style scoped>

.delete-button {
  background-color: rgb(237, 79, 79);
  color: white;
  font-size: 18px;


}




</style>
