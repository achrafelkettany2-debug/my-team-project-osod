<script setup>
import { ref, onMounted, nextTick } from 'vue'
import axios from 'axios'

const props = defineProps(['course', 'currentUser'])
const emit = defineEmits(['close'])

const messages = ref([])
const newMessage = ref('')
const messageBox = ref(null) // To auto-scroll to bottom

// Fetch Chat History
const fetchMessages = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/experiment1/discussions/course/${props.course.id}`)
    messages.value = response.data
    scrollToBottom()
  } catch (err) {
    alert("Error loading chat")
  }
}

// Send Message
const sendMessage = async () => {
  if (!newMessage.value.trim()) return

  try {
    await axios.post('http://localhost:8080/experiment1/discussions', {
      courseId: props.course.id,
      username: props.currentUser.username, // Send the user's name
      content: newMessage.value
    })
    newMessage.value = '' // Clear input
    fetchMessages() // Refresh chat
  } catch (err) {
    alert("Error sending message: " + err.message)
  }
}

// Auto-scroll to the newest message
const scrollToBottom = () => {
  nextTick(() => {
    if (messageBox.value) {
      messageBox.value.scrollTop = messageBox.value.scrollHeight
    }
  })
}

onMounted(() => {
  fetchMessages()
})
</script>

<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <div class="modal-header">
        <h2>💬 Class Chat: {{ course.courseName }}</h2>
        <button class="close-btn" @click="$emit('close')">X</button>
      </div>

      <div class="chat-history" ref="messageBox">
        <div v-if="messages.length === 0" class="empty">No messages yet. Start the conversation! 👋</div>
        
        <div 
          v-for="msg in messages" 
          :key="msg.id" 
          class="message-bubble"
          :class="{ 'my-message': msg.username === currentUser.username }"
        >
          <div class="msg-header">
            <span class="username">{{ msg.username }}</span>
            <span class="time">{{ msg.createdAt }}</span>
          </div>
          <div class="msg-content">{{ msg.content }}</div>
        </div>
      </div>

      <div class="chat-input">
        <input 
          v-model="newMessage" 
          @keyup.enter="sendMessage"
          placeholder="Type a message..." 
        />
        <button @click="sendMessage">Send ➤</button>
      </div>

    </div>
  </div>
</template>

<style scoped>
.modal-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.6); display: flex; justify-content: center; align-items: center; z-index: 1000; }
.modal-content { background: white; width: 500px; max-width: 90%; height: 600px; border-radius: 12px; display: flex; flex-direction: column; overflow: hidden; box-shadow: 0 10px 30px rgba(0,0,0,0.3); }
.modal-header { padding: 15px 20px; background: #f8fafc; border-bottom: 1px solid #e2e8f0; display: flex; justify-content: space-between; align-items: center; }
.close-btn { background: none; border: none; font-size: 1.2rem; cursor: pointer; color: #64748b; }

/* Chat History */
.chat-history { flex: 1; padding: 20px; overflow-y: auto; background-color: #f1f5f9; display: flex; flex-direction: column; gap: 10px; }
.empty { text-align: center; color: #94a3b8; font-style: italic; margin-top: 50px; }

/* Bubbles */
.message-bubble { background: white; padding: 10px 15px; border-radius: 12px; border-top-left-radius: 2px; max-width: 80%; align-self: flex-start; box-shadow: 0 1px 2px rgba(0,0,0,0.05); }
.my-message { background: #3b82f6; color: white; align-self: flex-end; border-top-left-radius: 12px; border-top-right-radius: 2px; }

.msg-header { display: flex; justify-content: space-between; font-size: 0.75rem; margin-bottom: 4px; opacity: 0.8; gap: 10px; }
.username { font-weight: bold; }

/* Input Area */
.chat-input { padding: 15px; background: white; border-top: 1px solid #e2e8f0; display: flex; gap: 10px; }
input { flex: 1; padding: 10px; border: 1px solid #cbd5e1; border-radius: 20px; outline: none; }
button { background: #3b82f6; color: white; border: none; padding: 0 20px; border-radius: 20px; cursor: pointer; font-weight: bold; }
button:hover { background: #2563eb; }
</style>