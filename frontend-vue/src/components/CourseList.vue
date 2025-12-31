<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const courses = ref([])
const error = ref('')

const fetchCourses = async () => {
  try {
    // Calling the NEW Course API we just built
    const response = await axios.get('http://localhost:8080/experiment1/courses')
    courses.value = response.data
  } catch (err) {
    error.value = "Error connecting to backend: " + err.message
    console.error(err)
  }
}

onMounted(() => {
  fetchCourses()
})
</script>

<template>
  <div class="container">
    <h2>Team Project: Course Management</h2>
    
    <p v-if="error" class="error">{{ error }}</p>

    <table v-if="courses.length > 0">
      <thead>
        <tr>
          <th>ID</th>
          <th>Course Name</th>
          <th>Teacher</th>
          <th>Description</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="course in courses" :key="course.id">
          <td>{{ course.id }}</td>
          <td>{{ course.courseName }}</td>
          <td>{{ course.teacherName }}</td>
          <td>{{ course.description }}</td>
        </tr>
      </tbody>
    </table>

    <p v-else-if="!error">Loading courses...</p>
  </div>
</template>

<style scoped>
.container { max-width: 800px; margin: 20px auto; padding: 20px; border: 1px solid #ccc; border-radius: 8px;}
table { width: 100%; border-collapse: collapse; margin-top: 10px; }
th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
th { background-color: #3b82f6; color: white; } /* Blue header for distinction */
.error { color: red; font-weight: bold; }
h2 { color: #3b82f6; }
</style>