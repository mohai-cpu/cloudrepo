package com.limai.producer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
@RabbitListener(queues = "heliu")
public class DirectReceive3 {
    @RabbitHandler
    public void getMsg(String msg){
        System.out.println(msg+"heliu");
    }
}
