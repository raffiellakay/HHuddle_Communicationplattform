<script setup>

import { computed, onMounted, ref, watch } from "vue";
import { useUserPostStore } from "@/stores/User/userPostStore";
import { useRoute } from "vue-router";
import { useAuthStore } from "@/stores/authStore";
import ConfirmDeleteCheck from "@/components/ConfirmDeleteCheck.vue";

const route = useRoute();
const userPostStore = useUserPostStore();
const userPosts = computed (() => userPostStore.userPosts);
const authStore = useAuthStore();

const show = ref(false);
const userId = computed(() => authStore.user.id)

/*
Category {
  //Schwarzes Brett
    BLACKBOARD,
  //Ankündigungen
    FRONTPAGE,
  //Wer hat mein Paket?
    PACKAGE,
  //Wer macht was wann wo?
    EVENTS,
  //Biete-Suche-Tausche-Verschenke
    EXCHANGE;

}*/

//Methode fehlend: getHouseIdByUserId, aktuell kann noch nich auf die houseId zugegriffen werden 
const showDeleteChecker = ref(false);
const postToDelete = ref(null); 

const props = defineProps({
  postId: Number, 
  userId: Number, 
})

console.log("Auth Store User:", authStore.user);
console.log("User ID:", userId.value);
console.log("Auth Token:", authStore.token);


onMounted(async () => {
  await userPostStore.getPostsByUserId(userId.value);
});







</script>



<template>
<!-- Dieser Teil beinhaltet das potenzielle Kartendesign für UserPosts-->

<v-container>
    <v-row>
      <v-col v-for="userPost in userPosts" :key="userPost.id" cols="12" md="4" lg="3">
        <v-card class="mx-auto" max-width="344">
          <!-- Photo als Header -->
          

          <!-- Titel -->
          <v-card-title>
            {{ userPost.title }}
          </v-card-title>

          <!-- Untertitel -->
          <v-card-subtitle>
            Startzeit: {{ userPost.startTime }}
          </v-card-subtitle>
          <v-card-subtitle>
            Endzeit: {{ userPost.endTime }}
          </v-card-subtitle>
          <v-card-subtitle>
            Erstellungszeit: {{ userPost.timestamp }}
          </v-card-subtitle>
          <v-card-subtitle>
            Ersteller: {{ userPost.user?.username || 'Unbekannt' }} <!-- Checkt ob es einen username gibt, ansonsten "Unbekannt"-->
          </v-card-subtitle>

          <!-- Aktionen -->
          <v-card-actions>
            <v-btn color="orange-lighten-2" text>Explore</v-btn>
            <v-spacer></v-spacer>
            <v-btn :icon="show ? 'mdi-chevron-up' : 'mdi-chevron-down'" @click="show = !show"></v-btn>
          </v-card-actions>

          <!-- Expandable Text -->
          <v-expand-transition>
            <div v-show="show">
              <v-divider></v-divider>
              <v-card-text>
                {{ userPost.description }}
              </v-card-text>
            </div>
          </v-expand-transition>
        </v-card>
      </v-col>
    </v-row>
  </v-container>

</template>