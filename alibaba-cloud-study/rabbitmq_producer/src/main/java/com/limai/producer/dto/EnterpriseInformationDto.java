package com.limai.producer.dto;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class EnterpriseInformationDto {
    private Long id;

    private String agentNo;

    private String informationId;

    private String content;

    private String from;

    private String createWay;

    private String author;

    private String title;

    private String simpleDesc;

    private String classifyDesc;
    //1000-要闻 1003-股市 1032-金融 1113-保险

    private String classifyId;

    private String status;

    private String isPublic;

    private String messageTime;

    private Timestamp createDate;

    private Timestamp modifyDate;
}
