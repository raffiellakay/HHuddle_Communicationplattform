//createFacility(facility), getAllFacilitiesByHouseId(houseId), deleteFacilityById(facilityId), updateFacility(facility)

import { API_URL } from "@/api";
import { defineStore } from 'pinia';
import axios from 'axios';

export const useFacilityStore = defineStore('facility', {
    state: () => ({
        facilities: [], //Array for all facilities
    }),

    actions: {
        async createFacility(facility) {
            facility.id = null;
            facility.postDto = null;
            const response = await axios.post(API_URL + 'admin/facility', facility);
            this.facilities.push(response.data);
        },

        async getAllFacilitiesByHouseId(houseId) {
            const response = await axios.get(API_URL + 'admin/house/' + houseId + '/facility')
            this.facilities = response.data
            return response.data
        },

        async deleteFacilityById(facilityId) {
            await axios.delete(API_URL + 'admin/facility/' + facilityId);
            const index = this.facilities.findIndex(p => p.id === facilityId);
            this.facilities.splice(index, 1);
        }
    }
})