import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { createVuetify } from 'vuetify'
import 'vuetify/styles'               // Vuetify-Styles hinzufügen
import App from './App.vue'

const app = createApp(App)
const vuetify = createVuetify()      // Vuetify-Instanz erstellt



app.use(createPinia())
app.use(vuetify)                    // Vuetify eingebunden
app.mount('#app')

//Reihenfolge der Plugins beachten: Es ist besser, Plugins wie Vuetify und Pinia vor app.mount('#app') zu registrieren. Könnte in manchen komplexeren Setups zu Problemen führen. 