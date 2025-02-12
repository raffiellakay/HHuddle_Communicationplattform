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
  postToDelete.value = { ...adminPost };
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
};

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
      <v-col v-for="adminPost in sortedAdminPostsByTimeCreated" :key="adminPost.id" cols="12" sm="6" md="4">
        <!-- 2 pro Reihe auf kleinen Bildschirmen, 3 auf großen -->

        <v-card class="mx-auto card-size" elevation="5">

          <!-- POST TITEL -->
          <v-card-item>
            <v-card-title class="font-weight-bold text-start">
              {{ adminPost.title }}
            </v-card-title>
          </v-card-item> 

          <!-- ERSTE TRENNLINIE -->
          <div class="divider-container">
            <v-divider></v-divider>
          </div>

          <!-- BESCHREIBUNG -->
          <v-card-text class="text-start">
            {{ adminPost.text }}
          </v-card-text>

          <!-- ZWEITE TRENNLINIE (Kurz, über den unteren Bereich) -->
          <div class="divider-container">
            <v-divider></v-divider>
          </div>

          <!-- HAUSVERWALTUNG, TIMESTAMP & DELETE-BUTTON IN EINER ZEILE -->
          <v-card-actions class="d-flex align-center justify-space-between">
            <div>
              <v-card-subtitle class="text-grey-darken-1">
                {{ adminPost.user.username }}
              </v-card-subtitle>
              <v-card-subtitle class="text-grey-darken-1">
                {{ formatToDateTime(adminPost.timestamp) }}
              </v-card-subtitle>
            </div>

            <!-- DELETE-BUTTON  -->
            <DeleteButton @click="openDeleteChecker(adminPost)" />

          </v-card-actions>

        </v-card>
      </v-col>
    </v-row>

    <!-- DELETE BESTÄTIGUNG -->
    <ConfirmDeleteCheck :show="showDeleteChecker" :itemName="'den Post'" @confirm="confirmDelete"
      @close="closeDeleteChecker" />
  </v-container>
</template>

<style scoped>
/* Kartenhöhe anpassen, um Inhalte gut darzustellen */
.card-size {
  min-height: 250px;
  max-width: 100%;
  width:100%;
}

/* Kürzere Trennlinien */
.divider-container {
  width: 80%;
  margin: 8px auto;
}

.title-row {
  display: flex;
  justify-content: flex-start;
  /* justify-content: space-between; */
  align-items: center;
  gap: 8px;
  width: 100%;
}

.title-text {
  flex-grow: 1;
  /* Lässt den Titel den gesamten verfügbaren Platz nutzen */
  font-size: 18px;
  font-weight: bold;
  color: #333;
  white-space: nowrap;
  /* Verhindert Zeilenumbruch */
  overflow: hidden;
  text-overflow: ellipsis;
  /* Falls Titel zu lang ist, werden "..." angezeigt */
}
</style>