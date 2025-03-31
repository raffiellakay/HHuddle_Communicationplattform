//Store für den einzelnen Post auf einem Board
//actions: addPost(post), getPosts, getPost(postId), changePost(postId, post), deletePost(postId)

import { API_URL } from "@/api";
import { defineStore } from 'pinia';
import axios from 'axios';
import { useAuthStore } from "@/stores/authStore";

function blobToData(blob) {
  return new Promise((resolve) => {
    const reader = new FileReader()
    reader.onloadend = () => resolve(reader.result)
    reader.readAsDataURL(blob)
  })
}


////////////////////////////CODE SNIPPET /////////////////////////////////

export const useUserPostStore = defineStore ('userPost', {
    state: () => ({
        userPosts: [], //Array für alle UserPosts
        currentCategory: null, //Noch ein State für das Filtern nach Kategorien
        facilities: [], //Array für alle Facilities
        selectedFacilityId: null,
        loading: false,
    }),

    getters: {
      filteredPostsByCategory: (state) => {
          if (!state.currentCategory) 
            return state.userPosts;//Falls Kategorie null ist wird die gesamte userPostListe zurückgegeben
          
          
          return state.userPosts.filter( //Falls eine Kategorie gesetzt ist, wird gefilterte Liste zurückgegeben
              (post) => post.category.toUpperCase() === state.currentCategory.toUpperCase() //Nur posts die exakt mit currentCategory übereinstimmen werden zurückgegeben. Backend nimmt category in caps, deswegen wird beides im UpperCase umgewandelt
          );
      },
  },



    actions: {
      
      setSelectedFacility(facilityId) {
        this.selectedFacilityId = facilityId;
      },

        async createUserPost(userPost) {
            try {
                console.log("API Request wird gesendet mit Daten: ", userPost);
                const authStore = useAuthStore(); 

          if(!authStore.user) {
            console.error("Es ist kein Benutzer eingeloggt. Post kann nicht ersellt werden.")
            return;
          }

          if (!userPost.photo) {
            console.warn("Kein Foto vorhanden. Post wird ohne Bild erstellt.");
        }

          //Wir rufen aus dem LocalStore das jwt Token ab, damit wir wissen ob der User überhaupt berechtigt ist, einen Post zu erstellen
          const token = localStorage.getItem("jwt");

          //Informationen des Users werden aus authStore geholt 
          const user = {
            id: authStore.user.id, 
            username: authStore.user.username
          }; 


          console.log("Zeig mir den API-Body: ", userPost); 


          //Bild hochladen falls es existiert
          let pathToImage = null; 
          if(userPost.photo) {
            console.log("Bild wird hochgeladen...");
            pathToImage = await this.uploadImage(userPost.photo);
          }


          //API Request Body wird erstellt um den Resident korrekt als user zu übergeben 
          const newUserPost = {
            ...userPost, //Enthät alle anderen Post Daten (title, text etc.) 
            pathToImage: pathToImage,
            user: user, //Eingeloggter Benutzer wird gesetzt 
            photo: null, 
           
          }

          //API Request wird im JSON Format gesendet
          const response = await axios.post(`${API_URL}posts/post`, newUserPost, {
            headers: {
              Authorization: token, //Token wird gesetzt im Header 
              "Content-Type": "application/json" //Dem Backend muss mitgeteilt werden, dass wir Daten im Multipart-Format senden
            }
          });
      
          if (response.data) {
            console.log("UserPost erfolgreich erstellt:", response.data);
            if (response.data.pathToImage != null && response.data.pathToImage != "") {
              const imageResponse = await axios.get(`${API_URL}posts/get-image-dynamic-type?filePath=${response.data.pathToImage}`, { responseType: "blob" });
              const base64data = await blobToData(imageResponse.data);
              response.data.image = base64data;
            }
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
            for (const userPost of this.userPosts) {
              if (userPost.pathToImage != null && userPost.pathToImage != "") {
                const imageResponse = await axios.get(`${API_URL}posts/get-image-dynamic-type?filePath=${userPost.pathToImage}`, { responseType: "blob" });
                const base64data = await blobToData(imageResponse.data);
                userPost.image = base64data;
              }
            }
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

    async getAllFacilitiesByHouseId(houseId) {

      try {
        console.log(`Lade alle Facilities für House ID: ${houseId}`);
        this.loading= true;
        const response = await axios.get(API_URL + 'admin/house/' + houseId + '/facility')
        this.facilities = response.data;
        console.log("Facilties für House ID geladen:", this.facilities);
      } catch (error) {
        console.error("Fehler beim Laden der Facilties für House ID: ", error);
      } finally {
        this.loading = false; 
      }
      

    },

    async uploadImage(file) {
      try {

        if (!file) {
          console.error("Datei ist null oder undefined");
          return null;
      }
        console.log("Hochladen des Bildes:", file);

        const formData = new FormData();
        formData.append("file", file, file.name);

        const token = localStorage.getItem("jwt");

        console.log("FormData Inhalt:", formData.get("file"));//Debug

        const response = await axios.post(`${API_URL}posts/uploadImage`, formData, {
          headers: {
              Authorization: token,
              "Content-Type": "multipart/form-data",
          },
      });

      console.log("Bild hochgeladen mit Pfad: ", response.data);
      return response.data;
    } catch (error) {
      console.log("Fehler beim Hochladen des Bildes:", error);
      throw error;
    }

  }
  }

});