<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const props = defineProps(['currentUser'])
// We emit 'open-homework' so App.vue knows to show the popup
const emit = defineEmits(['open-homework'])

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

// Allow parent to trigger refresh
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
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in myCourses" :key="item.id">
          <td class="course-name">{{ item.courseName }}</td>
          <td>
            <button class="hw-btn" @click="$emit('open-homework', item)">View HW</button>
          </td>
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

/* New HW Button Style */
.hw-btn { background-color: #8b5cf6; color: white; border: none; padding: 4px 12px; border-radius: 6px; cursor: pointer; font-size: 0.8rem; font-weight: bold; }
.hw-btn:hover { background-color: #7c3aed; }
</style>