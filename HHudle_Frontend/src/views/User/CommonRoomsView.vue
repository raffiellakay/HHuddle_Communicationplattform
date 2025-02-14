<script setup>
import PostCard from '@/components/User/PostCard.vue';
import { useUserPostStore } from '@/stores/User/userPostStore';
import { computed, onMounted,ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import { useAuthStore } from '@/stores/authStore';


const route = useRoute();

const category = "EVENTS"
const userPostStore = useUserPostStore();
const authStore = useAuthStore();
const tab = ref(null);

const selectedFacilityId = ref(null);
const facilities = computed(() =>{
  return userPostStore.facilities;
});

//Synchronisierung von selectedFacilityId und userPostStore.selectedFacilityId
watch(() => userPostStore.selectedFacilityId, (newValue) => {
  selectedFacilityId.value = newValue;
});



onMounted(async() => {
  userPostStore.setCategory(category); //Set Kategorie im Store
  try{
    const houseId = authStore.user.houseId; 
    if(!houseId) {
      console.error("Keine House ID gefunden!");
      return;
    }

    console.log("Lade Facilities für House ID: ", houseId);
    await userPostStore.getAllFacilitiesByHouseId(houseId);

    //Erste Facility auswählen
    if(userPostStore.facilities.length>0) {
      userPostStore.setSelectedFacility(userPostStore.facilities[0].id);
    }


  } catch (error) {
    console.error("Fehler beim Laden der Facilities: ", error)
  }
});

console.log("Aktuelle Kategorie: ", category)


</script>

<template>


<v-card class="facility-card">
  <v-tabs v-model="selectedFacilityId" class="facility-bar">
  <v-tab 
  v-for="facility in facilities" 
  :key="facility.id" 
  :value="facility.id"
  @click="userPostStore.setSelectedFacility(facility.id)">
   {{ facility.type }}
  </v-tab>
</v-tabs>

  <v-card-text class="facility-text-card">
      
      <v-tabs-window  v-model="selectedFacilityId" >
        <v-tabs-window-item v-for="facility in facilities" :key="facility.id" :value="facility.id"> 
          {{ facility.description }} 
        </v-tabs-window-item>
    
    </v-tabs-window>
    </v-card-text>



</v-card>



<v-container>
    <PostCard 
    v-if="selectedFacilityId !== null"
    :category="category"
    :facilityId="selectedFacilityId"/>
</v-container>
    
</template>

<style scoped>


.facility-card {
  border-radius: 0;
}
.facility-bar {

  background-color: #E98074;
  color: white;
}

.facility-text-card {
  background-color: #8E8D8A ;
  color: white;
}


</style>