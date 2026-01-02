package com.myexperiment.service;

import com.myexperiment.entity.Enrollment;
import com.myexperiment.mapper.EnrollmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentMapper enrollmentMapper;

    // Logic: Student joins a course
    public boolean enroll(Integer userId, Integer courseId) {
        // 1. Check if already enrolled
        int count = enrollmentMapper.checkEnrollment(userId, courseId);
        
        if (count > 0) {
            return false; // Already joined! Fail.
        }

        // 2. If not, create the enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setUserId(userId);
        enrollment.setCourseId(courseId);
        
        enrollmentMapper.insertEnrollment(enrollment);
        return true; // Success
    }

    // Logic: Get my schedule
    public List<Enrollment> getMyCourses(Integer userId) {
        return enrollmentMapper.getStudentEnrollments(userId);
    }
}