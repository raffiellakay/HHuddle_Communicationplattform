<template>
  <!-- Navbar -->
  <v-app-bar :elevation="2" rounded color="primary">
    <template v-slot:prepend>
      <!-- Bei Klick auf bar-nav-icon wird toggleDrawer Methode aufgerufen -->
      <v-app-bar-nav-icon @click="toggleDrawer"></v-app-bar-nav-icon>
    </template>
    <v-app-bar-title>Menü</v-app-bar-title>

    <v-btn icon @click="showNewChatModal = true">
      <v-icon class="plus-icon">mdi-plus-circle</v-icon>
    </v-btn>
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
  </v-app-bar>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useAuthStore } from '@/stores/authStore';
import { useChatStore } from '@/stores/User/chatStore';
import { useUserPostStore } from '@/stores/User/userPostStore';

const router = useRouter(); // Gibt Router Instanz zurück
const route = useRoute(); // Gibt aktuelle Route zurück 
const authStore = useAuthStore();
const chatStore = useChatStore();
const userPostStore = useUserPostStore();

const showDrawer = ref(false); // showDrawer Konstante ist per default auf false
const activeItem = ref(null); // Zustand des aktiven Items im Untermenü von Boards 
const isBoardsOpen = ref(false);
const showForm = ref(false);
const showNewChatModal = ref(false); 
const secondUserId = ref('');
const initialMessage = ref('');

// Dynamically get the current user ID
const currentUserId = computed(() => authStore.user?.id);

// Gibt aktuelle Kategorie an PostForm zurück um bestimmte Felder zu verstecken
watch(
  () => route.path,
  () => {
    // Definiere Kategorien basierend auf den Routen
    const categoryMap = {
      "user/home": "FRONTPAGE",
      "/user/board/commonrooms": "EVENTS",
      "/user/board/blackboard": "BLACKBOARD",
      "/user/board/packagefinder": "PACKAGE",
      "/user/board/search&find": "EXCHANGE",
    };

    // Setze die Kategorie im Store
    const category = categoryMap[route.path] || null;
    console.log("Setze Kategorie im Store:", category);
    userPostStore.setCategory(category);
  },
  { immediate: true } // Sofort ausführen
);

// List of board items
const items = ref([
  { title: "Gemeinschaftsräume", route: "/user/board/commonrooms" },
  { title: "Schwarzes Brett", route: "/user/board/blackboard" },
  { title: "Paketfinder", route: "/user/board/packagefinder" },
  { title: "Suche - Biete - Tausche", route: "/user/board/search&find" },
]);

// Function to create a new chat
const createChat = async () => {
  if (!secondUserId.value || !initialMessage.value) {
    alert('User ID und Nachricht sind erforderlich');
    return;
  }

  try {
    // Create a new chat
    const newChat = await chatStore.createChat({ firstUserId: authStore.user.id, secondUserId: secondUserId.value });

    // Send the initial message
    await chatStore.sendMessage({
      chatId: newChat.id,
      senderId: authStore.user.id,
      text: initialMessage.value,
    });

    showNewChatModal.value = false;
    secondUserId.value = '';
    initialMessage.value = '';
    alert('Chat erfolgreich erstellt');
  } catch (error) {
    console.error('Fehler beim Erstellen des Chats:', error);
    alert('Fehler beim Erstellen des Chats');
  }
};

// Function to toggle the drawer
const toggleDrawer = () => {
  showDrawer.value = !showDrawer.value;
};
</script>


<style scoped>

.v-navigation-drawer {
  padding: 0; 
  margin: 0;
}

/*
.custom-drawer {
  width: 100%;
  max-width: 100%;
}*/

@media (min-width: 1024px) {
  .custom-drawer {
    width: 30%;
  }
}

@media (max-width: 400px) {
  .custom-drawer {
    width: 100%;
    transform: none !important;
  }
}

.plus-icon {
  font-size: 40px;
  padding-right: 20px
}

.custom-drawer {
  display: flex;
  flex-direction: column;
  justify-content: space-between; /* Abstand zwischen oben und unten */
  height: 100%; /* Stellt sicher, dass der Drawer die volle Höhe einnimmt */
}

.drawer-content {
  flex-grow: 1; /* Füllt den verfügbaren Platz aus */
}

.logout-container {
  padding: 16px; /* Abstand des Buttons vom Rand */
  padding-top: 300px;
}
</style>