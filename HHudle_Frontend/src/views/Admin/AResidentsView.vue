<script setup>
import { ref, onMounted, computed } from 'vue';
import { useUserStore } from '@/stores/Admin/userStore';
import { useHouseStore } from '@/stores/Admin/houseStore';
import { useRoute, useRouter } from 'vue-router';
import viennaHouseImage1 from '@/assets/Pictures/ViennaHouse1.jpg';
import viennaHouseImage2 from '@/assets/Pictures/ViennaHouse2.jpg';
import viennaHouseImage3 from '@/assets/Pictures/ViennaHouse3.jpg';

const router = useRouter();
const route = useRoute();

const userStore = useUserStore();
const dialog = ref(false);

const houseId = computed(() => Number(route.params.houseId));

const newResident = ref({

    mail: '',
    username: '',
    houseId: houseId.value,

});


onMounted(async () => {
    await userStore.getAllUsersByHouseId(houseId.value);
    await houseStore.getAllHouses();
});

// new resident
async function saveResident() {
    try {
        await userStore.createUser(newResident.value);
        await userStore.getAllUsersByHouseId(houseId.value);

        dialog.value = false;
        newResident.value = { mail: '', username: '', houseId: houseId.value }; // house Id bleibt erhalten
    } catch (error) {
        console.error('Error while saving resident:', error);
    }
}


//Header
//Das aktuelle Haus abrufen
const houseStore = useHouseStore();

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
          <div @click="goToOverview(house.id)" style="cursor: pointer; text-decoration: underline; color: blue;">
          <p><strong>Adresse:</strong> {{ house.address }}</p>
        </div>

         <!--Klickbare "Tops" (Residents) -->
         <div @click="goToResidents(house.id)" style="cursor: pointer; text-decoration: underline; color: blue;">
            <p><strong>Tops:</strong> {{ house.residents.length }}</p>
          </div>

          <!--Klickbare "Einrichtungen" (Facilities) -->
          <div @click="goToFacilities(house.id)" style="cursor: pointer; text-decoration: underline; color: blue;">
            <p><strong>Einrichtungen:</strong> {{ house.facilities.length }}</p>
          </div>
        
        </div>
      </v-card>
      <v-alert v-else type="warning">Haus nicht gefunden!</v-alert>
    </div>
  </div>

    <v-container>
        <!-- Button: Add Resident -->
        <v-btn @click="dialog = true" class="mt-4" color="primary">
            + Bewohner*in hinzufügen
        </v-btn>

        <!-- Dialog zum Hinzufügen eines Residents -->
        <v-dialog v-model="dialog" max-width="500px">
            <v-card>
                <v-card-title>Add Resident</v-card-title>
                <v-card-text>
                    <!-- Formularfelder -->
                    <v-form @submit.prevent="saveResident">
                        <v-text-field v-model="newResident.mail" label="E-Mail" required></v-text-field>
                        <v-text-field v-model="newResident.username" label="Username" required></v-text-field>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-btn text @click="dialog = false">Abbrechen</v-btn>
                    <v-btn text color="primary" @click="saveResident">Speichern</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <!-- Residents-Liste anzeigen -->
        <v-list>
            <v-list-item v-for="resident in userStore.users" :key="resident.id">
                <div>
                    <v-list-item-title>{{ resident.username }}</v-list-item-title>
                    <v-list-item-subtitle>{{ resident.mail }}</v-list-item-subtitle>
                </div>
            </v-list-item>
        </v-list>
    </v-container>
</template>



<style scoped>
.delete-button{
  background-color: rgb(237, 79, 79);
  
  font-size: 16px;
}

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