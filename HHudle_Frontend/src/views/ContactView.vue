<script setup>


import NavbarResident from '@/components/User/Navbar.vue';
import NavbarPManagement from '@/components/Admin/Navbar.vue';
import NavbarDefault from '@/components/DefaultNavbar.vue';
import { useAuthStore } from '@/stores/authStore';

let userRoles = [];
  if (useAuthStore().user) {
    userRoles = useAuthStore().user.roles;
  }
const isPManagement = userRoles.find((role) => role === "ROLE_PMANAGEMENT");
const isResident = userRoles.find((role) => role === "ROLE_RESIDENT" )

</script>


<template>
<<<<<<< HEAD

  <NavbarResident v-if="isResident"></NavbarResident>
  <NavbarPManagement v-if="isPManagement"></NavbarPManagement>
  <NavbarDefault v-else></NavbarDefault>

  <v-main>
        Das ist die Kontaktseite 
  </v-main>

 

=======
>>>>>>> 6eb53bd73f6978779870e3ea383f19f273b70b0d


Das ist die Kontaktseite
    
</template>