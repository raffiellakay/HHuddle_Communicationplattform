<template>
  <v-container class="chat-container" fluid>
    <v-row class="chat-list-view" align-items="stretch">
      <!-- List of user chats -->
      <v-col>
        <div class="dialogs">
          <div
            v-for="(chat, index) in sortedChats"
            :key="chat.id"
            class="dialog"
            @click="navigateToChat(chat.id)"
          >
            <div class="dialog-header">
              <span class="chat-time">{{ chat.lastMessageTime || 'N/A' }}</span>
            </div>
            <div class="dialog-preview">
              <span>{{ chat.text || 'No messages yet' }}</span>
            </div>
          </div>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import { useChatStore } from "@/stores/User/chatStore";

const chatStore = useChatStore();
const router = useRouter();

// Reactive data for chats
const userChats = ref([]);

// Computed property to sort chats by last message time
const sortedChats = computed(() => {
  return [...userChats.value].sort((a, b) =>
    new Date(b.lastMessageTime) - new Date(a.lastMessageTime)
  );
});

// Fetch chats on component mount
onMounted(async () => {
  const userId = 2; // Replace with dynamic user ID as needed
  try {
    await chatStore.fetchChatsByUserId(userId);
    userChats.value = chatStore.chats; // Sync chats from the store
  } catch (error) {
    console.error("Error fetching user chats:", error);
  }
});

// Navigate to a specific chat
function navigateToChat(chatId) {
  router.push(`/chat/${chatId}`);
}






/*
   temporary hardcoded data 
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

.dialog {
  padding: 10px;
  border-bottom: 1px solid #eee;
  cursor: pointer;
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






    
