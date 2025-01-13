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
            try {
              console.log(`Fetching house with ID: ${houseId}`); // 🔎 Debugging
          
              // Falls das Haus im Store existiert, returnen
              let house = this.houses.find(h => h.id == houseId);
              if (house) {
                console.log(`✅ Haus ${houseId} aus dem Store geladen.`);
                return house;
              }
          
              // API-Request, falls das Haus noch nicht im Store ist
              const response = await axios.get(`${API_URL}admin/house/${houseId}`);
          
              if (response.data) {
                this.houses.push(response.data); // ✅ Haus zum Store hinzufügen
                console.log(`✅ Haus ${houseId} aus der API geladen.`);
                return response.data;
              } else {
                console.warn(`⚠️ Haus mit ID ${houseId} wurde nicht gefunden.`);
                return null;
              }
            } catch (error) {
              console.error("❌ Fehler beim Abrufen des Hauses:", error);
              return null;
            }
          },

        async deleteHouseById(houseId) {
            await axios.delete(API_URL + 'admin/house/' + houseId);
            const index = this.houses.findIndex(p => p.houseId === houseId);
            this.houses.splice(index, 1);
        },
    }
})