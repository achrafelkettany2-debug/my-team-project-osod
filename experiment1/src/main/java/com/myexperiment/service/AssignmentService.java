package com.myexperiment.service;

import com.myexperiment.entity.Assignment;
import com.myexperiment.mapper.AssignmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentMapper assignmentMapper;

    public List<Assignment> getAssignmentsByCourse(Integer courseId) {
        return assignmentMapper.findByCourseId(courseId);
    }

    public void addAssignment(Assignment assignment) {
        assignmentMapper.createAssignment(assignment);
    }

    // NEW
    public void removeAssignment(Integer id) {
        assignmentMapper.deleteAssignment(id);
    }
}