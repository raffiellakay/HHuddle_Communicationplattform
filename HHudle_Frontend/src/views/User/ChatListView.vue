<template>
  <v-container class="chat-container" fluid>
    <v-row class="chat-list-view" align="stretch">
      <!-- List of user chats -->
      <v-col>
        <div class="dialogs">
          <div
            v-for="(chat, index) in userChats"
            :key="index"
            class="dialog"
            @click="openChat(chat)"
          >
            <div class="dialog-header">
              <span class="chat-name">{{ chat.name }}</span>
              <span class="chat-time">{{ chat.lastMessageTime }}</span>
            </div>
            <div class="dialog-preview">
              <span>{{ chat.lastMessage }}</span>
            </div>
          </div>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { ref } from "vue";

export default {
  name: "ChatList",
  setup() {
    const userChats = ref([
      {
        id: 1,
        name: "John Doe",
        lastMessage: "Hey! How are you?",
        lastMessageTime: "10:45 AM",
      },
      {
        id: 2,
        name: "Jane Smith",
        lastMessage: "See you tomorrow.",
        lastMessageTime: "Yesterday",
      },
      
    ]);
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

.chat-list-view {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
}

.dialogs {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.dialog {
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background: #f9f9f9;
  cursor: pointer;
  transition: background-color 0.3s;
}

.dialog:hover {
  background-color: #e3f2fd;
}

.dialog-header {
  display: flex;
  justify-content: space-between;
  font-weight: bold;
  margin-bottom: 4px;
}

.dialog-preview {
  color: #555;
  font-size: 0.9rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
