package com.limai.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.limai.domain.Video;
import com.limai.domain.VideoOrder;
import com.limai.utils.ReturnResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Value("${service.url.video}")
    private String videoServer;
    private Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 测试服务间的调用
     * @param id
     * @return
     */
    @RequestMapping(value = "/getVideoOrder",method = RequestMethod.GET)
    public ReturnResult getVideoOrder(Integer id){
        logger.info("getVideoOrder;请求参数id:{}",id);
        ReturnResult result = new ReturnResult();
        if(id==null){
            result.setCode(2001);
            result.setMsg("请求参数为空");
            return result;
        }
        //获取nacos注册中心的服务
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-video-service");
        if(instances==null || instances.size()==0){
            result.setCode(2002);
            result.setMsg("cloud-video-service服务未注册");
            return result;
        }
        ServiceInstance serviceInstance = instances.get(0);
        ReturnResult returnResult = restTemplate.getForObject("http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/video/testGetVideo?id=" + id, ReturnResult.class);
        //ReturnResult returnResult = restTemplate.getForObject(videoServer+"/video/testGetVideo?id=" + id, ReturnResult.class);
        Object data = returnResult.getData();
        Video video = JSONObject.parseObject(JSONObject.toJSONString(data),Video.class);
        if(video==null){
            result.setCode(2003);
            result.setMsg("请求查询的数据不存在");
            return result;
        }
        logger.info("getVideoOrder;查询数据video:{}", JSONObject.toJSONString(video));
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setCreateTime(video.getCreateTime());
        videoOrder.setVideoId(video.getId());
        videoOrder.setVideoImg(video.getCoverImg());
        result.setCode(2000);
        result.setMsg("查询数据成功");
        result.setData(videoOrder);
        return result;
    }

    /**
     * 测试负载均衡
     * @param id
     * @return
     */
    @RequestMapping(value = "/testLoadBalanced",method = RequestMethod.GET)
    public ReturnResult testLoadBalanced(Integer id, HttpServletRequest request){
        logger.info("getVideoOrder;请求参数id:{}",id);
        ReturnResult result = new ReturnResult();
        if(id==null){
            result.setCode(2001);
            result.setMsg("请求参数为空");
            return result;
        }
        //获取nacos注册中心的服务
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-video-service");
        if(instances==null || instances.size()==0){
            result.setCode(2002);
            result.setMsg("cloud-video-service服务未注册");
            return result;
        }
        ServiceInstance serviceInstance = instances.get(0);
        ReturnResult returnResult = restTemplate.getForObject(videoServer+"/video/testGetVideo?id=" + id, ReturnResult.class);
        Object data = returnResult.getData();
        Video video = JSONObject.parseObject(JSONObject.toJSONString(data),Video.class);
        if(video==null){
            result.setCode(2003);
            result.setMsg("请求查询的数据不存在");
            return result;
        }
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
}
