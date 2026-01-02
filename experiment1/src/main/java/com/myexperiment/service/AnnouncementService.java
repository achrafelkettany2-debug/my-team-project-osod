package com.myexperiment.service;

import com.myexperiment.entity.Announcement;
import com.myexperiment.mapper.AnnouncementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementMapper announcementMapper;

    public List<Announcement> getAllAnnouncements() {
        return announcementMapper.findAll();
    }

    public void postAnnouncement(Announcement announcement) {
        announcementMapper.createAnnouncement(announcement);
    }
}