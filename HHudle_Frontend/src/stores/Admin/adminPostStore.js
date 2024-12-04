//Store für Posts die der Admin erstellt
//getAdminPostsByHouseId(houseId), createAdminPost(adminPost), deleteAdminPost, getAdminBoardIdByHouseId 

import { API_URL } from "@/api";
import { defineStore } from 'pinia';
import axios from 'axios';

export const useHouseStore = defineStore('adminPost', {
    state: () => ({
        adminPosts: [], //Array for all admin Posts 
        boards: [], //Array for the boards which stores the adminBoard
    }),

    actions: {
        async createAdminPost(adminPost) {
            const response = await axios.post(API_URL + 'admin/adminpost', adminPost);
            this.adminPost.push(response.data);
        },


        async getAdminPostsByHouseId(houseId) {
            const adminPost = this.adminPosts.find(p =>houseId === houseId)

            if(adminPost !== undefined) {
                return adminPost;
            }

            const response = await axios.get(API_URL + 'admin/house/' + houseId + '/adminposts')
            return response.data

        },

        async deleteAdminPost(postId) {
            await axios.delete(API_URL + 'admin/adminpost/' + postId);
            const index = this.adminPosts.findIndex(p => p.postId === postId);
            this.adminPosts.splice(index, 1);

        },

        async getAdminBoardIdByHouseId(houseId) {
            const adminBoard = this.boards.find(p => p.houseId === houseId)

            if( adminBoard !== undefined ) {
                return adminBoard;
            }

            const response = await axios.get(API_URL + 'admin/house/' + houseId + '/adminboard')
            return response.data
        }



    }

})