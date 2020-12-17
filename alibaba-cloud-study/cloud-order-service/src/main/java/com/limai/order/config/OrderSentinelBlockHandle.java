package com.limai.order.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSONObject;
import com.limai.utils.ReturnResult;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class OrderSentinelBlockHandle implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        ReturnResult result = new ReturnResult();
        if(e instanceof FlowException){
            result.setCode(2001);
            result.setMsg("限流异常");
        }
        else if(e instanceof DegradeException){
            result.setCode(2002);
            result.setMsg("降级异常");
        }
        else if(e instanceof ParamFlowException){
            result.setCode(2003);
            result.setMsg("热点限流异常");
        }
        else if(e instanceof SystemBlockException){
            result.setCode(2002);
            result.setMsg("系统异常");
        }
        else if(e instanceof AuthorityException){
            result.setCode(2002);
            result.setMsg("授权异常");
        }
        httpServletResponse.setStatus(200);
        httpServletResponse.setHeader("content-Type","application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(JSONObject.toJSONString(result));
    }
}
