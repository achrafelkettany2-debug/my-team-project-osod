package com.myexperiment.controller;

import com.myexperiment.entity.Course;
import com.myexperiment.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // API to get all courses
    @GetMapping
    public List<Course> listCourses() {
        return courseService.getAllCourses();
    }

    // API to add a new course
    @PostMapping
    public String addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
        return "Course added successfully!";
    }
}