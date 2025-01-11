//Store für Posts die der Admin erstellt
//getAdminPostsByHouseId(houseId), createAdminPost(adminPost), deleteAdminPost, getAdminBoardIdByHouseId 

import { API_URL } from "@/api";
import { defineStore } from 'pinia';
import axios from 'axios';
import { useAuthStore } from "@/stores/authStore";

export const useAdminPostStore = defineStore('adminPost', {
    state: () => ({
        adminPosts: [], //Array for all admin Posts 
        boards: [], //Array for the boards which stores the adminBoard
    }),

    actions: {
      async createAdminPost(adminPost) {
        try {
          console.log("API-Request wird gesendet mit Daten:", adminPost);

          //Beim Erstellen des AdminPosts muss der User korrekt als Admin gesetzt werden 
          //Zuerst wird geprüft ob der User eingeloggt ist, dafür nutzen wir den authstore
          const authStore = useAuthStore(); 

          if(!authStore.user) {
            console.error("Es ist kein Benutzer eingeloggt. AdminPost kann nicht ersellt werden.")
            return;
          }

          //Wir rufen aus dem LocalStore das jwt Token ab, damit wir wissen ob der User überhaupt berechtigt ist, einen AdminPost zu erstellen
          const token = localStorage.getItem("jwt");

          //Informationen des AdminUsers werden aus authStore geholt 
          const adminUser = {
            id: authStore.user.id, 
            username: authStore.user.username
          }; 

          //API Request Body wird erstellt um den adminUser korrekt als user zu übergeben 
          const requestBody = {
            ...adminPost, //Enthät alle anderen Post Daten (title, text etc.) 
            id: null,
            user: adminUser, //Eingeloggter Benutzer wird gesetzt 
            category: "FRONTPAGE", 
            timestamp: "2024-12-03T15:52:42", 
            photo: null, 
            starttime: null, 
            endtime: null, 
            facilityId: null, 
            private: false, 
            anonymous: false
          }

          console.log("Zeig mir den API-Body: ", requestBody); 


          
          const response = await axios.post(`${API_URL}admin/adminpost`, requestBody, {
            headers: {
              Authorization: token, //Token wird gesetzt im Header 
              "Content-Type": "application/json" //Dem Backend muss mitgeteilt werden, dass wir Daten im JSON Format senden 
            }
          });
      
          if (response.data) {
            console.log("AdminPost erfolgreich erstellt:", response.data);
            this.adminPosts.push(response.data); //Direkt in den Store einfügen
          } else {
            console.error("API hat keine gültige Antwort zurückgegeben:", response); //Debugging
          }
        } catch (error) {
          console.error("Fehler beim Erstellen des AdminPosts:", error); //Debugging
        }
      },


        async getAdminPostsByHouseId(houseId) {
            
                try {
                  console.log("Fetching AdminPosts for houseId:", houseId); //Debugging
                  const response = await axios.get(API_URL + 'admin/house/' + houseId + '/adminposts');
          
                  console.log("API Response:", response.data); //Debugging
          
                  if (Array.isArray(response.data)) {
                    this.adminPosts = response.data; 
                  } else {
                    console.error("Fehler: Die API hat kein Array zurückgegeben", response.data); //Debugging
                    this.adminPosts = [];
                  }
                } catch (error) {
                  console.error("Fehler beim Laden der AdminPosts:", error); //Debugging
                  this.adminPosts = [];
                }
            },
        
            


        async deleteAdminPost(postId) {
            await axios.delete(API_URL + 'admin/adminpost/' + postId);
            const index = this.adminPosts.findIndex(p => p.postId === postId);
            this.adminPosts.splice(index, 1);

        },

        async getAdminBoardIdByHouseId(houseId) {
          try {
            console.log(`Sende API-Request für BoardId von houseId: ${houseId}...`); //Debugging
        
            const response = await axios.get('${API_URL}admin/house/${houseId}/adminboard');
            
            console.log("API Antwort:", response.data); //Debugging: zeigt API-Antwort in der Konsole
        
            //Falls die API direkt eine Zahl (ID) zurückgibt, diese verwenden
            if (typeof response.data === "number") {
              this.boards.push({ houseId: houseId, id: response.data });
              console.log(`BoardId ${response.data} für houseId ${houseId} gefunden.`);
              return response.data;
            }
        
            //Falls die API ein Objekt zurückgibt, die ID extrahieren
            if (response.data && typeof response.data === "object" && response.data.id) {
              this.boards.push({ houseId: houseId, id: response.data.id });
              console.log(`BoardId ${response.data.id} für houseId ${houseId} gefunden.`); //Debugging
              return response.data.id;
            } 
        
            console.warn(`Keine gültige BoardId für houseId ${houseId} erhalten.`, response.data); //Debugging
            return null;
            
          } catch (error) {
            console.error(`Fehler beim Laden der BoardId für houseId ${houseId}:`, error); //Debugging
            return null;
          }
        },
        
        


    }

});