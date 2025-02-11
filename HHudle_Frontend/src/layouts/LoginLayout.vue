<script setup>
import { ref } from 'vue';
import { useAuthStore } from '@/stores/authStore';
import { useRouter } from 'vue-router';
import { isAxiosError } from 'axios';
import NavbarDefault from '@/components/DefaultNavbar.vue';




const router = useRouter();
const handleLogin = async () => {
  const userId = useAuthStore().user.id;
  const hasChangedPW = await useAuthStore().hasUserChangedTempPW(userId);
  const roles = useAuthStore().user.roles
  const rolePmanagement = roles.find(role => role === 'ROLE_PMANAGEMENT');
  const roleResident = roles.find(role => role === 'ROLE_RESIDENT')


  

  if(!hasChangedPW) {
    await router.push('/set-new-password')
    return
  }
  if(rolePmanagement) {
    await router.push('/admin/home')
    return
  }
  if(roleResident) {
    await router.push('/user/home')
    return
  }
}

const rulesPw = [
  value => {
    if (value) return true
    return 'Gib bitte dein Passwort an.'
  }]

const rulesMail = [
  value => {
    if (value) return true
    return 'Gib bitte eine E-Mail Adresse an.'
  }]

//Authentification
const authStore = useAuthStore()
const showInvalidCredentialsWarning = ref(false)
const showUnknownError = ref(false)

const credentials = ref({
  mail: '',
  password: ''
})


async function login() { 
  try {
    await authStore.login(credentials.value)
    await handleLogin();
    

  } catch (err) {
    if ( err.response?.status === 401) {
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
    <NavbarDefault/>
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
               <v-form @submit.prevent="login">
              <v-card-text>
                <!-- Benutzername -->
                <v-text-field
             
                  v-model="credentials.mail"
                  label="E-Mail"
                  type="email"
                 :rules="rulesMail"
                  hint="Gib hier deine E-Mail ein."
                  outlined
                  clearable
                  required
                ></v-text-field>

                <!-- Passwort -->


                <v-text-field
           
                  v-model="credentials.password"
                  label="Passwort"
                 type="password"
                 hint="Gib hier dein Passwort ein."
                  outlined
                  clearable
                  :rules="rulesPw"
                  required
                  
                ></v-text-field>
                
                <!-- Passwort vergessen -->
                <div class="text-right mt-2">
                  <a href="/password-reset" class="text-decoration-none">Passwort vergessen?</a>
                </div>

                
              </v-card-text>

              <!-- Aktionen -->
              <v-card-actions>
                <v-btn
                type="submit"
                  block
                  color="primary"
                 
                >
                  Anmelden
                </v-btn>
              </v-card-actions>
              <v-alert v-model="showUnknownError" >
                Ein unbekannter Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.
            </v-alert>
            <!-- Fehleranzeige -->
            <v-alert
            color="error" type="error" class="mb-4" closable v-model="showInvalidCredentialsWarning">
                <p>Falsche E-Mail-Adresse oder Password</p>
            </v-alert>
            </v-form>
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






