package com.myexperiment.mapper;

import com.myexperiment.entity.User;
import org.apache.ibatis.annotations.Select; 
import java.util.List;

public interface UserMapper {
    // === AUTHENTICATION & MANAGEMENT ===
    // Used for Login (We find user, then check password in Service)
    User findByUsername(String username);
    
    // Used for Register
    void createUser(User user);
    
    // Used for User List
    List<User> findAllUsers();
    
    // Used for Admin Deletion
    void deleteUser(Integer id);

    // === NEW: DIRECTOR STATS ===
    @Select("SELECT COUNT(*) FROM users WHERE role = 'student'")
    int countStudents();

    @Select("SELECT COUNT(*) FROM users WHERE role = 'teacher'")
    int countTeachers();

    @Select("SELECT COUNT(*) FROM courses")
    int countCourses();
    
    @Select("SELECT COUNT(*) FROM enrollments")
    int countEnrollments();
}