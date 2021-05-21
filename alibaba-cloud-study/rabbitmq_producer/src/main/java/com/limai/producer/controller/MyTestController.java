package com.limai.producer.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/mytest")
public class MyTestController {
    @RequestMapping("/test01")
    public String test01(HttpServletResponse response){
        Cookie cookie = new Cookie("zhangsan", "dhsahdhsa");
        //cookie.setDomain(".zhangsan.com");
        cookie.setPath("/");
        response.addCookie(cookie);
        return "测试下";
    }
    @RequestMapping(value = "/cookietest01",method = RequestMethod.GET)
    public String cookie01(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        StringBuilder sb = new StringBuilder();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                sb.append(cookie.getName()).append(",");
            }
        }
        return "cookietest01:"+sb.toString();
    }
    @RequestMapping(value = "/getSession",method = RequestMethod.GET)
    public String getSession(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if(session != null){
            Object username = session.getAttribute("share");
            return "share="+ JSONObject.toJSONString(username);
        }else{
            return "no session";
        }
    }
}
