package com.limai.order.service;

import com.limai.utils.ReturnResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cloud-video-service")
public interface VideoService {
    @GetMapping("video/testGetVideo")
    ReturnResult findById(@RequestParam("id") Integer id);
}
