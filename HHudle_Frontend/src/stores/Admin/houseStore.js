//Store für ein einzelnes Haus, alle Aktionen die auf HouseView ausgeführt werden können
//actions: createHouse(house), getAllHouses(), getHouse(houseId), deleteHouseById(houseId)

import { API_URL } from "@/api";
import { defineStore } from 'pinia';
import axios from 'axios';

export const useHouseStore = defineStore('house', {
    state: () => ({
        houses: [], //Array for all houses 
    }),

    actions: {
        async createHouse(house) {
            const response = await axios.post(API_URL + 'admin/house', house);
            this.houses.push(response.data);
        },

        async getAllHouses() {
            const response = await axios.get(API_URL + 'admin/houses');
            this.houses = response.data;
        },

        async getHouseById(houseId) {
            const house = this.houses.find(p => p.houseId === houseId)

            if (house !== undefined) {
                return house;
            }

            const response = await axios.get(API_URL + 'admin/house' + houseId)
            return response.data
        },

        async deleteHouseById(houseId) {
            await axios.delete(API_URL + 'admin/house/' + houseId);
            const index = this.houses.findIndex(p => p.houseId === houseId);
            this.houses.splice(index, 1);
        },
    }
})