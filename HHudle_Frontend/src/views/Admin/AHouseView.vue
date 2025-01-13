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
  <v-img
    class="header-image"
    :src="houseImage"
    cover></v-img>


  <v-container>
    
    <h1>Haus Details</h1>
    
    <v-card v-if="house">
      <v-card-title>{{ house.address }}</v-card-title>
      <v-card-text>
        <p><strong>Bewohner:</strong> {{ house.residents.join(", ") }}</p>
        <p><strong>Einrichtungen:</strong> {{ house.facilities.join(", ") }}</p>
      </v-card-text>
    </v-card>

    <v-alert v-else type="warning">Haus nicht gefunden!</v-alert>
  </v-container>

<v-container>
    <AdminPostsView :houseId="houseId" @adminPost-added="refreshPosts"/>
</v-container>

</template>

<style scoped>

.header-image {
  position: relative;
  top: 0;
  left: 0;
  width: 100vw; /* Volle Breite */
  height: 400px; /* Oder `100vh` für Fullscreen */
  object-fit: cover; /* Stellt sicher, dass das Bild nicht verzerrt wird */

}

</style>
