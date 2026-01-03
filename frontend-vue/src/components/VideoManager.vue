<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const props = defineProps(['course', 'currentUser'])
const emit = defineEmits(['close'])

const videos = ref([])
const newVideo = ref({ title: '', url: '' })
const activeVideoUrl = ref(null) // Which video is currently playing

// Helper: Extract YouTube ID to make it playable
const getEmbedUrl = (url) => {
  if (!url) return ''
  // Handle standard "youtube.com/watch?v=XYZ" and short "youtu.be/XYZ"
  let videoId = ''
  if (url.includes('v=')) {
    videoId = url.split('v=')[1].split('&')[0]
  } else if (url.includes('youtu.be/')) {
    videoId = url.split('youtu.be/')[1]
  }
  
  if (videoId) {
    return `https://www.youtube.com/embed/${videoId}`
  } else {
    return '' // Invalid URL
  }
}

// Fetch Videos
const fetchVideos = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/experiment1/videos/course/${props.course.id}`)
    videos.value = response.data
  } catch (err) {
    alert("Error loading videos")
  }
}

// Add Video (Teacher)
const addVideo = async () => {
  if (!newVideo.value.title || !newVideo.value.url) {
    alert("Title and URL are required!")
    return
  }
  try {
    await axios.post('http://localhost:8080/experiment1/videos', {
      courseId: props.course.id,
      title: newVideo.value.title,
      videoUrl: newVideo.value.url
    })
    newVideo.value = { title: '', url: '' }
    fetchVideos()
  } catch (err) {
    alert("Error adding video: " + err.message)
  }
}

// Delete Video (Teacher)
const deleteVideo = async (id) => {
  if(!confirm("Remove this lesson?")) return;
  try {
    await axios.delete(`http://localhost:8080/experiment1/videos/${id}`)
    fetchVideos()
  } catch (err) {
    alert("Error deleting: " + err.message)
  }
}

onMounted(() => {
  fetchVideos()
})
</script>

<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <div class="modal-header">
        <h2>📺 Lessons: {{ course.courseName }}</h2>
        <button class="close-btn" @click="$emit('close')">X</button>
      </div>

      <div v-if="currentUser.role === 'teacher'" class="add-box">
        <h3>Add New Lesson</h3>
        <input v-model="newVideo.title" placeholder="Lesson Title (e.g., Intro to Java)" />
        <input v-model="newVideo.url" placeholder="YouTube URL (e.g., https://youtube.com/watch?v=...)" />
        <button class="add-btn" @click="addVideo">Add Lesson</button>
      </div>

      <div v-if="activeVideoUrl" class="player-box">
        <iframe 
          width="100%" 
          height="315" 
          :src="activeVideoUrl" 
          frameborder="0" 
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
          allowfullscreen>
        </iframe>
        <button class="close-player" @click="activeVideoUrl = null">Close Player</button>
      </div>

      <div class="list-box">
        <div v-if="videos.length === 0" class="empty">No lessons uploaded yet. 📚</div>
        <div v-for="item in videos" :key="item.id" class="item">
          <div class="item-info">
            <strong>{{ item.title }}</strong>
            <div class="actions">
              <button class="play-btn" @click="activeVideoUrl = getEmbedUrl(item.videoUrl)">▶ Watch</button>
              
              <button v-if="currentUser.role === 'teacher'" class="delete-btn" @click="deleteVideo(item.id)">🗑️</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.6); display: flex; justify-content: center; align-items: center; z-index: 1000; }
.modal-content { background: white; padding: 25px; border-radius: 12px; width: 600px; max-width: 90%; max-height: 85vh; overflow-y: auto; box-shadow: 0 10px 25px rgba(0,0,0,0.3); }
.modal-header { display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #eee; padding-bottom: 15px; margin-bottom: 20px; }
.close-btn { background: none; border: none; font-size: 1.2rem; cursor: pointer; color: #64748b; font-weight: bold; }

/* Teacher Form */
.add-box { background: #fff1f2; padding: 15px; border-radius: 8px; margin-bottom: 20px; border: 1px dashed #fecdd3; }
input { display: block; width: 100%; padding: 8px; margin-bottom: 10px; border: 1px solid #cbd5e1; border-radius: 4px; box-sizing: border-box; }
.add-btn { background: #e11d48; color: white; padding: 8px 16px; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; width: 100%; }
.add-btn:hover { background: #be123c; }

/* Player */
.player-box { margin-bottom: 20px; background: black; padding: 10px; border-radius: 8px; text-align: right; }
.close-player { background: #333; color: white; border: none; padding: 5px 10px; margin-top: 5px; cursor: pointer; border-radius: 4px; }

/* List */
.item { border-bottom: 1px solid #eee; padding: 15px 0; }
.item-info { display: flex; justify-content: space-between; align-items: center; }
.actions { display: flex; gap: 10px; align-items: center; }
.play-btn { background: #10b981; color: white; border: none; padding: 6px 12px; border-radius: 20px; cursor: pointer; font-size: 0.85rem; font-weight: bold; }
.play-btn:hover { background: #059669; }
.delete-btn { background: none; border: none; cursor: pointer; font-size: 1.1rem; }
.empty { text-align: center; color: #94a3b8; font-style: italic; padding: 20px; }
</style>