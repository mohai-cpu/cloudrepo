package com.limai.order.controller;

import com.limai.utils.ReturnResult;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
@RestController
@RequestMapping("/mytest")
@RefreshScope
public class MyTest {
    private static AtomicInteger index = new AtomicInteger(0);
    @Value("${server.title}")
    private String title;
    @RequestMapping(value = "test01",method = RequestMethod.GET)
    public ReturnResult test01(){
        ReturnResult result = new ReturnResult();
        int andIncrement = index.getAndIncrement();
        result.setCode(2000);
        result.setMsg("测试成功");
        result.setData(andIncrement+title);
        return result;
    }

    public static void main(String[] args) {
        List<Integer> lists = new ArrayList<>();
        lists.add(1);
        lists.add(2);
        lists.add(3);
        System.out.println(lists.get(1));
    }
}
