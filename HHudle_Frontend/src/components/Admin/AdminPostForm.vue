<script setup>
//Form erstellt mit Hilfe von https://pablog.42web.io/vuetify-form-builder?i=1
//Diese Form dient dem hinzufügen und bearbeiten von AdminPosts 

import { ref, computed } from "vue";


//Definition des adminPost props welches alle Details beinhaltet
const props = defineProps(['adminPost']);

//Emitting der events für Kommunikation mit parent component (in diesem Fall die component, auf der man die Form aufrufen kann)
const emits = defineEmits(['add-adminPost', 'update-adminPost']);


//Definition ref Instanzen
const id = ref(null); //Kann wahrscheinlich entfernt werden da automatisch assigned
const title = ref('');
const text = ref('');
const category = ref('');
const timestamp = ref('');
const photo = ref(null);
const starttime = ref(null);
const endtime = ref(null);
const facilityId = ref(null);
const user = ref({});
const boardId = ref(0);
const isPrivate = ref(false);
const anonymous = ref(false);

//Die Form besitzt zwei Modi: Creation und Edit Mode
const isEdit = ref(false);



//Schaut ob Änderungen an der AdminPostForm gemacht wurden und füllt die Form mit den existierenden Werten vom adminPost Prop
watch(() => props.adminPost, (newPost) => {
    if(newPost) {
    // Werte aus adminPost zuweisen
    title.value = newPost.title || '';
      text.value = newPost.text || '';
      category.value = newPost.category || '';
      timestamp.value = newPost.timestamp || '';
      photo.value = newPost.photo || null;
      starttime.value = newPost.starttime || null;
      endtime.value = newPost.endtime || null;
      facilityId.value = newPost.facilityId || null;
      user.value = newPost.user || {};
      boardId.value = newPost.boardId || 0;
      isPrivate.value = newPost.private || false;
      anonymous.value = newPost.anonymous || false;

      // Wenn adminPost existiert, schalte in Edit Mode
      isEdit.value = true;
    }
  },
  { immediate: true } // Läuft beim ersten Laden automatisch
);


//Kümmert sich um Formsubmission, emitted update-post wenn isEdit true ist mit dem überarbeitenden Post Details, ansonsten wird add-post mit den neuen Post Details emitted 
const handleSubmit = () => {
  const updatedAdminPost = {
    title: title.value,
    text: text.value,
    category: category.value,
    timestamp: timestamp.value,
    photo: photo.value,
    starttime: starttime.value,
    endtime: endtime.value,
    facilityId: facilityId.value,
    user: user.value,
    boardId: boardId.value,
    private: isPrivate.value,
    anonymous: anonymous.value,
  };

  if (isEdit.value) {
    // Emit 'update-post' mit der ID von adminPost
    emits('update-adminPost', { ...updatedAdminPost, id: props.adminPost.id });
  } else {
    // Emit 'add-post' für das Erstellen eines neuen Posts
    emits('add-adminPost', updatedPost);
  }

  closeForm();
};








const formSubmit = (close) => {
  console.log("Form submitted!");
  close();
};




const pictureupload = ref([]);
const eventtag = ref("");
const eventtagGroup = ref([
  { id: "1732962385361", label: "Privat", value: "" },
  { id: "radioGroup-1732962390768", label: "Öffentlich", value: "" },
]);



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
const formattedStartDate = computed(() =>
  formatToGermanDate(selectedStartDate.value)
);
const formattedEndDate = computed(() =>
  formatToGermanDate(selectedEndDate.value)
);
</script>

<template>
  <v-card>
    <v-layout>
      <v-main>
        <v-container scrollable>
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
                  <template v-slot:activator="{ attrs }">
                    <v-text-field
                      :model-value="formattedStartDate"
                      label="Startdatum"
                      variant="outlined"
                      prepend-inner-icon="mdi-calendar-blank"
                      readonly
                      @click="showStartDatePicker = true"
                      v-bind="attrs"
                    ></v-text-field>
                  </template>

                  <!-- Date Picker -->
                  <v-date-picker
                    v-model="selectedStartDate"
                    @input="showStartDatePicker = false"
                    locale="de"
                  ></v-date-picker>
                </v-menu>
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
                      :model-value="formattedEndDate"
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
                    v-model="selectedEndDate"
                    @input="showEndDatePicker = false"
                    locale="de"
                  ></v-date-picker>
                </v-menu>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <div style="">
                  <v-textarea
                    variant="outlined"
                    label="Beschreibung"
                    density="default"
                    v-model="description"
                    name="description"
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
                    v-model="pictureupload"
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
                      v-model="anonymousCheckbox"
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