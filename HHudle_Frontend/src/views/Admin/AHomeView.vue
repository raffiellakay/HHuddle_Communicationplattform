<script setup>

import { ref, onMounted, computed } from 'vue';
import { useHouseStore } from '@/stores/Admin/houseStore';
import { useRouter } from 'vue-router';
import DeleteButton from '@/components/Icons/DeleteButton.vue';
import ConfirmDeleteCheck from "@/components/ConfirmDeleteCheck.vue";
import { getHouseImageById } from '@/utils/helpers';



//Zugriffe auf ...
const houseStore = useHouseStore();

const dialog = ref(false); // (dialog is a reaktiver Boolean, also ob´s fürs hinzufügen eines neuen Hauses geöffnet oder geschlossen is). 
//reaktive Variablen, sind Variablen die automatisch aktualisiert werden, wenn sich etwas an ihnen ändert (Ansicht wird auto aktualisiert)

const router = useRouter();

const newHouse = ref({
  address: '',
  residents: [],
  facilities: [


  ]
});
const newFacility = ref({
  type: '',
  description: '',
})


//beim Seitenstart werden alle Häuser geladen
onMounted(async () => {
  await houseStore.getAllHouses();
});

//neues Haus anlegen
async function saveHouse() {
  try {
    await houseStore.createHouse(newHouse.value);
    dialog.value = false;
    newHouse.value = { address: '', facilities: [] }; // Reset fields
    newFacility.value = { type: '', description: '' }; // Reset temporary facility
  } catch (error) {
    console.error('Error while saving house:', error);
  }
}

// Add a new facility to the facilities array
function addFacility() {
  if (newFacility.value.type && newFacility.value.description) {
    newHouse.value.facilities.push({ ...newFacility.value }); // Add facility to array
    newFacility.value = { type: '', description: '' }; // Reset temporary facility
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

const showDeleteChecker = ref(false);
const houseToDelete = ref(null);

//Öffnen des DeleteCheckers
const openDeleteChecker = (house) => {
  console.log("Haus zum Löschen: ", house) //Debugging
  houseToDelete.value = { ...house };
  showDeleteChecker.value = true;
}

//Schließen des DeleteCheckers
const closeDeleteChecker = (house) => {
  houseToDelete.value = null;
  showDeleteChecker.value = false;
}
//Haus Löschen
const confirmDelete = async () => {
  if (houseToDelete.value) {
    try {
      console.log(`Haus mit ID ${houseToDelete.value.id} wird gelöscht`);
      await houseStore.deleteHouseById(houseToDelete.value.id);

      await houseStore.getAllHouses();

      showDeleteChecker.value = false;
      houseToDelete.value = null;
    } catch (error) {
      console.error("Folgender Fehler beim Löschen aufgetreten: ", error)
    }
  }
}


</script>

<template>
  <!-- Gesamtes Layout -->
  <v-container>
    <v-btn @click="dialog = true" class="mt-4" color="primary">
      + Neues Haus hinzufügen
    </v-btn>

    <!-- Kachel-Layout: Zeige die Liste der Häuser -->
    <v-row>
      <v-col v-for="house in sortedHouses" :key="house.id" cols="12" sm="6" md="3">
        <v-card class="mx-auto" max-width="400">
          <!-- Hausbild mit Overlay-Text -->
          <v-img class="align-end text-white" height="200" :src="house.imageUrl || getHouseImageById(house.id)" cover>
            <v-card-title class="text-shadow ">{{ house.address }}</v-card-title>
          </v-img>
          

          <!-- Aktionen: Details & Löschen -->
          <v-card-actions>
            <v-btn color="black" @click="goToHouse(house.id)">Details</v-btn>
            <v-btn color="red" @click.stop="openDeleteChecker(house)">Löschen</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>

    <ConfirmDeleteCheck :show="showDeleteChecker" :itemName="'das Haus'" @confirm="confirmDelete"
      @close="closeDeleteChecker" />

    <!-- Button zum Hinzufügen eines neuen Hauses -->
    <!-- Dialog zum Hinzufügen eines neuen Hauses -->
    <v-dialog v-model="dialog" max-width="500px">
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
          <v-btn text @click="dialog = false">Abbrechen</v-btn>
          <v-btn text color="#E98074" @click="saveHouse">Speichern</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>


  </v-container>
</template>

<style scoped>
.delete-button {
  background-color: rgb(241, 102, 102);
  margin-left: 15px;

}
/* .text-shadow {
  text-shadow: 3px 3px 6px rgba(0, 0, 0, 1);
} */
.text-shadow {
  text-shadow: 4px 4px 8px rgba(0, 0, 0, 1), 
               2px 2px 4px rgba(0, 0, 0, 0.7);
}
.text-underline {
  text-decoration: underline;
}
.v-card {
  transition: transform 0.3s ease-in-out; /* Weiche Animation */
}

.v-card:hover {
  transform: scale(1.05); /* Kachel wird um 5% größer */
}

</style>



