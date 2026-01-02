package com.myexperiment.controller;

import com.myexperiment.entity.Enrollment;
import com.myexperiment.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    // API: Join a course
    // Expects JSON: { "userId": 1, "courseId": 5 }
    @PostMapping("/join")
    public Map<String, String> joinCourse(@RequestBody Map<String, Integer> payload) {
        Integer userId = payload.get("userId");
        Integer courseId = payload.get("courseId");

        boolean success = enrollmentService.enroll(userId, courseId);

        if (success) {
            return Collections.singletonMap("status", "success");
        } else {
            return Collections.singletonMap("status", "already_enrolled");
        }
    }

    // API: See my courses
    // Example: GET /enrollments/student/1
    @GetMapping("/student/{userId}")
    public List<Enrollment> getMyCourses(@PathVariable Integer userId) {
        return enrollmentService.getMyCourses(userId);
    }
}