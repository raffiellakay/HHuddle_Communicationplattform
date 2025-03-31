<script setup>
import PostCard from '@/components/User/PostCard.vue';
import { useUserPostStore } from '@/stores/User/userPostStore';
import { computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();



////////////////////////// CODE SNIPPET START //////////////////////////


//Ursprung: die Konstante category wird fest auf "BLACKBOARD" gesetzt
const category = "BLACKBOARD"
const userPostStore = useUserPostStore();

const props = defineProps ({
  houseId: Number, 
})
onMounted(async () => {
  if (props.boardId) {
    await userPostStore.getPostsByHouseId(props.boardId);
  }
});

const refreshPosts = async() => {
  if(props.houseId) {
    await userPostStore.getPostsByHouseId(props.boardId);
    console.log(`Refreshe Posts für Haus mit ID: ${props.houseId}`)
  }
}


//Kategorie wird im userPostStore gespeichert
onMounted(() => {
  userPostStore.setCategory(category); //Setze die Kategorie im Store
});

</script>

<template>

<v-container>
  <!--category wird als prop and PostCard Komponente übergeben, in diesem fall mit dem Wert "BLACKBOARD"-->
    <PostCard 
    :category="category"
    @userPost-added="refreshPosts"/>
</v-container>
    
</template>