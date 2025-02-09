<script setup>
import { computed, onMounted, ref, watch } from "vue";
import { useUserPostStore } from "@/stores/User/userPostStore";
import { useRoute } from "vue-router";
import { useAuthStore } from "@/stores/authStore";
import DeleteButton from "@/components/Icons/DeleteButton.vue";
import EditButton from "@/components/Icons/EditButton.vue";
import ConfirmDeleteCheck from "@/components/ConfirmDeleteCheck.vue";
import UserIconRound from "../Icons/UserIconRound.vue";
import { useChatStore } from "@/stores/User/chatStore";

const emits = defineEmits(["delete-userPost"]);
const userPostStore = useUserPostStore();
const chatStore = useChatStore();

//Prop Definition
const props = defineProps({
  category: {
    type: String,
    required: true,
  },
  postId: Number,
  facilityId: Number,
});

const showDeleteChecker = ref(false);
const postToDelete = ref(null);
const showNewChatModal = ref(false);
const text = ref("");
const secondUserId = ref(null);

//Gibt wenn es eine Facility ID gibt nach Kategorie gefilterte Posts aus Store zurück und filtert sie nach Facility
const filteredUserPosts = computed(() => {
  if (!props.facilityId) return userPostStore.filteredPostsByCategory;

  return userPostStore.filteredPostsByCategory.filter(
    (post) => post.facilityId === props.facilityId
  );
});

//Backslashes in Dateipfad für Photo korrigieren
/*const getImageUrl = (path) => {
  return `http://localhost:8081${path.replace(/\\/g, '/')}`;
};*/

const showDropdown = ref({});

// Umschalten des Dropdowns
const toggleDropdown = (postId) => {
  showDropdown.value[postId] = !showDropdown.value[postId];
};

// Methode, um den Zustand eines Dropdowns zu überprüfen
const isDropdownOpen = (postId) => {
  return !!showDropdown.value[postId]; // Rückgabe von `true` oder `false`
};

const authStore = useAuthStore();

const show = ref(false);
const userId = computed(() => authStore.user.id);

/*const normalizedCategory = computed(() => {
  return category.value ? category.value.toUpperCase() : null;
});*/
/*
Category {
  //Schwarzes Brett
    BLACKBOARD, 0
  //Ankündigungen
    FRONTPAGE, 1
  //Wer hat mein Paket?
    PACKAGE, 2
  //Wer macht was wann wo?
    EVENTS, 3
  //Biete-Suche-Tausche-Verschenke
    EXCHANGE; 4

}*/

//Öffnen des DeleteCheckers
const openDeleteChecker = (filteredUserPost) => {
  console.log("Post zum Löschen: ", filteredUserPost); //Debugging
  postToDelete.value = { ...filteredUserPost };
  showDeleteChecker.value = true;
};

//Schließen des DeleteCheckers
const closeDeleteChecker = (filteredUserPost) => {
  postToDelete.value = null;
  showDeleteChecker.value = false;
};

console.log("Auth Store User:", authStore.user);
console.log("User ID:", userId.value);
console.log("Auth Token:", authStore.token);

//Zeigt alle Posts eines Users an
/*onMounted(async () => {
  await userPostStore.getPostsByUserId(userId.value);
});*/

onMounted(async () => {
  try {
    const houseId = authStore.user.houseId; // Hole die House ID des Benutzers
    if (!houseId) {
      console.error("Keine House ID gefunden!");
      return;
    }

    console.log("Lade Posts für House ID:", houseId);
    await userPostStore.getPostsByHouseId(houseId);

    console.log("Setze Kategorie:", props.category);
    userPostStore.setCategory(props.category);
  } catch (error) {
    console.error("Fehler beim Laden der Posts:", error);
  }
});

//UserPost Löschen
const confirmDelete = async () => {
  if (postToDelete.value) {
    try {
      console.log(`Post mit ID ${postToDelete.value.id} wird gelöscht`);
      await userPostStore.deletePost(postToDelete.value.id);

      await userPostStore.getPostsByHouseId(authStore.user.houseId);

      showDeleteChecker.value = false;
      postToDelete.value = null;
    } catch (error) {
      console.error("Folgender Fehler beim Löschen aufgetreten: ", error);
    }
  }
};

//Sortiert userPosts nach Zeit und Erstellungsdatum
const sortedUserPostsByTimeCreated = computed(() => {
  return [...filteredUserPosts.value].sort(
    (a, b) => new Date(b.timestamp) - new Date(a.timestamp)
  );
});

// Öffnen des Chat-Modals
const openModal = (userId) => {
  console.log("Öffne Chat Modal für User ID:", userId);
  showNewChatModal.value = true;
  secondUserId.value = userId;
};

// Chat erstellen
const createChat = async () => {
  if (!secondUserId.value || !text.value.trim()) {
    return;
  }
  try {
    const newChat = await chatStore.createChat({
      firstUserId: userId.value,
      secondUserId: secondUserId.value,
      text: text.value,
    });
    await chatStore.sendMessage({
      chatId: newChat.id,
      senderId: authStore.user.id,
      text: text.value,
    });
    showNewChatModal.value = false;
    text.value = "";
    alert("Chat erfolgreich erstellt");
  } catch (error) {
    console.error("Fehler beim Erstellen des Chats:", error);
    alert("Fehler beim Erstellen des Chats");
  }
};

//Sortiert userPosts nach Zeit und Erstellungsdatum 
const filteredUserPostsByTimeCreated = computed(() => {
  return [...filteredUserPosts.value].sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp));
});






//Formatiert Datum auf DD.MM.YYYY
const formatToGermanDate = (dateTime) => {
  if (!dateTime) return ""; //Rückgabe eines leeren Strings, wenn kein Datum vorhanden ist
  const d = new Date(dateTime);
  return d.toLocaleDateString("de-DE", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });
};
</script>

<template>
  <!-- Dieser Teil beinhaltet das Kartendesign für UserPosts-->

  <v-container class="postcards-container">
    <v-row v-if="filteredUserPosts.length > 0" justify="space-evenly" >
      <v-col
        v-for="filteredUserPost in filteredUserPostsByTimeCreated"
        :key="filteredUserPost.id"
        cols="12"
        sm="6"
        md="4"
        lg="3"
        xl="2"
      >
        <v-card class="mx-auto post-card">

          
    
          <!-- Photo als Header -->
          <!--Bild anzeigen-->
          <v-img
            v-if="filteredUserPost.image"
            :src="filteredUserPost.image"
            alt="Post Bild"
            height="200"
            contain
          ></v-img>

          <!-- Titel -->
          <v-card-item class="title-row">
            <v-card-title class="title-text wrap-text">
              {{ filteredUserPost.title }}
            </v-card-title>
            <DeleteButton
                v-if="filteredUserPost.user?.id === userId"
                @click="openDeleteChecker(filteredUserPost)"
                @delete-success="$emit('userPost-deleted')"
                class="delete-button"
              />
          </v-card-item>
          <v-container class="divider-container">
            <v-divider class="border-opacity-50 divider"></v-divider>
          </v-container>

          

          <!-- Untertitel -->
          <div v-if="category === 'EVENTS'">
            <v-card-subtitle>
              Startzeit: {{ formatToGermanDate(filteredUserPost.starttime) }}
            </v-card-subtitle>
            <v-card-subtitle>
              Endzeit: {{ formatToGermanDate(filteredUserPost.endtime) }}
            </v-card-subtitle>
            <v-container class="divider-container">
            <v-divider class="border-opacity-50 divider"></v-divider>
          </v-container>
          </div>

      
              

           

          <!-- Aktionen -->

          <v-card-text>
            {{ filteredUserPost.text }}
          </v-card-text>
          <v-container class="divider-container">
            <v-divider class="border-opacity-50 divider"></v-divider>
          </v-container>

          <v-card-actions class="footer">
            <v-list-item class="w-100">
              <template v-slot:prepend>
              <v-avatar>
                <UserIconRound class="user-icon" />
              </v-avatar>
            </template>
              <v-list-item-title class="username-text wrap-text">{{ filteredUserPost.user?.username || "Unbekannt" }}</v-list-item-title>
              <v-list-item-subtitle class="username-subtitle"> {{ formatToGermanDate(filteredUserPost.timestamp) }}</v-list-item-subtitle>
              <template v-slot:append>
              <v-icon
              v-if="userId !== filteredUserPost.user?.id"
              variant="elevated"
              class="chat-button"
              @click="openModal(filteredUserPost.user?.id)"
            >mdi-chat-plus-outline
          
           </v-icon>
          </template>
          </v-list-item>
        </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <v-container v-if="filteredUserPosts.length === 0">
      <v-alert type="info"
        >Keine Beiträge für diese Kategorie verfügbar.</v-alert
      >
    </v-container>











    <v-dialog v-model="showNewChatModal" max-width="500">
      <v-card>
        <v-card-title>
          <span class="headline">Chat erstellen</span>
        </v-card-title>
        <v-card-text>
          <v-form ref="form">
            <v-text-field
              v-model="text"
              label="Nachricht"
              required
            ></v-text-field>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="createChat"
            >Erstellen</v-btn
          >
          <v-btn color="grey darken-1" text @click="showNewChatModal = false"
            >Abbrechen</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>

    

    <ConfirmDeleteCheck
      :show="showDeleteChecker"
      :itemName="'den Post'"
      @confirm="confirmDelete"
      @close="closeDeleteChecker"
    >
    </ConfirmDeleteCheck>
  </v-container>
</template>


<style scoped>
/* Maximale Größe und Positionierung */
.postcards-container {
  margin: 0 auto; 
  max-width: 1400px;
  padding: 16px;
  
}
.post-card {
  position: relative;
  max-width: 344px;
  min-height: 250px;
  display: flex;
  flex-direction: column;
  border-radius: 15px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
  margin: 0;
  padding: 0;
}

.post-card:hover {
  transform: translateY(-5px);
  box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.15);
}

.v-row {
  gap: 16px;

}

.v-col {
  padding: 8px;
}


.title-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
}

.title-text {
  font-size: 18px;
  font-weight: bold;
  color: #333;
}

.footer {
  padding: 0;
  margin: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;

}

/*UserIcon Styling*/
.user-icon {
  color: #E98074;
  font-size: 30px;
 
}


.username-text {
  font-size: 14px;
  color: #8E8D8A;
  margin: 0;
  padding: 0;

}

.username-subtitle {
  font-size: 12px;
  color: #8E8D8A;
}

.delete-button {
  position: absolute;
  top: 8px;
  right: 8px;
  z-index: 10;
  font-size: 15px;
  min-width: 40px; 
  height: 32px;
  padding: 4px;

}

/* Chat-Button Styling */
.chat-button {
  font-size: 20px;
  width: 10px;
  padding: 4px 8px;
  color: #E98074;
}


.wrap-text {
  white-space: normal; 
  word-break: break-word;
  max-width: 100%;
}

.divider-container {
  padding-bottom: 0;
  padding-top: 0;
  margin: 0;
}


.divider {
  padding: 0;
  margin: 0;
  color: #8E8D8A;
}




</style>