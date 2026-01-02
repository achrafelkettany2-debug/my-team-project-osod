<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

// 1. Accept the user info passed from App.vue
const props = defineProps(['currentUser'])

const courses = ref([])
const error = ref('')

// Variables for the new course form
const newCourse = ref({
  courseName: '',
  teacherName: '',
  description: ''
})

const fetchCourses = async () => {
  try {
    const response = await axios.get('http://localhost:8080/experiment1/courses')
    courses.value = response.data
  } catch (err) {
    error.value = "Error connecting to backend: " + err.message
  }
}

const addCourse = async () => {
  try {
    await axios.post('http://localhost:8080/experiment1/courses', newCourse.value)
    newCourse.value = { courseName: '', teacherName: '', description: '' }
    fetchCourses()
  } catch (err) {
    alert("Error adding course: " + err.message)
  }
}

const deleteCourse = async (id) => {
  if(!confirm("Are you sure you want to remove this course?")) return;
  try {
    await axios.delete(`http://localhost:8080/experiment1/courses/${id}`)
    fetchCourses()
  } catch (err) {
    alert("Error deleting course: " + err.message)
  }
}

onMounted(() => {
  fetchCourses()
})
</script>

<template>
  <div class="container">
    <div class="header-row">
      <span v-if="currentUser" class="role-badge" :class="currentUser.role">
        Mode: {{ currentUser.role.toUpperCase() }}
      </span>
    </div>

    <div v-if="currentUser && currentUser.role === 'teacher'" class="add-form">
      <h3>Add New Course</h3>
      <div class="input-group">
        <input v-model="newCourse.courseName" placeholder="Course Name" />
        <input v-model="newCourse.teacherName" placeholder="Teacher Name" />
        <input v-model="newCourse.description" placeholder="Description" />
        <button @click="addCourse">Add Course</button>
      </div>
    </div>
    
    <p v-if="error" class="error">{{ error }}</p>

    <table v-if="courses.length > 0">
      <thead>
        <tr>
          <th>ID</th>
          <th>Course Name</th>
          <th>Teacher</th>
          <th>Description</th>
          <th v-if="currentUser && currentUser.role === 'teacher'">Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="course in courses" :key="course.id">
          <td>{{ course.id }}</td>
          <td>{{ course.courseName }}</td>
          <td>{{ course.teacherName }}</td>
          <td>{{ course.description }}</td>
          <td v-if="currentUser && currentUser.role === 'teacher'">
            <button class="delete-btn" @click="deleteCourse(course.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>

    <p v-else-if="!error">Loading courses...</p>
  </div>
</template>

<style scoped>
.container { max-width: 100%; padding: 20px; }
.header-row { display: flex; justify-content: flex-end; margin-bottom: 10px; }

.role-badge { padding: 6px 12px; border-radius: 20px; font-size: 0.85rem; font-weight: bold; color: white; letter-spacing: 0.5px;}
.role-badge.teacher { background-color: #f59e0b; box-shadow: 0 2px 4px rgba(245, 158, 11, 0.3); } 
.role-badge.student { background-color: #3b82f6; box-shadow: 0 2px 4px rgba(59, 130, 246, 0.3); } 

/* Form Styles */
.add-form { background: #f8fafc; padding: 20px; margin-bottom: 25px; border-radius: 8px; border: 1px dashed #cbd5e1; }
h3 { margin-top: 0; color: #334155; font-size: 1rem; margin-bottom: 10px;}
.input-group { display: flex; gap: 10px; flex-wrap: wrap; }
input { padding: 10px; border: 1px solid #cbd5e1; border-radius: 6px; flex: 1; min-width: 150px; }
button { padding: 10px 20px; background-color: #0f172a; color: white; border: none; border-radius: 6px; cursor: pointer; transition: background 0.2s; font-weight: 500;}
button:hover { background-color: #334155; }

table { width: 100%; border-collapse: collapse; margin-top: 10px; background: white; border-radius: 8px; overflow: hidden; box-shadow: 0 1px 3px rgba(0,0,0,0.1); }
th, td { padding: 15px; text-align: left; border-bottom: 1px solid #e2e8f0; }
th { background-color: #f1f5f9; color: #475569; font-weight: 600; text-transform: uppercase; font-size: 0.85rem; letter-spacing: 0.5px;}
.error { color: #ef4444; font-weight: bold; background: #fee2e2; padding: 10px; border-radius: 6px; }

.delete-btn { background-color: #ef4444; padding: 6px 12px; font-size: 0.9rem;}
.delete-btn:hover { background-color: #dc2626; }
</style>