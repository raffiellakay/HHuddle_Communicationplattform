<script setup>
import PostCard from '@/components/User/PostCard.vue';
import { computed } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const category = computed(() => route.params.category);
</script>

<template>
Ich bin das schwarze Brett

<v-container>
    <PostCard :category="category"/>
</v-container>
    
</template>