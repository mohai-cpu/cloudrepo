package com.limai.producer.listener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.limai.producer.dto.EnterpriseInformationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;


//@Component
@RabbitListener(queues = "dahai")
public class DirectReceive2 {
    private Logger logger = LoggerFactory.getLogger(DirectReceive2.class);
    @RabbitHandler
    public void getMsg(String msg){
        logger.info("getMsg;msg:{}",msg);
        try{
            JSONArray objects = JSONArray.parseArray(msg);
            List<EnterpriseInformationDto> enterpriseInformationDtos = JSONArray.parseArray(msg, EnterpriseInformationDto.class);
            logger.info("getMsg;objects:{}", JSONObject.toJSONString(objects));
            logger.info("getMsg;enterpriseInformationDtos:{}", JSONObject.toJSONString(enterpriseInformationDtos));
        }catch (Exception e){
            logger.info("getMsg;e:{}",e);
        }
    }
}
