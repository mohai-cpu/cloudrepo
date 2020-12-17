package com.limai.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.limai.domain.Video;
import com.limai.domain.VideoOrder;
import com.limai.order.service.VideoService;
import com.limai.utils.ReturnResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("fegin")
public class FeginController {
    private Logger logger = LoggerFactory.getLogger(FeginController.class);
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private VideoService videoService;
    /**
     * 测试负fegin载均衡
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/testFegin", method = RequestMethod.GET)
    public ReturnResult testFegin(Integer id, HttpServletRequest request) {
        logger.info("testFegin接口进入");
        logger.info("getVideoOrder;请求参数id:{}", id);
        ReturnResult result = new ReturnResult();
        if (id == null) {
            result.setCode(2001);
            result.setMsg("请求参数为空");
            return result;
        }
       /* //获取nacos注册中心的服务
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-video-service");
        if (instances == null || instances.size() == 0) {
            result.setCode(2002);
            result.setMsg("cloud-video-service服务未注册");
            return result;
        }
        ServiceInstance serviceInstance = instances.get(0);*/
        ReturnResult returnResult = videoService.findById(id);
        Object data = returnResult.getData();
        Video video = JSONObject.parseObject(JSONObject.toJSONString(data), Video.class);
       /* if (video == null) {
            result.setCode(2003);
            result.setMsg("请求查询的数据不存在");
            return result;
        }*/
        logger.info("getVideoOrder;查询数据video:{}", JSONObject.toJSONString(video));
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setCreateTime(video.getCreateTime());
        videoOrder.setVideoId(video.getId());
        videoOrder.setVideoImg(video.getCoverImg());
        videoOrder.setServiceInfo(video.getServiceInfo());
        result.setCode(2000);
        result.setMsg("查询数据成功");
        result.setData(videoOrder);
        return result;
    }

    /**
     * 测试fegin的post请求
     *
     * @return
     */
    @RequestMapping(value = "/testPost", method = RequestMethod.POST)
    public ReturnResult testPost(@RequestBody Video video) {
        ReturnResult result = new ReturnResult();
        videoService.saveVideo(video);
        result.setCode(2000);
        result.setMsg("测试post请求成功");
        result.setData(video);
        return result;
    }
}
