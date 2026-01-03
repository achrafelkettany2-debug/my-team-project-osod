<script setup>
import { ref } from 'vue'
import axios from 'axios'

const emit = defineEmits(['login-success'])

// Toggle between Login (true) and Register (false)
const isLoginMode = ref(true)

const username = ref('')
const password = ref('')
const email = ref('') // Needed for registration
const error = ref('')

const handleSubmit = async () => {
  if(!username.value || !password.value) {
    error.value = "Please fill in all fields"
    return
  }

  try {
    if (isLoginMode.value) {
      // === LOGIN LOGIC ===
      const response = await axios.post('http://localhost:8080/experiment1/users/login', {
        username: username.value,
        password: password.value
      })

      if (response.data.status === 'success') {
        emit('login-success', response.data.user)
      } else {
        error.value = "Invalid username or password"
      }

    } else {
      // === REGISTER LOGIC ===
      if (!email.value) {
        error.value = "Email is required for registration"
        return
      }
      const response = await axios.post('http://localhost:8080/experiment1/users/register', {
        username: username.value,
        password: password.value,
        email: email.value,
        role: 'student' // Self-registered users are always students first
      })

      if (response.data.status === 'success') {
        alert("Registration successful! Please log in.")
        isLoginMode.value = true // Switch back to login
        error.value = ''
      } else {
        error.value = "Username already exists."
      }
    }

  } catch (err) {
    console.error(err)
    error.value = "Network Error: Is Tomcat running?"
  }
}
</script>

<template>
  <div class="login-container">
    <div class="login-box">
      <div class="icon">🦁</div>
      <h2>{{ isLoginMode ? 'Student Login' : 'Create Account' }}</h2>
      <p class="subtitle">
        {{ isLoginMode ? 'Enter your credentials to access the portal.' : 'Join the Osod Education platform.' }}
      </p>
      
      <div class="form-group">
        <label>Username</label>
        <input v-model="username" type="text" placeholder="Enter username" />
      </div>

      <div class="form-group" v-if="!isLoginMode">
        <label>Email</label>
        <input v-model="email" type="email" placeholder="student@university.edu" />
      </div>

      <div class="form-group">
        <label>Password</label>
        <input v-model="password" type="password" placeholder="••••••••" @keyup.enter="handleSubmit" />
      </div>

      <p v-if="error" class="error-msg">{{ error }}</p>

      <button @click="handleSubmit">{{ isLoginMode ? 'Sign In' : 'Register' }}</button>
      
      <p class="footer-text">
        <span v-if="isLoginMode">
          Don't have an account? <a href="#" @click.prevent="isLoginMode = false">Register here</a>.
        </span>
        <span v-else>
          Already have an account? <a href="#" @click.prevent="isLoginMode = true">Log in here</a>.
        </span>
      </p>
    </div>
  </div>
</template>

<style scoped>
.login-container { display: flex; justify-content: center; align-items: center; height: 100vh; background: linear-gradient(135deg, #0f172a 0%, #334155 100%); }
.login-box { background: white; padding: 40px; border-radius: 12px; width: 100%; max-width: 400px; text-align: center; box-shadow: 0 10px 25px rgba(0,0,0,0.2); }
.icon { font-size: 3rem; margin-bottom: 10px; }
h2 { color: #0f172a; margin-bottom: 5px; }
.subtitle { color: #64748b; font-size: 0.9rem; margin-bottom: 30px; }
.form-group { text-align: left; margin-bottom: 15px; }
label { display: block; margin-bottom: 5px; font-weight: 600; color: #334155; font-size: 0.9rem; }
input { width: 100%; padding: 12px; border: 1px solid #cbd5e1; border-radius: 6px; box-sizing: border-box; font-size: 1rem; transition: border 0.2s; }
input:focus { border-color: #3b82f6; outline: none; }
button { width: 100%; padding: 12px; background-color: #3b82f6; color: white; border: none; border-radius: 6px; font-size: 1rem; font-weight: bold; cursor: pointer; transition: background 0.2s; margin-top: 10px; }
button:hover { background-color: #2563eb; }
.error-msg { color: #ef4444; font-size: 0.9rem; margin: 10px 0; font-weight: bold; }
.footer-text { margin-top: 20px; font-size: 0.85rem; color: #94a3b8; }
a { color: #3b82f6; text-decoration: none; font-weight: bold; }
a:hover { text-decoration: underline; }
</style>