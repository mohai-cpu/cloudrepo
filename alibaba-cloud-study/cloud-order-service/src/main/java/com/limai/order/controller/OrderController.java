package com.limai.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.limai.domain.Video;
import com.limai.domain.VideoOrder;
import com.limai.utils.ReturnResult;
import jdk.nashorn.internal.ir.CallNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("order")
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping(value = "/getVideoOrder",method = RequestMethod.GET)
    public ReturnResult getVideoOrder(Integer id){
        logger.info("getVideoOrder;请求参数id:{}",id);
        ReturnResult result = new ReturnResult();
        if(id==null){
            result.setCode(2001);
            result.setMsg("请求参数为空");
            return result;
        }
        ReturnResult returnResult = restTemplate.getForObject("http://localhost:8083/video/testGetVideo?id=" + id, ReturnResult.class);
        Object data = returnResult.getData();
        Video video = JSONObject.parseObject(JSONObject.toJSONString(data),Video.class);
        if(video==null){
            result.setCode(2002);
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
}
