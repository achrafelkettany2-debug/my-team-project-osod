<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const props = defineProps(['currentUser'])
const emit = defineEmits(['open-homework', 'open-videos', 'open-discussion'])

const myCourses = ref([])

const fetchMyCourses = async () => {
  if (!props.currentUser) return;
  try {
    const response = await axios.get(`http://localhost:8080/experiment1/enrollments/student/${props.currentUser.id}`)
    myCourses.value = response.data
  } catch (err) {
    console.error("Error fetching schedule", err)
  }
}

defineExpose({ fetchMyCourses })

onMounted(() => {
  fetchMyCourses()
})
</script>

<template>
  <div class="schedule-container">
    <div v-if="myCourses.length === 0" class="empty-state">
      <p>No active classes.</p>
    </div>

    <table v-else>
      <tbody>
        <tr v-for="item in myCourses" :key="item.id">
          <td class="course-name-cell">
            <div class="course-name">{{ item.courseName }}</div>
          </td>
          
          <td class="actions-cell">
            <div class="action-bar-mini">
              
              <button class="icon-btn-mini hw" @click="$emit('open-homework', item)" title="Assignments">
                <svg viewBox="0 0 24 24" width="16" height="16" stroke="currentColor" stroke-width="2" fill="none"><path d="M9 5H7a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V7a2 2 0 0 0-2-2h-2M9 5a2 2 0 0 0 2 2h2a2 2 0 0 0 2-2M9 5a2 2 0 0 1 2-2h2a2 2 0 0 1 2 2m-6 9 2 2 4-4"/></svg>
              </button>

              <button class="icon-btn-mini vid" @click="$emit('open-videos', item)" title="Videos">
                <svg viewBox="0 0 24 24" width="16" height="16" stroke="currentColor" stroke-width="2" fill="none"><polygon points="5 3 19 12 5 21 5 3"></polygon></svg>
              </button>

              <button class="icon-btn-mini chat" @click="$emit('open-discussion', item)" title="Chat">
                <svg viewBox="0 0 24 24" width="16" height="16" stroke="currentColor" stroke-width="2" fill="none"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path></svg>
              </button>

            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.schedule-container { padding: 0; }
.empty-state { text-align: center; color: #94a3b8; padding: 20px; font-style: italic; font-size: 0.9rem; }

table { width: 100%; border-collapse: collapse; }
tbody tr { border-bottom: 1px solid #f1f5f9; }
tbody tr:last-child { border-bottom: none; }
td { padding: 12px 15px; vertical-align: middle; }

.course-name { font-weight: 600; color: #334155; font-size: 0.9rem; }

.actions-cell { text-align: right; width: 100px; }
.action-bar-mini { display: flex; gap: 6px; justify-content: flex-end; }

.icon-btn-mini { 
  background: #f1f5f9; border: none; padding: 6px; border-radius: 6px; 
  display: flex; align-items: center; justify-content: center; color: #64748b; cursor: pointer;
  transition: all 0.2s;
}
.icon-btn-mini:hover { transform: translateY(-1px); }

/* Color Grading on Hover */
.icon-btn-mini.hw:hover { background: #f3e8ff; color: #9333ea; }
.icon-btn-mini.vid:hover { background: #ffe4e6; color: #e11d48; }
.icon-btn-mini.chat:hover { background: #dbeafe; color: #2563eb; }
</style>