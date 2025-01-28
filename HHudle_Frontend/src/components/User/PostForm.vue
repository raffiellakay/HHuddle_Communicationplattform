<script setup>
//Form erstellt mit Hilfe von https://pablog.42web.io/vuetify-form-builder?i=1

import { ref, computed, watch, onMounted } from "vue";
import { useUserPostStore } from "@/stores/User/userPostStore.js"
import { useRoute } from "vue-router";
import { useAdminPostStore } from "@/stores/Admin/adminPostStore";
import { useAuthStore } from "@/stores/authStore";
import { useForm, Field} from "vee-validate";
import * as yup from "yup";


// Validierungsschema
const validationSchema = yup.object({
  title: yup.string().required("Titel ist erforderlich."),
  text: yup.string().required("Beschreibung ist erforderlich."),
});

// `useForm` Setup
const { handleSubmit, errors } = useForm({
  validationSchema,
});


const props = defineProps({
  houseId: Number, 
  boardId: Number,
  category: {
    type: String, 
    required: true, 
  },
});



const innerValue = ref(props.value || "");
const category = computed(() => props.category);



const emits = defineEmits(['userPost-added', 'close']); 
const userPostStore = useAdminPostStore(); 
const authStore = useAuthStore();

//Definition ref Instanzen
const houseId = authStore.user.houseId;

const title = ref(''); 
const text = ref('');

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

const resolvedCategory = computed(() => category.value);

// Kombinierte Start- und Endzeit
const combinedStartDateTime = computed(() =>
  combineDateTime(startDate.value, startTime.value)
);
const combinedEndDateTime = computed(() =>
  combineDateTime(endDate.value, endTime.value)
);



// Validierung der Zeit (HH:mm)
function validateTimeInput(event) {
  const value = event.target.value;
  const timeRegex = /^([01]\d|2[0-3]):([0-5]\d)?$/;
  if (!timeRegex.test(value)) {
    event.target.value = value.slice(0, -1); // Ungültige Eingabe entfernen
  }
}

// Kombiniere Datum und Zeit für das Backend
function combineDateTime(date, time) {
  if (!date || !time) return null;
  const isoDate = new Date(date).toISOString().split("T")[0];
  return `${isoDate}T${time}`;
}




// Beobachte Änderungen und aktualisiere die kombinierten Werte
watch([startDate, startTime], () => {
  combinedStartDateTime.value = combineDateTime(startDate.value, startTime.value);
});

watch([endDate, endTime], () => {
  combinedEndDateTime.value = combineDateTime(endDate.value, endTime.value);
});

console.log("UserPost wurde geöffnet mit boardId: ", boardId.value) //Debug 

//Die Form besitzt zwei Modi: Creation und Edit Mode
const isEdit = ref(false);





//Kümmert sich um Formsubmission, emitted update-post wenn isEdit true ist mit dem überarbeitenden Post Details, ansonsten wird add-post mit den neuen Post Details emitted 
// Form Submission
const submitPost = handleSubmit(async (values) => {
  try {
    const boardId = await userPostStore.getBoardIdByHouseIdAndCategory(
      authStore.user.houseId,
      props.category
    );

    if (!boardId) {
      console.error("Keine gültige Board-ID gefunden!");
      return;
    }

    const newUserPost = {
      title: values.title,
      text: values.text,
      category: props.category,
      photo: photo.value,
      isPrivate: isPrivate.value,
      anonymous: anonymous.value,
      boardId,
    };

    console.log("Sende UserPost an Backend:", newUserPost);
    await userPostStore.createUserPost(newUserPost);

    emits("userPost-added");
    emits("close");
  } catch (error) {
    console.error("Fehler beim Erstellen des UserPosts:", error);
  }
});





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

function formatTimeInput(field) {
  const time = field === "startTime" ? startTime.value : endTime.value;

  if (time) {
    const [hours, minutes] = time.split(":");

    // Stelle sicher, dass Stunden und Minuten zwei Ziffern haben
    const formattedTime = `${hours?.padStart(2, "0") || "00"}:${
      minutes?.padStart(2, "0") || "00"
    }`;

    if (field === "startTime") {
      startTime.value = formattedTime;
    } else if (field === "endTime") {
      endTime.value = formattedTime;
    }
  }
}


console.log("Empfangene Kategorie in PostForm:", props.category);
console.log("Category in PostForm:", category.value);
console.log("Empfangene Kategorie-Wert in PostForm:", category.value);


//Formatierte Datumsvariablen
/*const formattedStartDate = computed(() =>
  formatToGermanDate(selectedStartDate.value)
);
const formattedEndDate = computed(() =>
  formatToGermanDate(selectedEndDate.value)
);*/

</script>

<template>
  <v-card style="max-height: 80vh; overflow-y: auto;">
    <v-layout>
      <v-main>
        <v-container>
          <v-form ref="form" @submit.prevent="submitPost">
            <v-row>
              <v-col>
                <div>
                  <Field name="title" :rules="'required'" v-slot="{field, errors}">
                  <v-text-field
                    v-bind="field"
                    variant="outlined"
                    label="Titel"
                    density="default"
                    v-model="title"
                    type="text"
                    :persistent-hint="false"
                    :error-messages="errors"
                    required
                  >
                  </v-text-field>
                </Field>
            
                </div>
              </v-col>
            </v-row>

            <div v-if="category === 'EVENTS'">
            <v-row >
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
                  placeholder="HH:mm"
                  v-model="startTime"
                  :persistent-hint="false"
                  @blur="formatTimeInput('startTime')"
                  ></v-text-field>
                </div>
            </v-col>
            </v-row>

            

            <v-row v-if="resolvedCategory === 'EVENTS'">
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
                  placeholder="HH:mm"
                  v-model="endTime"
                  :persistent-hint="false"
                  @blur="formatTimeInput('endTime')"
                  ></v-text-field>
                </div>
              
              </v-col>
            </v-row>
          </div>
         
          

            <v-row>
              <v-col>
                <div style="">
                  <Field name="text" :rules="'required'" v-slot="{ field, errors }">
                  <v-textarea
                    v-bind="field"
                    variant="outlined"
                    label="Beschreibung"
                    density="default"
                    v-model="text"
                    :error-messages="errors"
                    required
                  >
                  </v-textarea>
                </Field>
                </div>
              </v-col>
            </v-row>

            <!--Buttons zum auswählen ob die Veranstaltung öffentlich oder privat ist-->
            <v-row>
              <v-col>
                <v-radio-group
                  v-model="isPrivate"
                  inline
                  label="Ist deine Veranstaltung öffentlich oder privat?"
                  name="eventtag"
                  color="primary"
                  v-if="category === 'EVENTS'"
                > 
                  <v-radio :value="false" label="Öffentlich"></v-radio>
                  <v-radio :value="true" label="Privat"></v-radio>
                </v-radio-group>
              </v-col>
            </v-row>

            <!--Foto Upload-->

            <v-row v-if="category === 'EXCHANGE' || category === 'BLACKBOARD' || category === 'FRONTPAGE'">
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
                    type="submit"
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
