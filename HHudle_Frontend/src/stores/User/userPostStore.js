//Store für den einzelnen Post auf einem Board
//actions: addPost(post), getPosts, getPost(postId), changePost(postId, post), deletePost(postId)

import { API_URL } from "@/api";
import { defineStore } from 'pinia';
import axios from 'axios';
import { useAuthStore } from "@/stores/authStore";

export const useUserPostStore = defineStore ('adminPost', {
    state: () => ({
        userPosts: [], //Array für alle UserPosts
        currentCategory: null, //Noch ein State für das Filtern nach Kategorien
        loading: false,
    }),

    getters: {
      filteredPostsByCategory: (state) => {
          if (!state.currentCategory) return state.userPosts;
          return state.userPosts.filter(
              (post) => post.category.toUpperCase() === state.currentCategory.toUpperCase()
          );
      },
  },



    actions: {
        async createUserPost(userPost) {
            try {
                console.log("API Request wird gesendet mit Daten: ", userPost);
                const authStore = useAuthStore(); 

          if(!authStore.user) {
            console.error("Es ist kein Benutzer eingeloggt. Post kann nicht ersellt werden.")
            return;
          }

          //Wir rufen aus dem LocalStore das jwt Token ab, damit wir wissen ob der User überhaupt berechtigt ist, einen Post zu erstellen
          const token = localStorage.getItem("jwt");

          //Informationen des Users werden aus authStore geholt 
          const user = {
            id: authStore.user.id, 
            username: authStore.user.username
          }; 

          //API Request Body wird erstellt um den adminUser korrekt als user zu übergeben 
          const requestBody = {
            ...userPost, //Enthät alle anderen Post Daten (title, text etc.) 
            user: user, //Eingeloggter Benutzer wird gesetzt 
           
          }

          console.log("Zeig mir den API-Body: ", requestBody); 


          
          const response = await axios.post(`${API_URL}posts/post`, requestBody, {
            headers: {
              Authorization: token, //Token wird gesetzt im Header 
              "Content-Type": "application/json" //Dem Backend muss mitgeteilt werden, dass wir Daten im JSON Format senden 
            }
          });
      
          if (response.data) {
            console.log("UserPost erfolgreich erstellt:", response.data);
            this.userPosts.push(response.data); //Direkt in den Store einfügen
          } else {
            console.error("API hat keine gültige Antwort zurückgegeben:", response); //Debugging
          }
        } catch (error) {
          console.error("Fehler beim Erstellen des UserPosts:", error); //Debugging
        }
      },

      async getBoardIdByHouseIdAndCategory(houseId, category) {
        try {
            console.log(`Lade boardId für houseId: ${houseId} und Kategorie: ${category}`);
            this.loading = true;
    
            // API-Aufruf
            const response = await axios.get(`${API_URL}posts/house/${houseId}/${category}`, {
                headers: {
                    Authorization: localStorage.getItem("jwt"),
                },
            });
    
            // Wenn die Antwort eine Zahl ist, direkt zurückgeben
            if (typeof response.data === "number") {
                console.log(`Gefundene boardId: ${response.data}`);
                return response.data;
            } else {
                console.warn("Antwort hat unerwartetes Format:", response.data);
                return null;
            }
        } catch (error) {
            console.error("Fehler beim Laden der boardId:", error);
            throw error; // Fehler erneut werfen, damit der Aufrufer darauf reagieren kann
        } finally {
            this.loading = false; // Ladezustand zurücksetzen
        }
    },


      async getPostsByHouseId(houseId) {
        try {
            console.log(`Lade alle Posts für House ID: ${houseId}`);
            this.loading = true;
            const response = await axios.get(`${API_URL}posts/house/${houseId}`);
            this.userPosts = response.data;
            console.log("Posts für House ID geladen:", this.userPosts);
        } catch (error) {
            console.error("Fehler beim Laden der Posts für House ID:", error);
        } finally {
            this.loading = false;
        }
    },

    setCategory(category) {
        console.log(`Setze aktuelle Kategorie: ${category}`);
        this.currentCategory = category;
    },

  

      async getHouseIdByUser() {
        const response = await axios.get(`${API_URL}user/home`); 
        this.userPosts = response.data;
      },


      async getPost(postId) {
     
            console.log("Fetching UserPost for postId:", postId); //Debugging

            const userPost = this.userPosts.find(p => p.postId === postId); 
            console.log("Checking if ", userPost , "exists with postId ", postId)

            const response = await axios.get(API_URL + 'posts/post/' + postId);
    
            console.log("API Response:", response.data); //Debugging

            this.userPosts = response.data;

 

    },

    async getPostsByUserId(userId) {
        console.log("Fetching UserPosts for userId: ", userId); //Debug 
        const response = await axios.get(API_URL + 'posts/user/' + userId);

        console.log("API Response:", response.data); //Debug

        this.userPosts = response.data;
    },

    async deletePost(postId) {
        console.log("Attempting to delete UserPost with PostId: ", postId);//Debug 

        await axios.delete(API_URL + 'posts/post/' + postId);
        const index = this.userPosts.findIndex(p => p.postId === postId);

        console.log("Lösche UserPost mit Index: ", index); //Debug

        this.userPosts.splice(index, 1); 

        console.log("UserPost gelöscht mit Index: ", index); //Debug 


    }, 

    async deletePostByUserId(userId) {
        console.log("Attempting to delete UserPost with userId: ", userId);//Debug 

        await axios.delete(API_URL + 'posts/' + userId);
        const index = this.userPosts.findIndex(p => p.userId === userId);

        console.log("Lösche UserPost mit UserId: ", index); //Debug

        this.userPosts.splice(index, 1); 

        console.log("UserPost gelöscht mit UserId: ", index); //Debug 


    }, 

    async updateUserPost(userPost, postId){
        try {
            const response = await axios.put(`${API_URL}posts/update/${postId}`, userPost);
            console.log("Attempting to update userPost:", response.data);
    
            const index = this.userPosts.findIndex(p => p.postId === postId);
            console.log("UserPost gefunden am Index ", index, "mit postId:", postId);
    
            if (index !== -1) {
                this.userPosts[index] = response.data;
            }
            return response.data;
        } catch (error) {
            console.error("Fehler beim updaten von userPost", error);
            throw error;
        }
    }, 
  

    }




});