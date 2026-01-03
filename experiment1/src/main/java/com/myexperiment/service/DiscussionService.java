package com.myexperiment.service;

import com.myexperiment.entity.Discussion;
import com.myexperiment.mapper.DiscussionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DiscussionService {
    @Autowired
    private DiscussionMapper discussionMapper;

    public List<Discussion> getCourseMessages(Integer courseId) {
        return discussionMapper.findByCourseId(courseId);
    }

    public void postMessage(Discussion discussion) {
        discussionMapper.createMessage(discussion);
    }
}