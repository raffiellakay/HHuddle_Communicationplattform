import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'

import 'vuetify/styles'
import '@mdi/font/css/materialdesignicons.css'

import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'



const vuetify = createVuetify({
    components, 
    directives,
    locale: 'de',
    icons: {
      defaultSet: 'mdi',
    }
  })


const app = createApp(App)



app.use(createPinia())
app.use(router)
app.use(vuetify)




app.mount('#app')

//Reihenfolge der Plugins beachten: Es ist besser, Plugins wie Vuetify und Pinia vor app.mount('#app') zu registrieren. Könnte in manchen komplexeren Setups zu Problemen führen. 