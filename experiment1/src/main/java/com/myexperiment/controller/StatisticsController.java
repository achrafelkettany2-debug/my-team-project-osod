package com.myexperiment.controller;

import com.myexperiment.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/stats")
public class StatisticsController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/dashboard")
    public Map<String, Integer> getDashboardStats() {
        Map<String, Integer> stats = new HashMap<String, Integer>();
        stats.put("students", userMapper.countStudents());
        stats.put("teachers", userMapper.countTeachers());
        stats.put("courses", userMapper.countCourses());
        stats.put("enrollments", userMapper.countEnrollments());
        return stats;
    }
}