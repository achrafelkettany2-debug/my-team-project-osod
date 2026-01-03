package com.myexperiment.service;

import com.myexperiment.entity.Video;
import com.myexperiment.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VideoService {
    @Autowired
    private VideoMapper videoMapper;

    public List<Video> getVideosByCourse(Integer courseId) {
        return videoMapper.findByCourseId(courseId);
    }

    public void addVideo(Video video) {
        videoMapper.createVideo(video);
    }

    public void removeVideo(Integer id) {
        videoMapper.deleteVideo(id);
    }
}