<script setup>
//Form erstellt mit Hilfe von https://pablog.42web.io/vuetify-form-builder?i=1

import { ref,computed } from "vue";

const form = ref(null);
const formSubmit = (close) => {
  console.log("Form submitted!");
  close();
};
const title = ref("");
const startdate = ref("");
const description = ref("");
const anonymouscheckbx = ref("");
const anonymouscheckbxGroup = ref([
  { id: "1732962203368", label: "Anonym", value: "" },
]);
const pictureupload = ref([]);
const eventtag = ref("");
const eventtagGroup = ref([
  { id: "1732962385361", label: "Privat", value: "" },
  { id: "radioGroup-1732962390768", label: "Öffentlich", value: "" },
]);
const postClick = () => {
  console.log(2 + 2);
};

const showPicker = ref(false);
const selectedStartDate = ref(new Date());


//Formatiert Datum auf DD.MM.YYYY
const formattedDate = computed(()=> {
  if (!selectedStartDate.value) return "";
  const date = new Date (selectedStartDate.value);
  return date.toLocaleDateString("de-DE", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  })
})
</script>

<template>
  <v-card>
    <v-layout>
      <v-main>
        <v-container>
          <v-form ref="form" @submit.prevent="formSubmit">
            <v-row>
              <v-col>
                <div style="height: 250px">
                  <v-text-field
                    variant="outlined"
                    label="Titel"
                    density="default"
                    v-model="title"
                    placeholder=""
                    type="text"
                    hint=""
                    :persistent-hint="false"
                    class=""
                    name="title"
                  >
                  </v-text-field>
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <!--v-date-input ist ein experimentelles Feature. Es ist wohl besser, es über ein menu mit text field und date picker zu lösen wie hier
               https://stackoverflow.com/questions/57696615/vuetify-how-to-fix-v-date-picker-not-showing-the-chosen-date-in-the-text-field-->
            
                  <v-menu
                    v-model="showPicker"
                    :close-on-content-click="false"
                    transition="scale-transition"
                    offset-y
                    min-width="290px"
                    :max-width="290"
                  >
                    <!-- Slot für den Activator -->
                    <template v-slot:activator="{ attrs }">
                      <v-text-field
                        :model-value="formattedDate"
                        label="Startdatum"
                        readonly
                        @click="showPicker = true"
                        v-bind="attrs"
                      ></v-text-field>
                    </template>

                    <!-- Date Picker -->
                    <v-date-picker
                      v-model="selectedStartDate"
                      no-title
                      @input="showPicker = false"
                      locale="de"
                    ></v-date-picker>
                  </v-menu>
                
                
              </v-col>
            </v-row>

            <v-row>
              <v-col>
                <div style="">
                  
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
                    v-model="description"
                    placeholder=""
                    hint=""
                    :persistent-hint="false"
                    class=""
                    name="description"
                  >
                  </v-textarea>
                </div>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <div style="">
                  <div class="d-flex flex-wrap">
                    <v-checkbox
                      v-for="checkbox in anonymouscheckbxGroup"
                      v-model="anonymouscheckbx"
                      label="checkbox.label"
                      :value="checkbox.value"
                      :key="checkbox.id"
                      class="me-6"
                      hint=""
                      :persistent-hint="false"
                      name="anonymouscheckbx"
                      :multiple="false"
                      color="undefined"
                    >
                    </v-checkbox>
                  </div>
                </div>
              </v-col>
              <v-col>
                <div style="">
                  <v-text-field
                    variant="outlined"
                    label=""
                    density="default"
                    placeholder=""
                    type="text"
                    hint=""
                    :persistent-hint="false"
                    class=""
                    name=""
                  >
                  </v-text-field>
                </div>
              </v-col>
            </v-row>
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
              <v-col>
                <div style="">
                  <v-text-field
                    variant="outlined"
                    label=""
                    density="default"
                    placeholder=""
                    type="text"
                    hint=""
                    :persistent-hint="false"
                    class=""
                    name=""
                  >
                  </v-text-field>
                </div>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <div style="">
                  <v-radio-group
                    v-model="eventtag"
                    label=""
                    class=""
                    name="eventtag"
                  >
                    <v-radio
                      v-for="radio in eventtagGroup"
                      :key="radio.id"
                      :label="radio.label"
                      :value="radio.value"
                      color="undefined"
                    >
                    </v-radio>
                  </v-radio-group>
                </div>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <div style="">
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
                </div>
              </v-col>
            </v-row>
          </v-form>
        </v-container>
      </v-main>
    </v-layout>
  </v-card>
</template>


<style scoped>

.v-overlay-container {
  z-index: 2000 !important;
}




</style>
