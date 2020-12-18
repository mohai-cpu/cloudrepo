package com.limai.order.service.fallback;

import com.limai.domain.Video;
import com.limai.order.service.VideoService;
import com.limai.utils.ReturnResult;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceFallback implements VideoService {
    @Override
    public ReturnResult findById(Integer videoid) {
        ReturnResult result = new ReturnResult();
        Video video = new Video();
        video.setTitle("fallback的兜底视频数据");
        result.setMsg("video服务出问题啦,请稍后重试");
        result.setCode(2008);
        return result;
    }

    @Override
    public void saveVideo(Video video) {
        ReturnResult result = new ReturnResult();
        result.setMsg("video服务出问题啦,请稍后重试");
        result.setCode(2008);
        return;
    }
}
