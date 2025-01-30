<script setup>
import PostCard from '@/components/User/PostCard.vue';
import { useUserPostStore } from '@/stores/User/userPostStore';
import { computed, onMounted,ref } from 'vue';
import { useRoute } from 'vue-router';
import { useAuthStore } from '@/stores/authStore';


const route = useRoute();

const category = "EVENTS"
const userPostStore = useUserPostStore();
const authStore = useAuthStore();
const tab =ref(null);
const facilities = computed(() =>{
  return userPostStore.facilities;
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
    if(facilities.value.length>0) {
      tab.value = facilities.value[0].id;
    }


  } catch (error) {
    console.error("Fehler beim Laden der Facilities: ", error)
  }
});

console.log("Aktuelle Kategorie: ", category)


</script>

<template>


<v-card>
  <v-tabs v-model="tab" bg-color="primary">
  <v-tab v-for="facility in facilities" :key="facility.id" :value="facility.id">
   {{ facility.type }}
  </v-tab>
</v-tabs>

  <v-card-text>
      
      <v-tabs-window  v-model="tab" bg-color="primary">
        <v-tabs-window-item v-for="facility in facilities" :key="facility.id" :value="facility.id"> 
          {{ facility.description }} 
        </v-tabs-window-item>
    
    </v-tabs-window>
    </v-card-text>



</v-card>



<v-container>
    <PostCard :category="category"/>
</v-container>
    
</template>