<script setup>
import { RouterLink, RouterView } from 'vue-router';
import Navbar from './components/Navbar.vue';

</script>

<template>

  <!-- **router von milly 
  <v-app>
    <div id="app" data-app>
      <RouterView></RouterView>
    </div>

    <Navbar />
  </v-app>
  ** -->
  <v-container class="d-flex align-center justify-center" style="min-height: 100vh;">
    <v-card max-width="400" class="pa-4">
      <v-card-title class="text-h5">Login</v-card-title>
      <v-form @submit.prevent="login" v-model:valid="formValid">
        <v-text-field
          v-model="credentials.email"
          label="E-Mail"
          placeholder="Enter your email"
          :rules="emailRules"
          required
          type="email"
        ></v-text-field>
        <v-text-field
          v-model="credentials.password" 
          label="Password"
          placeholder="Enter your password"
          :rules="passwordRules"
          required
          type="password"
        ></v-text-field>
        <v-btn
          type="submit"
          color="primary"
          :disabled="!formValid"
          class="mt-4"
        >
          Login
        </v-btn>
        <v-btn
          to="/signup"
          text
          class="mt-2"
        >
          Don't have an account? Sign Up
        </v-btn>
      </v-form>
    </v-card>
  </v-container>
</template>



<style scoped>

body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
  background-color: #f0f2f5; /* Helles Hintergrundgrau für Eleganz */
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh; /* Vollbildhöhe */
}

/* **2. Login-Kachel** */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
}

.login-card {
  background: white; /* Weißer Hintergrund für Kontrast */
  padding: 30px;
  border-radius: 10px; /* Abgerundete Ecken */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Leichter Schatten */
  max-width: 400px;
  width: 100%;
  text-align: center;
}

/* **3. Titel** */
.login-card h2 {
  margin-bottom: 20px;
  font-size: 24px;
  color: #333;
}

/* **4. Formulargruppen** */
.form-group {
  margin-bottom: 20px;
  text-align: left;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #555;
}

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
  box-sizing: border-box;
  transition: border-color 0.3s ease;
}

input:focus {
  border-color: #007bff; /* Blaues Highlight beim Fokus */
  outline: none;
}

/* **5. Button** */
.login-button {
  width: 100%;
  padding: 12px;
  background-color: #007bff; /* Primärfarbe: Blau */
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.login-button:hover {
  background-color: #0056b3; /* Dunkleres Blau beim Hover */
}

/* **6. Responsivität** */
@media (max-width: 600px) {
  .login-card {
    padding: 20px;
  }

  .login-card h2 {
    font-size: 20px;
  }
}
</style>






