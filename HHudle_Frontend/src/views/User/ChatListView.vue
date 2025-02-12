<template>
  <v-container class="chat-container" fluid>
    <v-row class="chat-list-view" align-items="stretch">
      <v-col>
        <div v-if="sortedChats.length" class="dialogs">
          <div
            v-for="(chat, index) in sortedChats"
            :key="chat.id"
            class="dialog"
            @click="navigateToChat(chat.id)"
            
          >
          <div class="dialog-header">
          <span class="chat-name">{{ getChatPartnerName(chat) }}</span>
           <v-badge v-if="getUnreadMessageCount(chat) > 0"
           color="red"
           :content="getUnreadMessageCount(chat)"
            floating
             />
            <span class="chat-time">{{ formatDate(getLastMessageTimestamp(chat)) }}</span>
              </div>
              <div class="dialog-preview">
              <span>{{ getLastMessageText(chat) }}</span>
            </div>
            <DeleteButton @click.stop="showModalWindow(chat.id)" />  
            </div>
          </div>
       
        <div v-else>
          <div>No chats</div>
          <div>
            <v-btn @click="navigateToHome()">go home</v-btn>
          </div>
        </div>

        <ConfirmDeleteCheck
          :show="showDeleteDialog"
          @confirm="deleteChatById(currentChatId)"
          @close="showDeleteDialog = false"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useChatStore } from '@/stores/User/chatStore';
import ConfirmDeleteCheck from '@/components/ConfirmDeleteCheck.vue';
import { useAuthStore } from '@/stores/authStore';
import DeleteButton from "@/components/Icons/DeleteButton.vue";

// Auth + store + router
const authStore = useAuthStore();
const chatStore = useChatStore();
const router = useRouter();

// Current user
const userId = authStore.user?.id;

// Reactive chat list
const userChats = ref([]);
const showDeleteDialog = ref(false);
const currentChatId = ref(null);

/**
 * Sort by newest message on top
 */
const sortedChats = computed(() => {
  return [...userChats.value]
  .filter(chat => chat.messages && chat.messages.length > 0)
  .sort((a, b) => {
    const timestampA = a.messages?.length
      ? new Date(a.messages[a.messages.length - 1].timestamp)
      : 0;
    const timestampB = b.messages?.length
      ? new Date(b.messages[b.messages.length - 1].timestamp)
      : 0;
    return timestampB - timestampA;
  });
});


function formatDate (date) {
 
  const messageDate = new Date(date);
  const today = new Date();
  const yesterday = new Date();
  yesterday.setDate(today.getDate() - 1);
  const timeString = messageDate.toLocaleTimeString('de-DE', { hour: '2-digit', minute: '2-digit' });

  return messageDate.toDateString() === today.toDateString() ? "heute" + " " + timeString : 
    messageDate.toDateString() === yesterday.toDateString() ? "gestern" + " " + timeString :
    messageDate.toLocaleString('de-DE', {  day: '2-digit',  month: '2-digit',  year: '2-digit' 
      }) ;
       
};


function getLastMessageTimestamp(chat) {
  if (!chat.messages?.length) return null;
  return chat.messages[chat.messages.length - 1].timestamp;
}


function getLastMessageText(chat) {
  if (!chat.messages?.length) return '(No messages)';
  return chat.messages[chat.messages.length - 1].text || '(No text)';
}

function getUnreadMessageCount(chat) {
  return chat?.messages?.filter((m) => !m.read && m.user.id !== chat.first_participant.id).length || 0;
}

function getChatPartnerName(chat) {
  if (!chat.first_participant || !chat.second_participant) {
    return 'Unknown User';
  }
  // If I'm first participant, partner is second
  if (chat.first_participant.id === userId) {
    return chat.second_participant?.username || 'No name';
  } else {
    return chat.first_participant?.username || 'No name';
  }
}


async function fetchUserChats() {
  try {
    await chatStore.fetchChatsByUserId(userId);
    const fetched = chatStore.chats.map(chat => {
      if (chat.messages?.length) {
        chat.messages = chat.messages.sort(
          (m1, m2) => new Date(m1.timestamp) - new Date(m2.timestamp)
        );
      }
      return chat;
    });
    userChats.value = fetched;
  } catch (error) {
    console.error('Error fetching user chats:', error);
  }
}

//do the initial fetch
onMounted(async () => {
  await fetchUserChats();
});

// ALWAYS mark the chat read, then navigate.

async function navigateToChat(chatId) {
  try {
    //  Mark the chat read in the backend
    if (chatStore.markChatAsRead) {
      await chatStore.markChatAsRead(chatId);
      //  Re-fetch so local data sees 'read=true'
      await fetchUserChats();
    }
  } catch (err) {
    console.error('Error marking chat read:', err);
  }

  //  Navigate to chat
  router.push({ name: 'ChatView', params: { chatId }, query: { senderId: userId } });
}


async function deleteChatById(chatId) {
  try {
    await chatStore.deleteChat(chatId, userId);
    userChats.value = userChats.value.filter(chat => chat.id !== chatId);
    showDeleteDialog.value = false;
  } catch (error) {
    console.error('Error deleting chat:', error);
  }
}

function showModalWindow(chatId) {
  showDeleteDialog.value = true;
  currentChatId.value = chatId;
}


function navigateToHome() {
  router.push({ path: '/user/home' });
}
</script>

<style>
.chat-container {
  max-width: 100%;
  width: 600px;
  margin: auto;
  height: 80vh;
  display: flex;
  flex-direction: column;
  border: 1px solid #ccc;
  border-radius: 8px;
  overflow: hidden;
  background-color: #f9f9f9;
}

.chat-list-view {
  flex: 1;
  overflow-y: auto;
}

.dialogs {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 10px;
  
}

.dialog {
  position: relative; 
  padding: 10px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  border-radius: 5px;
  cursor: pointer;
}


.dialog:hover {
  background-color: #f0f0f0;
}

.dialog-header {
  display: flex;
  justify-content: space-between;
}

.chat-name {
  font-weight: bold;
  margin-right: 1rem;
}

.chat-time {
  color: #999;
}

.dialog-preview {
  color: #666;
}

.unread-badge {
  position: absolute;
  top: 10px;
  right: 10px;
}

</style>
