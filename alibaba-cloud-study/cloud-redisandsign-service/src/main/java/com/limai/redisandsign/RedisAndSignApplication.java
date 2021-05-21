package com.limai.redisandsign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisAndSignApplication {
    public static void main(String[] args) {
        //以下两种都行
        SpringApplication.run(RedisAndSignApplication.class);
        //SpringApplication.run(RedisAndSignApplication.class,args);
    }
}
