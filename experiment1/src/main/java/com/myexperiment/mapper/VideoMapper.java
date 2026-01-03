package com.myexperiment.mapper;

import com.myexperiment.entity.Video;
import java.util.List;

public interface VideoMapper {
    List<Video> findByCourseId(Integer courseId);
    void createVideo(Video video);
    void deleteVideo(Integer id);
}