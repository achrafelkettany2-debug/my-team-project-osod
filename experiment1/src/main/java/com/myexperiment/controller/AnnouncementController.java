package com.myexperiment.controller;

import com.myexperiment.entity.Announcement;
import com.myexperiment.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping
    public List<Announcement> getNews() {
        return announcementService.getAllAnnouncements();
    }

    @PostMapping
    public Map<String, String> postNews(@RequestBody Announcement news) {
        announcementService.postAnnouncement(news);
        return Collections.singletonMap("status", "success");
    }
}