<script setup>

import AdminPostCard from '@/components/Admin/AdminPostCard.vue';
import { useAdminPostStore } from '@/stores/Admin/adminPostStore';
import { computed, onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";




const adminPostStore = useAdminPostStore();
const route = useRoute();
const show = ref(false);

const props = defineProps({
  houseId: Number // Falls es als String übergeben wird, sonst Number
});

//Holt AdminPosts aus Store
const adminPosts = computed(() => adminPostStore.adminPosts);

// HouseId aus Route holen
const houseId = computed(() => route.params.houseId);

onMounted(async () => {
  if (houseId.value) {
    await adminPostStore.getAdminPostsByHouseId(houseId.value);
  }
});

</script>

<template>



    <h1>Admin Posts für Haus {{ houseId }}</h1>

<v-container>
<AdminPostCard/>
</v-container>




</template>