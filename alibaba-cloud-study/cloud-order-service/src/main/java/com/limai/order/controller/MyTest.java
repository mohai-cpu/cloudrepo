package com.limai.order.controller;

import com.limai.utils.ReturnResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicInteger;
@RestController
@RequestMapping("/mytest")
@RefreshScope
public class MyTest {
    private Logger logger = LoggerFactory.getLogger(MyTest.class);
    private static AtomicInteger index = new AtomicInteger(0);
    @Value("${server.title}")
    private String title;
    @RequestMapping(value = "test01",method = RequestMethod.GET)
    public ReturnResult test01(){
        ReturnResult result = new ReturnResult();
        int andIncrement = index.getAndIncrement();
        logger.info("test01;andIncrement:{};title:{}",andIncrement,title);
        result.setCode(2000);
        result.setMsg("测试成功");
        result.setData(andIncrement+title);
        return result;
    }
}
