<script setup>

import { computed, onMounted, ref, watch } from "vue";
import { useUserPostStore } from "@/stores/User/userPostStore";
import { useRoute } from "vue-router";
import { useAuthStore } from "@/stores/authStore";
import ConfirmDeleteCheck from "@/components/ConfirmDeleteCheck.vue";


const userPostStore = useUserPostStore();
const filteredUserPosts = computed(() => {
  return userPostStore.filteredPostsByCategory;
});


const authStore = useAuthStore();

const show = ref(false); 
const userId = computed(() => authStore.user.id)

const props = defineProps({
  category: {
    type: String, 
    required: true, 
  }
})

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

//Methode fehlend: getHouseIdByUserId, aktuell kann noch nich auf die houseId zugegriffen werden 
const showDeleteChecker = ref(false);
const postToDelete = ref(null); 
const showNewChatModal = ref(false); 
const initialMessage = ref('');
const secondUserId = ref('');


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

// Function to create a new chat
const createChat = async () => {
  if (!secondUserId.value || !initialMessage.value) {;
    return;
  }

  try {
    // Create a new chat
    const newChat = await chatStore.createChat({ firstUserId: userId.value, secondUserId: secondUserId.value });
    // Send the initial message
    await chatStore.sendMessage({
      chatId: newChat.id,
      senderId: authStore.user.id,
      text: initialMessage.value,
    });

    showNewChatModal.value = false;
    initialMessage.value = '';
    alert('Chat erfolgreich erstellt');
  } catch (error) {
    console.error('Fehler beim Erstellen des Chats:', error);
    alert('Fehler beim Erstellen des Chats');
  }
};
const openModal = (userId) => {
  console.log("Öffne Chat Modal für User ID:", userId);
  showNewChatModal.value = true;
  secondUserId.value = userId;
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
          <v-btn v-if="userId !== filteredUserPost.user?.id" icon @click="openModal(filteredUserPost.user?.id)">
            <v-icon class="plus-icon">mdi-plus-circle</v-icon>
          </v-btn>

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
            Ersteller: {{ filteredUserPost.user?.username || 'Unbekannt' }} 
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

    
    <v-dialog v-model="showNewChatModal" max-width="500">
      <v-card>
        <v-card-title>
          <span class="headline">Chat erstellen</span>
        </v-card-title>
        <v-card-text>
          <v-form ref="form">
            <v-text-field
              v-model="initialMessage"
              label="Nachricht"
              required
            ></v-text-field>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="createChat">Erstellen</v-btn>
          <v-btn color="grey darken-1" text @click="showNewChatModal = false">Abbrechen</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>

</template>