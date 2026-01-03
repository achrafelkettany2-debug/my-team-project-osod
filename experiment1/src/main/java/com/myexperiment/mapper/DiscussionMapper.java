package com.myexperiment.mapper;

import com.myexperiment.entity.Discussion;
import java.util.List;

public interface DiscussionMapper {
    // Get chat history for a course
    List<Discussion> findByCourseId(Integer courseId);
    
    // Post a new message
    void createMessage(Discussion discussion);
}