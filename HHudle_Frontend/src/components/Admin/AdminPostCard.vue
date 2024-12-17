<script setup>
import { computed, onMounted, ref, watch } from "vue";
import { useAdminPostStore } from "@/stores/Admin/adminPostStore";
import { useRouter } from "vue-router";

//Initalisieren des AdminPostStore
const adminPostStore = useAdminPostStore();
const adminPosts = computed(() => adminPostStore.adminPosts);
const router = useRouter();

onMounted(async () => {
  await adminPostStore.getAdminPostsByHouseId(houseId);
});

//Löschen des AdminPosts
async function deleteAdminPost(postId) {
  await adminPostStore.deleteAdminPost(postId);
  //Eventuell kommt hier ein router.push je nachdem wo nach dem Löschen hinnavigiert werden soll
}
</script>

<template>
  <!--Card layout without store implementation-->
  <v-row align="center" justify="center" dense>
    <v-col cols="12" md="6">
      <v-card
        append-icon="mdi-check"
        class="mx-auto"
        prepend-icon="mdi-account"
        subtitle="prepend-icon and append-icon"
        title="Icons"
      >
        <v-card-text
          >Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          eiusmod.</v-card-text
        >
      </v-card>
    </v-col>
  </v-row>

  <v-row align="center" justify="center" dense>
    <v-col cols="12" md="6">
      <v-card 
      v-for="adminPost in adminPosts" 
      :key="adminPost.id"
      title={{ adminPost.title }}> 

      <v-card-text>{{ adminPost.description }}</v-card-text>
    
    
    
    
    
    </v-card>
    </v-col>
  </v-row>
</template>
