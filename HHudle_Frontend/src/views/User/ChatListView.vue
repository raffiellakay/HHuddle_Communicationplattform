


<template>
  <v-container class="chat-container" fluid>
    <v-row class="chat-list-view" align-items="stretch">
      <!-- List of user chats -->
      <v-col>
        <div v-if="sortedChats.length" class="dialogs">
          <div
            v-for="(chat, index) in sortedChats"
            :key="chat.id"
            class="dialog"
            :class="{ 'new-message': chat.isNewChat }"
            @click="navigateToChat(chat.id)"
          >
            <div class="dialog-header">
              <span class="chat-time">{{formatDate(chat.messages[0]?.timestamp) }}</span>
            </div>
            <div class="dialog-preview">
              <span>{{sortArrayByProperty(chat.messages, "timestamp")[0]?.text }}</span>
            </div>
            <div class="delete-chat">
              <v-btn @click.stop="showModalWindow(chat.id)">Löschen</v-btn>
            </div>
          </div>
        </div>
        <div v-else> 
         <div> 
          No chats
         </div>
         <div>
          <v-btn @click="navigateToHome()">go home</v-btn>
         </div>
        </div>

        <ConfirmDeleteCheck :show="showDeleteDialog" @confirm="deleteChatById(currentChatId)" @close="showDeleteDialog = false"/>
        
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import { useChatStore } from "@/stores/User/chatStore";
import ConfirmDeleteCheck from "@/components/ConfirmDeleteCheck.vue";
import { useAuthStore } from '@/stores/authStore'

const authStore = useAuthStore();

const chatStore = useChatStore();
const router = useRouter();
const userId = authStore.user?.id;
// Reactive data for chats
const userChats = ref([]);
const sourceUserChats = ref([]);
const showDeleteDialog = ref(false);
const currentChatId = ref(null);

// Computed property to sort chats by last message time
const sortedChats = computed(() => {
  return [...userChats.value].sort((a, b) =>
    new Date(a.timestamp) - new Date(b.timestamp)
  );
});

//date format
const formatDate = (date) => {
  console.log("date", date);
  console.log("new date", new Date(date));
  return new Date(date).toLocaleDateString() +  " " + new Date(date).toLocaleTimeString();
};

const getChats = () => {
  console.log("userChats", userChats.value);
  console.log("sourceUserChats", sourceUserChats.value);
  
  setInterval(async () => {
    console.log("userChats", userChats.value);
    await fetchUserChats();

     
      
      userChats.value = userChats.value.map((chat, index) => {
        chat.isNewChat = sourceUserChats.value[index]?.id === chat.id ? false : true;

        return chat;
      });
   
  }, 3000);
};

// Fetch user chats
const fetchUserChats = async () => {
  try {
    await chatStore.fetchChatsByUserId(userId);
    userChats.value = chatStore.chats.filter((chat) => {
      if(chat.first_participant && chat.first_participant.id === userId) {
        return chat.visibleToFirstParticipant;
      } else {
        return chat.visibleToSecondParticipant;
      }
     
    }) // Sync chats from the store
  } catch (error) {
    console.error("Error fetching user chats:", error);
  }
};
// Fetch chats on component mount
onMounted(async () => {
  
  try {
    await fetchUserChats();
    console.log("userChats", userChats.value);
    sourceUserChats.value = JSON.parse(JSON.stringify(userChats.value));
    userChats.value = userChats.value.map((chat, index) => {
      if (index === 0) {
        chat.isNewChat = true;
      } else {
        chat.isNewChat = false;
}

      return chat;
    });

   // getChats();
  } catch (error) {
    console.error("Error fetching user chats:", error);
  }
});


// Navigate to a specific chat
function navigateToChat(chatId) {
  router.push({ name: 'ChatView', params: { chatId }, query: { senderId: userId } });;
}

const sortArrayByProperty = (array, property) => {
  return array.sort((a, b) => {
    return new Date(b[property]) - new Date(a[property]);
  });
};

// Delete a chat
async function deleteChatById(chatId) {
  try {
    await chatStore.deleteChat(chatId, userId);
    userChats.value = userChats.value.filter(chat => chat.id !== chatId);
    showDeleteDialog.value = false;
  } catch (error) {
    console.error("Error deleting chat:", error);
  }
}

const showModalWindow = (chatId) => {
  showDeleteDialog.value = true;
  currentChatId.value = chatId;
};

const navigateToHome = () => {
  router.push({ path: '/user/home' });
};

</script>

<style>
.chat-container {
  max-width: 100%;
  width: 600px;
  margin: auto;
  height: 80vh;
  display: flex;
  flex-direction: column;
  border: 1px solid #ccc;
  border-radius: 8px;
  overflow: hidden;
  background-color: #f9f9f9;
}

.chat-list-view {
  flex: 1;
  overflow-y: auto;
}

.dialogs {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 10px;
}

.dialog {
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
}

.dialog.new-message {
  border: 1px solid red;
}

.dialog:hover {
  background-color: #f0f0f0;
}

.dialog-header {
  display: flex;
  justify-content: space-between;
}

.chat-name {
  font-weight: bold;
}

.chat-time {
  color: #999;
}

.dialog-preview {
  color: #666;
}
</style>
