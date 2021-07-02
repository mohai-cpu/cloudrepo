package com.limai.redisandsign.redisdemo.service;

import com.limai.utils.ReturnResult;

/**
 * 非阻塞保存redis数据到数据库中
 */
public interface TimedTaskService {
    /**
     * 保存数据到数据库中
     * @return
     */
    public ReturnResult saveRedisDateToDB();
}
