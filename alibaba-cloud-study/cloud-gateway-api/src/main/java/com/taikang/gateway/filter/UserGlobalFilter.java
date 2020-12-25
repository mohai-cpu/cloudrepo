//package com.taikang.gateway.filter;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
////用户鉴权过滤器
//@Component
//public class UserGlobalFilter implements GlobalFilter, Ordered {
//    private Logger logger = LoggerFactory.getLogger(UserGlobalFilter.class);
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        logger.info("*************************进入鉴权过滤器****************************");
//        //获取token
//        String token = exchange.getRequest().getHeaders().getFirst("token");
//        if(StringUtils.isBlank(token)){
//            logger.info("*************************token为空****************************");
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//           return exchange.getResponse().setComplete();
//        }
//        return chain.filter(exchange);
//    }
//    //数字越小优先级越高
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
