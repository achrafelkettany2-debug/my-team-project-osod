<script setup>
import { ref } from 'vue'
import CourseList from './components/CourseList.vue'
import Login from './components/Login.vue'
import MySchedule from './components/MySchedule.vue'
import AnnouncementList from './components/AnnouncementList.vue'
import AssignmentManager from './components/AssignmentManager.vue'
import VideoManager from './components/VideoManager.vue' 
import DiscussionManager from './components/DiscussionManager.vue'
import AdminDashboard from './components/AdminDashboard.vue'

const isLoggedIn = ref(false)
const currentUser = ref(null)
const scheduleRef = ref(null)

const showAssignmentModal = ref(false)
const showVideoModal = ref(false)
const showDiscussionModal = ref(false)
const selectedCourse = ref(null)

const onLoginSuccess = (userData) => { currentUser.value = userData; isLoggedIn.value = true; }
const logout = () => { isLoggedIn.value = false; currentUser.value = null; }

const refreshSchedule = () => { if (scheduleRef.value) scheduleRef.value.fetchMyCourses() }

const prepareCourseData = (item) => ({ id: item.courseId || item.id, courseName: item.courseName })
const openHomework = (item) => { selectedCourse.value = prepareCourseData(item); showAssignmentModal.value = true; }
const openVideos = (item) => { selectedCourse.value = prepareCourseData(item); showVideoModal.value = true; }
const openDiscussion = (item) => { selectedCourse.value = prepareCourseData(item); showDiscussionModal.value = true; }
</script>

<template>
  <div class="app-layout">
    
    <div v-if="currentUser" class="modal-wrapper">
      <AssignmentManager v-if="showAssignmentModal" :course="selectedCourse" :currentUser="currentUser" @close="showAssignmentModal = false" />
      <VideoManager v-if="showVideoModal" :course="selectedCourse" :currentUser="currentUser" @close="showVideoModal = false" />
      <DiscussionManager v-if="showDiscussionModal" :course="selectedCourse" :currentUser="currentUser" @close="showDiscussionModal = false" />
    </div>

    <div v-if="!isLoggedIn">
      <Login @login-success="onLoginSuccess" />
    </div>

    <div v-else class="dashboard-wrapper">
      
      <nav class="navbar">
        <div class="nav-container">
          <div class="brand">
            <div class="logo-circle">🦁</div>
            <div class="brand-text">OSOD <span class="brand-highlight">ACADEMY</span></div>
          </div>
          <div class="nav-right">
            <div class="role-badge" :class="currentUser.role">{{ currentUser.role }}</div>
            <div class="user-name">{{ currentUser.username }}</div>
            <button @click="logout" class="logout-icon-btn" title="Sign Out">⏻</button>
          </div>
        </div>
      </nav>

      <header class="hero-section">
        <div class="hero-content">
          <h1 class="hero-title">Welcome back, {{ currentUser.username }}</h1>
          <p class="hero-subtitle">
            <span v-if="currentUser.role === 'student'">Track your progress and stay ahead.</span>
            <span v-if="currentUser.role === 'teacher'">Manage your classroom effectively.</span>
            <span v-if="currentUser.role === 'admin'">System Overview & Controls.</span>
          </p>
        </div>
      </header>

      <main class="main-container">
        <div v-if="currentUser.role === 'admin'">
           <AdminDashboard :currentUser="currentUser" @open-homework="openHomework" @open-videos="openVideos" @open-discussion="openDiscussion" />
        </div>

        <div v-else class="grid-layout">
          <div class="main-column">
             <section class="premium-card hover-lift">
              <div class="card-header-gradient blue-gradient">
                <h2>📚 Course Catalog</h2>
                <span class="header-pill">Active Semesters</span>
              </div>
              <div class="card-body">
                <CourseList :currentUser="currentUser" @course-joined="refreshSchedule" @open-homework="openHomework" @open-videos="openVideos" @open-discussion="openDiscussion" />
              </div>
            </section>
          </div>

          <div class="sidebar-column">
            <section class="premium-card sidebar-card hover-lift" v-if="currentUser.role === 'student'">
              <div class="card-header-gradient purple-gradient">
                <h3>📅 My Classes</h3>
              </div>
              <div class="card-body">
                <MySchedule :currentUser="currentUser" ref="scheduleRef" @open-homework="openHomework" @open-videos="openVideos" @open-discussion="openDiscussion" />
              </div>
            </section>

            <section class="premium-card sidebar-card hover-lift">
              <div class="card-header-gradient orange-gradient">
                <h3>📢 Campus News</h3>
              </div>
              <div class="card-body">
                <AnnouncementList :currentUser="currentUser" />
              </div>
            </section>
          </div>
        </div>
      </main>

      <footer class="app-footer">
        <p>&copy; 2026 Osod Education Inc. <span class="sep">•</span> Excellence in Learning</p>
      </footer>
    </div>
  </div>
</template>

<style>
/* === GLOBAL THEME === */
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;800&display=swap');

body {
  margin: 0;
  font-family: 'Inter', sans-serif;
  background: #0f172a; 
  background-image: radial-gradient(at top center, #1e293b 0%, #0f172a 80%);
  background-attachment: fixed;
  color: #f8fafc;
  min-height: 100vh;
}

/* Fix Visible Text in Modals */
.modal-content, .modal-content * { color: #1e293b !important; }

/* Navbar */
.navbar { background: rgba(15, 23, 42, 0.85); border-bottom: 1px solid rgba(255,255,255,0.05); height: 70px; display: flex; align-items: center; position: sticky; top: 0; z-index: 100; backdrop-filter: blur(12px); }
.nav-container { width: 100%; max-width: 1280px; margin: 0 auto; padding: 0 24px; display: flex; justify-content: space-between; align-items: center; }

.brand { display: flex; align-items: center; gap: 12px; }
.logo-circle { width: 40px; height: 40px; background: linear-gradient(135deg, #3b82f6, #2563eb); color: white; border-radius: 12px; display: flex; align-items: center; justify-content: center; font-size: 1.5rem; box-shadow: 0 0 15px rgba(59, 130, 246, 0.4); }
.brand-text { font-weight: 800; font-size: 1.2rem; letter-spacing: -0.5px; color: white; }
.brand-highlight { color: #3b82f6; font-weight: 300; }

.nav-right { display: flex; align-items: center; gap: 16px; }
.user-name { font-weight: 600; font-size: 0.95rem; color: #e2e8f0; }
.role-badge { padding: 4px 10px; border-radius: 6px; font-size: 0.7rem; font-weight: bold; text-transform: uppercase; letter-spacing: 0.5px; }
.role-badge.student { background: rgba(59, 130, 246, 0.15); color: #60a5fa; border: 1px solid rgba(59, 130, 246, 0.3); }
.role-badge.teacher { background: rgba(245, 158, 11, 0.15); color: #fbbf24; border: 1px solid rgba(245, 158, 11, 0.3); }
.role-badge.admin { background: rgba(124, 58, 237, 0.15); color: #a78bfa; border: 1px solid rgba(124, 58, 237, 0.3); }

.logout-icon-btn { background: rgba(255,255,255,0.05); color: #ef4444; width: 36px; height: 36px; border-radius: 50%; border: none; font-size: 1rem; display: flex; align-items: center; justify-content: center; transition: all 0.2s; }
.logout-icon-btn:hover { background: rgba(239, 68, 68, 0.2); transform: scale(1.1); }

/* Hero */
.hero-section { padding: 40px 0 20px; text-align: center; }
.hero-title { 
  font-size: 2.2rem; 
  margin: 0; 
  font-weight: 800; 
  letter-spacing: -1px; 
  background: linear-gradient(to right, #fff, #94a3b8); 
  
  /* ADD THIS LINE TO FIX THE WARNING: */
  background-clip: text; 
  
  /* Keep these lines: */
  -webkit-background-clip: text; 
  -webkit-text-fill-color: transparent; 
}.hero-subtitle { color: #94a3b8; margin-top: 8px; font-size: 1.1rem; font-weight: 300; }

/* Layout & Cards */
.main-container { max-width: 1280px; margin: 0 auto; padding: 20px 24px; }
.grid-layout { display: grid; grid-template-columns: 2.5fr 1fr; gap: 30px; align-items: start; }

.premium-card {
  background: #ffffff; border-radius: 16px; overflow: hidden;
  /* Soft Premium Shadow */
  box-shadow: 0 4px 20px rgba(0,0,0,0.2); 
  border: 1px solid rgba(255,255,255,0.05); color: #1e293b;
  /* Hover Transition for the Card */
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

/* === THE ANIMATION YOU ASKED FOR === */
.hover-lift:hover {
  transform: translateY(-5px);
  box-shadow: 0 15px 30px rgba(0,0,0,0.3);
}

.card-header-gradient { padding: 20px 25px; display: flex; justify-content: space-between; align-items: center; color: white !important; }
.card-header-gradient h2, .card-header-gradient h3 { margin: 0; font-size: 1.1rem; font-weight: 700; letter-spacing: 0.5px; color: white !important; }

/* Gradients */
.blue-gradient { background: linear-gradient(135deg, #2563eb 0%, #1e40af 100%); }
.purple-gradient { background: linear-gradient(135deg, #7c3aed 0%, #5b21b6 100%); }
.orange-gradient { background: linear-gradient(135deg, #f59e0b 0%, #b45309 100%); }

.header-pill { background: rgba(255,255,255,0.2); padding: 4px 12px; border-radius: 20px; font-size: 0.75rem; font-weight: bold; color: white !important;}
.card-body { padding: 0; }

.app-footer { text-align: center; padding: 60px 0 40px; color: #64748b; font-size: 0.9rem; }
.sep { margin: 0 8px; opacity: 0.3; }

@media (max-width: 900px) { .grid-layout { grid-template-columns: 1fr; } }

/* Global Tables with Hover Rows */
table { width: 100%; border-collapse: collapse; }
thead th { background: #f8fafc; color: #64748b !important; font-size: 0.75rem; text-transform: uppercase; padding: 12px 20px; text-align: left; }
tbody tr { border-bottom: 1px solid #f1f5f9; transition: transform 0.2s, background-color 0.2s; }

/* === ROW ANIMATION === */
tbody tr:hover { 
  background: #f8fafc; 
  transform: scale(1.01); /* Subtle Pop */
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
  position: relative; z-index: 10;
}
tbody td { padding: 15px 20px; color: #334155 !important; font-size: 0.95rem; }
button { cursor: pointer; }
</style>