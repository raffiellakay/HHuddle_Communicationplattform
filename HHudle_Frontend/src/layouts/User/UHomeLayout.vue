<script setup>
import { useChatStore } from "@/stores/User/chatStore";
import { useRoute, useRouter } from "vue-router";
import { ref, watch, onMounted, onUnmounted } from "vue";
import { computed } from "vue";
import PostForm from "@/components/User/PostForm.vue";
import { useAuthStore } from "@/stores/authStore";
import { useUserPostStore } from "@/stores/User/userPostStore";
import { getHouseImageById } from "@/utils/helpers";
import { useHouseStore } from "@/stores/Admin/houseStore";
import hhuddle from '@/assets/Pictures/hhuddle.png';
import HouseGif from '@/assets/HouseGif.gif';
import Frame1Gif from '@/assets/Frame1Gif.png';

const userPostStore = useUserPostStore();
const router = useRouter(); //Gibt Router Instanz zurück
const route = useRoute(); // Gibt aktuelle Route zurück
const authStore = useAuthStore();
const chatStore = useChatStore();
const houseStore = useHouseStore();

const currentImage = ref(HouseGif);


const switchToStaticImage = () => {
  setTimeout(() => {
    currentImage.value = Frame1Gif; // Wechselt zum statischen Bild nach dem GIF
  }, 1166.5); //Delay für Gif Dauer
};

//HausId über User
const houseId = computed(() => authStore.user.houseId);

//Aktuelles Haus
const house = computed(() =>
  houseStore.houses.find((h) => h.id == houseId.value)
);

//HausImage über imageURL
const houseImage = computed(() => {
  return house.value?.imageUrl || getHouseImageById(houseId.value);
});

const showDrawer = ref(false);
const activeItem = ref(null);
const isBoardsOpen = ref(false);
const showForm = ref(false);
const currentUserId = computed(() => authStore.user?.id);

//Aktueller Seitenname
const currentPageTitle = computed(() => {
  const currentItem = items.value.find((item) => item.route === route.path);
  return currentItem ? currentItem.title : "Deine Chats";
});

const currentPageDescription = computed (() => {
  const currentItem = items.value.find((item) => item.route === route.path);
  return currentItem ? currentItem.description : "Chatverlauf";
})

//////////////////////////// CODE SNIPPET Start/////////////////////////////////

// Kategorien werden aus route.path abgeleitet und gemapped
const categoryMap = {
  "user/home": "FRONTPAGE",
  "/user/board/commonrooms": "EVENTS",
  "/user/board/blackboard": "BLACKBOARD",
  "/user/board/packagefinder": "PACKAGE",
  "/user/board/search&find": "EXCHANGE",
};

// Aktuelle Kategorie bestimmen und im userPostStore speichern
const currentCategory = computed(() => userPostStore.currentCategory);

// List Items
const items = ref([
  { title: "Startseite", 
    route: "/user/home", 
    icon: "mdi-home",
    description: "Ankündigungen der Hausverwaltung",
    isBoardPage: false,
  },
  {
    title: "Gemeinschaftsräume",
    route: "/user/board/commonrooms",
    icon: "mdi-account-group",
    description: "Nutzung der Gemeinschaftsräume",
    isBoardPage: true,
  },
  {
    title: "Schwarzes Brett",
    route: "/user/board/blackboard",
    icon: "mdi-bulletin-board",
    description: "Verschiedene Ankündigungen",
    isBoardPage: true,
  },
  {
    title: "Paketfinder",
    route: "/user/board/packagefinder",
    icon: "mdi-package-variant-closed",
    description: "Wo ist mein Paket?",
    isBoardPage: true,
  },
  {
    title: "Suche & Biete",
    route: "/user/board/search&find",
    icon: "mdi-swap-horizontal",
    description: "Suche - Biete- Tausche - Verschenke",
    isBoardPage: true,
  },
  { 
    title: "Deine Chats", 
    route: "/user/board/chatlist", 
    icon: "mdi-message",
    description: "Deine Chats",
    isBoardPage: false,
  },
  { title: "Kontakt", route: "/user/contact", icon: "mdi-mail", isBoardPage: false },
  { title: "Über Uns", route: "/user/about", icon: "mdi-information", isBoardPage: false },
]);

// Responsives Verhalten
const isMobile = computed(() => window.innerWidth < 960);

const updateDrawerState = () => {
  showDrawer.value = !isMobile.value;
};

onMounted(() => {
  updateDrawerState();
  switchToStaticImage();
  window.addEventListener("resize", updateDrawerState);
});



onUnmounted(() => {
  window.removeEventListener("resize", updateDrawerState);
});

// Prüft, ob die aktuelle Route eine Board-Seite ist
const isBoardPage = computed(() =>
  items.value.some((item) => item.route === route.path && item.isBoardPage)
);

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

/*Route beobachten und Kategorie setzen
Falls sich Route ändert, wird die passende Kategorie aus categoryMap gesucht
Dann wird im userPostStore die setCategory Methode aufgerufen um die category im Store zu aktualisieren

*/
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
      <v-app-bar app class="navbar" elevation="0">
        <template v-slot:prepend>
          <v-app-bar-nav-icon
            v-if="isMobile"
            @click="toggleDrawer"
          ></v-app-bar-nav-icon>
        </template>

        <v-toolbar-title class="category-text">
          {{ currentPageTitle }}
        </v-toolbar-title>

          <v-spacer></v-spacer>

        <!-- Zentriertes Bild -->
         <div class="navbar-image-container">
        <v-img 
          :src="hhuddle" 
          class="navbar-image"
          width="200"
          height="50"
        ></v-img>
      </div>

        <v-spacer></v-spacer>

        <template v-if="isBoardPage">
          <v-tooltip text="Neuen Post Erstellen">
            <template v-slot:activator="{ props }">
              <v-btn icon @click="showForm = true" v-bind="props">
                <v-icon class="plus-icon" aria-label="Post hinzufügen"
                  >mdi-note-plus</v-icon
                >
              </v-btn>
            </template>
          </v-tooltip>

          <!-- PostForm Dialog -->
          <v-dialog v-model="showForm" max-width="500" scrollable>
            <v-card style="max-height: 80vh; overflow-y: auto">

              <!-- Hier wird currentCategory als prop an die PostForm Komponente übergeben damit Komponente weiß, welche Art von post gerade erstellt wird   -->
              <PostForm
                :category="currentCategory"
                @close="handleClose"
                :facilityId="userPostStore.selectedFacilityId"
              />
            </v-card>
          </v-dialog>
        </template>
      </v-app-bar>

      <!-- Sidebar -->
      <v-navigation-drawer
        :permanent="!isMobile"
        v-if="!isMobile || showDrawer"
        app
        class="sidebar"
      >
        <v-container class="text-center mt-5">
          <v-img 
            :src="currentImage" 
            class="house-gif"
            @load="switchToStaticImage"
          />
        </v-container>

        <v-list dense>
            
            <v-list-item
              v-for="item in items"
              :key="item.title"
              
              @click="setActiveItem(item)"
              class="sidebar-titles"
            >
            <v-list-item-title>
                {{ item.title }}
            </v-list-item-title>
              <template v-slot:prepend>
                <v-icon class="sidebar-icons">
                  {{ item.icon }}
                </v-icon>
              </template>

           
            </v-list-item>
         
        </v-list>

        <v-spacer></v-spacer>

        <!--Logout-->
        <div class="logout-container">
          <v-list-item link @click="handleLogout">
           
              <v-icon class="logout-icon">mdi-logout</v-icon>
            
          </v-list-item>
        </div>
      </v-navigation-drawer>

      <!--BoardViews-->
      <v-main class="main-content">
        <v-container fluid class="full-height full-width pa-0">
          <v-img class="house-image" :src="houseImage" cover> </v-img>
          <v-toolbar class="description-bar">
            <v-toolbar-title> {{ currentPageDescription }}</v-toolbar-title>
          </v-toolbar>
          <RouterView />
          
        </v-container>
      </v-main>

      <v-footer></v-footer>
    </v-layout>
  </v-app>
</template>

<style scoped>
.navbar {
  background-color: white;
  color: black;
 
}

.navbar-image-container {
  position: absolute;
  margin-left: 50%;
  transform: translateX(-50%);
        
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
  background-color: #eae7dc;
  color: black;
  
}

.house-gif {
  height: 50px;
  width: auto;
}

.sidebar-titles .v-list-item-title {
  font-size: 14px;
  font-weight: 500;
}




.description-bar {
  background-color: #dc796e;
  color: white;
}

.main-content {
  display: flex;
  flex-grow: 1;
  min-height: calc(100% - 64px);
  background-color: #f1f0ef;
  height: 100%;
  width: 100%;
}

.full-height {
  height: 100%;
}

.full-width {
  max-width: 100%;
}

.house-image {
  width: 100%;
  max-height: 350px;
  object-fit: cover;
}

/* Logout-Button am unteren Rand */
.logout-container {
  align-self: flex-end;
  width: 100%;
  padding-bottom: 10px;
}

.plus-icon {
  color: #e85a4f;
  font-size: 30px;
  padding-right: 20px;
}

.logout-icon {
  color: #e85a4f;
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
