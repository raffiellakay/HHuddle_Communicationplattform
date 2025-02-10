<script setup>
//Form erstellt mit Hilfe von https://pablog.42web.io/vuetify-form-builder?i=1
//Diese Form dient dem hinzufügen und bearbeiten von AdminPosts 

import { ref, computed, watch, onMounted } from "vue";
import { useAdminPostStore } from "@/stores/Admin/adminPostStore.js"
import { useRoute } from "vue-router";


const props = defineProps({
  houseId: Number
})

//Emitting der events für Kommunikation mit parent component (in diesem Fall die component, auf der man die Form aufrufen kann)
const emits = defineEmits(['adminPost-added', 'close']);
const adminPostStore = useAdminPostStore();
const route = useRoute();

//Definition ref Instanzen
const title = ref('');
const text = ref('');
const boardId = ref(null);


const houseId = ref(route.params.houseId ? Number(route.params.houseId) : null);

console.log("AdminPostForm wurde geöffnet mit houseId: ", houseId.value) //Debug 


//Die Form besitzt zwei Modi: Creation und Edit Mode
//const isEdit = ref(false);

onMounted(async () => {
  if (houseId.value) {
    console.log('Lade boardId für houseId: ${houseId.value}...');// Debug
    boardId.value = await adminPostStore.getAdminBoardIdByHouseId(houseId.value);
    console.log('Geladene boardId: ${boardId.value} für houseId: ${houseId.value}');
  } else {
    console.error("Fehler: `houseId` ist undefined");
  }
});


//Bearbeitung eines AdminPosts ist nicht implementiert 

/*
watch(() => props.adminPost, (newPost) => {
    if(newPost) {
    // Werte aus adminPost zuweisen
      title.value = newPost.title || '';
      text.value = newPost.text || '';
      boardId.value = newPost.boardId || 0;

      // Wenn adminPost existiert, schalte in Edit Mode
      isEdit.value = true;
    }
  },
  { immediate: true } // Läuft beim ersten Laden automatisch
);*/


//Kümmert sich um Formsubmission, emitted update-post wenn isEdit true ist mit dem überarbeitenden Post Details, ansonsten wird add-post mit den neuen Post Details emitted 
const handleSubmit = async () => {
  if (!boardId.value) {
    console.error("Fehler: `boardId` wurde nicht gefunden.");
    return;
  }

  const newAdminPost = {
    title: title.value,
    text: text.value,
    boardId: boardId.value
  };

  console.log("Sende AdminPost an Backend:", newAdminPost);// Debug

  try {
    await adminPostStore.createAdminPost(newAdminPost);
    emits("adminPost-added"); //Event auslösen, um `HouseView.vue` zu aktualisieren
    emits("close");
  } catch (error) {
    console.error("Fehler beim Erstellen des AdminPosts:", error);
  }
};


</script>

<template>
  <v-card>
    <v-layout>
      <v-main>
        <v-container>
          <v-form ref="form" @submit.prevent="handleSubmit">
            <v-row>
              <v-col>
                <div>
                  <v-text-field
                    variant="outlined"
                    label="Titel"
                    density="default"
                    v-model="title"
                    type="text"
                    :persistent-hint="false"
                    name="title"
                    required
                  >
                  </v-text-field>
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <div style="">
                  <v-textarea
                    variant="outlined"
                    label="Beschreibung"
                    density="default"
                    v-model="text"
                    name="description"
                    required
                  >
                  </v-textarea>
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <div></div>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
               
                  <v-btn
                    type="submit"
                    
                    color="#E98074"
                    variant="flat"
                    size="default"
                    :icon="false"
                    prepend-icon=""
                    append-icon=""
                    density="default"
                    :block="true"
                    elevation="0"
                    :ripple="true"
                  >
                  Add Post 
                 
                  </v-btn>
             
              </v-col>
            </v-row>
          </v-form>
        </v-container>
      </v-main>
    </v-layout>
  </v-card>
</template>

<style scoped></style>