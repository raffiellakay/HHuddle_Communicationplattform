<script setup>
import { ref } from 'vue';
import {useRouter} from 'vue-router';

const username = ref('');
const password = ref('');
const errorMessage = ref('');

const router = useRouter(); 

const handleLogin = () => {
  router.push('/user/home');
}


</script>


<template>
  <!-- Vuetify App -->
  <v-app>
    <!-- Hauptbereich -->
    <v-main class="d-flex align-center justify-center" style="height: 100vh; background-color: #f5f5f5;">
      <v-container>
        <v-row justify="center">
          <v-col cols="12" md="6" lg="4">
            <!-- Login-Karte -->
            <v-card class="pa-4" elevation="3">
              <!-- Titel der Karte -->
              <v-card-title class="text-h5 text-center">
                <!-- Oberüberschrift -->
              <div class="text-center text-h6 mb-3">
                HUDDLE
              </div>
                Login
              </v-card-title>
              <!-- Login-Formular -->
              <v-card-text>
                <!-- Benutzername -->
                <v-text-field
                  v-model="username"
                  label="Benutzername"
                  outlined
                  clearable
                  required
                ></v-text-field>

                <!-- Passwort -->
                <v-text-field
                  v-model="password"
                  label="Passwort"
                  type="password"
                  outlined
                  clearable
                  required
                ></v-text-field>

                <!-- Passwort vergessen -->
                <div class="text-right mt-2">
                  <a href="/password-reset" class="text-decoration-none">Passwort vergessen?</a>
                </div>

                <!-- Fehleranzeige -->
                <v-alert
                  v-if="errorMessage"
                  type="error"
                  class="mt-2"
                  dismissible
                >
                  {{ errorMessage }}
                </v-alert>
              </v-card-text>

              <!-- Aktionen -->
              <v-card-actions>
                <v-btn
                  block
                  color="primary"
                  @click="handleLogin"
                  :loading="loading"
                >
                  Anmelden
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>



<style scoped>

/* Hintergrund für die Seite */
body {
  background-color: #f5f5f5;
}
</style>






