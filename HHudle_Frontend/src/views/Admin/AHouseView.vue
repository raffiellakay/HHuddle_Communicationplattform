<script setup>
import { computed, onMounted } from "vue";
import { useHouseStore } from "@/stores/Admin/houseStore";
import { useRoute, useRouter } from "vue-router";
import AdminPostsView from '@/components/Admin/AdminPosts.vue';
import { useAdminPostStore } from "@/stores/Admin/adminPostStore";

import { getHouseImageById } from '@/utils/helpers';

const props = defineProps({
  houseId: Number
})

const route = useRoute();
const router = useRouter();

const houseStore = useHouseStore();
const adminPostStore = useAdminPostStore();

// 🛠 HouseId aus der URL holen
const houseId = computed(() => Number(route.params.houseId));

onMounted(async () => {
  if (houseId.value) {
    await houseStore.getHouseById(houseId.value);
    await adminPostStore.getAdminPostsByHouseId(houseId.value);
  }
});


const refreshPosts = async () => {
  if (houseId.value) {
    await adminPostStore.getAdminPostsByHouseId(houseId.value);
  }
};


//Das aktuelle Haus abrufen
const house = computed(() => houseStore.houses.find(h => h.id == houseId.value));


const houseImage = computed(() => {
  return house.value?.imageUrl || getHouseImageById(houseId.value);
});

// Navigiere zur Residents-Seite
const goToResidents = (houseId) => {
  router.push(`/admin/house/${houseId}/user`);
};

// Navigiere zur Facilities Seite

const goToFacilities = (houseId) => {
  router.push(`/admin/house/${houseId}/facilities`);
};

// zu Post/startseite

const goToOverview = (houseId) => {
  router.push(`/admin/house/${houseId}`)
};


</script>

<template>
  <div class="header-container">
 
    <!-- Hintergrundbild -->
   <v-img 
    class="header-image" 
    :src="houseImage" 
    cover
    height="400px">
  </v-img>


    <!-- Hausdetails über dem Bild -->
    <div class="house-details">
      <v-card v-if="house" class="house-card">
        <div class="house-info-row">
          <div @click="goToOverview(house.id)" class="clickable-text">
            <p><strong>Adresse:</strong> {{ house.address }}</p>
          </div>

          <!--Klickbare "Tops" (Residents) -->
          <div @click="goToResidents(house.id)" class="clickable-text">
            <p><strong>Tops:</strong> {{ house.residents.length }}</p>
          </div>

          <!--Klickbare "Einrichtungen" (Facilities) -->
          <div @click="goToFacilities(house.id)" class="clickable-text">
            <p><strong>Einrichtungen:</strong> {{ house.facilities.length }}</p>
          </div>

        </div>
      </v-card>
      <v-alert v-else type="warning">Haus nicht gefunden!</v-alert>
    </div>
  </div>



  <v-container>
    <AdminPostsView :houseId="houseId" @adminPost-added="refreshPosts" />
  </v-container>
</template>

<style scoped>
.header-container {
  position: relative;
  width: 100vw;
  max-width: 100%;
  min-height: 400px; 
  overflow: hidden;
  
}

.header-image {
  width: auto;
  height: auto;
  object-fit: cover;
  display:block;

}

.house-details {
  position: absolute;
  bottom: 15px;
  left: 50%;
  transform: translateX(-50%);
  width: 95%;
  max-width: 100vw;
}

.house-card {
  background: rgba(255, 255, 255, 0.7);
  padding: 16px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
}


.house-info-row {
  display: flex;
  justify-content: space-around;
  gap: 300px;
  text-align: center;
}

/*Anpassung für kleine Bildschirme */
@media (max-width: 768px) {
  .house-info-row {
    grid-template-columns: 1fr;
    gap: 16px;
    
  }
}

.house-info-row p {
  margin: 0;
  font-size: 16px;
  font-weight: 500;
}

.clickable-text {
  color: black; /* Standardfarbe */
  text-decoration: underline;
  cursor: pointer;
  transition: color 0.3s ease-in-out; /* Weiche Farbänderung beim Hover */
}

.clickable-text:hover {
  color: blue; /* Farbe beim Hover */
}
</style>
