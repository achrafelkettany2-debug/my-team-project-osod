<script setup>
import { ref, onMounted, watch } from 'vue'
import axios from 'axios'

const props = defineProps(['currentUser'])

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

// We expose this function so the parent can trigger a refresh when we click "Join"
defineExpose({ fetchMyCourses })

onMounted(() => {
  fetchMyCourses()
})
</script>

<template>
  <div class="schedule-container">
    <div v-if="myCourses.length === 0" class="empty-state">
      <p>You haven't joined any classes yet.</p>
    </div>

    <table v-else>
      <thead>
        <tr>
          <th>Course Name</th>
          <th>Status</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in myCourses" :key="item.id">
          <td class="course-name">{{ item.courseName }}</td>
          <td><span class="status-badge">Enrolled</span></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.schedule-container { padding: 10px; }
.empty-state { text-align: center; color: #64748b; padding: 20px; font-style: italic; }
table { width: 100%; border-collapse: collapse; }
th, td { padding: 12px; text-align: left; border-bottom: 1px solid #eee; }
th { color: #64748b; font-size: 0.85rem; }
.course-name { font-weight: 600; color: #0f172a; }
.status-badge { background: #dcfce7; color: #166534; padding: 4px 8px; border-radius: 12px; font-size: 0.75rem; font-weight: bold; }
</style>