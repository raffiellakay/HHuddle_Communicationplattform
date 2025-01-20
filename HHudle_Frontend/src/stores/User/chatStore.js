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
        const response = await axios.post("/api/chats", chat); // Backend endpoint
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
        const chat = this.chats.find((p) => p.chatId === chatId); // Find the chat in the chats state
        if (chat !== undefined) {
          return chat;
        }
        const response = await axios.get(`/api/chats/${chatId}`); // Backend endpoint
        return response.data;
      } catch (error) {
        console.error("Error fetching chat by id:", error);
      }
    },
    // Delete a chat by id
    async deleteChatById(chatId) {
      try {
        await axios.delete(`/api/chats/${chatId}`); // Backend endpoint
        const index = this.chats.findIndex((p) => p.chatId === chatId); // Find the chat in the chats state
        this.chats.splice(index, 1); // Remove the chat from the chats state
      } catch (error) {
        console.error("Error deleting chat by id:", error);
      }
    },
  },
});
