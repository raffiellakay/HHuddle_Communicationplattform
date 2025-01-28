import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import { defineRule, configure} from "vee-validate";
import { required, email, min, max } from '@vee-validate/rules';



import 'vuetify/styles'
import '@mdi/font/css/materialdesignicons.css'

import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import axios from 'axios'


// 1. Globale Regeln definieren
defineRule('required', required); // Regel für Pflichtfelder
defineRule('email', email);       // Regel für gültige E-Mail
defineRule('min', min);           // Regel für Mindestanzahl Zeichen
defineRule('max', max);           // Regel für maximale Anzahl Zeichen

// 2. Vee-Validate konfigurieren (globale Fehlernachrichten)
configure({
  generateMessage: (ctx) => {
    const messages = {
      required: `${ctx.field} ist erforderlich.`,
      email: `${ctx.field} muss eine gültige E-Mail-Adresse sein.`,
      min: `${ctx.field} muss mindestens ${ctx.rule.params[0]} Zeichen lang sein.`,
      max: `${ctx.field} darf maximal ${ctx.rule.params[0]} Zeichen lang sein.`,
    };
    return messages[ctx.rule.name] || `${ctx.field} ist ungültig.`;
  },
  validateOnInput: true, // Validierung direkt beim Eingeben
});



const vuetify = createVuetify({
    components, 
    directives,
    locale: 'de',
    icons: {
      defaultSet: 'mdi',
    }
  })

  axios.interceptors.request.use(request => {
    const jwt = localStorage.getItem('jwt')
    if(!request.headers.hasAuthorization() && jwt) {
        request.headers.setAuthorization(jwt)
    }
    return request
})
const app = createApp(App)



app.use(createPinia())
app.use(router)
app.use(vuetify)




app.mount('#app')

//Reihenfolge der Plugins beachten: Es ist besser, Plugins wie Vuetify und Pinia vor app.mount('#app') zu registrieren. Könnte in manchen komplexeren Setups zu Problemen führen. 