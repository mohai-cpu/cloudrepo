package com.limai.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class VideoOrder implements Serializable {
    private Integer id;
    private String outTradeNo;
    private Integer state;
    private Date createTime;
    private  Integer totalFee;
    private Integer videoId;
    private String videoTitle;
    private String videoImg;
    private Integer userId;
    //测试负载均衡时，查看调用的是哪一台服务
    private String serviceInfo;
}
