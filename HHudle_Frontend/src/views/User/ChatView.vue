<template>
    <v-container class="chat-container" fluid>
      <v-row class="chat-messages" align-items="stretch">
        <v-col>
          <div class="messages">
            <div
              v-for="(message, index) in messages"
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
          <v-form ref="form" @submit.prevent="sendMessage">
            <v-text-field v-model="newMessage" label="Type your message"></v-text-field>
            <v-btn type="submit" color="primary">Send</v-btn>
          </v-form>
        </v-col>
      </v-row>
    </v-container>
  </template>
  
  <script>
  import { ref } from "vue";
  import { useRoute } from "vue-router";
  
  export default {
    name: "ChatView",
    setup() {
      const route = useRoute();
      const chatId = route.params.id;
      const messages = ref([
        { text: "Hello!", sender: "bot" },
        { text: "Hi, how are you?", sender: "user" },
        { text: "I'm good, thanks!", sender: "bot" },
      ]);
      const newMessage = ref("");
  
      // Send a new message
      const sendMessage = () => {
        if (newMessage.value.trim() === "") return;
  
        // Add the new message to the messages array
        messages.value.push({ text: newMessage.value, sender: "user" });
  
        // Simulate a bot response
        setTimeout(() => {
          messages.value.push({ text: "This is a bot response.", sender: "bot" });
        }, 1000);
  
        // Clear the input field
        newMessage.value = "";
      };
  
      return {
        messages,
        newMessage,
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
    border: 1px solid #ccc;
    border-radius: 8px;
    overflow: hidden;
  }
  
  .chat-messages {
    flex: 1;
    overflow-y: auto;
  }
  
  .message {
    padding: 10px;
    margin: 5px 0;
    border-radius: 8px;
  }
  
  .message.user {
    background-color: #e0f7fa;
    align-self: flex-end;
  }
  
  .message.bot {
    background-color: #f1f1f1;
    align-self: flex-start;
  }
  
  .chat-input {
    padding: 10px;
    border-top: 1px solid #eee;
  }
  </style>