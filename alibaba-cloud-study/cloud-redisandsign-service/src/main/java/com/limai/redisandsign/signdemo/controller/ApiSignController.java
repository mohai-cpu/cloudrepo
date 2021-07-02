package com.limai.redisandsign.signdemo.controller;

import com.limai.redisandsign.signdemo.service.ApiSignService;
import com.limai.redisandsign.utils.SignUtil;
import com.limai.utils.ReturnResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * api接口签名加密    主要用于第三方系统调用进行安全认证
 */
@RestController
@RequestMapping("/apisign")
public class ApiSignController {
    @Autowired
    private ApiSignService apiSignService;

    @RequestMapping(value = "/signtest", method = RequestMethod.GET)
    public ReturnResult SignTest(String username, HttpServletRequest request) {
        return apiSignService.signTest(username,request);
    }

    public static void main(String[] args) {
        //double random = Math.random();
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <5 ; i++) {
            sb.append(random.nextInt(10));
        }
        String nonce = sb.toString();
        //43969
        System.out.println(nonce);
        //1621926896648
        String timestamp = String.valueOf(System.currentTimeMillis());
        System.out.println(timestamp);
        Map<String,String> map = new HashMap<>();
        map.put("username","zhangsan");
        map.put("accessKey","123456");
        map.put("timestamp",timestamp);
        map.put("nonce",nonce);
        map.put("sign","11112233");
        map.put("secretKry","mysecret123456");
        Map<String, String> signMap = SignUtil.sign(map);
        //eb280d1b0610a74b03c15566cd84ad54
        System.out.println(signMap.get("sign"));
    }
}
