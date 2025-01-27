<script setup>
  import { ref } from 'vue'
  import { useAuthStore } from '@/stores/authStore';
  import { useRouter } from 'vue-router';

  import NavbarDefault from '@/components/DefaultNavbar.vue';

const newPassword = ref('')
const againNewPassword = ref('')
const router = useRouter();
const authStore = useAuthStore();

const rules = [
  value => {
    if (value) return true
    return 'Bitte überprüfe deine Eingabe.'
  }]

  async function resetPassword() {
      try {
          if (newPassword.value === againNewPassword.value) {
              authStore.resetPassword(authStore.user.mail, newPassword.value)
              router.push('/');
              return
          }
      }
      catch (error) {
          console.error("Fehler beim Zürücksetzen des Passwort ");
          return null;
      }
    }
  
  
</script>

<template>
<NavbarDefault/>
 <v-main class="d-flex align-center justify-center" style="height: 100vh; background-color: #f5f5f5;">
      <v-container>
        <v-row justify="center">
          <v-col cols="12" md="6" lg="4">
            <v-card class="pa-4" elevation="3">
              <v-card-title class="text-h5 text-center">
              <div class="text-center text-h6 mb-3">
                HUDDLE
              </div>
                Passwort zurücksetzen
              </v-card-title>
             
              <v-card-text>
                <v-form @submit.prevent>
                <v-text-field
                  v-model="newPassword"
                   :rules="rules"
          label="Dein neues Passwort"
                  outlined
                  clearable
                  required
                ></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-text>
                <v-form @submit.prevent>
                <v-text-field
                  v-model="againNewPassword"
                   :rules="rules"
          label="Neues Passwort wiederholen"
                  outlined
                  clearable
                  required
                ></v-text-field>
                </v-form>
              </v-card-text>

              <v-card-actions>
                <v-btn
                  block
                  color="primary"
                  @click="resetPassword"
                >
                  Passwort zurücksetzen
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
</template>





 
<style scoped>
</style>
