<script setup>

import { ref, onMounted, computed } from 'vue';
import { useHouseStore } from '@/stores/Admin/houseStore';
import { useRouter } from 'vue-router';
import DeleteButton from '@/components/Icons/DeleteButton.vue';
import EditButton from '@/components/Icons/EditButton.vue';


//Zugriffe auf ...
const houseStore = useHouseStore();

const dialog = ref(false); // (dialog is a reaktiver Boolean, also ob´s fürs hinzufügen eines neuen Hauses geöffnet oder geschlossen is). //Dialogfensterung mit v-dialog aus Vuetify. Is auf true gesetzt, Dialogfenster wird angezeigt, is false, bleibts geschlossen. Also, wirds eben angeklickt oder nicht
                          //reaktive Variablen, sind Variablen die automatisch aktualisiert werden, wenn sich etwas an ihnen ändert (Ansict wird auto aktualisiert)
 
const router = useRouter();

const newHouse = ref({
  address: '', 
  residents: [],
  facilities: []
});


//beim Seitenstart werden alle Häuser geladen
onMounted(async () => {
  await houseStore.getAllHouses();
});

//neues Haus anlegen
async function saveHouse() {
  try {
    const houseData = {
      address: newHouse.value.address,
      residents: newHouse.value.residents,
      facilities: newHouse.value.facilities
    };

    await houseStore.createHouse(houseData);
    dialog.value = false; // Dialog schließen

    // Felder zurücksetzen - wichtig, dass Felder genauso aussehen wie oben --> leeres Array wenn Backend ein Set erwartet!
    newHouse.value = { address: '', residents: [], facilities: [] };
  } catch (error) {
    console.error('Fehler beim Speichern des Hauses:', error);
  }
}

//Sortiert die Häuser aufsteigend nach ID
const sortedHouses = computed(() => {
  return [...houseStore.houses].sort((a, b) => a.id - b.id);
});

//Soll bei Klick auf Kachel auf die jeweilige Hausseite navigieren
const goToHouse = (houseId) => {
  router.push(`/admin/house/${houseId}`);
};



</script>

<template>
  Ich bin die Admin Homeview

 <!-- Gesamtes Layout -->
 <v-container>
        <!-- Kachel-Layout: Zeige die Liste der Häuser -->
        <v-row>
          <v-col
            v-for="house in sortedHouses" 
            :key="house.id"
            cols="12"
            sm="6"
            md="4"
          >
            <v-card class="d-flex flex-column align-center" outlined @click="goToHouse(house.id)">
              <v-card-item>
              <!-- Anzeige der Adresse des Hauses -->
              <v-card-title>{{ house.address }}</v-card-title>
              <EditButton/>
              <DeleteButton/>
            </v-card-item>
            </v-card>
          </v-col>
        </v-row>

    <!-- Button zum Hinzufügen eines neuen Hauses -->
    <v-btn @click="dialog = true" class="mt-4" color="primary">
      + Neues Haus hinzufügen
    </v-btn>

        <!-- Dialog zum Hinzufügen eines neuen Hauses -->
        <v-dialog v-model="dialog" max-width="500px">
        <v-card>
          <v-card-title>Neues Haus hinzufügen</v-card-title>
          <v-card-text>
            <!-- Feld 1: Adresse -->
            <v-text-field
              v-model="newHouse.address"
              label="Adresse des Hauses"
              required
            ></v-text-field>

          <!-- Feld 2: Bewohner -->
          <v-text-field v-model="newHouse.residents" label="Bewohner" hint="Gib die Namen oder Anzahl der Bewohner ein"
            persistent-hint></v-text-field>

          <!-- Feld 3: Einrichtungen -->
          <v-text-field v-model="newHouse.facilities" label="Einrichtungen"
            hint="Z.B.: Pool, Gemeinschaftsraum, Parkplatz" persistent-hint></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-btn text @click="dialog = false">Abbrechen</v-btn>
          <v-btn text color="primary" @click="saveHouse">Speichern</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>



