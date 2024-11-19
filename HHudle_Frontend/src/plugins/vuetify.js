// src/plugins/vuetify.js
import { createVuetify } from 'vuetify'
import 'vuetify/styles' // Import Vuetify CSS
import '@mdi/font/css/materialdesignicons.css' // Import Material Design Icons (optional)

// Vuetify Configuration
export default createVuetify({
  theme: {
    defaultTheme: 'light',
    themes: {
      light: {
        colors: {
          primary: '#6200ea', // Custom primary color
          secondary: '#03dac6', // Custom secondary color
          success: '#4caf50',
          error: '#f44336',
          warning: '#ff9800',
          info: '#2196f3',
        },
      },
      dark: {
        colors: {
          primary: '#bb86fc', // Custom dark mode primary color
          secondary: '#03dac6',
        },
      },
    },
  },
  icons: {
    defaultSet: 'mdi', // Use Material Design Icons
  },
})
