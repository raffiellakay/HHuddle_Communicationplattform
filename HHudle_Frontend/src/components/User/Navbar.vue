<script setup>



import { useRoute, useRouter } from 'vue-router'
import { ref, watch } from 'vue'
import { computed } from 'vue';
import PostForm from '@/components/User/PostForm.vue';
import { useAuthStore } from '@/stores/authStore';
import { useUserPostStore } from '@/stores/User/userPostStore';


const userPostStore = useUserPostStore();
const currentCategory = computed(() => userPostStore.currentCategory);

const router = useRouter(); //Gibt Router Instanz zurück
const route = useRoute(); // Gibt aktuelle Route zurück 
const authStore = useAuthStore();





const showDrawer = ref(false);
const activeItem = ref(null);
const isBoardsOpen = ref(false);
const showForm = ref(false); 


// Dynamically get the current user ID
const currentUserId = computed(() => authStore.user?.id);



const handleClose = () => {
  showForm.value = false;
}

//Gibt aktuelle Kategorie an PostForm zurück um bestimmte Felder zu verstecken
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

const toggleDrawer = () => {
  showDrawer.value = !showDrawer.value;
};

const handleBoardClick = () => {
  router.push("/user/allboards");
  showDrawer.value = false;
};

const setActiveItem = (item) => {
  activeItem.value = item.title;
  isBoardsOpen.value = true;
  router.push(item.route);
  isBoardsOpen.value = true;
  router.push({ path: item.route });
  showDrawer.value = false;
};


//Checkt ob die aktuelle Seite eine Board Seite ist indem es mit items im items array abgeglichen wird
//Ist true wenn auf einer BoardSeite und false wenn nicht
const isBoardPage = computed(() =>{
  const boardRoutes = items.value.map(item => item.route);
  return boardRoutes.includes(route.path);
});

const handleLogout = () => {
  authStore.logout();
  router.push("/");
};





console.log("Kategorie vor Übergabe an PostForm:", currentCategory.value);

const handleCreateChat = async () => {
  if (!initialMessage.value.trim()) {
    console.warn("Leere Nachricht kann nicht gesendet werden.");
    return;
  }

  if (!currentUserId.value) {
    console.error("Kein gültiger Benutzer gefunden!");
    return;
  }

  try {
    const secondUserId = 2; // Replace with logic to get recipient's user ID
 
    // Create the chat first
    const newChat = await chatStore.createChat({
      firstUserId: currentUserId.value,
      secondUserId,
    });

    if (!newChat?.id) {
      console.error("Fehler beim Erstellen des Chats: Keine Chat-ID zurückgegeben.");
      return;
    }

    console.log("Neuer Chat erstellt:", newChat.id);

    // Send the initial message **immediately** after chat creation
    const messageResponse = await chatStore.sendMessage({
      chatId: newChat.id,
      text: initialMessage.value,
      senderId: currentUserId.value,
    });

    if (!messageResponse) {
      console.error("Fehler beim Senden der Nachricht.");
      return;
    }

    console.log("Nachricht erfolgreich gesendet!");

    // Navigate to the newly created chat
    router.push({
      name: "ChatView",
      params: { id: newChat.id },
      query: { senderId: currentUserId.value },
    });

    // Close the modal & reset input field
    showNewChatModal.value = false;
    initialMessage.value = "";
  } catch (error) {
    console.error("Fehler beim Erstellen des Chats oder beim Senden der Nachricht:", error);
  }
};
 
 
 
console.log("Kategorie vor Übergabe an PostForm:", currentCategory.value);
 
 
 
 
 
 





</script>


<template>

  <!--Navbar-->
  <v-app-bar :elevation="2" rounded color="primary">
    <template v-slot:prepend>
      <!--Bei Klick auf bar-nav-icon wird toggleDrawer Methode aufgerufen-->
      <v-app-bar-nav-icon @click="toggleDrawer"></v-app-bar-nav-icon>
    </template>
    <v-app-bar-title>Menü</v-app-bar-title>
    
    <v-app-bar-title>{{ currentCategory || "Keine Kategorie"}}</v-app-bar-title>

    <template v-if="isBoardPage">
      <v-btn icon @click="showForm = true">
        <v-icon class="plus-icon"> mdi-plus-circle</v-icon>
      </v-btn>
<!-- Öffnen der PostForm Komponent in einem Dialog -->
   
      <v-dialog v-model="showForm" max-width="500" scrollable>
        <template v-slot:default="{close}">
          <v-card style="max-height: 80vh; overflow-y: auto;">
          <PostForm 
          :category="currentCategory"
          @close="handleClose"
          :facilityId="userPostStore.selectedFacilityId">
        </PostForm>
      </v-card>
        </template>
      
    </v-dialog>

    </template>

    




  </v-app-bar>
  <!--Linkes Seitenmenü ausklappbar-->
  <v-navigation-drawer 
    v-if="showDrawer" 
    app
    :style="{ width: width }" 
    color="grey-darken-2" 
    class="custom-drawer">



    <!-- Inhalte des Navigation Drawers -->
    <v-list class="drawer-content">
      <v-list-item :to="{ path: '/user/home'}">
        <v-list-item-title>Startseite</v-list-item-title>
      </v-list-item>


      <v-list-group v-model="isBoardsOpen">
        <template v-slot:activator="{props}">
        <v-list-item v-bind="props">
          <v-list-item-title @click="handleBoardClick">
            Boards
          </v-list-item-title>
        </v-list-item>
        </template>


        <template v-slot>
          <v-list-item 
          v-for="item in items"
          :key="item.title"
          :title="item.title"
          :class="{'v-list-item--active': activeItem === item.title}"
          @click="setActiveItem(item)">
        </v-list-item>
          
        </template>
      </v-list-group>
      <v-list-item>Chat</v-list-item>
      <v-list-item :to="{ path: '/aboutUs'}">Über Uns</v-list-item>
      <v-list-item :to="{ path: '/contact'}">Kontakt</v-list-item>


    </v-list>

    <div class="logout-container">
    <v-btn icon @click="handleLogout" >
        <v-icon class="logout-icon" color="red">mdi-logout</v-icon>
      </v-btn>
    </div>
  </v-navigation-drawer>
</template>


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