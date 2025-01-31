<script setup>
import { ref, onMounted } from 'vue';
import { useFacilityStore } from '@/stores/Admin/facilityStore';
import { useRoute } from 'vue-router';
import DeleteButton from "@/components/Icons/DeleteButton.vue";
import ConfirmDeleteCheck from "@/components/ConfirmDeleteCheck.vue";

const router = useRoute();

const facilityStore = useFacilityStore();
const houseId = Number(router.params.houseId);

const newFacility = ref({
    type: '',
    description: '',
    houseId: houseId
})

const showDeleteChecker = ref(false);
const facilityToDelete = ref(null);



//Öffnen des DeleteCheckers
const openDeleteChecker = (facility) => {
  console.log("Einrichtung zum Löschen: ", facility) //Debugging
  facilityToDelete.value = {...facility};
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

      await facilityStore.getAllFacilitiesByHouseId(houseId);

      showDeleteChecker.value = false; 
      facilityToDelete.value = null;
    } catch (error) {
      console.error("Folgender Fehler beim Löschen aufgetreten: ", error)
    }
  } 
}
const dialog = ref(false);


onMounted(async () => {
    await facilityStore.getAllFacilitiesByHouseId(houseId);
});

async function saveNewFacility(){
    try {
        await facilityStore.createFacility(newFacility.value);
        await facilityStore.getAllFacilitiesByHouseId(houseId);

        dialog.value = false;
        newFacility.value = { type: '', description: '', houseId }; // house Id bleibt erhalten
    } catch (error) {
        console.error('Error while saving resident:', error);
    }
}

</script>

<template>
<v-container>
   

    <!-- Dialog zum Hinzufügen Facility -->
    <v-dialog v-model="dialog" max-width="500px">
        <v-card>
            <v-card-title>Einrichtung hinzufügen</v-card-title>
            <v-card-text>
                <!-- Formularfelder -->
                <v-form @submit.prevent="saveNewFacility">
                    <v-text-field v-model="newFacility.type" label="Art der Einrichtung" required></v-text-field>
                    <v-text-field v-model="newFacility.description" label="Beschreibung der Einrichtung" required></v-text-field>
                </v-form>
            </v-card-text>
            <v-card-actions>
                <v-btn text @click="dialog = false">Cancel</v-btn>
                <v-btn text color="primary" @click="saveNewFacility">Speichern</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
    <!-- Facilities anzeigen -->
    <v-list>
        <v-list-item v-for="facility in facilityStore.facilities" :key="facility.id">
            <v-list-item-content>
                <v-list-item-title>{{ facility.type }}  <DeleteButton @click="openDeleteChecker(facility)" @delete-success="$emit('adminPost-deleted')" class="delete-button"/> </v-list-item-title>
                <v-list-item-subtitle>{{ facility.description }}</v-list-item-subtitle>
            
            </v-list-item-content>
        </v-list-item>
    </v-list>

     <!-- Button: Add Facility -->
     <v-btn @click="dialog = true" class="mt-4" color="primary">
        + Einrichtung hinzufügen
    </v-btn>

    <ConfirmDeleteCheck
    :show="showDeleteChecker"
    :itemName="'die Einrichtung'"
    @confirm="confirmDelete"
    @close="closeDeleteChecker"/>
</v-container>
</template>



<style scoped>
.delete-button{
  background-color: rgb(237, 79, 79);
  
  font-size: 16px;
}
  </style>