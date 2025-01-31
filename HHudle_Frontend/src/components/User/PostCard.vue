<script setup>

import { computed, onMounted, ref, watch } from "vue";
import { useUserPostStore } from "@/stores/User/userPostStore";
import { useRoute } from "vue-router";
import { useAuthStore } from "@/stores/authStore";
import DeleteButton from "@/components/Icons/DeleteButton.vue";
import EditButton from "@/components/Icons/EditButton.vue";
import ConfirmDeleteCheck from "@/components/ConfirmDeleteCheck.vue";

const emits = defineEmits(['delete-userPost']);
const userPostStore = useUserPostStore();

const props = defineProps({
  category: {
    type: String, 
    required: true, 
  },
  postId: Number,
  facilityId: Number,
})


const filteredUserPosts = computed(() => {
  if (!props.facilityId) return userPostStore.filteredPostsByCategory;
  
  return userPostStore.filteredPostsByCategory.filter(
    post => post.facilityId === props.facilityId
  );
});

const showDropdown = ref({});

// Umschalten des Dropdowns
const toggleDropdown = (postId) => {
  showDropdown.value[postId] = !showDropdown.value[postId];
};


// Methode, um den Zustand eines Dropdowns zu überprüfen
const isDropdownOpen = (postId) => {
  return !!showDropdown.value[postId]; // Rückgabe von `true` oder `false`
};

const authStore = useAuthStore();

const show = ref(false); 
const userId = computed(() => authStore.user.id)



/*const normalizedCategory = computed(() => {
  return category.value ? category.value.toUpperCase() : null;
});*/
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



const showDeleteChecker = ref(false);
const postToDelete = ref(null); 

//Öffnen des DeleteCheckers
const openDeleteChecker = (userPost) => {
  console.log("Post zum Löschen: ", userPost) //Debugging
  postToDelete.value = {...userPost};
  showDeleteChecker.value = true;
}

//Schließen des DeleteCheckers
const closeDeleteChecker = (userPost) => {
  postToDelete.value = null; 
  showDeleteChecker.value = false;
}


console.log("Auth Store User:", authStore.user);
console.log("User ID:", userId.value);
console.log("Auth Token:", authStore.token);

//Zeigt alle Posts eines Users an
/*onMounted(async () => {
  await userPostStore.getPostsByUserId(userId.value);
});*/

onMounted(async () => {
  try {
    const houseId = authStore.user.houseId; // Hole die House ID des Benutzers
    if (!houseId) {
      console.error("Keine House ID gefunden!");
      return;
    }

    console.log("Lade Posts für House ID:", houseId);
    await userPostStore.getPostsByHouseId(houseId);

    console.log("Setze Kategorie:", props.category);
    userPostStore.setCategory(props.category);
  } catch (error) {
    console.error("Fehler beim Laden der Posts:", error);
  }
});

//UserPost Löschen
const confirmDelete = async () => {
  if (postToDelete.value) {
    try {
      console.log(`Post mit ID ${postToDelete.value.id} wird gelöscht`);
      await userPostStore.deletePost(postToDelete.value.id);

      await userPostStore.getPostsByHouseId(authStore.user.houseId.value);

      showDeleteChecker.value = false; 
      postToDelete.value = null;
    } catch (error) {
      console.error("Folgender Fehler beim Löschen aufgetreten: ", error)
    }
  } 
}



//Sortiert userPosts nach Zeit und Erstellungsdatum 
const sortedUserPostsByTimeCreated = computed(() => {
  return [...filteredUserPosts.value].sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp));
});

//Formatiert Datum auf DD.MM.YYYY
const formatToGermanDate = (dateTime) => {
  if (!dateTime) return ""; //Rückgabe eines leeren Strings, wenn kein Datum vorhanden ist
  const d = new Date(dateTime);
  return d.toLocaleDateString("de-DE", {
    weekday: "short",
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });
};



</script>



<template>
<!-- Dieser Teil beinhaltet das potenzielle Kartendesign für UserPosts-->

<v-container>
  <h1> Zeige Beiträge für Kategorie: {{ category }}</h1>
    <v-row v-if="filteredUserPosts.length > 0">
      <v-col v-for="filteredUserPost in filteredUserPosts" :key="filteredUserPost.id" cols="12" md="4" lg="3">
        <v-card class="mx-auto" max-width="344">
          <!-- Photo als Header -->
           <!-- Bild anzeigen, falls vorhanden -->
           <v-img 
            v-if="filteredUserPost.pathToImage"
            :src="`http://localhost:8081/${filteredUserPost.pathToImage}`" 
            alt="Post Bild"
            height="200"
            contain
          ></v-img>

          <!-- Titel -->
          <v-card-item>
          <v-card-title>
            {{ filteredUserPost.title }}
            </v-card-title>
          

            <template v-slot:append >
                  <DeleteButton 
                  v-if="filteredUserPost.user?.id === userId"
                  @click="openDeleteChecker(userPost)" 
                  @delete-success="$emit('userPost-deleted')" 
                  class="delete-button"/> 
                </template>
             
            </v-card-item>
        

          <!-- Untertitel -->
           <div v-if="category === 'EVENTS'">
          <v-card-subtitle>
            Startzeit: {{ formatToGermanDate(filteredUserPost.starttime) }}
          </v-card-subtitle>
          <v-card-subtitle>
            Endzeit: {{ formatToGermanDate(filteredUserPost.endtime) }}
          </v-card-subtitle>
          <v-card-subtitle>
            Erstellungszeit: {{ filteredUserPost.timestamp }}
          </v-card-subtitle>
          </div>
          <v-card-subtitle>
            Ersteller: {{ filteredUserPost.user?.username || 'Unbekannt' }} 
          </v-card-subtitle>

          <!-- Aktionen -->
          <v-card-actions>
            <v-btn color="orange-lighten-2" text>Explore</v-btn>
            <v-spacer></v-spacer>
            <v-btn 
            :icon="isDropdownOpen(filteredUserPost.id) ? 'mdi-chevron-up' : 'mdi-chevron-down'"
            @click="toggleDropdown(filteredUserPost.id)"></v-btn>
          </v-card-actions>

          <!-- Expandable Text -->
          <v-expand-transition>
            <div v-show="isDropdownOpen(filteredUserPost.id)">
              <v-divider></v-divider>
              <v-card-text>
                {{ filteredUserPost.text }}
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