package com.myexperiment.controller;

import com.myexperiment.entity.Assignment;
import com.myexperiment.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping("/course/{courseId}")
    public List<Assignment> getCourseAssignments(@PathVariable Integer courseId) {
        return assignmentService.getAssignmentsByCourse(courseId);
    }

    @PostMapping
    public Map<String, String> addAssignment(@RequestBody Assignment assignment) {
        assignmentService.addAssignment(assignment);
        return Collections.singletonMap("status", "success");
    }

    // NEW: Delete Endpoint
    @DeleteMapping("/{id}")
    public Map<String, String> deleteAssignment(@PathVariable Integer id) {
        assignmentService.removeAssignment(id);
        return Collections.singletonMap("status", "success");
    }
}