<script setup>
import { computed, onMounted } from "vue";
import { useHouseStore } from "@/stores/Admin/houseStore";
import { useRoute } from "vue-router";
import AdminPostsView from '@/components/Admin/AdminPosts.vue';
import { useAdminPostStore } from "@/stores/Admin/adminPostStore";


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


// 🛠 Das aktuelle Haus abrufen
const house = computed(() => houseStore.houses.find(h => h.id == houseId.value));
</script>

<template>
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
