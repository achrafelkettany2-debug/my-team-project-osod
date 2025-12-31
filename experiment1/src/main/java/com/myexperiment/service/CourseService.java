package com.myexperiment.service;

import com.myexperiment.entity.Course;
import com.myexperiment.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    public List<Course> getAllCourses() {
        return courseMapper.findAllCourses();
    }

    public void addCourse(Course course) {
        courseMapper.addCourse(course);
    }
    public void deleteCourse(Integer id) {
    courseMapper.deleteCourse(id);
    }
}