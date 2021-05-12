package com.limai.producer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
@RabbitListener(queues = "test01")
public class DirectReceive4 {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @RabbitHandler
    public void getMsg(String msg){
        System.out.println(msg+"test01");
    }
}
