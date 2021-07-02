package com.limai.redisandsign.redisdemo.service;

import org.springframework.web.multipart.MultipartFile;

public interface DataHandleService {
    //数据导出
    String dataExport(String redisKey);
    //数据导入
    String dataImport(MultipartFile multipartFile);
}
