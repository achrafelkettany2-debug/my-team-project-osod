<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

// We need to know who is looking to decide if we show the "Post" form
const props = defineProps(['currentUser'])

const announcements = ref([])
const newAnnouncement = ref({
  title: '',
  content: ''
})

// Fetch News
const fetchNews = async () => {
  try {
    const response = await axios.get('http://localhost:8080/experiment1/announcements')
    announcements.value = response.data
  } catch (err) {
    console.error("Error fetching news:", err)
  }
}

// Post News (Teacher Only)
const postNews = async () => {
  if (!newAnnouncement.value.title || !newAnnouncement.value.content) {
    alert("Please fill in both title and content.")
    return
  }

  try {
    await axios.post('http://localhost:8080/experiment1/announcements', {
      title: newAnnouncement.value.title,
      content: newAnnouncement.value.content,
      teacherName: props.currentUser.username // Auto-sign with the teacher's name
    })
    
    // Clear form and refresh
    newAnnouncement.value = { title: '', content: '' }
    fetchNews()
  } catch (err) {
    alert("Error posting news: " + err.message)
  }
}

onMounted(() => {
  fetchNews()
})
</script>

<template>
  <div class="news-container">
    
    <div v-if="currentUser && currentUser.role === 'teacher'" class="post-box">
      <h3>📢 Post an Announcement</h3>
      <input v-model="newAnnouncement.title" placeholder="Title (e.g., Exam Alert)" class="news-input" />
      <textarea v-model="newAnnouncement.content" placeholder="Write your message here..." class="news-textarea"></textarea>
      <button @click="postNews" class="post-btn">Post to All Students</button>
    </div>

    <div class="news-feed">
      <div v-if="announcements.length === 0" class="no-news">
        No announcements yet. Quiet day! 🍃
      </div>

      <div v-for="item in announcements" :key="item.id" class="news-card">
        <div class="news-header">
          <span class="news-title">{{ item.title }}</span>
          <span class="news-date">{{ new Date(item.createdAt).toLocaleDateString() }}</span>
        </div>
        <p class="news-content">{{ item.content }}</p>
        <div class="news-footer">
          Posted by: <span class="teacher-name">{{ item.teacherName }}</span>
        </div>
      </div>
    </div>

  </div>
</template>

<style scoped>
.news-container { padding: 10px; }

/* Posting Form */
.post-box { background: #fff7ed; border: 1px solid #fed7aa; padding: 20px; border-radius: 8px; margin-bottom: 30px; }
.post-box h3 { margin-top: 0; color: #9a3412; }
.news-input, .news-textarea { width: 100%; padding: 10px; margin-bottom: 10px; border: 1px solid #fdba74; border-radius: 6px; box-sizing: border-box; }
.news-textarea { height: 80px; resize: vertical; }
.post-btn { background-color: #ea580c; color: white; padding: 10px 20px; border: none; border-radius: 6px; cursor: pointer; font-weight: bold; }
.post-btn:hover { background-color: #c2410c; }

/* News Feed */
.news-card { background: white; border-left: 4px solid #f59e0b; padding: 15px; margin-bottom: 15px; border-radius: 4px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); }
.news-header { display: flex; justify-content: space-between; margin-bottom: 10px; border-bottom: 1px solid #eee; padding-bottom: 5px; }
.news-title { font-weight: 700; color: #0f172a; font-size: 1.1rem; }
.news-date { color: #64748b; font-size: 0.85rem; }
.news-content { color: #334155; line-height: 1.5; }
.news-footer { margin-top: 10px; font-size: 0.85rem; color: #94a3b8; font-style: italic; }
.teacher-name { font-weight: 600; color: #0f172a; }
.no-news { text-align: center; color: #94a3b8; font-style: italic; padding: 20px; }
</style>