package com.myexperiment.mapper;

import com.myexperiment.entity.Announcement;
import java.util.List;

public interface AnnouncementMapper {
    List<Announcement> findAll();
    void createAnnouncement(Announcement announcement);
}