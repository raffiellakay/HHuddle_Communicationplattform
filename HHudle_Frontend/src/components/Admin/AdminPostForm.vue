<script setup>
//Form erstellt mit Hilfe von https://pablog.42web.io/vuetify-form-builder?i=1
//Diese Form dient dem hinzufügen und bearbeiten von AdminPosts 

import { ref, computed, watch } from "vue";


//Definition des adminPost props welches alle Details beinhaltet
const props = defineProps(['adminPost']);

//Emitting der events für Kommunikation mit parent component (in diesem Fall die component, auf der man die Form aufrufen kann)
const emits = defineEmits(['add-adminPost', 'update-adminPost']);


//Definition ref Instanzen
const id = ref(null); //Kann wahrscheinlich entfernt werden da automatisch assigned
const title = ref('');
const text = ref('');
const boardId = ref(0);


//Die Form besitzt zwei Modi: Creation und Edit Mode
const isEdit = ref(false);



//Schaut ob Änderungen an der AdminPostForm gemacht wurden und füllt die Form mit den existierenden Werten vom adminPost Prop
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
);


//Kümmert sich um Formsubmission, emitted update-post wenn isEdit true ist mit dem überarbeitenden Post Details, ansonsten wird add-post mit den neuen Post Details emitted 
const handleSubmit = () => {
  const updatedAdminPost = {
    title: title.value,
    text: text.value,
    boardId: boardId.value,

  };

  if (isEdit.value) {
    // Emit 'update-post' mit der ID von adminPost
    emits('update-adminPost', { ...updatedAdminPost, id: props.adminPost.id });
  } else {
    // Emit 'add-post' für das Erstellen eines neuen Posts
    emits('add-adminPost', updatedAdminbPost);
  }

  closeForm();
};




const formSubmit = (close) => {
  console.log("Form submitted!");
  closeForm();
};




const pictureupload = ref([]);




const postClick = () => {
  console.log(2 + 2);
};


const anonymousCheckbox = ref(false);



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
                    v-model="description"
                    name="description"
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
                  {{ isEdit ? 'Update Post' : 'Add Post' }}
                 
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