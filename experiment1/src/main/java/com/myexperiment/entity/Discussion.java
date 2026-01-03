package com.myexperiment.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Discussion {
    private Integer id;
    private Integer courseId;
    private String username;
    private String content;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createdAt;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}