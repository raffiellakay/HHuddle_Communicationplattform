import { defineStore } from 'pinia'
import axios from 'axios'
import { API_URL } from '@/api'

export const useChatStore = defineStore("chatStore", {
  state: () => ({
    chats: [], // Initialize an empty array
  }),
  actions: {
    // Fetch chats from backend
    async fetchChats() {
      try {
        const response = await axios.get("/api/chats"); // Backend endpoint
        this.chats = response.data; // Set the response data to the chats state
      } catch (error) {
        console.error("Error fetching chats from backend:", error);
      }
    },
  },
});
