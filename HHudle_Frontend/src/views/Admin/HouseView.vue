<script setup>

import AdminPostCard from '@/components/Admin/AdminPostCard.vue';
import { computed, onMounted, ref, watch } from "vue";

const show = ref(false);

const adminPosts = ref([
  {
    id: 1,
    title: "Ankündigung 1",
    startTime: "08:00",
    endTime: "10:00",
    data: "Details zur Ankündigung 1",
    image: "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
    description: "Hier steht der Text für die Ankündigung 1.",
  },
  {
    id: 2,
    title: "Ankündigung 2",
    startTime: "10:00",
    endTime: "12:00",
    data: "Details zur Ankündigung 2",
    image: "https://cdn.vuetifyjs.com/images/cards/road.jpg",
    description: "Hier steht der Text für die Ankündigung 2.",
  },
])



</script>

<template>

    Seite für ein einzelnes Haus
    <v-container>
      <v-row>
        <v-col v-for="adminPost in adminPosts" :key="adminPost.id" cols="12" md="4" lg="3">
          <v-card class="mx-auto" max-width="344">
            <!-- Photo als Header -->
            <v-img height="200px" :src="adminPost.image" cover></v-img>
  
            <!-- Titel -->
            <v-card-title>
              {{ adminPost.title }}
            </v-card-title>
  
            <!-- Untertitel -->
            <v-card-subtitle>
              Startzeit: {{ adminPost.startTime }}
            </v-card-subtitle>
            <v-card-subtitle>
              Endzeit: {{ adminPost.endTime }}
            </v-card-subtitle>
            <v-card-subtitle>
              Daten: {{ adminPost.data }}
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
                  {{ adminPost.description }}
                </v-card-text>
              </div>
            </v-expand-transition>
          </v-card>
        </v-col>
      </v-row>
    </v-container>


</template>