<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const users = ref([])
const showAddModal = ref(false)
const newUser = ref({ username: '', password: '', email: '', role: 'student' })

const fetchUsers = async () => {
  try {
    // CORRECTED URL: Points to UserController @RequestMapping("/users")
    const response = await axios.get('http://localhost:8080/experiment1/users')
    users.value = response.data
  } catch (err) {
    console.error("Error fetching users", err)
  }
}

// DELETE USER
const deleteUser = async (id, username) => {
  if (username === 'Director_Admin') {
    alert("You cannot delete yourself!")
    return
  }
  if(!confirm(`Are you sure you want to delete user: ${username}?`)) return

  try {
    // CORRECTED URL
    await axios.delete(`http://localhost:8080/experiment1/users/${id}`)
    fetchUsers()
  } catch (err) {
    alert("Error deleting user: " + err.message)
  }
}

// ADD USER
const addUser = async () => {
  if (!newUser.value.username || !newUser.value.password || !newUser.value.email) {
    alert("All fields are required!")
    return
  }
  
  try {
    // CORRECTED URL
    const response = await axios.post('http://localhost:8080/experiment1/users/register', newUser.value)
    
    if (response.data.status === 'success') {
      alert("User created successfully! 🎉")
      showAddModal.value = false
      newUser.value = { username: '', password: '', email: '', role: 'student' } 
      fetchUsers() 
    } else {
      alert("Error: Username likely taken.")
    }
  } catch (err) {
    alert("Error creating user: " + err.message)
  }
}

onMounted(() => {
  fetchUsers()
})
</script>

<template>
  <div class="user-list-container">
    
    <div class="list-header">
      <h4>Registered Accounts</h4>
      <button class="add-btn" @click="showAddModal = true">+ Add New User</button>
    </div>

    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Username</th>
          <th>Role</th>
          <th>Email</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id">
          <td>{{ user.id }}</td>
          <td><strong>{{ user.username }}</strong></td>
          <td>
            <span class="badge" :class="user.role">{{ user.role }}</span>
          </td>
          <td>{{ user.email }}</td>
          <td>
             <button class="del-btn" @click="deleteUser(user.id, user.username)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>

    <div v-if="showAddModal" class="modal-overlay">
      <div class="modal-content">
        <h3>👤 Create New User</h3>
        
        <label>Username</label>
        <input v-model="newUser.username" placeholder="jdoe" />

        <label>Email</label>
        <input v-model="newUser.email" placeholder="email@osod.edu" />

        <label>Password</label>
        <input v-model="newUser.password" type="password" placeholder="*****" />

        <label>Role</label>
        <select v-model="newUser.role">
          <option value="student">Student</option>
          <option value="teacher">Teacher</option>
          <option value="admin">Admin</option>
        </select>

        <div class="modal-actions">
          <button class="save-btn" @click="addUser">Create User</button>
          <button class="cancel-btn" @click="showAddModal = false">Cancel</button>
        </div>
      </div>
    </div>

  </div>
</template>

<style scoped>
.user-list-container { overflow-x: auto; }

.list-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px; }
.list-header h4 { margin: 0; color: #64748b; }

table { width: 100%; border-collapse: collapse; background: white; border-radius: 8px; overflow: hidden; }
th, td { padding: 12px; text-align: left; border-bottom: 1px solid #eee; }
th { background-color: #f8fafc; color: #64748b; font-size: 0.85rem; text-transform: uppercase; }

.badge { padding: 4px 8px; border-radius: 12px; font-size: 0.75rem; font-weight: bold; color: white; text-transform: uppercase; }
.badge.admin { background-color: #7c3aed; }
.badge.teacher { background-color: #f59e0b; }
.badge.student { background-color: #3b82f6; }

/* Buttons */
.add-btn { background-color: #10b981; color: white; border: none; padding: 8px 16px; border-radius: 6px; cursor: pointer; font-weight: bold; }
.add-btn:hover { background-color: #059669; }

.del-btn { background-color: #ef4444; color: white; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer; font-size: 0.8rem; }
.del-btn:hover { background-color: #dc2626; }

/* Modal Styles */
.modal-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); display: flex; justify-content: center; align-items: center; z-index: 2000; }
.modal-content { background: white; padding: 25px; border-radius: 12px; width: 350px; box-shadow: 0 10px 25px rgba(0,0,0,0.2); }
.modal-content h3 { margin-top: 0; color: #0f172a; }
label { display: block; margin: 10px 0 5px; font-weight: 600; font-size: 0.9rem; color: #334155; }
input, select { width: 100%; padding: 8px; border: 1px solid #cbd5e1; border-radius: 6px; box-sizing: border-box; }
.modal-actions { margin-top: 20px; display: flex; gap: 10px; }
.save-btn { flex: 1; background: #3b82f6; color: white; border: none; padding: 10px; border-radius: 6px; cursor: pointer; font-weight: bold; }
.cancel-btn { flex: 1; background: #e2e8f0; color: #475569; border: none; padding: 10px; border-radius: 6px; cursor: pointer; font-weight: bold; }
</style>