package com.limai.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private Integer id;
    private String name;
    private String pwd;
    private String headImg;
    private String phone;
    private Date createTime;
    private String wechat;
}
