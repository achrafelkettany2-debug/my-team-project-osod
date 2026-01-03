<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const props = defineProps(['currentUser'])
const emit = defineEmits(['course-joined', 'open-homework', 'open-videos']) 

const courses = ref([])
const error = ref('')

const newCourse = ref({ courseName: '', teacherName: '', description: '' })

const fetchCourses = async () => {
  try {
    const response = await axios.get('http://localhost:8080/experiment1/courses')
    courses.value = response.data
  } catch (err) {
    error.value = "Error: " + err.message
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
  if(!confirm("Are you sure?")) return;
  try {
    await axios.delete(`http://localhost:8080/experiment1/courses/${id}`)
    fetchCourses()
  } catch (err) {
    alert("Error deleting course: " + err.message)
  }
}

const joinCourse = async (courseId) => {
  try {
    const response = await axios.post('http://localhost:8080/experiment1/enrollments/join', {
      userId: props.currentUser.id,
      courseId: courseId
    })
    if (response.data.status === 'success') {
      alert("Success! Class joined.")
      emit('course-joined') 
    } else if (response.data.status === 'already_enrolled') {
      alert("Already in this class!")
    }
  } catch (err) {
    alert("Error: " + err.message)
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
        <button class="add-btn" @click="addCourse">Add Course</button>
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
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="course in courses" :key="course.id">
          <td>{{ course.id }}</td>
          <td>{{ course.courseName }}</td>
          <td>{{ course.teacherName }}</td>
          <td>{{ course.description }}</td>
          
          <td>
            <div v-if="currentUser.role === 'teacher'">
              <button class="hw-btn" @click="$emit('open-homework', course)">Manage HW</button>
              <button class="video-btn" @click="$emit('open-videos', course)">Lessons</button>
              <button class="delete-btn" @click="deleteCourse(course.id)">Delete</button>
            </div>
            
            <button v-if="currentUser.role === 'student'" class="join-btn" @click="joinCourse(course.id)">Join Class</button>
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
.role-badge { padding: 6px 12px; border-radius: 20px; font-size: 0.85rem; font-weight: bold; color: white; }
.role-badge.teacher { background-color: #f59e0b; } 
.role-badge.student { background-color: #3b82f6; } 

.add-form { background: #f8fafc; padding: 20px; margin-bottom: 25px; border-radius: 8px; border: 1px dashed #cbd5e1; }
.input-group { display: flex; gap: 10px; flex-wrap: wrap; }
input { padding: 10px; border: 1px solid #cbd5e1; border-radius: 6px; flex: 1; }
button { padding: 6px 12px; color: white; border: none; border-radius: 6px; cursor: pointer; font-weight: 500; margin-right: 5px; }

/* Colors */
.add-btn { background-color: #0f172a; padding: 10px 20px; }
.delete-btn { background-color: #ef4444; }
.delete-btn:hover { background-color: #dc2626; }
.join-btn { background-color: #10b981; }
.hw-btn { background-color: #8b5cf6; }
/* NEW Video Button */
.video-btn { background-color: #e11d48; } 
.video-btn:hover { background-color: #be123c; }

table { width: 100%; border-collapse: collapse; margin-top: 10px; background: white; border-radius: 8px; overflow: hidden; box-shadow: 0 1px 3px rgba(0,0,0,0.1); }
th, td { padding: 15px; text-align: left; border-bottom: 1px solid #e2e8f0; }
th { background-color: #f1f5f9; color: #475569; font-weight: 600; text-transform: uppercase; font-size: 0.85rem; }
.error { color: #ef4444; font-weight: bold; }
</style>