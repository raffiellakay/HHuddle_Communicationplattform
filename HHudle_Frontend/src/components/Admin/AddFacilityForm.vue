<script setup>
import { ref, computed } from 'vue';
import { useFacilityStore } from '@/stores/Admin/facilityStore';
import { useRoute } from 'vue-router';

const route = useRoute();
const facilityStore = useFacilityStore();
const houseId = computed(() => Number(route.params.houseId));


const emit = defineEmits(["close"]);


const newFacility = ref({
  type: '',
  description: '',
  houseId: houseId.value
});

async function saveNewFacility() {
  try {
    await facilityStore.createFacility(newFacility.value);
    await facilityStore.getAllFacilitiesByHouseId(houseId.value);
    emit("close"); // Dialog schließen
    newFacility.value = { type: '', description: '', houseId: houseId.value };
  } catch (error) {
    console.error('Fehler beim Speichern der Einrichtung:', error);
  }
}
</script>

<template>
  <v-card>
    <v-card-title>Einrichtung hinzufügen</v-card-title>
    <v-card-text>
      <v-form @submit.prevent="saveNewFacility">
        <v-text-field v-model="newFacility.type" label="Art der Einrichtung" required></v-text-field>
        <v-text-field v-model="newFacility.description" label="Beschreibung der Einrichtung" required></v-text-field>
      </v-form>
    </v-card-text>
    <v-card-actions>
      <v-btn text @click="$emit('close')">Abbrechen</v-btn>
      <v-btn text color="primary" @click="saveNewFacility">Speichern</v-btn>
    </v-card-actions>
  </v-card>
</template>
