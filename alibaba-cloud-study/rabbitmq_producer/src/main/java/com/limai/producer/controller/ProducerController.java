package com.limai.producer.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/producer")
public class ProducerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @RequestMapping("/testSend1")
    public void testSend1(){
        rabbitTemplate.convertAndSend("itcast","qinqing i miss you11");
    }
    @RequestMapping("/testSend2")
    public void testSend2(){
        ArrayList<Object> list = new ArrayList<>();
        JSONObject json1 = new JSONObject();
        json1.put("informationId","1111");
        json1.put("content","非标签");
        json1.put("from","nry_source");
        json1.put("createWay","1");
        json1.put("author","泰康人寿保险");
        json1.put("title" ,"大尺寸面板供需续紧，头部公司业绩持续改善，行业有望迎来景气大年（附股）");
        json1.put("simpleDesc","在经济复苏、大型体育赛事延迟、韩厂产能退出的多重利好影响下，面板价格有望在2021年持续保持上涨。");
        json1.put("classifyDesc", "要闻");
        json1.put("classifyId", "1000");
        json1.put("status","add");
        list.add(json1);
        String ss = JSONObject.toJSONString(list);
        rabbitTemplate.convertAndSend("fanouttest","heliu","哈哈哈哈哈");
    }

    @RequestMapping("/testSend3")
    public void testSend3(){
        rabbitTemplate.convertAndSend("topictest","good.log","topic模式");
    }

    public static void main(String[] args) {
        /*String ss = "10";
        int num = Integer.valueOf(ss);
        StringBuilder builder = new StringBuilder();
        builder.append("nihao").append(Integer.valueOf(ss));
        System.out.println(num);
        System.out.println(builder.toString());
        String s1 = "522222196910141217";
        String s2 = "";*/
        List<String> list1 = new ArrayList<>();
        list1.add("123");
        list1.add("121");
        list1.add("126");
        list1.add("125");
        List<String> list2 = new ArrayList<>();
        list2.add("126");
        list2.add("125");
        list2.add("123");
        list2.add("121");
        boolean b = (list1.size()==list2.size() && list1.containsAll(list2));
        System.out.println(b);
    }
}