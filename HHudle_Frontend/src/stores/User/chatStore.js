import { defineStore } from 'pinia';
import axios from 'axios';
import { API_URL } from '@/api';

export const useChatStore = defineStore("chatStore", {
  state: () => ({
    chats: [], // List of all fetched chats
  }),

  actions: {
    /**
     * Create a new chat between two users.
     * @param {Object} params - Contains firstUserId and secondUserId.
     */
    async createChat({ firstUserId, secondUserId }) {
      try {
        const response = await axios.post(`${API_URL}/api/chats/create`, null, {
          params: { firstUserId, secondUserId },
        });
        this.chats.push(response.data); // Add the new chat to state
      } catch (error) {
        console.error("Error creating chat:", error);
      }
    },

    /**
     * Send a message in a chat.
     * @param {Object} messageData - Data for the chat message.
     * @returns {Object} - Sent message response.
     */
    async sendMessage(messageData) {
      try {
        const response = await axios.post(`${API_URL}/api/chats/send-message`, messageData);
        return response.data; // Return the sent message
      } catch (error) {
        console.error("Error sending message:", error);
      }
    },

    /**
     * Fetch all chats for a specific user.
     * @param {Number} userId - ID of the user.
     */
    async fetchChatsByUserId(userId) {
      try {
        const response = await axios.get(`${API_URL}/api/chats/user/${userId}`);
        this.chats = response.data; // Update the chats list
      } catch (error) {
        console.error("Error fetching user chats:", error);
      }
    },

    /**
     * Fetch a single chat by ID.
     * @param {Number} chatId - ID of the chat.
     * @returns {Object} - Chat data.
     */
    async fetchChatById(chatId) {
      try {
        const response = await axios.get(`${API_URL}/api/chats/${chatId}`);
        return response.data; // Return the chat details
      } catch (error) {
        console.error("Error fetching chat by ID:", error);
      }
    },

    /**
     * Delete a chat for a specific user.
     * @param {Number} chatId - ID of the chat to delete.
     * @param {Number} userId - ID of the user requesting deletion.
     */
    async deleteChat(chatId, userId) {
      try {
        await axios.delete(`${API_URL}/api/chats/${chatId}`, {
          params: { userId },
        });
        this.chats = this.chats.filter((chat) => chat.id !== chatId); // Remove chat from state
      } catch (error) {
        console.error("Error deleting chat:", error);
      }
    },
  },
});
