package com.limai.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Video implements Serializable {
    private Integer id;
    private String title;
    private String summary;
    private String coverImg;
    private Integer  price;
    private Date createTime;
    private Double point;
    //测试负载均衡时，查看调用的是哪一台服务
    private String serviceInfo;
}
