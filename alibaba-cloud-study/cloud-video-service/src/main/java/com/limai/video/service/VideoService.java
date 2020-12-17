package com.limai.video.service;

import com.limai.domain.Video;

public interface VideoService {
    /**
     * 根据id获取视频信息
     * @param id
     * @return
     */
    Video findById(Integer id);
}
