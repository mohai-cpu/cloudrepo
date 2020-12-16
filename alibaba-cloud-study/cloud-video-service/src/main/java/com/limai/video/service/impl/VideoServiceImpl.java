package com.limai.video.service.impl;
import com.limai.domain.Video;
import com.limai.video.dao.VideoMapper;
import com.limai.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired(required = true)
    private VideoMapper videoMapper;

    @Override
    public Video findById(int id) {
        return videoMapper.findById(id);
    }
}
