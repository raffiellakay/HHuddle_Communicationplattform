<script setup>

import { ref } from 'vue';

// Array mit den bestehenden Häusern
const houses = ref([
  { id: 1, title: 'Haus 1', image: 'link-zu-bild1.jpg', route: '/haus-1' }   // /haus-1' offene Frage muss noch bearbeien(Pfad zur Seite mit Details des Hauses) 
]);

// Zustand für den Dialog und das neue Haus (dialog is ein reaktiver Boolean, also ob´s fürs hinzufügen eines neuen Hauses geöffnet oder geschlossen is)
//ich steuere das Dialogfenster mit v-dialog aus Vuetify. Wenns auf true gesetzt ist, wird  Dialogfenster angezeigt, wenn false, dann bleibts geschlossen. Also, obs eben angeklickt wird oder nicht
//reaktive Variablen, sind Variablen die automatisch aktualisiert werden, wenn sich etwas an ihnen ändert (Ansict wird auto aktualisiert)
const dialog = ref(false);
const newHouse = ref({ title: '', image: '' });  //is nur ein temporäres Objekt fürs neue Haus anlegen (tempo Speicher, um die Eingaben des Admins zu sammeln, bevor das neue Haus zu meiner Arraylist, also houses-Array hinzugefügt wird)


// Methode zum Speichern eines neuen Hauses
const saveHouse = () => {
  const newId = houses.value.length + 1; // erstellt neue ID basierend auf Anzahl der bestehenden Häuser
  houses.value.push({     //füge hier ein neues Haus-Objekt in mein housses-Array ein mit folgendem Inhalt, was Admin eingibt:          
    id: newId,
    title: newHouse.value.title,
    image: newHouse.value.image,
    route: `/haus-${newId}`      // Dynamischer Pfad z.b /haus-4 oder so
  });

  // Dialog schließen und Eingaben zurücksetzen
  dialog.value = false; //nach speichern Fenster schließen
  newHouse.value = { title: '', image: '' };  //hier setze ich das newHouse-Objekt zurück, sodass die Eingabefelder für die nächste Benutzung leer sind. So kann der Admin bei der nächsten Erstellung eines Hauses mit leeren Feldern beginnt usw.
};
/*
{
    "id": null,
    "adress": "Testhausengasse 32",
    "residents": [],
    "facilities": []
}
*/

</script>

<template>
Ich bin die Admin Homeview

<v-container>
        <!-- Kachel-Layout (v-card für Kachel, v-for- basierend auf den Daten wird die Kachel auto gerendert)-->
        <v-row>
          <v-col
            v-for="house in houses"          
  
            :key="house.id"
            cols="12"
            sm="6"
            md="4"
          >
            <v-card  
              class="d-flex flex-column align-center"
              :to="house.route"
              outlined
            >
              <v-img :src="house.image" height="200px" />
              <v-card-title>{{ house.title }}</v-card-title>
            </v-card>
          </v-col>
        </v-row>    
  
        <!-- + Button- Wenn Btn geklickt wird, setzt er die Variable dialog auf true. Dialog öffnet sich-->
        <v-btn @click="dialog = true" class="mt-4" color="primary">+</v-btn>
    
        <!-- Dialog für das Hinzufügen eines neuen Hauses -->
        <v-dialog v-model="dialog" max-width="500px">
          <v-card>
            <v-card-title>Neues Haus hinzufügen</v-card-title>
            <v-card-text>
              <!-- Eingabe für den Titel -->
              <v-text-field
                v-model="newHouse.title"
                label="Titel"
              ></v-text-field>
    
              <!-- Bild-Upload -->
              <v-file-input
                v-model="newHouse.image"
                label="Hintergrundbild hochladen"
                accept="image/*"
              ></v-file-input>
            </v-card-text>
            <v-card-actions>
              <v-btn text @click="dialog = false">Abbrechen</v-btn>
              <v-btn text color="primary" @click="saveHouse">Speichern</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-container>


    
</template>



