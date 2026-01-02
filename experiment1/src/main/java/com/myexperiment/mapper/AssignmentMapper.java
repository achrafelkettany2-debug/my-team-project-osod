package com.myexperiment.mapper;

import com.myexperiment.entity.Assignment;
import java.util.List;

public interface AssignmentMapper {
    // Find all assignments for a specific course
    List<Assignment> findByCourseId(Integer courseId);
    
    // Teacher creates a new assignment
    void createAssignment(Assignment assignment);
    void deleteAssignment(Integer id);
}