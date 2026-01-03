<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'

const props = defineProps(['currentUser'])
const emit = defineEmits(['course-joined', 'open-homework', 'open-videos', 'open-discussion']) 

const courses = ref([])
const error = ref('')
const newCourse = ref({ courseName: '', teacherName: '', description: '' })
const searchQuery = ref('')

const filteredCourses = computed(() => {
  if (!searchQuery.value) return courses.value
  const lowerQuery = searchQuery.value.toLowerCase()
  return courses.value.filter(c => 
    c.courseName.toLowerCase().includes(lowerQuery) || 
    c.teacherName.toLowerCase().includes(lowerQuery)
  )
})

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
    
    <div v-if="currentUser && (currentUser.role === 'teacher' || currentUser.role === 'admin')" class="add-panel">
      <div class="input-row">
        <input v-model="newCourse.courseName" placeholder="Course Name" />
        <input v-model="newCourse.teacherName" placeholder="Teacher Name" />
        <input v-model="newCourse.description" placeholder="Short Description" />
        <button class="create-btn" @click="addCourse">Create Course</button>
      </div>
    </div>
    
    <div class="search-wrapper">
      <input v-model="searchQuery" placeholder="🔍 Find a course..." class="search-input" />
    </div>

    <p v-if="error" class="error">{{ error }}</p>

    <table v-if="courses.length > 0">
      <thead>
        <tr>
          <th style="width: 50px">ID</th>
          <th>Course Name</th>
          <th>Instructor</th>
          <th>About</th>
          <th style="text-align: right; padding-right: 30px;">Actions</th> 
        </tr>
      </thead>
      <tbody>
        <tr v-for="course in filteredCourses" :key="course.id">
          <td class="id-col">#{{ course.id }}</td>
          <td><strong>{{ course.courseName }}</strong></td>
          <td><span class="teacher-tag">{{ course.teacherName }}</span></td>
          <td class="desc">{{ course.description }}</td>
          
          <td class="actions-cell">
            
            <div v-if="currentUser.role === 'teacher' || currentUser.role === 'admin'" class="action-bar">
              
              <button class="icon-btn hw" @click="$emit('open-homework', course)" title="Assignments">
                <svg viewBox="0 0 24 24" width="18" height="18" stroke="currentColor" stroke-width="2" fill="none"><path d="M9 5H7a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V7a2 2 0 0 0-2-2h-2M9 5a2 2 0 0 0 2 2h2a2 2 0 0 0 2-2M9 5a2 2 0 0 1 2-2h2a2 2 0 0 1 2 2m-6 9 2 2 4-4"/></svg>
              </button>

              <button class="icon-btn vid" @click="$emit('open-videos', course)" title="Videos">
                <svg viewBox="0 0 24 24" width="18" height="18" stroke="currentColor" stroke-width="2" fill="none"><polygon points="5 3 19 12 5 21 5 3"></polygon></svg>
              </button>

              <button class="icon-btn chat" @click="$emit('open-discussion', course)" title="Chat Room">
                <svg viewBox="0 0 24 24" width="18" height="18" stroke="currentColor" stroke-width="2" fill="none"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path></svg>
              </button>

              <button class="icon-btn del" @click="deleteCourse(course.id)" title="Delete Course">
                <svg viewBox="0 0 24 24" width="18" height="18" stroke="currentColor" stroke-width="2" fill="none"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path></svg>
              </button>
            </div>
            
            <button v-if="currentUser.role === 'student'" class="join-btn-pill" @click="joinCourse(course.id)">
              Join Class
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <div v-else-if="!error" class="empty-state">No courses found.</div>
  </div>
</template>

<style scoped>
.container { padding: 20px 30px; }

/* Form Styles */
.add-panel { background: #f8fafc; padding: 15px; border-radius: 12px; margin-bottom: 20px; border: 1px dashed #cbd5e1; }
.input-row { display: flex; gap: 10px; }
input { padding: 10px 15px; border: 1px solid #e2e8f0; border-radius: 8px; flex: 1; outline: none; transition: border 0.2s; }
input:focus { border-color: #3b82f6; }
.create-btn { background: #0f172a; color: white; border: none; padding: 0 20px; border-radius: 8px; font-weight: 600; font-size: 0.9rem; }

/* Search */
.search-wrapper { margin-bottom: 10px; }
.search-input { width: 100%; background: #f1f5f9; border: none; }

/* Table Styles */
.id-col { color: #94a3b8; font-size: 0.85rem; }
.teacher-tag { background: #eff6ff; color: #3b82f6; padding: 4px 10px; border-radius: 20px; font-size: 0.75rem; font-weight: bold; }
.desc { color: #64748b; font-size: 0.9rem; max-width: 250px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }

/* ACTION BAR (The New Buttons) */
.actions-cell { text-align: right; }
.action-bar { display: inline-flex; gap: 8px; background: #f8fafc; padding: 4px; border-radius: 8px; border: 1px solid #f1f5f9; }

.icon-btn { 
  background: transparent; border: none; padding: 6px; border-radius: 6px; 
  display: flex; align-items: center; justify-content: center; color: #64748b; 
}
.icon-btn:hover { transform: translateY(-1px); }

/* Individual Icon Colors on Hover */
.icon-btn.hw:hover { background: #f3e8ff; color: #9333ea; } /* Purple */
.icon-btn.vid:hover { background: #ffe4e6; color: #e11d48; } /* Rose */
.icon-btn.chat:hover { background: #dbeafe; color: #2563eb; } /* Blue */
.icon-btn.del:hover { background: #fee2e2; color: #ef4444; } /* Red */

/* Student Join Button */
.join-btn-pill { background: #10b981; color: white; border: none; padding: 8px 20px; border-radius: 50px; font-weight: bold; font-size: 0.85rem; box-shadow: 0 4px 6px -1px rgba(16, 185, 129, 0.3); }
.join-btn-pill:hover { background: #059669; transform: translateY(-1px); }

.empty-state { text-align: center; color: #94a3b8; margin-top: 30px; font-style: italic; }
.error { color: #ef4444; }
</style>