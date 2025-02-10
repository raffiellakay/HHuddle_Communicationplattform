import { defineStore } from 'pinia';
import axios from 'axios';
import { API_URL } from '@/api'; // e.g. API_URL = "http://localhost:8081/api/"

export const useChatStore = defineStore("chatStore", {
  state: () => ({
    chats: []
  }),

  actions: {
    
    async createChat({ firstUserId, secondUserId, text }) {
      try {
        const response = await axios.post(
          `${API_URL}chats/create`,
          null,
          { params: { firstUserId, secondUserId, text } }
        );
        const newChat = response.data;
        this.chats.push(newChat);
        return newChat;
      } catch (error) {
        console.error("Error creating chat:", error);
        throw error;
      }
    },

    
    async sendMessage(messageData) {
      try {
        const response = await axios.post(`${API_URL}chats/send-message`, messageData);
        return response.data;
      } catch (error) {
        console.error("Error sending message:", error);
        throw error;
      }
    },

    
    async fetchChatsByUserId(userId) {
      try {
        const response = await axios.get(`${API_URL}chats/user/${userId}`);
        console.log("Fetched chats", response.data);
        this.chats = response.data;
        return this.chats;
      } catch (error) {
        console.error("Error fetching user chats:", error);
        throw error;
      }
    },

   
    async fetchChatById(chatId) {
      try {
        const response = await axios.get(`${API_URL}chats/${chatId}`);
        return response.data;
      } catch (error) {
        console.error("Error fetching chat by ID:", error);
        throw error;
      }
    },

    
    async markChatAsRead(chatId) {
      try {
        await axios.put(`${API_URL}chats/${chatId}/markRead`);
      } catch (err) {
        console.error("Error marking chat read:", err);
        throw err;
      }
    },

    async deleteChat(chatId, userId) {
      try {
        await axios.delete(`${API_URL}chats/${chatId}`, {
          params: { userId }
        });
        this.chats = this.chats.filter(chat => chat.id !== chatId);
        console.log("Deleted chat:", chatId);
      } catch (error) {
        console.error("Error deleting chat:", error);
        throw error;
      }
    }
  }
});
