<script setup>
import { useRoute, useRouter } from 'vue-router';
import { ref, computed, watch, onMounted, onUnmounted } from 'vue';
import { useAuthStore } from '@/stores/authStore';
import AdminPostForm from '@/components/Admin/AddAdminPostForm.vue';
import { useHouseStore } from '@/stores/Admin/houseStore';
import AddFacilityForm from '@/components/Admin/AddFacilityForm.vue';
import AddHouseForm from '@/components/Admin/AddHouseForm.vue';
import AddAdminPostForm from '@/components/Admin/AddAdminPostForm.vue';
import AddResidentForm from '@/components/Admin/AddResidentForm.vue';
import hhuddle from '@/assets/Pictures/hhuddle.png';

const authStore = useAuthStore();
const houseStore = useHouseStore();
const router = useRouter();
const route = useRoute();

const showDrawer = ref(false);

const houseId = computed(() => Number(route.params.houseId) || null);
const houseAddress = ref("Hausadresse");

const isMobile = computed(() => window.innerWidth < 960);
const updateDrawerState = () => { showDrawer.value = !isMobile.value; };

//Refs für Forms
const showAdminPostForm = ref(false);
const showHouseForm = ref(false);
const showResidentsForm = ref(false);
const showFacilitiesForm = ref(false);


//Icons je nach Seite auf Basis der Route 


const currentIcon = computed(() => {
    if (route.path === "/admin/home") 
        return { icon: "mdi-home-plus", form: "house", tooltip: "Neues Haus hinzufügen" };
    if (route.path.startsWith("/admin/house") && !route.path.includes("/user") && !route.path.includes("/facilities")) 
        return { icon: "mdi-post", form: "adminPost", tooltip: "Neue Ankündigung erstellen" };
    if (route.path.includes("/user")) 
        return { icon: "mdi-account-plus", form: "residents", tooltip: "Bewohner*in hinzufügen" };
    if (route.path.includes("/facilities")) 
        return { icon: "mdi-domain", form: "facilities", tooltip: "Neue Einrichtung hinzufügen" };
    return null;
});

//Öffnet richtiges Formular wenn Icon angeklickt wird
const openForm = () => {
    if (!currentIcon.value) return;
    switch (currentIcon.value.form) {
        case "house":
            showHouseForm.value = true;
            break;
        case "adminPost":
            showAdminPostForm.value = true;
            break;
        case "residents":
            showResidentsForm.value = true;
            break;
        case "facilities":
            showFacilitiesForm.value = true;
            break;
    }
};

onMounted(() => {
    updateDrawerState();
    window.addEventListener("resize", updateDrawerState);
});

onUnmounted(() => {
    window.removeEventListener("resize", updateDrawerState);
});

/*Sidebar-Menüpunkte */
const adminItems = ref([
    { title: "Häuser", 
    route: "/admin/home", 
    icon: "mdi-home" },

    { title: "Über Uns", 
    route: "/admin/about", 
    icon: "mdi-information" 
    },

    { title: "Kontakt", 
    route: "/admin/contact", 
    icon: "mdi-mail" 
    },
]);


/*Lädt die Adresse des Hauses, wenn sich houseId ändert und lädt die Adresse asynchron aus Store*/
const loadHouseAddress = async () => {
    if (houseId.value) {
        try {
            const house = await houseStore.getHouseById(houseId.value); // Falls asynchron
            houseAddress.value = house?.address || "Hausadresse";
        } catch (error) {
            console.error("Fehler beim Laden der Hausadresse:", error);
        }
    } else {
        houseAddress.value = "Hausadresse";
    }
};

/* Beobachte houseId, um die Adresse zu aktualisieren. Lädt immer neu wenn sich Haus ändert*/
watch(houseId, () => {
    loadHouseAddress();
}, { immediate: true });


/* Berechnung der aktuellen Navbartitle */
const currentNavbarTitle = computed(() => {
    if (route.path.startsWith("/admin/house")) {
        if (route.path.includes("/user")) 
            return "Bewohner*innen";
        if (route.path.includes("/facilities")) 
            return "Einrichtungen";
        return houseAddress.value; 
    }

    else if (route.path.startsWith("/admin")) {
        if(route.path.includes("/home"))
            return "Häuser";
        if(route.path.includes("/about"))
            return "Über Uns";
        if(route.path.includes("/contact"))
            return "Kontakt";
    }
    return "Dashboard"; //Standardwert
});




/*Post-Formular nur auf Admin-Hausseiten sichtbar */
const isPostPage = computed(() => route.path.includes('/admin/house'));

/*Kategorien für die Routen */
const categoryMap = {
    "/admin/home": "HÄUSER",
    "/aboutUs": "ÜBER UNS",
    "/contact": "KONTAKT",
};

/*Dynamische Kategorie basierend auf der Route setzen */
const currentCategory = ref(null);

watch(
    () => route.path,
    () => {
        currentCategory.value = categoryMap[route.path] || null;
    },
    { immediate: true }
);

/*Sidebar öffnen/schließen */
const toggleDrawer = () => { if (isMobile.value) showDrawer.value = !showDrawer.value; };
const handleLogout = () => { authStore.logout(); router.push('/'); };
</script>
  
<template>
    <v-app>
        <!--Navbar -->
        <v-app-bar app class="navbar" fixed>
            <template v-slot:prepend>
                <v-app-bar-nav-icon v-if="isMobile" @click="toggleDrawer"></v-app-bar-nav-icon>
            </template>

            <v-toolbar-title class="toolbar">
                {{ currentNavbarTitle}}
                
            </v-toolbar-title>
          
            <v-img
     :src= hhuddle
     />
   

            <!--Post-Formular nur auf bestimmten Seiten sichtbar -->
            <template v-if="currentIcon">
                <v-tooltip left>
                    <template v-slot:activator="{ props }">
                        <v-btn icon class="add-btn" v-bind="props" @click="openForm()">
                            <v-icon>{{ currentIcon.icon }}</v-icon>
                        </v-btn>
                    </template>
                    <span>{{ currentIcon.tooltip }}</span>
                </v-tooltip>
            </template>

            <!--Dialog für AdminPostForm -->
            <v-dialog v-model="showAdminPostForm" max-width="500">
                <v-card>
                    <AddAdminPostForm :houseId="houseId" @close="showAdminPostForm = false" />
                </v-card>
            </v-dialog>
      

        <!--Dialog für HousePostForm -->
        <v-dialog v-model="showHouseForm" max-width="500">
                <v-card>
                    <AddHouseForm @close="showHouseForm = false" />
                </v-card>
            </v-dialog>


             <!--Dialog für FacilityPostForm -->
        <v-dialog v-model="showFacilitiesForm" max-width="500">
                <v-card>
                    <AddFacilityForm @close="showFacilitiesForm = false" />
                </v-card>
            </v-dialog>

            

                <!--Dialog für ResidentPostForm -->
        <v-dialog v-model="showResidentsForm" max-width="500">
                <v-card>
                    <AddResidentForm @close="showResidentsForm = false" />
                </v-card>
            </v-dialog>



        </v-app-bar>

        <!--Sidebar + Hauptinhalt -->
        <div class="layout-container">
            <!--Sidebar -->
            <v-navigation-drawer class="sidebar" :permanent="!isMobile" v-if="!isMobile || showDrawer">
                <v-container class="text-center mt-5">
                    <h3 class="font-weight-bold sidebar-title">HHuddle</h3>
                </v-container>

                <v-list dense>
                    <v-list-item-group>
                        <v-list-item v-for="item in adminItems" :key="item.title" :title="item.title" :to="item.route">
                            <template v-slot:prepend>
                                <v-icon class="sidebar-icons">{{ item.icon }}</v-icon>
                            </template>
                        </v-list-item>
                    </v-list-item-group>
                </v-list>

                <v-spacer></v-spacer>

                <!--Logout Button -->
                <div class="logout-container">
                    <v-list-item link @click="handleLogout">
                        <v-list-item-icon>
                            <v-icon class="logout-icon">mdi-logout</v-icon>
                        </v-list-item-icon>
                    </v-list-item>
                </div>
            </v-navigation-drawer>

            <!--Hauptinhalt -->
            <v-main class="main-content">
                <v-container fluid>
                    <RouterView />
                </v-container>
            </v-main>
            </div>
        
    </v-app>
</template>

<style scoped>
/*Navbar */
.navbar {
    background-color: #D8C3A5;
    height: 64px;
    z-index: 1000;
    position: fixed;
    width: 100%;
    top: 0;
    left: 0;
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}

/*Flexbox für Sidebar + Hauptinhalt */
.layout-container {
    display: flex;
    min-height: calc(100vh - 64px); 
    width: 100%;
}

/*Sidebar */
.sidebar {
    width: 250px;
    height: calc(100vh - 64px);
    background-color: #EAE7DC;
    position: fixed;
    left: 0;
    top: 64px;
    z-index: 900;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    box-shadow: 2px 0px 10px rgba(0, 0, 0, 0.1);
}

/*Hauptinhalt */
.main-content {
    flex-grow: 1;
    background-color: #F8F4E3;
    overflow: auto; /* Verhindert abgeschnittenen Inhalt */
    min-height: 100%; /* Stellt sicher, dass es die gesamte Höhe füllt */
    display: flex;
    flex-direction: column; /* Ermöglicht flexibles Layout */
}

/* Sidebar-Text */
.sidebar-title {
    color: #8E8D8A;
}

/*Sidebar-Icons */
.sidebar-icons {
    color: #8E8D8A;
}

/*Buttons */
.add-btn {
    color: white;
    background-color: #E98074;
}

/*Logout-Button */
.logout-container {
    align-self: flex-end;
    width: 100%;
    padding-bottom: 10px;
}

.logout-icon {
    color: #E85A4F;
}



/*Mobile Ansicht */
@media (max-width: 960px) {
    .sidebar {
        display: none;
    }

    .main-content {
        margin-left: 0;
        width: 100%;
    }
}

</style>