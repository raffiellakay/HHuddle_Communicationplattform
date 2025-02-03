
import { defineStore } from 'pinia';
import axios from 'axios';
import { API_URL } from '@/api';

export const useChatStore = defineStore("chatStore", {
  state: () => ({
    chats: [], // List of all fetched chats
  }),

  actions: {
    // Create a new chat between two users.
     
    async createChat({ firstUserId, secondUserId, text }) {
      try {
        const response = await axios.post(`${API_URL}chats/create`, null, {
          params: { firstUserId, secondUserId, text },
        });
    
        const newChat = response.data;
        this.chats.push(newChat); // Add chat to store
        return newChat; // Return chat object for further use
      } catch (error) {
        console.error("Error creating chat:", error);
        throw error;
      }
    },
    
    // Send a message in a chat.
     
    async sendMessage(messageData) {
      try {
        const response = await axios.post(`${API_URL}chats/send-message`, messageData);
        return response.data; // Return the sent message
      } catch (error) {
        console.error("Error sending message:", error);
      }
    },

    // Fetch all chats for a specific user.
     
    async fetchChatsByUserId(userId) {
      try {
        const response = await axios.get(`${API_URL}chats/user/${userId}`);
        console.log("Fetched chats", response.data);
        this.chats = response.data; // Update the chats list
        return this.chats;
      } catch (error) {
        console.error("Error fetching user chats:", error);
      }
    },

    // Fetch a single chat by ID.
    
    async fetchChatById(chatId) {
      try {
        const response = await axios.get(`${API_URL}chats/${chatId}`);
        return response.data; // Return the chat details
      } catch (error) {
        console.error("Error fetching chat by ID:", error);
      }
    },

    //Delete a chat for a specific user.
    
    async deleteChat(chatId, userId) {
      try {
        await axios.delete(`${API_URL}chats/${chatId}`, {
          params: { userId },
        });
        this.chats = this.chats.filter((chat) => chat.id !== chatId); // Remove chat from state
        console.log("Deleted chat:", chatId);
      } catch (error) {
        console.error("Error deleting chat:", error);
      }
    },
  },
});


