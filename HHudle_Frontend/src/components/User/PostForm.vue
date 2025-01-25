<script setup>
//Form erstellt mit Hilfe von https://pablog.42web.io/vuetify-form-builder?i=1

import { ref, computed, watch, onMounted } from "vue";
import { useUserPostStore } from "@/stores/User/userPostStore.js"
import { useRoute } from "vue-router";
import { useAdminPostStore } from "@/stores/Admin/adminPostStore";



const props = defineProps({
  houseId: Number, 
  boardId: Number,
})

const emits = defineEmits(['userPost-added', 'close']); 
const userPostStore = useAdminPostStore(); 
const route = useRoute(); 

//Definition ref Instanzen
const title = ref(''); 
const text = ref('');
const category = ref('');
const timestamp = ref("2024-12-18T15:52:42"); 
const photo = ref(null);

const user = {};
const boardId = ref(null);
const isPrivate = ref(false); 
const anonymous = ref(true); 

//Datum und Zeit 
const startDate = ref(null);
const startTime = ref("");
const endDate = ref(null); 
const endTime = ref(""); 

// Validierung der Zeiteingabe (HH:mm)
function validateTimeInput(event) {
  const value = event.target.value;
  const timeRegex = /^([01]\d|2[0-3]):([0-5]\d)?$/;
  if (!timeRegex.test(value)) {
    event.target.value = value.slice(0, -1); // Ungültige Eingabe entfernen
  }
}

// Kombiniere Datum und Zeit
function combineDateTime(date, time) {
  if (!date || !time) return null;
  return `${date}T${time}`;
}

const combinedStartTime = combineDateTime(startDate.value, startTime.value);
const combinedEndTime = combineDateTime(endDate.value, endTime.value); 


console.log("UserPost wurde geöffnet mit boardId: ", boardId.value) //Debug 

//Die Form besitzt zwei Modi: Creation und Edit Mode
const isEdit = ref(false);

//Kümmert sich um Formsubmission, emitted update-post wenn isEdit true ist mit dem überarbeitenden Post Details, ansonsten wird add-post mit den neuen Post Details emitted 
const handleSubmit = async () => {
  if (!boardId.value) {
    console.error("Fehler: `boardId` wurde nicht gefunden.");
    return;
  }

  const newUserPost = {
    title:  	  title.value,
    text:       text.value,
    category:   category.value, 
    timestamp:  timestamp.value, 
    photo:      photo.value, 
    starttime:  combinedStartTime, 
    endtime:    combinedEndTime, 
    user:       user.value, 
    boardId:    boardId.value, 
    isPrivate:  isPrivate.value, 
    anonymous:  anonymous.value,
  };

  console.log("Sende UserPost an Backend:", newUserPost);// Debug

  try {
    await userPostStore.createUserPost(newUserPost);
    emits("userPost-added"); //Event auslösen, um `HouseView.vue` zu aktualisieren
    emits("close");
  } catch (error) {
    console.error("Fehler beim Erstellen des UserPosts:", error);
  }
};





const form = ref(null);
const formSubmit = (close) => {
  console.log("Form submitted!");
  close();
};





const postClick = () => {
  console.log(2 + 2);
};


const anonymousCheckbox = ref(false);


//Datumsfelder
const showStartDatePicker = ref(false);
const selectedStartDate = ref(new Date());

const showEndDatePicker = ref(false);
const selectedEndDate = ref(new Date());

//Formatiert Datum auf DD.MM.YYYY
const formatToGermanDate = (date) => {
  if (!date) return ""; // Rückgabe eines leeren Strings, wenn kein Datum vorhanden ist
  const d = new Date(date);
  return d.toLocaleDateString("de-DE", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  });
};

//Formatierte Datumsvariablen
/*const formattedStartDate = computed(() =>
  formatToGermanDate(selectedStartDate.value)
);
const formattedEndDate = computed(() =>
  formatToGermanDate(selectedEndDate.value)
);*/

</script>

<template>
  <v-card>
    <v-layout>
      <v-main>
        <v-container>
          <v-form ref="form" @submit.prevent="formSubmit">
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
                  >
                  </v-text-field>
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <!--Implementierung Startdatum Feld + Date Picker -->
                <v-menu
                  v-model="showStartDatePicker"
                  :close-on-content-click="false"
                  transition="scale-transition"
                  offset-y
                  absolute
                  attach
                >
                  <!-- Slot für den Activator -->
                  <template v-slot:activator="{ attr }">
                    <v-text-field
                      v-bind="attr"
                      :model-value="formatToGermanDate(startDate)"
                      label="Startdatum"
                      variant="outlined"
                      prepend-inner-icon="mdi-calendar-blank"
                      readonly
                      @click="showStartDatePicker = true"
                      
                    ></v-text-field>
                  </template>

                  <!-- Date Picker -->
                  <v-date-picker
                    v-model="startDate"
                    @input="showStartDatePicker = false"
                    locale="de"
                  ></v-date-picker>
                </v-menu>
              </v-col>
              <v-col>
                <div>
                  <v-text-field
                    variant="outlined"
                    label="Startzeit"
                    density="default"
                    v-model="starttime"
                    type="text"
                    :persistent-hint="false"
                    name="Startzeit"
                  >
                  </v-text-field>
                </div>

              </v-col>
            </v-row>

            

            <v-row>
              <v-col>
                <!--Implementierung Enddatum Feld + Date Picker -->
                <v-menu
                  v-model="showEndDatePicker"
                  :close-on-content-click="false"
                  transition="scale-transition"
                  offset-y
                  absolute
                  attach
                >
                  <!-- Slot für den Activator -->
                  <template v-slot:activator="{ attrs }">
                    <v-text-field
                      :model-value="formatToGermanDate(endDate)"
                      label="Enddatum"
                      variant="outlined"
                      prepend-inner-icon="mdi-calendar-blank"
                      readonly
                      @click="showEndDatePicker = true"
                      v-bind="attrs"
                    ></v-text-field>
                  </template>

                  <!-- Date Picker -->
                  <v-date-picker
                    v-model="endDate"
                    @input="showEndDatePicker = false"
                    locale="de"
                  ></v-date-picker>
                </v-menu>
              </v-col>
              <v-col>
                <div>
                  <v-text-field
                    variant="outlined"
                    label="Endzeit"
                    density="default"
                    v-model="endtime"
                    type="text"
                    :persistent-hint="false"
                    name="Endzeit"
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
                    name="Beschreibung"
                  >
                  </v-textarea>
                </div>
              </v-col>
            </v-row>

            <!--Buttons zum auswählen ob die Veranstaltung öffentlich oder privat ist-->
            <v-row>
              <v-col>
                <v-radio-group
                  inline
                  label="Ist deine Veranstaltung öffentlich oder privat?"
                  name="eventtag"
                  color="primary"
                >
                  <v-radio label="Öffentlich" value="publicEvent"></v-radio>
                  <v-radio label="Privat" value="privateEvent"></v-radio>
                </v-radio-group>
              </v-col>
            </v-row>

            <!--Foto Upload-->

            <v-row>
              <v-col>
                <div style="">
                  <v-file-input
                    variant="outlined"
                    label="Foto hochladen"
                    density="default"
                    :clearable="false"
                    v-model="photo"
                    multiple=""
                    hint=""
                    :persistent-hint="false"
                    class=""
                    name="pictureupload"
                  >
                  </v-file-input>
                </div>
              </v-col>
            
            </v-row>


            <!--Checkbox für anonymes Posten-->
            <v-row>
              <v-col>
                <div>
                  <div>
                    <v-checkbox
                      v-model="anonymous"
                      label="Anonym Posten"
                      color="primary"
            
                    >
                    </v-checkbox>
                  </div>
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
                    @click="postClick"
                    color="primary"
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
                    <span>
                      {{ "Post" }}
                    </span>
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
