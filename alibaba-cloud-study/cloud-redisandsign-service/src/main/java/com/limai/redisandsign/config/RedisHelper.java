package com.limai.redisandsign.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RedisHelper {
    private Logger logger = LoggerFactory.getLogger(RedisHelper.class);
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    /**
     * @param redisTemplate
     * @param pattern       表达式，如：abc*，找出所有以abc开始的键
     * @param limit         返回匹配到的数目
     * @return
     */

    @SuppressWarnings("unchecked")
    public static Cursor<String> scan(RedisTemplate redisTemplate, String pattern, int limit) {
        ScanOptions options = ScanOptions.scanOptions().match(pattern).count(limit).build();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        return (Cursor) redisTemplate.executeWithStickyConnection(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return new ConvertingCursor<>(redisConnection.scan(options), redisSerializer::deserialize);
            }
        });
    }

    /**
     * 匹配指定key表达式的所有key值
     * @param pattern  表达式，如：abc*，找出所有以abc开始的键
     * @param limit    返回匹配到的数目
     * @return
     */
    public Set<String> keys(String pattern, int limit) {
        Set<String> keys = new HashSet<>();
        Cursor<String> cursor = null;
        try {
            cursor = scan(redisTemplate, pattern, limit);
            while (cursor.hasNext()) {
                keys.add(cursor.next());
            }
        } catch (Exception e) {
            logger.info("redis scan exception：{}", e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                try {
                    cursor.close();
                } catch (Exception e) {
                    logger.info("cursor close fail:{}", e);
                }
            }
        }
        return keys;
    }
}
