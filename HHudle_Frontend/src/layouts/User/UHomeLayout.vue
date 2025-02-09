<script setup>


import { useChatStore } from "@/stores/User/chatStore";
import { useRoute, useRouter } from 'vue-router'
import { ref, watch, onMounted, onUnmounted } from 'vue'
import { computed } from 'vue';
import PostForm from '@/components/User/PostForm.vue';
import { useAuthStore } from '@/stores/authStore';
import { useUserPostStore } from '@/stores/User/userPostStore';


const userPostStore = useUserPostStore();
const router = useRouter(); //Gibt Router Instanz zurück
const route = useRoute(); // Gibt aktuelle Route zurück 
const authStore = useAuthStore();
const chatStore = useChatStore();




const showDrawer = ref(false);
const activeItem = ref(null);
const isBoardsOpen = ref(false);
const showForm = ref(false);
const currentUserId = computed(() => authStore.user?.id);





// Kategorien für die Routen
const categoryMap = {
  "user/home": "FRONTPAGE",
  "/user/board/commonrooms": "EVENTS",
  "/user/board/blackboard": "BLACKBOARD",
  "/user/board/packagefinder": "PACKAGE",
  "/user/board/search&find": "EXCHANGE",
};

// Aktuelle Kategorie bestimmen
const currentCategory = computed(() => userPostStore.currentCategory);

// List Items
const items = ref([
  { title: "Startseite", route: "/user/home", icon:"mdi-home" },
  { title: "Gemeinschaftsräume", route: "/user/board/commonrooms", icon: "mdi-account-group" },
  { title: "Schwarzes Brett", route: "/user/board/blackboard", icon: "mdi-bulletin-board" },
  { title: "Paketfinder", route: "/user/board/packagefinder", icon: "mdi-package-variant-closed" },
  { title: "Suche & Biete", route: "/user/board/search&find", icon: "mdi-swap-horizontal" },
  { title: "Chats", route: "/user/board/chatlist", icon: "mdi-message" },
  { title: "Kontakt", route: "/contact", icon: "mdi-mail"},
  { title: "Über Uns", route: "/aboutUs", icon: "mdi-information" },

]);

// Responsives Verhalten
const isMobile = computed(() => window.innerWidth < 960);

const updateDrawerState = () => {
  showDrawer.value = !isMobile.value;
};

onMounted(() => {
  updateDrawerState();
  window.addEventListener("resize", updateDrawerState);
});

onUnmounted(() => {
  window.removeEventListener("resize", updateDrawerState);
});

// Prüft, ob die aktuelle Route eine Board-Seite ist
const isBoardPage = computed(() => items.value.some((item) => item.route === route.path));

// Drawer umschalten (Nur für Mobile!)
const toggleDrawer = () => {
  if (isMobile.value) {
    showDrawer.value = !showDrawer.value;
  }
};

// Aktives Board setzen (Seitenmenü bleibt immer sichtbar)
const setActiveItem = (item) => {
  activeItem.value = item.title;
  isBoardsOpen.value = true;
  router.push({ path: item.route });
};

// Logout-Funktion
const handleLogout = () => {
  authStore.logout();
  router.push("/");
};

// Post-Form schließen
const handleClose = () => {
  showForm.value = false;
};

// Route beobachten und Kategorie setzen
watch(
  () => route.path,
  () => {
    userPostStore.setCategory(categoryMap[route.path] || null);
  },
  { immediate: true }
);

console.log("Kategorie vor Übergabe an PostForm:", currentCategory.value);






</script>


<template>
  <v-app>
    <v-layout>
      <!-- Navbar -->
      <v-app-bar app class="navbar" fixed>
        <template v-slot:prepend>
          <v-app-bar-nav-icon v-if="isMobile" @click="toggleDrawer"></v-app-bar-nav-icon>
        </template>
        <v-toolbar-title class="text-white"> Kategorie </v-toolbar-title>

        <template v-if="isBoardPage">
          <v-btn icon @click="showForm = true">
            <v-icon class="plus-icon" aria-label="Post hinzufügen">mdi-note-plus</v-icon>
          </v-btn>

          <!-- PostForm Dialog -->
          <v-dialog v-model="showForm" max-width="500" scrollable>
            <v-card style="max-height: 80vh; overflow-y: auto;">
              <PostForm 
                :category="currentCategory"
                @close="handleClose"
                :facilityId="userPostStore.selectedFacilityId"
              />
            </v-card>
          </v-dialog>
        </template>
      </v-app-bar>

      <!-- Sidebar (immer sichtbar auf Desktop) -->
      <v-navigation-drawer :permanent="!isMobile" v-if="!isMobile || showDrawer" app class="sidebar">
        <v-container class="text-center mt-5">
          <h3 class="font-weight-bold text-black mt-3">HHuddle</h3>
        </v-container>

        <v-list dense>
          <v-list-item-group>
            <v-list-item
              v-for="item in items"
              :key="item.title"
              :title="item.title"
              @click="setActiveItem(item)"
            >
            
              <template v-slot:prepend>
              <v-icon class="sidebar-icons">
                {{ item.icon }}
              </v-icon>
            </template>
            
            <v-list-item-content>
              
            </v-list-item-content>
          </v-list-item>
          </v-list-item-group>
        </v-list>

        <v-spacer></v-spacer>
   

        <!--Logout-->
        <div class="logout-container">
          <v-list-item link @click="handleLogout">
            <v-list-item-icon>
              <v-icon class="logout-icon">mdi-logout</v-icon>
            </v-list-item-icon>
    
          </v-list-item>
        </div>
      </v-navigation-drawer>

      <!--BoardViews-->
      <v-main class="main-content">
        <v-container class="content">
          <RouterView />
        </v-container>
      </v-main>
    </v-layout>
  </v-app>
</template>

<style scoped>

.navbar {
  background-color: #8E8D8A
}

.v-layout {
  display: flex;
  min-height: 100vh;
}


.sidebar {
  width: 250px;
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #EAE7DC ;
}


.main-content {
  flex-grow: 1;
  overflow-y: auto;
  padding: 20px; 
  background-color: white;
}


.content {
  max-width: 100%;
  margin-left: 0; 
  padding-left: 0;
}

/* Logout-Button am unteren Rand */
.logout-container {
  align-self: flex-end;
  width: 100%;
  padding-bottom: 10px;
}

.logout-icon {
  color: #E85A4F;
}
/*Mobile*/
@media (max-width: 960px) {
  .sidebar {
    width: 0;
    display: none;
  }
  .main-content {
    margin-left: 0;
  }
}
</style>