package com.limai.producer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "itcast")
public class DirectReceive {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @RabbitHandler
    public void getMsg(String msg){
        System.out.println(msg);
       /* for(int i=1;i<5;i++){
            rabbitTemplate.convertAndSend("itacst","ceshi");
        }*/
    }
}
