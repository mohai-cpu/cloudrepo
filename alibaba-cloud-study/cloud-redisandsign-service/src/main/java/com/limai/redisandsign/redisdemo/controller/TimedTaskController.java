package com.limai.redisandsign.redisdemo.controller;

import com.limai.redisandsign.redisdemo.service.TimedTaskService;
import com.limai.utils.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timedTask")
public class TimedTaskController {
    @Autowired
    private TimedTaskService timedTaskService;
    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public ReturnResult saveRedisDateToDB(){
        ReturnResult result = new ReturnResult();
        try {
            timedTaskService.saveRedisDateToDB();
        } catch (Exception e) {
           result.setCode(2002);
           result.setMsg("保存数据库失败");
           return result;
        }
        result.setCode(2000);
        result.setMsg("保存数据库成功");
        return result;
    }
}
