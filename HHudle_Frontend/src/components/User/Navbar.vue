<template>
  <!-- Navbar -->
  <v-app-bar :elevation="2" rounded color="primary">
    <template v-slot:prepend>
      <!-- Bei Klick auf bar-nav-icon wird toggleDrawer Methode aufgerufen -->
      <v-app-bar-nav-icon @click="toggleDrawer"></v-app-bar-nav-icon>
    </template>
    <v-app-bar-title>Menü</v-app-bar-title>
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