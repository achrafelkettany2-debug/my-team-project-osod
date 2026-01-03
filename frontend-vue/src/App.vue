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

// Variables for Popups
const showAssignmentModal = ref(false)
const showVideoModal = ref(false)
const showDiscussionModal = ref(false)
const selectedCourse = ref(null)

const onLoginSuccess = (userData) => {
  currentUser.value = userData
  isLoggedIn.value = true
}

const logout = () => {
  isLoggedIn.value = false
  currentUser.value = null
}

const refreshSchedule = () => {
  if (scheduleRef.value) scheduleRef.value.fetchMyCourses()
}

// Logic to Open Windows
const prepareCourseData = (courseOrEnrollment) => {
  return {
    id: courseOrEnrollment.courseId || courseOrEnrollment.id,
    courseName: courseOrEnrollment.courseName
  }
}
const openHomework = (item) => { selectedCourse.value = prepareCourseData(item); showAssignmentModal.value = true; }
const openVideos = (item) => { selectedCourse.value = prepareCourseData(item); showVideoModal.value = true; }
const openDiscussion = (item) => { selectedCourse.value = prepareCourseData(item); showDiscussionModal.value = true; }
</script>

<template>
  <div class="app-layout">
    
    <div v-if="currentUser">
      <AssignmentManager v-if="showAssignmentModal" :course="selectedCourse" :currentUser="currentUser" @close="showAssignmentModal = false" />
      <VideoManager v-if="showVideoModal" :course="selectedCourse" :currentUser="currentUser" @close="showVideoModal = false" />
      <DiscussionManager v-if="showDiscussionModal" :course="selectedCourse" :currentUser="currentUser" @close="showDiscussionModal = false" />
    </div>

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
        
        <div v-if="currentUser.role === 'admin'">
           <AdminDashboard 
              :currentUser="currentUser" 
              @open-homework="openHomework" 
              @open-videos="openVideos"
              @open-discussion="openDiscussion"
           />
        </div>

        <div v-else class="standard-view">
          
          <section class="card">
            <div class="card-header">
              <h2>📢 Campus News</h2>
              <p class="card-subtitle">Latest updates from your professors.</p>
            </div>
            <div class="card-body">
              <AnnouncementList :currentUser="currentUser" />
            </div>
          </section>

          <section class="card" v-if="currentUser.role === 'student'">
            <div class="card-header">
              <h2>📅 My Class Schedule</h2>
              <p class="card-subtitle">Your active enrollments.</p>
            </div>
            <div class="card-body">
              <MySchedule 
                :currentUser="currentUser" 
                ref="scheduleRef" 
                @open-homework="openHomework" 
                @open-videos="openVideos"
                @open-discussion="openDiscussion"
              />
            </div>
          </section>

          <section class="card">
            <div class="card-header">
              <h2>📚 Course Management</h2>
              <p class="card-subtitle">Browse the catalog of available courses.</p>
            </div>
            <div class="card-body">
              <CourseList 
                :currentUser="currentUser" 
                @course-joined="refreshSchedule"
                @open-homework="openHomework"
                @open-videos="openVideos"
                @open-discussion="openDiscussion"
              />
            </div>
          </section>
        </div>

      </main>

      <footer class="footer">
        <p>&copy; 2026 Osod Education Inc. All rights reserved.</p>
      </footer>
    </div>
  </div>
</template>

<style>
/* Global Styles */
:root { --primary: #0f172a; --accent: #f59e0b; --bg-light: #f3f4f6; --white: #ffffff; --text-dark: #1e293b; --text-light: #64748b; }
body { margin: 0; padding: 0; font-family: 'Inter', 'Segoe UI', sans-serif; background-color: var(--bg-light); color: var(--text-dark); }
.navbar { background-color: var(--white); box-shadow: 0 1px 3px rgba(0,0,0,0.1); position: sticky; top: 0; z-index: 100; }
.nav-container { max-width: 1200px; margin: 0 auto; padding: 1rem 2rem; display: flex; justify-content: space-between; align-items: center; }
.brand { display: flex; align-items: center; gap: 10px; font-size: 1.5rem; font-weight: 800; color: var(--primary); }
.highlight { color: var(--accent); }
.nav-links { display: flex; gap: 20px; align-items: center; }
.welcome-msg { font-weight: 600; color: var(--text-dark); margin-right: 10px;}
.login-btn { background-color: var(--primary); color: white !important; padding: 8px 20px; border-radius: 6px; font-size: 0.9rem; text-decoration: none; cursor: pointer; }
.hero { background: linear-gradient(135deg, #0f172a 0%, #1e3a8a 100%); color: white; padding: 4rem 2rem; text-align: center; margin-bottom: -40px; padding-bottom: 80px; }
.hero h1 { font-size: 2.5rem; margin-bottom: 10px; }
.hero p { font-size: 1.1rem; opacity: 0.8; }
.main-content { max-width: 1000px; margin: 0 auto; padding: 0 20px; display: flex; flex-direction: column; gap: 30px; }

.standard-view { display: flex; flex-direction: column; gap: 30px; } 
.card { background: white; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1); overflow: hidden; }
.card-header { padding: 20px 30px; border-bottom: 1px solid #e2e8f0; background-color: #fafafa; }
.card-header h2 { margin: 0; color: var(--primary); font-size: 1.25rem; }
.card-subtitle { margin: 5px 0 0; color: var(--text-light); font-size: 0.9rem; }
.card-body { padding: 0; }
.footer { text-align: center; padding: 40px; color: var(--text-light); font-size: 0.9rem; margin-top: 40px; }
</style>