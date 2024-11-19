<script setup>

//JS Teil

</script>




<template> 

<div class="login-container">
    <div class="login-card">
      <h2>Login</h2>
      <form>
        <div class="form-group">
          <label for="username">Benutzername</label>
          <input type="text" id="username" placeholder="Gib deinen Benutzernamen ein" />
        </div>
        <div class="form-group">
          <label for="password">Passwort</label>
          <input type="password" id="password" placeholder="Gib dein Passwort ein" />
        </div>
        <button type="submit" class="login-button">Anmelden</button>
      </form>
    </div>
  </div>
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
