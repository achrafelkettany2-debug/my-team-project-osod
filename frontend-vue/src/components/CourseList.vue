<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

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

// Function to send data to Java
const addCourse = async () => {
  try {
    await axios.post('http://localhost:8080/experiment1/courses', newCourse.value)
    // Clear the form
    newCourse.value = { courseName: '', teacherName: '', description: '' }
    // Refresh the list to show the new item
    fetchCourses()
  } catch (err) {
    alert("Error adding course: " + err.message)
  }
}

onMounted(() => {
  fetchCourses()
})
// Function to delete a course
const deleteCourse = async (id) => {
  if(!confirm("Are you sure you want to remove this course from Osod Education?")) return;
  
  try {
    await axios.delete(`http://localhost:8080/experiment1/courses/${id}`)
    // Refresh the list immediately
    fetchCourses()
  } catch (err) {
    alert("Error deleting course: " + err.message)
  }
}
</script>

<template>
  <div class="container">
    <h2>Team Project: Course Management</h2>

    <div class="add-form">
      <h3>Add New Course</h3>
      <input v-model="newCourse.courseName" placeholder="Course Name" />
      <input v-model="newCourse.teacherName" placeholder="Teacher Name" />
      <input v-model="newCourse.description" placeholder="Description" />
      <button @click="addCourse">Add Course</button>
    </div>
    
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
        <td>
            <button class="delete-btn" @click="deleteCourse(course.id)">Delete</button>
        </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.container { max-width: 800px; margin: 20px auto; padding: 20px; border: 1px solid #ccc; border-radius: 8px;}
/* Form Styles */
.add-form { background: #f0f9ff; padding: 15px; margin-bottom: 20px; border-radius: 5px; display: flex; gap: 10px; flex-wrap: wrap;}
input { padding: 8px; border: 1px solid #ddd; border-radius: 4px; flex: 1; }
button { padding: 8px 16px; background-color: #3b82f6; color: white; border: none; border-radius: 4px; cursor: pointer; }
button:hover { background-color: #2563eb; }

table { width: 100%; border-collapse: collapse; margin-top: 10px; }
th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
th { background-color: #3b82f6; color: white; }
h2 { color: #3b82f6; }
h3 { margin-top: 0; color: #1e3a8a; width: 100%; font-size: 1rem;}
.delete-btn {
  background-color: #ef4444; /* Red color */
  margin-left: 5px;
}
.delete-btn:hover {
  background-color: #dc2626; /* Darker red on hover */
}
</style>