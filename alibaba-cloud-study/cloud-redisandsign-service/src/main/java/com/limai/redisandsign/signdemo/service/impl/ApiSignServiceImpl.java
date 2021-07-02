package com.limai.redisandsign.signdemo.service.impl;

import com.limai.redisandsign.signdemo.service.ApiSignService;
import com.limai.redisandsign.utils.SignUtil;
import com.limai.utils.ReturnResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ApiSignServiceImpl implements ApiSignService {
    private Logger logger = LoggerFactory.getLogger(ApiSignServiceImpl.class);
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    private static final String secretKry = "mysecret123456";
    @Override
    public ReturnResult signTest(String username, HttpServletRequest request) {
        logger.info("SignTest;username:{}",username);
        ReturnResult result = new ReturnResult();
        String accessKey = request.getHeader("accessKey");
        String timestamp = request.getHeader("timestamp");
        String nonce = request.getHeader("nonce");
        String sign = request.getHeader("sign");
        if (StringUtils.isNotBlank(checkParams(username, accessKey, timestamp, nonce, sign))) {
            result.setCode(2001);
            result.setMsg(checkParams(username, accessKey, timestamp, nonce, sign));
            return result;
        }
        //时间判断
        /*long time = 60*1000L;  //单位毫秒
        long now = System.currentTimeMillis();
        long timeDiff = now - Long.valueOf(timestamp);
        if(timeDiff>time){
            result.setCode(2002);
            result.setMsg("请求发起时间超过服务器限制时间");
            return result;
        }
        //记录这次请求设置过期时间
        if(redisTemplate.opsForHash().putIfAbsent("third_key",accessKey+nonce,nonce)){
            redisTemplate.expire("third_key",60, TimeUnit.SECONDS);
        }else {
            result.setCode(2003);
            result.setMsg("请不要发送重复的请求");
            return result;
        }*/
        //将数据存入map
        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("accessKey",accessKey);
        map.put("timestamp",timestamp);
        map.put("nonce",nonce);
        map.put("sign",sign);
        map.put("secretKry",secretKry);
        //签名处理  获取生成后的签名map
        Map<String, String> signMap = SignUtil.sign(map);
        String mysign = signMap.get("sign");
        logger.info("signTest;新生成的mysign:{}",mysign);
        // 验证签名
        if (!mysign.equals(sign)) {
            result.setCode(2004);
            result.setMsg("签名信息错误");
            return result;
        }
        result.setCode(2000);
        result.setMsg("签名验证成功");
        return result;
    }
    private String checkParams(String username,String accessKey,String timestamp,String nonce,String sign){
        StringBuilder sb = new StringBuilder();
        if(StringUtils.isBlank(username)){
            sb.append("username is null").append(",");
        }
        if(StringUtils.isBlank(accessKey)){
            sb.append("accessKey is null").append(",");
        }
        if(StringUtils.isBlank(timestamp)){
            sb.append("timestamp is null").append(",");
        }
        if(StringUtils.isBlank(nonce)){
            sb.append("nonce is null").append(",");
        }
        if(StringUtils.isBlank(sign)){
            sb.append("sign is null").append("!");
        }
        return sb.toString();
    }
}
