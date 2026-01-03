package com.myexperiment.controller;

import com.myexperiment.entity.Video;
import com.myexperiment.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/course/{courseId}")
    public List<Video> getCourseVideos(@PathVariable Integer courseId) {
        return videoService.getVideosByCourse(courseId);
    }

    @PostMapping
    public Map<String, String> addVideo(@RequestBody Video video) {
        videoService.addVideo(video);
        return Collections.singletonMap("status", "success");
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteVideo(@PathVariable Integer id) {
        videoService.removeVideo(id);
        return Collections.singletonMap("status", "success");
    }
}