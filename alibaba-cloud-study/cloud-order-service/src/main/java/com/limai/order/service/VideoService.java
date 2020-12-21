package com.limai.order.service;

import com.limai.domain.Video;
import com.limai.order.service.fallback.VideoServiceFallback;
import com.limai.utils.ReturnResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cloud-video-service",fallback = VideoServiceFallback.class)
public interface VideoService {
    @GetMapping("/video/testGetVideo")
    Video findById(@RequestParam("videoid") Integer videoid);
    @RequestMapping(value = "video/saveVideo",method = RequestMethod.POST)
    void saveVideo(@RequestBody Video video);
}
