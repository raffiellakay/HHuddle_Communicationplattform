<script setup>
import { ref, computed, defineEmits } from 'vue';
import { useUserStore } from '@/stores/Admin/userStore';
import { useRoute } from 'vue-router';
import { useHouseStore } from '@/stores/Admin/houseStore';

const route = useRoute();
const userStore = useUserStore();
const houseStore = useHouseStore();
const houseId = computed(() => Number(route.params.houseId));

const emit = defineEmits(["close"]);


const newResident = ref({
  mail: '',
  username: '',
  houseId: houseId.value,
});

async function saveResident() {
  try {
    await userStore.createUser(newResident.value);
    await houseStore.getAllHouses();
    await userStore.getAllUsersByHouseId(houseId.value);
    emit("close"); // Dialog schließen
    newResident.value = { mail: '', username: '', houseId: houseId.value };
  } catch (error) {
    console.error('Fehler beim Speichern des Bewohners:', error);
  }
}
</script>

<template>
  <v-card>
    <v-card-title>Bewohner*in hinzufügen</v-card-title>
    <v-card-text>
      <v-form @submit.prevent="saveResident">
        <v-text-field v-model="newResident.mail" label="E-Mail" required></v-text-field>
        <v-text-field v-model="newResident.username" label="Top" required></v-text-field>
      </v-form>
    </v-card-text>
    <v-card-actions>
      <v-btn text @click="$emit('close')">Abbrechen</v-btn>
      <v-container/>
      <v-btn text color="#E98074" @click="saveResident">Speichern</v-btn>
    </v-card-actions>
  </v-card>
</template>
