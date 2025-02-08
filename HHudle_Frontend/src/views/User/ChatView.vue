
<template>
  <v-container class="chat-container" fluid>
    <v-row class="chat-view" align-items="stretch">
      <!-- Chat messages -->
      <v-col>
        <div class="messages">
          <div
            v-for="(message, index) in chatMessages"
            :key="index"
            class="message"
          >
            <div class="message-header">
              <span class="message-time">{{ formatDate(message.timestamp) }}</span>
            </div>
            <div class="message-content">
              <span>{{ message.text }}</span>
            </div>
          </div>
        </div>
        <!-- Input field to write new messages -->
        <v-text-field
          v-model="newMessage"
          label="Write a message"
          @keyup.enter="sendMessage"
        ></v-text-field>
        <v-btn @click="sendMessage">Send</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { useChatStore } from "@/stores/User/chatStore";

const chatStore = useChatStore();
const route = useRoute();

const chatId = route.params.chatId;
const senderId = route.query.senderId;
const chatMessages = ref([]);
const newMessage = ref("");
console.log(route);
onMounted(async () => {
  await fetchChat();
});

async function fetchChat() {
  try {
    const chat = await chatStore.fetchChatById(chatId);
    chatMessages.value = chat.messages.sort(
      (a, b) => new Date(a.timestamp) - new Date(b.timestamp)
  );
  } catch (error) {
    console.error("Error fetching chat:", error);
  }
}

async function sendMessage() {
  console.log("Sending message:", newMessage.value, senderId);
  if (!newMessage.value.trim()) return;
//"senderId": 52,
 // "chatId": 2,
 // "text": "wie gehts dir?"

  const messageData = {
    chatId,
    text: newMessage.value,
    senderId,
  };

  try {
    const sentMessage = await chatStore.sendMessage(messageData);
    chatMessages.value.push(sentMessage);

    // Sort messages after adding the new one
     chatMessages.value.sort((a, b) => new Date(a.timestamp) - new Date(b.timestamp));
     
    newMessage.value = "";
  } catch (error) {
    console.error("Error sending message:", error);
  }
}

function formatDate(date) {
  return new Date(date).toLocaleDateString() + " " + new Date(date).toLocaleTimeString();
}
</script>

<style scoped>
.chat-container {
  padding: 20px;
}

.messages {
  max-height: 400px;
  overflow-y: auto;
  margin-bottom: 20px;
}

.message {
  padding: 10px;
  border-bottom: 1px solid #ccc;
}

.message-header {
  font-size: 0.8em;
  color: #888;
}

.message-content {
  font-size: 1em;
}
</style>