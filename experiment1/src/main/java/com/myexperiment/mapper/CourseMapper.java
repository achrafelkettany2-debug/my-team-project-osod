package com.myexperiment.mapper;

import com.myexperiment.entity.Course;
import java.util.List;

public interface CourseMapper {
    List<Course> findAllCourses();
    void addCourse(Course course);
    void deleteCourse(Integer id);
}