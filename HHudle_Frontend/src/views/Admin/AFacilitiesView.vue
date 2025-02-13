<script setup>
import { ref, onMounted, computed, defineEmits } from 'vue';
import { useHouseStore } from "@/stores/Admin/houseStore";
import { useFacilityStore } from '@/stores/Admin/facilityStore';
import { useRoute, useRouter } from 'vue-router';
import DeleteButton from "@/components/Icons/DeleteButton.vue";
import ConfirmDeleteCheck from "@/components/ConfirmDeleteCheck.vue";
import EditButton from '@/components/Icons/EditButton.vue';

import { getHouseImageById } from '@/utils/helpers';

const router = useRouter();
const route = useRoute();

const facilityStore = useFacilityStore();
const houseId = computed(() => Number(route.params.houseId));



const showDeleteChecker = ref(false);
const facilityToDelete = ref(null);



//Öffnen des DeleteCheckers
const openDeleteChecker = (facility) => {
  console.log("Einrichtung zum Löschen: ", facility) //Debugging
  facilityToDelete.value = { ...facility };
  showDeleteChecker.value = true;
}

//Schließen des DeleteCheckers
const closeDeleteChecker = (facility) => {
  facilityToDelete.value = null;
  showDeleteChecker.value = false;
}
//Facility Löschen
const confirmDelete = async () => {
  if (facilityToDelete.value) {
    try {
      console.log(`Facility mit ID ${facilityToDelete.value.id} wird gelöscht`);
      await facilityStore.deleteFacilityById(facilityToDelete.value.id);
      await houseStore.getAllHouses();
      await facilityStore.getAllFacilitiesByHouseId(houseId.value);

      showDeleteChecker.value = false;
      facilityToDelete.value = null;
    } catch (error) {
      console.error("Folgender Fehler beim Löschen aufgetreten: ", error)
    }
  }
}
const dialog = ref(false);

const headers = [
    { title: 'Einrichtung', key: 'type' },
    { title: 'Beschreibung', key: 'description' },
    { title: 'Bearbeiten', key: 'actions' },
    { title: 'Löschen', key: 'action'}
];

//Update Facility
const editDialog = ref(false);
const updatedFacility = ref({
  type: '',
  description: '',
  houseId: houseId.value
})
const handleEdit = (facility) => {
  console.log("Einrichtung zum Bearbeiten: ", facility) //Debugging
  updatedFacility.value = { ...facility };
  editDialog.value = true;
}
async function updateFacility() {
  try {
    await facilityStore.updateFacility(updatedFacility.value);
    await facilityStore.getAllFacilitiesByHouseId(houseId.value);

    editDialog.value = false;
    updatedFacility.value = { type: '', description: '', houseId: houseId.value }; // house Id bleibt erhalten
  } catch (error) {
    console.error('Error while updating Facility:', error);
  }
}



onMounted(async () => {
  await facilityStore.getAllFacilitiesByHouseId(houseId.value);
  await houseStore.getAllHouses();
});



//Header
//Das aktuelle Haus abrufen
const houseStore = useHouseStore();

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
  <!--Header-->
  <div class="header-container">
    <!-- Hintergrundbild -->
    <v-img class="header-image" :src="houseImage" cover></v-img>

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

     <!-- Editing Facility-->
     <v-dialog v-model="editDialog" max-width="500px">
            <v-card>
              <v-card-title>Einrichtung bearbeiten</v-card-title>
              <v-card-text>
                <!-- Felder zum Editieren-->
                <v-form @submit.prevent="updateFacility">
                  <v-text-field v-model="updatedFacility.type" label="Art der Einrichtung" required></v-text-field>
                  <v-text-field v-model="updatedFacility.description" label="Beschreibung der Einrichtung"
                    required></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn text @click="editDialog = false">Abbrechen</v-btn>
                <v-btn text color="primary" @click="updateFacility">Speichern</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>

    <!-- Facilities anzeigen -->
   
          <v-data-table :items="facilityStore.facilities" :headers="headers">
            <template v-slot:item.actions="{ item }">
                <EditButton @click="handleEdit(item)"/>
                    
            </template>
            <template v-slot:item.action="{ item }">
                <DeleteButton @click="openDeleteChecker(item)"/>
                
            </template>

        </v-data-table>


    <ConfirmDeleteCheck :show="showDeleteChecker" :itemName="'die Einrichtung'" @confirm="confirmDelete"
      @close="closeDeleteChecker" />
  </v-container>
</template>



<style scoped>
.delete-button {
  border-color:none;
  box-shadow: none;
  border: none;
}


.header-container {
  position: relative;
  width: 100vw;
  max-width: 100%;
  height: 400px; 
  overflow: hidden;
  
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