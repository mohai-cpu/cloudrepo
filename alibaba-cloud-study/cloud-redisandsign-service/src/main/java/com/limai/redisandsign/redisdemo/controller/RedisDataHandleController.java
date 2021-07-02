package com.limai.redisandsign.redisdemo.controller;

import com.limai.redisandsign.config.RedisHelper;
import com.limai.redisandsign.redisdemo.service.DataHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.Set;

@RestController
@RequestMapping("/redisdata")
public class RedisDataHandleController {
    private Logger logger = LoggerFactory.getLogger(RedisDataHandleController.class);
    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private DataHandleService dataHandleService;

    /**
     * @param keyPattern 需要匹配到的redis key值，需要区分大小写
     *                   （例如：想要删除所有以abc开头的key,keyPattern为abc*）
     * @return
     */
    @RequestMapping(value = "/del/delteByPattern", method = RequestMethod.GET)
    public String delteByPattern(@RequestParam(value = "keyPattern") String keyPattern) {
        logger.info("delteByPattern;keyPattern:{}", keyPattern);
        Set<String> keySet = redisHelper.keys(keyPattern, 1000);
        if (keySet == null || keySet.size() < 1) {
            logger.info("keySet is null");
            return "keySet is null";
        }
        int i = 0;
        for (String s : keySet) {
            i++;
            redisTemplate.delete(s);
        }
        logger.info("delteByPattern success;keys.size:{}", i);
        return "success";
    }

    /**
     * 将需要导出的rediskey（区分大小写）值作为入参
     * 导出的文件位置固定为${user.home}/blastoise/
     *
     * @param redisKey 需要匹配到的redis key值，需要区分大小写
     *                 （例如：想要删除所有以AbC开头的key,keyPattern为AbC*）
     * @return
     */
    @RequestMapping(value = "/dataexport", method = RequestMethod.GET)
    public String dataToFile(@RequestParam(value = "rediskey") String redisKey) {
        String returnResult = dataHandleService.dataExport(redisKey);
        return returnResult;
    }

    /**
     * 此接口为将导出的txt文件导入到redis，通过输入流逐条导入到redis,
     * 如果是list和hash类型，则将此key的value存入list，或者map里，整体存入redis
     * 如果是其他类型，则逐条存入redis。
     *
     * @param multipartFile 需要导入的文件路径
     * @return
     */
    @RequestMapping(value = "/dataimport", method = RequestMethod.POST)
    public String fileToRedis(@RequestParam("file") MultipartFile multipartFile) {
        String returnResult = dataHandleService.dataImport(multipartFile);
        return returnResult;
    }

}
