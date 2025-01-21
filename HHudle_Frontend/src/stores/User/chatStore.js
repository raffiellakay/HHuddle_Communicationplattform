import { defineStore } from 'pinia'
import axios from 'axios'
import { API_URL } from '@/api'

export const useChatStore = defineStore("chatStore", {
  state: () => ({
    chats: [], // Initialize an empty array
  }),
  actions: {

    // Create a new chat
    async createChat(chat) {
      try {
        const response = await axios.post(`${API_URL}/api/chats`, chat); // Backend endpoint
        this.chats.push(response.data); // Add the response data to the chats state
      } catch (error) {
        console.error("Error creating chat:", error);
      }
    },
    // Fetch chats from backend
    async fetchChats() {
      try {
        const response = await axios.get("/api/chats"); // Backend endpoint
        this.chats = response.data; // Set the response data to the chats state
      } catch (error) {
        console.error("Error fetching chats from backend:", error);
      }
    },
    // Fetch a chat by id
    async fetchChatById(chatId) {
      try {
        const response = await axios.get(`/api/chats/${chatId}`);
        return response.data;
      } catch (error) {
        console.error("Error fetching chat by ID:", error);
      }
    },
    // Delete a chat by id
    async deleteChat(chatId, userId) {
      try {
        await axios.delete(`/api/chats/${chatId}`, {
          params: { userId },
        });
        this.chats = this.chats.filter((chat) => chat.id !== chatId);
      } catch (error) {
        console.error("Error deleting chat:", error);
      }
    },
  },
});