
<template>
    
<NavbarResident v-if="isResident"></NavbarResident>
<NavbarPManagement v-if="isPManagement"></NavbarPManagement>
<NavbarDefault v-if="!isResident && !isPManagement"></NavbarDefault>
  
    <v-container class="container" style="margin-top: 3rem;" :class="isMobile ? 'mobile-style' : ''">

        <v-form ref="form" v-model="valid" @submit.prevent="submitForm" class="px-5 py-3" style="font-size: 16px;">
           
          <v-alert
      v-if="showSuccessMessage"
      class="success-message"
      icon="$success"
      type="success"
  variant="tonal"
      title="Nachricht gesendet!"
      closable
      dismissible
      @input="showSuccessMessage = false"
    >
   <p style="font-size: 16px">Vielen Dank für deine Kontaktaufnahme! </p> 
   <p style="font-size: 16px;">Wir melden uns so schnell wie möglich bei dir!</p>
  </v-alert>
            <h1 class="pb-8">Kontaktiere uns</h1>
            <v-row>
                <v-col :cols="isMobile ? 12 : 6" class="custom-height">
                    <label>Vorname <span>*</span></label>
                    <v-hover>
                    <v-text-field color="hsl(169, 82%, 27%)" base-color="hsl(170, 80%, 27%)" class="custom-inputs" v-model="firstName" style="margin-top: 8px;" variant="outlined" density="comfortable"
                        required></v-text-field>
                    </v-hover>
                    <div style="height: 5px; margin-top: -12px; margin-bottom: 10px; font-size: 14px; color:hsl(0, 67%, 59%);"
                        v-if="firstNameErrors.length !== 0">
                        Bitte fühle das Feld aus.</div>
                </v-col>
                <v-col :cols="isMobile ? 12 : 6">
                    <label>Nachname <span>*</span></label>
                    <v-hover>
                    <v-text-field color="hsl(169, 82%, 27%)" base-color="hsl(170, 80%, 27%)" v-model="lastName" style="margin-top: 8px;" variant="outlined" density="comfortable"
                        required></v-text-field>
                    </v-hover>
                    <div style="height: 5px; margin-top: -12px; margin-bottom: 10px; font-size: 14px; color:hsl(0, 67%, 59%);"
                        v-if="lastNameErrors.length !== 0">
                        Bitte fühle das Feld aus.</div>
                </v-col>
            </v-row>
            <v-row style="margin-top: -20px;">
                <v-col cols="12">
                    <label>E-mail Adresse <span>*</span></label>
                    <v-text-field color="hsl(169, 82%, 27%)" base-color="hsl(170, 80%, 27%)" class="custom-inputs" v-model="email" style="margin-top: 8px;" variant="outlined" density="comfortable"
                        required></v-text-field>
                    <div style=" margin-top: -8px;margin-bottom: 10px ; font-size: 14px; color:hsl(0, 67%, 59%);"
                        v-if="emailErrors.includes('Email is required')">
                        Bitte fühle das Feld aus.</div>
                    <div style=" margin-top: -8px;margin-bottom: 10px ; font-size: 14px; color:hsl(0, 67%, 59%);"
                        v-else-if="emailErrors.includes('Email must be valid')">E-Mail Adresse muss gültig sein.</div>
                </v-col>
            </v-row>
    
            
            <v-row style="margin-top: -20px;">
                <v-col cols="12">
                    <label> Nachricht<span>*</span></label>
                    <v-textarea color="hsl(169, 82%, 27%)" base-color="hsl(170, 80%, 27%)" class="custom-inputs" v-model="message" auto-grow variant="outlined"></v-textarea>
                </v-col>
            </v-row>
            <div style="height: 5px; margin-top: -22px; margin-bottom: 22px; font-size: 14px; color:hsl(0, 67%, 59%);"
                v-if="messageErrors.length !== 0">
                Bitte fühle das Feld aus.</div>
            <v-row style="margin-top: -16px; margin-left: -20px;" align="center">
                <v-col cols="1">
                    <v-checkbox v-model="consent" required></v-checkbox>
                </v-col>
                <v-col cols="11">
                    <p style="margin-bottom: 20px;" :style="[isMobile ? {'margin-left': '20px'} : {'margin-left':'0px'}]">Ich stimme zu von HHuddle kontaktiert zu werden. 
          
                        <span>*</span>
                    </p>
                </v-col>

            </v-row>
            <div style="height: 5px; margin-top: -40px; margin-bottom: 40px; font-size: 14px; color:hsl(0, 67%, 59%);"
                v-if="consentErrors.length !== 0">
                Um die Nachricht senden zu können, stimme bitte zu.</div>
                
            <v-btn @click="validateForm" block height="52">
                Abschicken
            </v-btn>

            

        </v-form>
    </v-container>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import NavbarResident from '@/components/User/Navbar.vue';
import NavbarPManagement from '@/components/Admin/Navbar.vue';
import NavbarDefault from '@/components/DefaultNavbar.vue';
import { useAuthStore } from '@/stores/authStore';

// for matching navbar
let userRoles = [];
  if (useAuthStore().user) {
    userRoles = useAuthStore().user.roles;
  }
const isPManagement = userRoles.find((role) => role === "ROLE_PMANAGEMENT");
const isResident = userRoles.find((role) => role === "ROLE_RESIDENT" )



const valid = ref(false);

const firstName = ref('');
const lastName = ref('');
const email = ref('');
const message = ref('');
const consent = ref(false);
const showSuccessMessage = ref(false);

// Error messages

const firstNameErrors = ref([])
const lastNameErrors = ref([])
const emailErrors = ref([])
const messageErrors = ref([])
const consentErrors = ref([])

const validateForm = () => {
    firstNameErrors.value = []
    lastNameErrors.value = []
    emailErrors.value = []
  
    messageErrors.value = []
    consentErrors.value = []

    if (!firstName.value) {
        firstNameErrors.value.push('First name is required')
    }
    if (!lastName.value) {
        lastNameErrors.value.push('Last name is required')
    }
    if (!email.value) {
        emailErrors.value.push('Email is required')
    } else if (!/.+@.+\..+/.test(email.value)) {
        emailErrors.value.push('Email must be valid')
    }

    if (!message.value) {
        messageErrors.value.push('Please write a message');
    }

    if (!consent.value) {
        consentErrors.value.push('Please consent to this');
    }

    if (!firstNameErrors.value.length && !lastNameErrors.value.length && !emailErrors.value.length) {
        submitForm()
    }
}
//inlinefkt deshalb kein this.
const submitForm = () => {
    console.log('Form submitted:', { firstName: firstName.value, lastName: lastName.value, email: email.value });
    showSuccessMessage.value = true;
    firstName.value = '';
    lastName.value = '';
    message.value = '';
    email.value = '';
    consent.value = false;

}

const isMobile = ref(window.innerWidth < 600)

// Function to handle window resize
const handleResize = () => {
  isMobile.value = window.innerWidth < 600
}

onMounted(() => {
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
})

const form = ref(null);

</script>


<style scoped>
.container {
    max-width: 700px;
    background-color: white;
    border-radius: 20px
}


span {
    color: hsl(169, 82%, 27%)
}

button {
    background-color: hsl(169, 82%, 27%);
    color: white;
}

.v-checkbox {
    color: hsl(169, 82%, 27%);
}

.mobile-style{
    max-width: 90%;
    margin-bottom: 2rem
    
}

.success-message{
    position: absolute;
    top: 4rem;
    left: 0rem;
  

}


</style>