//Store für den Admin zur Userverwaltung
//actions: createUser, updateUser




import { API_URL } from "@/api";
import { defineStore } from 'pinia';
import axios from 'axios';

export const useUserStore = defineStore('user', {
    state: () => ({
        users: [], //Array for all users living in a house
    }),

    actions: {
        async createUser(user) {
            const response = await axios.post(API_URL + 'admin/user', user);
            this.users.push(response.data);
        },

        //It is unclear, whether the put request needs to explicitly send the userId or if the userId is sent when sending the User.
        //Has to be tested
        async updateUser(userId, user) {
            const response = await axios.put(API_URL + 'admin/user' + user);
            const index = this.users.findIndex(p => p.userId === userId);
            if (index !== -1) {
                this.users[index] = response.data;
            }

            return response.data;
        },

        async getAllUsersByHouseId(houseId) {
            const usersInStore = this.users.filter(user => user.houseId === houseId);

            if (usersInStore.length > 0) {
                return usersInStore;  // Falls Daten bereits existieren, direkt zurückgeben
            }

            try {
                const response = await axios.get(API_URL + 'admin/house/' + houseId + '/user');

                this.users = response.data;

                return response.data;
            } catch (error) {
                console.error("Fehler beim Laden der Benutzer:", error.response?.data || error.message);
                throw error;
            }
        }

    },
});