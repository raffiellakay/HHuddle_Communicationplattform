<script setup>
import avatarraffy from '@/assets/Pictures/avatar_raffy.png';
import avatarhuda from '@/assets/Pictures/avatar_huda.png';
import avatarmilly from '@/assets/Pictures/avatar_milly.png';
import avatarksenia from '@/assets/Pictures/avatar_ksenia.png';

</script>


<template>
  
  <v-card class="side">
    <v-container class="header">
    
      
      <v-row  align="center"
    justify="center" >
    <h1>Über uns</h1>
  </v-row>
      <v-row  align="center"
      justify="center" >
      <v-col cols="1">
        <v-img
     :src= avatarmilly
    />
        </v-col>
      <v-col cols="1">
        <v-img
     :src= avatarhuda
    />
        </v-col>
        <v-col cols="1">
        <v-img
     :src= avatarraffy
     />
    </v-col>
     <v-col cols="1">
     <v-img
     :src= avatarksenia
    />
        </v-col>
    </v-row>
  
    
    <v-row  align="center"
      justify="center" >
  <v-card-subtitle  >
      <h2>Wer wir sind, wieso es diese Seite gibt & was eine Briefwahlkarte damit zu tun hat </h2>
  </v-card-subtitle>
  </v-row>
  
     
    
     
  </v-container>
  <v-container class="text">

  
<v-card-text><p>   Jeder Person, die in einem größeren Wohnhaus wohnt, kennt mindestens eines der folgenden Szenarien:</p>
  <br>
  <p>
    <v-list>
  <v-list-item>  • das eigene Paket ist irgendwo im Haus abgegeben worden, aber man kann nicht genau entziffern bei welcher Tür </v-list-item>
    <v-list-item>  • im Haus bellt die ganze Zeit ein Hund/bohrt jemand zu Unzeiten Löcher in die Wand/passiert etwas anderes über das man sich aufregen möchte </v-list-item>
      <v-list-item>  • man möchte unbedingt den Kindergeburtstag im Gemeinschaftsraum feiern, aber der analoge Kalender zum Eintragen von Terminen ist irgendwie verschwunden</v-list-item>
        
</v-list>
</p>
<br>
<p>
 Was braucht es um diese Probleme zu lösen? K-O-M-M-U-N-I-K-A-T-I-O-N.
  Aber wie nachhaltig die Person(en) addressieren, die man erreichen möchte, wenn man sich kaum kennt? Und sind wir ehrlich: eigentlich auch nicht kennen lernen will.
</p>
<br>
<br>
<p>
  Was braucht es um diese Problem zu lösen?
  
  </p>
  <br> 
  <h3> HHUDDLE</h3>
  <br>
  <p>
   Als Hausverwaltung können Sie Ihren Bewohner*innen mit HHuddle eine Onlineplattform bieten, die die Kommunikation mittels Schmierzetteln über den Briefkästen 
  in das digitale Zeitalter hebt und gleichzeitig im Gegensatz zu selbsterstellten Chatgruppen sensible mit den Daten Ihrer Bewohner*innen umgeht.
  Auf mehreren nutzerfreundlichen Pinnwänden können Bewohner*innen Events in Gemeinschaftsräumen planen, ihre verschwundenen Pakete suchen, statt Gegenstände zur freien Entnahme einfach 
  auf die Briefkästen zu stellen online stellen, anbieten mit dem Hund Gassi zu gehen, der regelmäßig das ganze Haus beschallt, und sich am Schwarzen Brett bezüglich weiterer Anliegen Gehör verschaffen.
</p>
<br>
<p> Und all das ohne Zettelwirtschaft oder chaotische Chatgruppe!</p>
<p>
  Gleichzeitig müssen Sie als Hausverwaltung auch keine Zettel mehr an die Tür hängen, die der Wind dann wegweht, um Ankündigungen zu machen.
</p>
<br>
<h3> Heben Sie die Kommunikation in Ihrem Wohnhaus auf ein neues, digitales Level!</h3>
<br>
<br>
</v-card-text>
<v-card-text >
 Sie haben bis hierher gelesen und fragen sich immernoch was jetzt eine Briefwahlkarte damit zu tun hat?
  Diese Frage ist leicht beantwortet: Die Idee kam, nachdem die Post einer der Gründerinnen eine Briefwahlkarte zugestellt hatte, die Briefwahlkarte aber nie in der Wohnung ankam.
</v-card-text>

</v-container>
  
  </v-card>
  
  </template>
  
  <style scoped>
.side {
  max-width: 1200;
}

.text {
  max-width: 900px;
}

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
  .header {
    background-color: #fac0b9;
    padding-top: 3rem;
    width: 100%;
  }
  </style>