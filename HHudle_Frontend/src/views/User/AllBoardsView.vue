<script setup>
import { useRoute } from "vue-router";
import BoardCard from "@/components/User/BoardCard.vue";



const route = useRoute();





</script>


<template>
    <v-container>
    
      
        <v-card 
        class="board-card"
        :elevation="3" 
        :rounded="2"
        hover>
        
            <v-card-title>Gemeinschaftsräume</v-card-title>
        </v-card>
        <v-card class="board-card">
            <v-card-title>Schwarzes Brett</v-card-title>
        </v-card>
        <v-card class="board-card">
            <v-card-title>Paketfinder</v-card-title>
        </v-card>
        <v-card class="board-card">
            <v-card-title>Suchen und Finden</v-card-title>
        </v-card>
  
    
    </v-container>
    
    
    </template>



<style scoped>

.board-card {
    margin: 10px;
    width: 300px; 

}





</style>