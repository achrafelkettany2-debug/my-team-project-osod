package com.myexperiment.controller;

import com.myexperiment.entity.Discussion;
import com.myexperiment.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/discussions")
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

    // GET all messages for a course
    @GetMapping("/course/{courseId}")
    public List<Discussion> getMessages(@PathVariable Integer courseId) {
        return discussionService.getCourseMessages(courseId);
    }

    // POST a new message
    @PostMapping
    public Map<String, String> sendMessage(@RequestBody Discussion discussion) {
        discussionService.postMessage(discussion);
        return Collections.singletonMap("status", "success");
    }
}