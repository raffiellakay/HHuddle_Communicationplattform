<script setup>
import { useRouter } from "vue-router";



const router = useRouter();





</script>


<template>
    <v-container>
    
      
        <v-card 
        @click="router.push('/user/board/commonrooms')"
        class="board-card"
        :elevation="3" 
        :rounded="2"
        hover>
        
            <v-card-title>Gemeinschaftsräume</v-card-title>
        </v-card>
        <v-card 
        @click="router.push('/user/board/blackboard')"
        class="board-card">
            <v-card-title>Schwarzes Brett</v-card-title>
        </v-card>
        <v-card 
        @click="router.push('/user/board/packagefinder')"
        class="board-card">
            <v-card-title>Paketfinder</v-card-title>
        </v-card>
        <v-card 
        @click="router.push('/user/board/search&find')"
        class="board-card">
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