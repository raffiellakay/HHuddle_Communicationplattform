<script setup>
import PostCard from '@/components/User/PostCard.vue';
import { useUserPostStore } from '@/stores/User/userPostStore';
import { computed, onMounted} from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();

const category = "FRONTPAGE"
const userPostStore = useUserPostStore();

onMounted(() => {
  userPostStore.setCategory(category); // Setze die Kategorie im Store
});
</script>

<template>

<v-container fluid class="pa-0">
    <PostCard :category="category"/>
</v-container>
    
</template>


<style scoped>






</style>