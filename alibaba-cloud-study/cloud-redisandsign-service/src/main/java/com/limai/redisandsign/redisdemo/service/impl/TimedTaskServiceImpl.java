package com.limai.redisandsign.redisdemo.service.impl;

import com.limai.redisandsign.constants.RedisConstants;
import com.limai.redisandsign.redisdemo.service.TimedTaskService;
import com.limai.utils.ReturnResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
@Service
public class TimedTaskServiceImpl implements TimedTaskService {
    private Logger logger = LoggerFactory.getLogger(TimedTaskServiceImpl.class);
    @Autowired
    private HashOperations<String,String,String> hashOperations;
    @Async("taskExecutor")
    @Override
    public ReturnResult saveRedisDateToDB() {
        logger.info("saveRedisDateToDB ceshi come on");
        ReturnResult result = new ReturnResult();
        Cursor<Map.Entry<String, String>> scan = null;
        try {
            scan = hashOperations.scan(RedisConstants.AGENT_FROWARD_HASH_KEY, ScanOptions.scanOptions().count(1000).build());
            while (scan.hasNext()){
                Map.Entry<String, String> next = scan.next();
                saveRedisDate(next);
            }
        } catch (Exception e) {
            result.setCode(2001);
            result.setMsg("保存数据库失败");
            return result;
        }finally {
            if(scan != null && !scan.isClosed()){
                try {
                    scan.close();
                } catch (IOException e) {
                    result.setCode(2001);
                    result.setMsg("保存数据库失败");
                    return result;
                }
            }
        }
        result.setCode(2000);
        result.setMsg("保存数据库成功");
        return result;
    }

    private void saveRedisDate(Map.Entry<String, String> next) {
        String key = next.getKey();
        String value = next.getValue();
        //查询数据库 有就修改 没有新建
    }
}
