<script setup>
import { computed, onMounted } from "vue";
import { useHouseStore } from "@/stores/Admin/houseStore";
import { useRoute } from "vue-router";
import AdminPostsView from '@/components/Admin/AdminPosts.vue';
import { useAdminPostStore } from "@/stores/Admin/adminPostStore";
import viennaHouseImage1 from '@/assets/Pictures/ViennaHouse1.jpg';
import viennaHouseImage2 from '@/assets/Pictures/ViennaHouse2.jpg';
import viennaHouseImage3 from '@/assets/Pictures/ViennaHouse3.jpg';

const props = defineProps({
  houseId: Number
})

const route = useRoute();
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


//Bilder setzen je nach Haus
const houseImage = computed(() => {
  switch (houseId.value) {
    case 1:
      return viennaHouseImage1;
    case 2:
      return viennaHouseImage2;
    case 3:
      return viennaHouseImage3;
    default:
      return viennaHouseImage1;
  }
});



</script>

<template>
  <div class="header-container">
    <!-- Hintergrundbild -->
    <v-img class="header-image" :src="houseImage" cover></v-img>

    <!-- Hausdetails über dem Bild -->
    <div class="house-details">
      <v-card v-if="house" class="house-card">
        <div class="house-info-row">
          <p><strong>Adresse:</strong> {{ house.address }}</p>
          <p><strong>Tops:</strong> {{ house.residents.length }}</p>
          <p><strong>Einrichtungen:</strong> {{ house.facilities.length }}</p>
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
  height: 400px;
}

.header-image {
  position: relative;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
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
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  /* 3 gleichmäßige Spalten */
  gap: 300px;
  /* Abstand zwischen den Spalten */
  text-align: center;
}

/*Anpassung für kleine Bildschirme */
@media (max-width: 768px) {
  .house-info-row {
    grid-template-columns: 1fr;
    /* Eine Spalte, um die Inhalte untereinander zu setzen */
    gap: 16px;
    /* Weniger Abstand */
  }
}

.house-info-row p {
  margin: 0;
  font-size: 16px;
  font-weight: 500;
}
</style>
