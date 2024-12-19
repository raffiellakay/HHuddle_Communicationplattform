<script setup>
import { ref } from 'vue';
import { useAuthStore } from '@/stores/authStore';
import { useRouter } from 'vue-router';
import { isAxiosError } from 'axios';


const errorMessage = ref('');

const router = useRouter();
const handleLogin = () => {
  router.push('/user/home');
}


//Authentification
const authStore = useAuthStore()
const showInvalidCredentialsWarning = ref(false)
const showUnknownError = ref(false)

const credentials = ref({
  mail: '',
  password: ''
})

//Funktion noch nicht eingebunden
async function login() { 
  try {
    await authStore.login(credentials.value)

    await router.push('/admin/home')

  } catch (err) {
    if (isAxiosError(err) && err.response?.status === 401) {
      return showInvalidCredentialsWarning.value = true
    }
    console.error(err)
    showUnknownError.value = true
  }
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
                  v-model="credentials.mail"
                  label="E-Mail"
                  outlined
                  clearable
                  required
                ></v-text-field>

                <!-- Passwort -->
                <v-text-field
                  v-model="credentials.password"
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
                  @click="login"
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






