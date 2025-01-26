<script setup>

import NavbarResident from '@/components/User/Navbar.vue';
import NavbarPManagement from '@/components/Admin/Navbar.vue';
import NavbarDefault from '@/components/DefaultNavbar.vue';
import avatarraffy from '@/assets/Pictures/avatar_raffy.png';
import { useAuthStore } from '@/stores/authStore';

let userRoles = [];
  if (useAuthStore().user) {
    userRoles = useAuthStore().user.roles;
  }
const isPManagement = userRoles.find((role) => role === "ROLE_PMANAGEMENT");
const isResident = userRoles.find((role) => role === "ROLE_RESIDENT" )

</script>


<template>


  <NavbarResident v-if="isResident"></NavbarResident>
  <NavbarPManagement v-if="isPManagement"></NavbarPManagement>
  <NavbarDefault v-else></NavbarDefault>
<v-main>


  <v-card-title
    class=" bg-blue-lighten-5 text-center  "
    variant="text"    
  >
    <v-row  align="center"
    justify="center" class="bg-blue-lighten-5 px-0">
    <v-col cols="1">
      <v-img
   :src= avatarraffy
  />
      </v-col>
    <v-col cols="1">
      <v-img
   :src= avatarraffy
  />
      </v-col>
      <v-col cols="1">
      <v-img
   :src= avatarraffy
   />
   <v-img
   class="d-inline pa-2 "
 

   :src= avatarraffy
  />
      </v-col>
  </v-row>


  <h1>Über uns</h1>

    </v-card-title>

<v-card-subtitle class=" text-center pb-4 "  color="gray">
    <h2>Wer wir sind, wieso es diese Seite gibt & was eine Briefwahlkarte damit zu tun hat </h2>
</v-card-subtitle>

<v-container>
<v-card-text><p>   Jeder Person, die in einem größeren Wohnhaus wohnt, kennt mindestens eines der folgenden Szenarien:</p>
  <br>
  <p>
    <v-list>
  <v-list-item>  • das eigene Paket ist irgendwo im Haus abgegeben worden, aber man kann nicht genau entziffern bei welcher Tür </v-list-item>
    <v-list-item>  • im Haus bellt die ganze Zeit ein Hund/bohrt jemand zu Unzeiten Löcher in die Wand/passiert etwas anderes über das man sich aufregen möchte </v-list-item>
      <v-list-item>  • man möchte unbedingt den Kindergeburtstag im Gemeinschaftsraum feiern, aber der analoge Kalender zum Eintragen von Terminen ist irgendwie verschwunden</v-list-item>
        <v-list-item>  • man hat wegen einem der drei Themen einen handschriftlichen Zettel über die Briefkästen gehängt, aber das Putzpersonal hat den nach einem Tag schon abgenommen</v-list-item>
</v-list>
</p>
<br>
<p>
  Es ist klar, dass  Kommunikation, der Schlüssel ist, der all diese Themen löst, bevor sie zu einem wirklich großen Problem werden, das das nachbarschaftliche Miteinander stört.
  Aber wie nachhaltig die Person(en) addressieren, die man erreichen möchte, wenn man sich kaum kennt? Und sind wir ehrlich: eigentlich auch nicht kennen lernen will.
</p>
<br>
<br>
<p>
  Da kommt HHuddle ins Spiel! Als Hausverwaltung können Sie Ihren Bewohner*innen mit HHuddle eine online Plattform bieten, die die Kommunikation mittels Schmierzetteln über den Briefkästen 
  in das digitale Zeitalter hebt und gleichzeitig im Gegensatz zu selbsterstellten Chatgruppen sensible mit den Daten Ihrer Bewohner*innen umgeht.
  Auf mehreren nutzerfreundlichen Pinnwänden können Bewohner*innen Events in Gemeinschaftsräumen planen, ihre verschwundenen Pakete suchen, statt Gegenstände zur freien Entnahme einfach 
  auf die Briefkästen zu stellen online stellen, anbieten mit dem Hund Gassi zu gehen, der regelmäßig das ganze Haus beschallt, und sich am Schwarzen Brett bezüglich weiterer Anliegen Gehör verschaffen.
</p>
<br>
<p> Und all das ohne Zettelchaos oder chaotische Chatgruppe!</p>
<p>
  Gleichzeitig müssen Sie als Hausverwaltung auch keine Zettel mehr an die Tür hängen, die der Wind dann wegweht, um Ankündigungen zu machen.
</p>
<br>
<h3> Heben Sie die Kommunikation in Ihrem Wohnhaus auf ein neues, digitales Level!</h3>
<br>
<br>
</v-card-text>
<v-card-text class="text-body-1 text-blue-grey-darken-2">
 Sie haben bis hierher gelesen und fragen sich immernoch was jetzt eine Briefwahlkarte damit zu tun hat?
  Diese Frage ist leicht beantwortet: Die Idee kam, nachdem die Post einer der Gründerinnen eine Briefwahlkarte zugestellt hatte, die Briefwahlkarte aber nie in der Wohnung ankam.
</v-card-text>
</v-container>

</v-main>
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
h3 {
  font-size: 2.0em ;
    font-family: "Verdana";

  
}
p {
    font-size: 1.5em;
    font-family: "Verdana";
    font-weight: lighter;
}

</style>