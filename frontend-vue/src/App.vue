<script setup>
import { ref } from 'vue'
import UserList from './components/UserList.vue'
import CourseList from './components/CourseList.vue'
import Login from './components/Login.vue'

// State
const isLoggedIn = ref(false)
const currentUser = ref(null)

// Handle Login
const onLoginSuccess = (userData) => {
  currentUser.value = userData
  isLoggedIn.value = true
}

// Handle Logout
const logout = () => {
  isLoggedIn.value = false
  currentUser.value = null
}
</script>

<template>
  <div class="app-layout">
    
    <div v-if="!isLoggedIn">
      <Login @login-success="onLoginSuccess" />
    </div>

    <div v-else>
      <nav class="navbar">
        <div class="nav-container">
          <div class="brand">
            <span class="logo-icon">🦁</span>
            <span class="brand-name">OSOD <span class="highlight">EDUCATION</span></span>
          </div>
          <div class="nav-links">
            <span class="welcome-msg">Welcome, {{ currentUser.username }} ({{ currentUser.role }})</span>
            <a href="#" @click="logout" class="login-btn">Log Out</a>
          </div>
        </div>
      </nav>

      <header class="hero">
        <div class="hero-content">
          <h1>Master Your Future</h1>
          <p>The premium platform for modern learning.</p>
        </div>
      </header>

      <main class="main-content">
        
        <section class="card">
          <div class="card-header">
            <h2>📚 Course Management</h2>
            <p class="card-subtitle">Browse the catalog of available courses.</p>
          </div>
          <div class="card-body">
            <CourseList :currentUser="currentUser" />
          </div>
        </section>

        <section class="card" v-if="currentUser.role === 'teacher'">
          <div class="card-header">
            <h2>👥 Student Directory</h2>
            <p class="card-subtitle">Administrator Access Only: View registered users.</p>
          </div>
          <div class="card-body">
            <UserList />
          </div>
        </section>

      </main>

      <footer class="footer">
        <p>&copy; 2026 Osod Education Inc. All rights reserved.</p>
      </footer>
    </div>

  </div>
</template>

<style>
/* GLOBAL STYLES */
:root { --primary: #0f172a; --accent: #f59e0b; --bg-light: #f3f4f6; --white: #ffffff; --text-dark: #1e293b; --text-light: #64748b; }
body { margin: 0; padding: 0; font-family: 'Inter', 'Segoe UI', sans-serif; background-color: var(--bg-light); color: var(--text-dark); }

/* NAVBAR */
.navbar { background-color: var(--white); box-shadow: 0 1px 3px rgba(0,0,0,0.1); position: sticky; top: 0; z-index: 100; }
.nav-container { max-width: 1200px; margin: 0 auto; padding: 1rem 2rem; display: flex; justify-content: space-between; align-items: center; }
.brand { display: flex; align-items: center; gap: 10px; font-size: 1.5rem; font-weight: 800; color: var(--primary); }
.highlight { color: var(--accent); }
.nav-links { display: flex; gap: 20px; align-items: center; }
.welcome-msg { font-weight: 600; color: var(--text-dark); margin-right: 10px;}
.login-btn { background-color: var(--primary); color: white !important; padding: 8px 20px; border-radius: 6px; font-size: 0.9rem; text-decoration: none; cursor: pointer; }

/* HERO */
.hero { background: linear-gradient(135deg, #0f172a 0%, #1e3a8a 100%); color: white; padding: 4rem 2rem; text-align: center; margin-bottom: -40px; padding-bottom: 80px; }
.hero h1 { font-size: 2.5rem; margin-bottom: 10px; }
.hero p { font-size: 1.1rem; opacity: 0.8; }

/* MAIN CARDS */
.main-content { max-width: 1000px; margin: 0 auto; padding: 0 20px; display: flex; flex-direction: column; gap: 30px; }
.card { background: white; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1); overflow: hidden; }
.card-header { padding: 20px 30px; border-bottom: 1px solid #e2e8f0; background-color: #fafafa; }
.card-header h2 { margin: 0; color: var(--primary); font-size: 1.25rem; }
.card-subtitle { margin: 5px 0 0; color: var(--text-light); font-size: 0.9rem; }
.card-body { padding: 0; }

/* FOOTER */
.footer { text-align: center; padding: 40px; color: var(--text-light); font-size: 0.9rem; margin-top: 40px; }
</style>