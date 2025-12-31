package com.myexperiment.entity;

public class Course {
    private Integer id;
    private String courseName;
    private String teacherName;
    private String description;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}