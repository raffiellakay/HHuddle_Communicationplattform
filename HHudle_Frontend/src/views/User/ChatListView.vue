<template>
  <v-container class="chat-container" fluid>
    <v-row class="chat-list-view" align-items="stretch">
      <!-- List of user chats -->
      <v-col>
        <div class="dialogs">
          <div
            v-for="(chat, index) in chats"
            :key="index"
            class="dialog"
            @click="navigateToChat(chat.id)"
          >
            <div class="dialog-header">
              <span class="chat-name">{{ chat.name }}</span>
              <span class="chat-time">{{ chat.lastMessageTime }}</span>
            </div>
            <div class="dialog-preview">
              <span>{{ chat.lastMessage }}</span>
            </div>
          </div>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
//import axios from "axios";
import { useRouter } from "vue-router";
import {useChatStore}from "@/stores/User/chatStore";


const ChatStore = userChatStore();

const dialog = ref(false);

const router = useRouter();

const newChat = ref({
  userId: 
});

onMounted(async() => {
  await ChatStore.fetchChats();
});

async function saveChat() {
  try {
    const chatData = {
      userId: newChat.value.userId,
    }
  }
  await ChatStore.createChat(chatData);
  dialog.value = false;
}

newChat.value = { userId: };
catch (error) {
  console.error("Fehler beim Speichern des Chats:", error);
}


//Sortiert die Chats aufsteigend nach ID
const sortedChats = computed(() => {
  return ChatStore.chats.value.sort((a, b) => a.id - b.id);
});

//Navigiert zur ChatView
function.navigateToChat = (ChatId) => {
  router.push(´/chat/${ChatId}´);
}


/*export default {
  // temporary hardcoded data 
  name: "ChatListView",
  setup() {
    const chats = ref([
      {
        id: 1,
        name: "John Doe",
        lastMessage: "Hey, how are you?",
        lastMessageTime: "10:30 AM",
      },
      {
        id: 2,
        name: "Jane Smith",
        lastMessage: "Let's catch up later.",
        lastMessageTime: "11:00 AM",
      },
      {
        id: 3,
        name: "Alice Johnson",
        lastMessage: "See you tomorrow!",
        lastMessageTime: "12:15 PM",
      },
    ]);
    const router = useRouter();
   /* const chats = ref([]);
    const router = useRouter();
    const userId = 1; // Replace with the actual user ID

    // Fetch chats from backend
    const fetchChats = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/chats/user/${userId}`);
        chats.value = response.data;
      } catch (error) {
        console.error("Error fetching chats:", error);
      }
    };

    // Load chats on mount
    onMounted(() => {
      fetchChats();
    }); */

    // Navigate to ChatView
    
</script>

<style>
.chat-container {
  max-width: 600px;
  margin: auto;
  height: 80vh;
  display: flex;
  flex-direction: column;
  border: 1px solid #ccc;
  border-radius: 8px;
  overflow: hidden;
}

.chat-list-view {
  flex: 1;
  overflow-y: auto;
}

.dialog {
  padding: 10px;
  border-bottom: 1px solid #eee;
  cursor: pointer;
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