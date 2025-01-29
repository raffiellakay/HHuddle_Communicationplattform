<script setup>
import { ref, onMounted, computed } from 'vue';
import { useFacilityStore } from '@/stores/Admin/facilityStore';
import { useRouter } from 'vue-router';

const router = useRouter();
const facilityStore = useFacilityStore();
const houseId = ref(Number(router.params.houseId));

const newFacility = ref({
    id: null,
    type: '',
    description: '',
    houseId: houseId.value,
    postDtos: null
})




</script>


<template></template>



<style scoped></style>