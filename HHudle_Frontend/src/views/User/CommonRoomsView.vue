<script setup>
import PostCard from '@/components/User/PostCard.vue';
import { computed } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();

const category = "EVENTS"

</script>

<template>
Ich bin die Übersicht für Gemeinschaftsräume 

<v-container>
    <PostCard :category="category"/>
</v-container>
    
</template>