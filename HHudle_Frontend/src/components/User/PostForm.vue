<script setup>
//Form erstellt mit Hilfe von https://pablog.42web.io/vuetify-form-builder?i=1

import { ref, computed } from "vue";

const form = ref(null);
const formSubmit = (close) => {
  console.log("Form submitted!");
  close();
};
const title = ref("");
const startdate = ref("");
const description = ref("");


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
