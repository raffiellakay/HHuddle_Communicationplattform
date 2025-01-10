<script setup>

import AdminPostCard from '@/components/Admin/AdminPostCard.vue';
import { useAdminPostStore } from '@/stores/Admin/adminPostStore';
import AdminPostForm from '@/components/Admin/AdminPostForm.vue'
import { computed, onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";




const adminPostStore = useAdminPostStore();
const adminPosts = computed(() => adminPostStore.adminPosts);
const showAdminPostForm = ref(false);

const addAdminPost = async (adminPost) => {
  await adminPostStore.createAdminPost(adminPost);
  showAdminPostForm=false;
}



const route = useRoute();
const show = ref(false);

const props = defineProps({
  houseId: Number // Falls es als String übergeben wird, sonst Number
});



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