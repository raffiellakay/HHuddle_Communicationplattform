<script setup>
import { useRouter } from 'vue-router'


const router = useRouter()


</script>

<template>
    <v-app>
        <v-app-bar :elevation="2" rounded color="primary">
            <template v-slot:prepend>
                <v-app-bar-nav-icon></v-app-bar-nav-icon>
            </template>
            <v-app-bar-title>Menü</v-app-bar-title>
        </v-app-bar>
        <v-container>
            <!-- Content goes here -->
        </v-container>
    </v-app>
</template>


<style scoped>






</style>