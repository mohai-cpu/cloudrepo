package com.limai.utils;

import java.io.Serializable;

/**
 * 公共结果返回类
 * @param <T>
 */
public class ReturnResult<T> implements Serializable {
    private int code;
    private String msg;
    private T data;
    public ReturnResult(){

    }
    public ReturnResult(int code,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
