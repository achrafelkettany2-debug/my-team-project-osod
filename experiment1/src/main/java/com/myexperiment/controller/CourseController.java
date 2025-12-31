package com.myexperiment.controller;

import com.myexperiment.entity.Course;
import com.myexperiment.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Collections;

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

    // API to add a new course - NOW RETURNS JSON!
    @PostMapping
    public Map<String, String> addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
        // This creates a proper JSON response: { "status": "success" }
        return Collections.singletonMap("status", "success");
    }
    // API to delete a course
@DeleteMapping("/{id}")
public Map<String, String> deleteCourse(@PathVariable Integer id) {
    courseService.deleteCourse(id);
    return Collections.singletonMap("status", "deleted");
}
}