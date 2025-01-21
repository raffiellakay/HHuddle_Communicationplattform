<script setup>

import { computed, onMounted, ref, watch } from "vue";
import { useUserPostStore } from "@/stores/User/userPostStore";
import { useRoute } from "vue-router";
import { useAuthStore } from "@/stores/authStore";
import ConfirmDeleteCheck from "@/components/ConfirmDeleteCheck.vue";

const route = useRoute();
const userPostStore = useUserPostStore();
const filteredUserPosts = ref([]);
const authStore = useAuthStore();

const category = computed(() => route.params.category);
console.log("Aktuelle Kategorie aus der URL:", category.value);
const show = ref(false); 
const userId = computed(() => authStore.user.id)



const normalizedCategory = computed(() => {
  return category.value ? category.value.toUpperCase() : null;
});
/*
Category {
  //Schwarzes Brett
    BLACKBOARD, 0
  //Ankündigungen
    FRONTPAGE, 1
  //Wer hat mein Paket?
    PACKAGE, 2
  //Wer macht was wann wo?
    EVENTS, 3
  //Biete-Suche-Tausche-Verschenke
    EXCHANGE; 4

}*/

//Methode fehlend: getHouseIdByUserId, aktuell kann noch nich auf die houseId zugegriffen werden 
const showDeleteChecker = ref(false);
const postToDelete = ref(null); 


console.log("Auth Store User:", authStore.user);
console.log("User ID:", userId.value);
console.log("Auth Token:", authStore.token);

//Zeigt alle Posts eines Users an
/*onMounted(async () => {
  await userPostStore.getPostsByUserId(userId.value);
});*/

//Hole alle Posts aus Store und filtere nach Kategorie 
onMounted(async () => {
  if (!normalizedCategory.value) {
    console.error('Kategorie ist nicht definiert!');
    return;
  }
  console.log('Kategorie nach Normalisierung:', normalizedCategory.value);

  await userPostStore.getAllPosts();
  userPostStore.filterPostsByCategory(normalizedCategory.value); // Filter mit Großbuchstaben
  filteredUserPosts.value = userPostStore.filteredPostsByCategory;
});



</script>



<template>
<!-- Dieser Teil beinhaltet das potenzielle Kartendesign für UserPosts-->

<v-container v-if="filteredUserPosts && filteredUserPosts.length > 0">
  <h1> Zeige Beiträge für Kategorie: {{ category  }}</h1>
    <v-row>
      <v-col v-for="filteredUserPost in filteredUserPosts" :key="filteredUserPost.id" cols="12" md="4" lg="3">
        <v-card class="mx-auto" max-width="344">
          <!-- Photo als Header -->
          

          <!-- Titel -->
          <v-card-title>
            {{ filteredUserPost.title }}
          </v-card-title>

          <!-- Untertitel -->
          <v-card-subtitle>
            Startzeit: {{ filteredUserPost.startTime }}
          </v-card-subtitle>
          <v-card-subtitle>
            Endzeit: {{ filteredUserPost.endTime }}
          </v-card-subtitle>
          <v-card-subtitle>
            Erstellungszeit: {{ filteredUserPost.timestamp }}
          </v-card-subtitle>
          <v-card-subtitle>
            Ersteller: {{ filteredUserPost.user?.username || 'Unbekannt' }} <!-- Checkt ob es einen username gibt, ansonsten "Unbekannt"-->
          </v-card-subtitle>

          <!-- Aktionen -->
          <v-card-actions>
            <v-btn color="orange-lighten-2" text>Explore</v-btn>
            <v-spacer></v-spacer>
            <v-btn :icon="show ? 'mdi-chevron-up' : 'mdi-chevron-down'" @click="show = !show"></v-btn>
          </v-card-actions>

          <!-- Expandable Text -->
          <v-expand-transition>
            <div v-show="show">
              <v-divider></v-divider>
              <v-card-text>
                {{ filteredUserPost.description }}
              </v-card-text>
            </div>
          </v-expand-transition>
        </v-card>
      </v-col>
    </v-row>
    <v-container v-if="filteredUserPosts.length === 0">
      <v-alert type="info">Keine Beiträge für diese Kategorie verfügbar.</v-alert>
    </v-container>
  </v-container>

</template>