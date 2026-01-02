<script setup>
import { ref } from 'vue'
import axios from 'axios'

const emit = defineEmits(['login-success'])

const isRegistering = ref(false)
const formData = ref({
  username: '',
  password: '',
  email: '',
  role: 'student' // Default role
})
const error = ref('')

// Function to handle Login
const handleLogin = async () => {
  try {
    const response = await axios.post('http://localhost:8080/experiment1/users/login', {
      username: formData.value.username,
      password: formData.value.password
    })

    if (response.data.status === 'success') {
      // Tell the main app we are in!
      emit('login-success', response.data)
    } else {
      error.value = "Invalid username or password"
    }
  } catch (err) {
    error.value = "Login failed: " + err.message
  }
}

// Function to handle Register
const handleRegister = async () => {
  try {
    await axios.post('http://localhost:8080/experiment1/users/register', formData.value)
    alert("Registration successful! Please log in.")
    isRegistering.value = false // Switch back to login mode
    error.value = ''
  } catch (err) {
    error.value = "Registration failed: " + err.message
  }
}
</script>

<template>
  <div class="login-container">
    <div class="login-card">
      <div class="brand-header">
        <span class="logo">🦁</span>
        <h2>{{ isRegistering ? 'Join OSOD' : 'Welcome Back' }}</h2>
        <p>Sign in to access the Education Platform</p>
      </div>

      <div class="form-group">
        <label>Username</label>
        <input v-model="formData.username" placeholder="Enter username" />
      </div>

      <div class="form-group">
        <label>Password</label>
        <input type="password" v-model="formData.password" placeholder="Enter password" />
      </div>

      <div v-if="isRegistering">
        <div class="form-group">
          <label>Email</label>
          <input v-model="formData.email" placeholder="Enter email" />
        </div>
        <div class="form-group">
          <label>I am a:</label>
          <select v-model="formData.role">
            <option value="student">Student</option>
            <option value="teacher">Teacher</option>
          </select>
        </div>
      </div>

      <p v-if="error" class="error-msg">{{ error }}</p>

      <button v-if="!isRegistering" @click="handleLogin" class="btn-primary">Log In</button>
      <button v-else @click="handleRegister" class="btn-primary">Create Account</button>

      <p class="toggle-text">
        {{ isRegistering ? 'Already have an account?' : 'New to Osod Education?' }}
        <span @click="isRegistering = !isRegistering" class="link">
          {{ isRegistering ? 'Log in here' : 'Create account' }}
        </span>
      </p>
    </div>
  </div>
</template>

<style scoped>
.login-container {
  display: flex; justify-content: center; align-items: center;
  min-height: 100vh; background: #f1f5f9;
}
.login-card {
  background: white; padding: 40px; border-radius: 12px;
  box-shadow: 0 4px 6px -1px rgba(0,0,0,0.1); width: 100%; max-width: 400px;
}
.brand-header { text-align: center; margin-bottom: 30px; }
.logo { font-size: 3rem; display: block; margin-bottom: 10px; }
h2 { color: #0f172a; margin: 0; }
p { color: #64748b; margin-top: 5px; }

.form-group { margin-bottom: 20px; text-align: left; }
label { display: block; margin-bottom: 8px; font-weight: 500; color: #334155; }
input, select {
  width: 100%; padding: 10px; border: 1px solid #cbd5e1; border-radius: 6px;
  font-size: 1rem; box-sizing: border-box; /* Fixes width issues */
}
.btn-primary {
  width: 100%; padding: 12px; background: #0f172a; color: white;
  border: none; border-radius: 6px; font-size: 1rem; cursor: pointer;
}
.btn-primary:hover { background: #1e293b; }
.error-msg { color: #ef4444; text-align: center; margin-bottom: 15px; }
.toggle-text { text-align: center; margin-top: 20px; color: #64748b; }
.link { color: #f59e0b; cursor: pointer; font-weight: bold; }
.link:hover { text-decoration: underline; }
</style>