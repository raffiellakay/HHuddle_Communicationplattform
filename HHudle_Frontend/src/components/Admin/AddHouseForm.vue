<script setup>
import { ref, defineEmits } from 'vue';
import { useHouseStore } from '@/stores/Admin/houseStore';

const houseStore = useHouseStore();

const emit = defineEmits(["close"]);
const newHouse = ref({
  address: '',
  residents: [],
  facilities: [],
});

const newFacility = ref({
  type: '',
  description: '',
});

// Neues Haus anlegen
async function saveHouse() {
  try {
    await houseStore.createHouse(newHouse.value);
    emit("close"); // Dialog schließen
    newHouse.value = { address: '', facilities: [] };
    newFacility.value = { type: '', description: '' };
  } catch (error) {
    console.error('Error while saving house:', error);
  }
}

// Einrichtung hinzufügen
function addFacility() {
  if (newFacility.value.type) {
    newHouse.value.facilities.push({ ...newFacility.value });
    newFacility.value = { type: '', description: '' };
  }
}
</script>

<template>
  <v-card>
    <v-card-title>Neues Haus hinzufügen</v-card-title>
    <v-card-text>
      <v-form @submit.prevent="saveHouse">
        <v-text-field v-model="newHouse.address" label="Adresse des Hauses" required></v-text-field>

        <v-divider></v-divider>
        <v-card-subtitle>Einrichtungen hinzufügen</v-card-subtitle>
        <v-text-field v-model="newFacility.type" label="Art der Einrichtung" required></v-text-field>
        <v-text-field v-model="newFacility.description" label="Beschreibung" required></v-text-field>
        <v-btn color="#E98074" @click="addFacility">Einrichtung hinzufügen</v-btn>

        <v-divider></v-divider>
        <div v-for="(facility, index) in newHouse.facilities" :key="index">
          <p>{{ index + 1 }}. {{ facility.type }}: {{ facility.description }}</p>
        </div>
      </v-form>
    </v-card-text>
    <v-card-actions>
      <v-btn text @click="$emit('close')">Abbrechen</v-btn>
      <v-container/>
      <v-btn text color="#E98074" @click="saveHouse">Speichern</v-btn>
    </v-card-actions>
  </v-card>
</template>