<script setup>
import UserList from './components/UserList.vue'
import CourseList from './components/CourseList.vue'
</script>

<template>
  <div class="app-layout">
    
    <nav class="navbar">
      <div class="nav-container">
        <div class="brand">
          <span class="logo-icon">🦁</span>
          <span class="brand-name">OSOD <span class="highlight">EDUCATION</span></span>
        </div>
        <div class="nav-links">
          <a href="#" class="active">Dashboard</a>
          <a href="#">Courses</a>
          <a href="#">Students</a>
          <a href="#" class="login-btn">Log In</a>
        </div>
      </div>
    </nav>

    <header class="hero">
      <div class="hero-content">
        <h1>Master Your Future</h1>
        <p>The premium platform for modern learning and course management.</p>
      </div>
    </header>

    <main class="main-content">
      <section class="card">
        <div class="card-header">
          <h2>📚 Course Management</h2>
          <p class="card-subtitle">Add, edit, or remove courses from the catalog.</p>
        </div>
        <div class="card-body">
          <CourseList />
        </div>
      </section>

      <section class="card">
        <div class="card-header">
          <h2>👥 Student Directory</h2>
          <p class="card-subtitle">View registered users and system administrators.</p>
        </div>
        <div class="card-body">
          <UserList />
        </div>
      </section>
    </main>

    <footer class="footer">
      <p>&copy; 2025 Osod Education Inc. All rights reserved.</p>
    </footer>

  </div>
</template>

<style>
/* 1. GLOBAL RESET & VARIABLES */
:root {
  --primary: #0f172a;   /* Dark Navy */
  --accent: #f59e0b;    /* Gold */
  --bg-light: #f3f4f6;  /* Light Gray */
  --white: #ffffff;
  --text-dark: #1e293b;
  --text-light: #64748b;
}

body {
  margin: 0;
  padding: 0;
  font-family: 'Inter', 'Segoe UI', sans-serif;
  background-color: var(--bg-light);
  color: var(--text-dark);
}

/* 2. NAVIGATION BAR */
.navbar {
  background-color: var(--white);
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-container {
  max-width: 100%;
  margin: 0 auto;
  padding: 1rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.brand { display: flex; align-items: center; gap: 10px; font-size: 1.5rem; font-weight: 800; color: var(--primary); }
.highlight { color: var(--accent); }

.nav-links { display: flex; gap: 20px; align-items: center; }
.nav-links a { text-decoration: none; color: var(--text-light); font-weight: 500; transition: color 0.3s; }
.nav-links a:hover, .nav-links a.active { color: var(--primary); }
.login-btn { 
  background-color: var(--primary); 
  color: white !important; 
  padding: 8px 20px; 
  border-radius: 6px; 
  font-size: 0.9rem;
}

/* 3. HERO SECTION */
.hero {
  background: linear-gradient(135deg, #0f172a 0%, #1e3a8a 100%);
  color: white;
  padding: 4rem 2rem;
  text-align: center;
  margin-bottom: -40px; /* Overlap effect */
  padding-bottom: 80px;
}
.hero h1 { font-size: 2.5rem; margin-bottom: 10px; }
.hero p { font-size: 1.1rem; opacity: 0.8; }

/* 4. MAIN CONTENT CARDS */
.main-content {
  max-width: 100%;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.card {
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.card-header {
  padding: 20px 30px;
  border-bottom: 1px solid #e2e8f0;
  background-color: #fafafa;
}
.card-header h2 { margin: 0; color: var(--primary); font-size: 1.25rem; }
.card-subtitle { margin: 5px 0 0; color: var(--text-light); font-size: 0.9rem; }

.card-body { padding: 0; } /* Let the components handle their own padding */

/* 5. FOOTER */
.footer {
  text-align: center;
  padding: 40px;
  color: var(--text-light);
  font-size: 0.9rem;
  margin-top: 40px;
}
</style>