<template>
    <v-container class="chat-container" fluid>
      <v-row class="chat-list-view" align="stretch">
        <!-- Chat messages area -->
        <v-col>
          <div class="messages" ref="messages">
            <div
              v-for="(message, index) in chatMessages"
              :key="index"
              :class="['message', message.sender === 'user' ? 'user' : 'bot']"
            >
              <span>{{ message.text }}</span>
            </div>
          </div>
        </v-col>
      </v-row>
      <v-row class="chat-input">
        <v-col>
          <v-text-field
            v-model="userMessage"
            label="Type your message"
            outlined
            dense
            @keyup.enter="sendMessage"
          ></v-text-field>
        </v-col>
        <v-col cols="auto">
          <v-btn color="primary" @click="sendMessage">Send</v-btn>
        </v-col>
      </v-row>
    </v-container>
  </template>
  
  <script>
  import { ref } from "vue";
  
  export default {
    name: "ChatListView",
    setup() {
      const chatMessages = ref([
        { text: "Hi! How can I help you?", sender: "bot" },
      ]);
      const userMessage = ref("");
  
      const sendMessage = () => {
        if (userMessage.value.trim()) {
          chatMessages.value.push({ text: userMessage.value, sender: "user" });
          userMessage.value = "";
  
          // Simulate a bot response after a delay
          setTimeout(() => {
            chatMessages.value.push({
              text: "Thank you for your message!",
              sender: "bot",
            });
            scrollToBottom();
          }, 1000);
        }
      };
  
      const scrollToBottom = () => {
        const messagesElement = document.querySelector(".messages");
        if (messagesElement) {
          messagesElement.scrollTop = messagesElement.scrollHeight;
        }
      };
  
      return {
        chatMessages,
        userMessage,
        sendMessage,
      };
    },
  };
  </script>
  
  <style>
  .chat-container {
    max-width: 600px;
    margin: auto;
    height: 80vh;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    border: 1px solid #ccc;
    border-radius: 8px;
    overflow: hidden;
  }
  
  .chat-list-view {
    flex: 1;
    overflow-y: auto;
    padding: 16px;
  }
  
  .messages {
    display: flex;
    flex-direction: column;
    gap: 8px;
  }
  
  .message {
    padding: 8px 12px;
    border-radius: 8px;
    max-width: 70%;
    word-wrap: break-word;
  }
  
  .message.user {
    align-self: flex-end;
    background-color: #e3f2fd;
  }
  
  .message.bot {
    align-self: flex-start;
    background-color: #f1f8e9;
  }
  
  .chat-input {
    display: flex;
    align-items: center;
    padding: 8px;
    background: #f9f9f9;
    border-top: 1px solid #ccc;
  }
  </style>
  