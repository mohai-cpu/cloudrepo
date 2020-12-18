package com.limai.video.service;

import com.limai.domain.Video;

public interface VideoService {
    /**
     * 根据id获取视频信息
     * @param videoid
     * @return
     */
    Video findById(Integer videoid);
}
