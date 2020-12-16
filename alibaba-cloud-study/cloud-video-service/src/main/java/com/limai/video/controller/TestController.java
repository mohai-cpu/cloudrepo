package com.limai.video.controller;

import com.alibaba.fastjson.JSONObject;
import com.limai.domain.Video;
import com.limai.utils.ReturnResult;
import com.limai.video.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("video")
public class TestController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private VideoService videoService;
    /**
     * 根据id查询视频信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/testGetVideo",method = RequestMethod.GET)
    public ReturnResult testGetVideo(Integer id, HttpServletRequest request){
        logger.info("testGetVideo;参数id:{}",id);
        ReturnResult result = new ReturnResult();
        if(id==null){
            result.setCode(2001);
            result.setMsg("参数id为空");
            return result;
        }
        Video video = videoService.findById(id);
        logger.info("testGetVideo;参数id:{};video:{}",id, JSONObject.toJSONString(video));
        if(video==null){
            result.setCode(2002);
            result.setMsg("查询的数据不存在");
            return result;
        }
        video.setServiceInfo(request.getServerName()+":"+request.getServerPort());
        result.setCode(2000);
        result.setMsg("查询数据成功");
        result.setData(video);
        return result;
    }

}
