<script setup>

import Navbar from '@/components/User/Navbar.vue';
import avatarraffy from '@/assets/Pictures/avatar_raffy.png';

</script>


<template>
<v-app>

<Navbar  />

   
 <v-main>
        <v-card-title
    class=" bg-blue-lighten-5 text-center pa-2 rounded mx-auto pa-2 rounded "
    variant="text"    
  >
  <h1>Über uns</h1>

    </v-card-title>

<v-card-subtitle class=" text-center pb-4 " color="gray">
    <h2>Wer wir sind, wieso es diese Seite gibt & was eine Katze damit zu tun hat </h2>
</v-card-subtitle>

<v-row class="flex-row justify-center h-25 pb-8">
 

 <v-img
class="d-inline pa-2"
 

   :src= avatarraffy
   
  />
   <v-img
   class="d-inline pa-2"
 

   :src= avatarraffy
  />
   <v-img
 class="d-inline pa-2"


   :src= avatarraffy
   />
   <v-img
   class="d-inline pa-2 "
 

   :src= avatarraffy
   />
 

</v-row>

<v-card-text><p>    Das sind wir.</p></v-card-text>

</v-main>
</v-app>
 
</template>
<style>
h1 {
  font-size: 3.5em;
  font-family: "Georgia";
 
}
h2 {
    font-size: 2.0em ;
    font-family: "Verdana";
    font-weight: lighter;
    font-style : italic;
}
p {
    font-size: 1.7em;
    font-family: "Verdana";
    font-weight: lighter;
}
</style>