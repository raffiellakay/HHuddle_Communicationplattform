<script setup>
  import { ref } from 'vue'

  import NavbarDefault from '@/components/DefaultNavbar.vue';

const mailAddress = ref('')

const rules = [
  value => {
    if (value) return true
    return 'Bitte gib deine E-Mail-Adresse ein.'
  }]
  
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
                  v-model="mailAddress"
                   :rules="rules"
          label="E-Mail"
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
                  :loading="loading"
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