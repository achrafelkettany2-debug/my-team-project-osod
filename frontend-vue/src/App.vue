<script setup>
import UserList from './components/UserList.vue'
import CourseList from './components/CourseList.vue'
</script>

<template>
  <main>
    <h1>Team Project: Online Education Platform</h1>
    
    <CourseList />
    
    <hr class="separator" />

    <UserList />
  </main>
</template>

<style scoped>
h1 { color: #2c3e50; text-align: center; margin-top: 20px; font-family: sans-serif; }
.separator { margin: 40px 0; border: none; border-top: 2px dashed #ccc; }
</style>