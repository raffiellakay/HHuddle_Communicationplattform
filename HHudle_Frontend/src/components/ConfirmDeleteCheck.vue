<script setup>
import { ref } from "vue";

//Props benötigt um Text dynamisch anzupassen
const props = defineProps({
  show: Boolean, 
  itemName: String, //Stellt sicher, dass itemName ein String ist
});

//Emits für die Parentcomponents 
const emit = defineEmits(["confirm", "close"]);

</script>

<template>
  <v-dialog v-model="props.show" max-width="400">
    <v-card>
      <v-card-title>Löschen bestätigen</v-card-title>
      <v-card-text>
        Sind Sie sich sicher, dass Sie {{ itemName }} löschen möchten?
      </v-card-text>
      <v-card-actions>
        <v-btn color="red darken-1" text @click="emit('confirm')">Löschen</v-btn>
        <v-btn color="grey darken-1" text @click="emit('close')">Abbrechen</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
