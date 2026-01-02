package com.myexperiment.mapper;

import com.myexperiment.entity.Enrollment;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnrollmentMapper {
    // Student joins a course
    void insertEnrollment(Enrollment enrollment);

    // Check if student is already in the class (Prevent double joining)
    int checkEnrollment(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

    // Get list of courses a specific student is taking
    List<Enrollment> getStudentEnrollments(Integer userId);
}