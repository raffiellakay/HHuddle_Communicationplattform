<script setup>
import PostCard from '@/components/User/PostCard.vue';
import { computed } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();

const category = "BLACKBOARD"

</script>

<template>
Ich bin das schwarze Brett

<v-container>
    <PostCard :category="category"/>
</v-container>
    
</template>