<script setup>
import { useRoute, useRouter } from 'vue-router';
import { ref, computed, watch, onMounted, onUnmounted } from 'vue';
import { useAuthStore } from '@/stores/authStore';
import AdminPostForm from '@/components/Admin/AdminPostForm.vue';
import { useHouseStore } from '@/stores/Admin/houseStore';

const authStore = useAuthStore();
const router = useRouter();
const route = useRoute();

const showDrawer = ref(false);
const showAdminPostForm = ref(false);
const houseId = computed(() => Number(route.params.houseId) || null);

const isMobile = computed(() => window.innerWidth < 960);
const updateDrawerState = () => { showDrawer.value = !isMobile.value; };

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
    route: "/aboutUs", 
    icon: "mdi-information" 
    },

    { title: "Kontakt", 
    route: "/contact", 
    icon: "mdi-mail" 
    },
]);



const navbarTitles = ref([
    { title: "Hier muss addresse stehen", 
    route: "/admin/house/:houseId", 
    },

    { title: "Tops", 
    route: "/admin/house/:houseId/user", 
    },

    { title: "Einrichtungen", 
    route: "/admin/house/:houseId/facilities", 
    },
]);

/*Post-Formular nur auf Admin-Hausseiten sichtbar */
const isPostPage = computed(() => route.path.includes('/admin/house'));

/*Kategorien für die Routen */
const categoryMap = {
    "/admin/home": "HÄUSER",
    "/admin/house": "HAUSVERWALTUNG",
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

            <v-toolbar-title class="text-black">
                {{ currentCategory || 'Admin Dashboard' }}
            </v-toolbar-title>

            <!--Post-Formular nur auf bestimmten Seiten sichtbar -->
            <template v-if="isPostPage">
                <v-btn icon class="add-btn" @click="showAdminPostForm = true">
                    <v-icon>mdi-plus-circle</v-icon>
                </v-btn>
            </template>

            <!--Dialog für AdminPostForm -->
            <v-dialog v-model="showAdminPostForm" max-width="500">
                <v-card>
                    <AdminPostForm :houseId="houseId" @close="showAdminPostForm = false" />
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