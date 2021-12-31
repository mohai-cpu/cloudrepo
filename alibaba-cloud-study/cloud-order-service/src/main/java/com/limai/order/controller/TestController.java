package com.limai.order.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/nacostest")
@RefreshScope
public class TestController {
    public static void main(String[] args) {
        String ss ="aabcddee";
        String str="";
        List<String> list = new ArrayList<>();
        for (int j = 0; j < ss.length();j++) {
            if(str.contains(ss.charAt(j)+"")){
                list.add(str);
                if(j==ss.length()-1){
                    list.add(ss.charAt(j)+"");
                }else{
                    str = ss.charAt(j)+"";
                }

            }else{
                str=str+ss.charAt(j);
            }
        }
        for(String sstr:list){
            System.out.println(sstr);
        }
    }
}

