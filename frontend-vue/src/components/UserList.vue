<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const users = ref([])
const error = ref('')

const fetchUsers = async () => {
  try {
    // This connects to your Tomcat server
    const response = await axios.get('http://localhost:8080/experiment1/users')
    users.value = response.data
  } catch (err) {
    error.value = "Error connecting to backend: " + err.message
    console.error(err)
  }
}

onMounted(() => {
  fetchUsers()
})
</script>

<template>
  <div class="container">
    <h2>User List from Java Backend</h2>
    <p v-if="error" class="error">{{ error }}</p>
    <table v-if="users.length > 0">
      <thead>
        <tr>
          <th>ID</th>
          <th>Username</th>
          <th>Email</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id">
          <td>{{ user.id }}</td>
          <td>{{ user.username }}</td>
          <td>{{ user.email }}</td>
        </tr>
      </tbody>
    </table>
    <p v-else-if="!error">Loading data...</p>
  </div>
</template>

<style scoped>
.container { max-width: 600px; margin: 0 auto; padding: 20px; }
table { width: 100%; border-collapse: collapse; margin-top: 20px; }
th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
th { background-color: #42b983; color: white; }
.error { color: red; font-weight: bold; }
</style>