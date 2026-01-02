<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const props = defineProps(['course', 'currentUser'])
const emit = defineEmits(['close'])

const assignments = ref([])
const newAssignment = ref({ title: '', description: '', dueDate: '' })

// Fetch assignments
const fetchAssignments = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/experiment1/assignments/course/${props.course.id}`)
    assignments.value = response.data
  } catch (err) {
    alert("Error loading assignments")
  }
}

// Teacher adds assignment
const addAssignment = async () => {
  if (!newAssignment.value.title || !newAssignment.value.dueDate) {
    alert("Title and Due Date are required!")
    return
  }

  // Formatting date for Java
  const formattedDate = newAssignment.value.dueDate.replace('T', ' ') + ':00'

  try {
    await axios.post('http://localhost:8080/experiment1/assignments', {
      courseId: props.course.id,
      title: newAssignment.value.title,
      description: newAssignment.value.description,
      dueDate: formattedDate
    })
    
    newAssignment.value = { title: '', description: '', dueDate: '' }
    fetchAssignments() 
  } catch (err) {
    alert("Error adding assignment: " + err.message)
  }
}

// NEW: Delete Assignment
const deleteAssignment = async (id) => {
  if(!confirm("Delete this assignment?")) return;
  try {
    await axios.delete(`http://localhost:8080/experiment1/assignments/${id}`)
    fetchAssignments() // Refresh list immediately
  } catch (err) {
    alert("Error deleting: " + err.message)
  }
}

onMounted(() => {
  fetchAssignments()
})
</script>

<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <div class="modal-header">
        <h2>📝 Homework: {{ course.courseName }}</h2>
        <button class="close-btn" @click="$emit('close')">X</button>
      </div>

      <div v-if="currentUser.role === 'teacher'" class="add-box">
        <h3>Add New Assignment</h3>
        <input v-model="newAssignment.title" placeholder="Title (e.g., Essay 1)" />
        <input v-model="newAssignment.description" placeholder="Instructions..." />
        <label>Due Date:</label>
        <input type="datetime-local" v-model="newAssignment.dueDate" />
        <button class="add-btn" @click="addAssignment">Assign Task</button>
      </div>

      <div class="list-box">
        <div v-if="assignments.length === 0" class="empty">No assignments yet. 🎉</div>
        <div v-for="item in assignments" :key="item.id" class="item">
          <div class="item-header">
            <strong>{{ item.title }}</strong>
            <div class="header-right">
              <span class="due-date">Due: {{ item.dueDate }}</span>
              <button 
                v-if="currentUser.role === 'teacher'" 
                class="delete-btn" 
                @click="deleteAssignment(item.id)">
                🗑️
              </button>
            </div>
          </div>
          <p>{{ item.description }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); display: flex; justify-content: center; align-items: center; z-index: 1000; }
.modal-content { background: white; padding: 25px; border-radius: 12px; width: 500px; max-width: 90%; max-height: 80vh; overflow-y: auto; box-shadow: 0 10px 25px rgba(0,0,0,0.2); }
.modal-header { display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #eee; padding-bottom: 15px; margin-bottom: 20px; }
.close-btn { background: none; border: none; font-size: 1.2rem; cursor: pointer; color: #64748b; font-weight: bold; }

/* Teacher Form */
.add-box { background: #eff6ff; padding: 15px; border-radius: 8px; margin-bottom: 20px; border: 1px dashed #bfdbfe; }
input { display: block; width: 100%; padding: 8px; margin-bottom: 10px; border: 1px solid #cbd5e1; border-radius: 4px; box-sizing: border-box; }
.add-btn { background: #3b82f6; color: white; padding: 8px 16px; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; width: 100%; }
.add-btn:hover { background: #2563eb; }

/* List */
.item { border-bottom: 1px solid #eee; padding: 15px 0; }
.item-header { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 5px; }
.header-right { display: flex; align-items: center; gap: 10px; }
.due-date { color: #ef4444; font-size: 0.85rem; font-weight: bold; }
.empty { text-align: center; color: #94a3b8; font-style: italic; padding: 20px; }

/* Delete Button */
.delete-btn { background: none; border: none; cursor: pointer; font-size: 1.1rem; padding: 2px; }
.delete-btn:hover { transform: scale(1.1); }
</style>