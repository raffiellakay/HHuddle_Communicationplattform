<script setup>
import PostCard from '@/components/User/PostCard.vue';
import { computed } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();

const category = "FRONTPAGE"

</script>

<template>
Ich bin die User Home View

<v-container>
    <PostCard :category="category"/>
</v-container>
    
</template>