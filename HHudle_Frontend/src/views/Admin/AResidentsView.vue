<script setup>
import { ref, onMounted, computed } from 'vue';
import { useUserStore } from '@/stores/Admin/userStore';
import { useHouseStore } from '@/stores/Admin/houseStore';
import { useRoute } from 'vue-router';

const router = useRoute();

const userStore = useUserStore();
const dialog = ref(false);

const houseId = ref(Number(router.params.houseId));

const newResident = ref({

    mail: '',
    username: '',
    houseId: houseId.value,

});


// new resident
async function saveResident() {
    try {
        await userStore.createUser(newResident.value);
        dialog.value = false; c
        newResident.value = { mail: '', username: '', houseId: houseId.value }; // house Id bleibt erhalten
    } catch (error) {
        console.error('Error while saving resident:', error);
    }
}




</script>



<template>
    <v-container>
        <!-- Button: Add Resident -->
        <v-btn @click="dialog = true" class="mt-4" color="primary">
            + Add Resident
        </v-btn>

        <!-- Dialog zum Hinzufügen eines Residents -->
        <v-dialog v-model="dialog" max-width="500px">
            <v-card>
                <v-card-title>Add Resident</v-card-title>
                <v-card-text>
                    <!-- Formularfelder -->
                    <v-form @submit.prevent="saveResident">
                        <v-text-field v-model="newResident.mail" label="E-Mail" required></v-text-field>
                        <v-text-field v-model="newResident.username" label="Username" required></v-text-field>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-btn text @click="dialog = false">Cancel</v-btn>
                    <v-btn text color="primary" @click="saveResident">Save</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-container>
</template>



<style scoped></style>