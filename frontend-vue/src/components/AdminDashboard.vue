<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import UserList from './UserList.vue'
import CourseList from './CourseList.vue' // Import CourseList for supervision

// We need to pass the current user to CourseList
const props = defineProps(['currentUser'])
const emit = defineEmits(['open-homework', 'open-videos', 'open-discussion'])

const stats = ref({ students: 0, teachers: 0, courses: 0, enrollments: 0 })

const fetchStats = async () => {
  try {
    const response = await axios.get('http://localhost:8080/experiment1/stats/dashboard')
    stats.value = response.data
  } catch (err) {
    console.error("Error fetching stats", err)
  }
}

onMounted(() => {
  fetchStats()
})
</script>

<template>
  <div class="admin-wrapper">
    <div class="dashboard-header">
      <h2>🏛️ University Director Dashboard</h2>
      <p>System Overview & Global Management</p>
    </div>

    <h3 class="section-title">📊 System Statistics</h3>
    <div class="stats-grid">
      <div class="card stat-card blue">
        <h3>👨‍🎓 Students</h3>
        <div class="number">{{ stats.students }}</div>
      </div>
      <div class="card stat-card orange">
        <h3>👨‍🏫 Teachers</h3>
        <div class="number">{{ stats.teachers }}</div>
      </div>
      <div class="card stat-card green">
        <h3>📚 Active Courses</h3>
        <div class="number">{{ stats.courses }}</div>
      </div>
      <div class="card stat-card purple">
        <h3>📝 Enrollments</h3>
        <div class="number">{{ stats.enrollments }}</div>
      </div>
    </div>

    <section class="admin-card">
      <div class="card-header">
        <h3>👁️ Course Supervision</h3>
        <p>Monitor class activities, chats, and assignments.</p>
      </div>
      <div class="card-body">
        <CourseList 
          :currentUser="currentUser" 
          @open-homework="$emit('open-homework', $event)"
          @open-videos="$emit('open-videos', $event)"
          @open-discussion="$emit('open-discussion', $event)"
        />
      </div>
    </section>

    <section class="admin-card">
      <div class="card-header">
        <h3>👥 User Directory</h3>
        <p>Manage registered accounts (Delete Users).</p>
      </div>
      <div class="card-body">
        <UserList />
      </div>
    </section>

  </div>
</template>

<style scoped>
/* Fix for the blocked view: Add padding to push it down from the Hero banner */
.admin-wrapper { 
  padding: 40px 0; 
  max-width: 100%;
}

.dashboard-header { 
  background: white; 
  padding: 30px; 
  border-radius: 12px; 
  margin-bottom: 30px; 
  box-shadow: 0 4px 6px rgba(0,0,0,0.05);
}
.dashboard-header h2 { margin: 0; color: #0f172a; font-size: 1.8rem; }
.dashboard-header p { color: #64748b; margin: 5px 0 0; }

.section-title { margin-bottom: 15px; color: #334155; font-size: 1.2rem; border-left: 4px solid #3b82f6; padding-left: 10px; }

/* Stats Grid */
.stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; margin-bottom: 40px; }
.stat-card { padding: 25px; border-radius: 12px; color: white; text-align: center; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }
.stat-card h3 { margin: 0; font-size: 1rem; opacity: 0.9; }
.stat-card .number { font-size: 2.5rem; font-weight: 800; margin-top: 10px; }

/* Colors */
.blue { background: linear-gradient(135deg, #3b82f6, #2563eb); }
.orange { background: linear-gradient(135deg, #f59e0b, #d97706); }
.green { background: linear-gradient(135deg, #10b981, #059669); }
.purple { background: linear-gradient(135deg, #8b5cf6, #7c3aed); }

/* Admin Sections */
.admin-card { background: white; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.1); margin-bottom: 30px; overflow: hidden; }
.card-header { padding: 20px 30px; border-bottom: 1px solid #e2e8f0; background-color: #fafafa; }
.card-header h3 { margin: 0; color: #0f172a; }
.card-header p { margin: 5px 0 0; color: #64748b; font-size: 0.9rem; }
.card-body { padding: 0; }
</style>