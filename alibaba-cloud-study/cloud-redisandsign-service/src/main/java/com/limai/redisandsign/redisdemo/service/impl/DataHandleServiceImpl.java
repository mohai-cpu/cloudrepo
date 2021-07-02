package com.limai.redisandsign.redisdemo.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.limai.redisandsign.config.RedisHelper;
import com.limai.redisandsign.redisdemo.service.DataHandleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
@Service
public class DataHandleServiceImpl implements DataHandleService {
    private static final Logger logger = LoggerFactory.getLogger(DataHandleServiceImpl.class);
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisHelper redisHelper;
    @Override
    public String dataExport(String redisKey) {
        logger.info("method:dataExport入参为：{}", redisKey);
        int line = 0;
        BufferedWriter writer = null;
        File file = null;
        try {
            //需要确定文件放置位置
            String fileName = redisKey.replace("*", "") + ".txt";
//            String userHome = "D:\\";
//            String localPath = "blastoise\\";
            String userHome = System.getProperty("user.home");
            String localPath = "/blastoise/";
            logger.info("method:dataExport,filePath is :" + userHome + localPath + fileName);

            file = new File(userHome + localPath + fileName);
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();//创建多级目录
            }
            if (!file.exists()) {
                file.createNewFile();//没有文件创建文件
            }
        } catch (Exception e) {
            logger.info("method:dataExport,create file exception:{}", e);
            return "create file exception";
        }
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            Set<String> keySet = redisHelper.keys(redisKey, 1000);
            if (keySet == null || keySet.size() < 1) {
                logger.info("keySet is null");
                return "keySet is null";
            }
            for (String key : keySet) {
                DataType type = redisTemplate.type(key);
                switch (type) {
                    case HASH:
                        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
                        for (Map.Entry value1 : entries.entrySet()) {
                            line++;
                            JSONObject hashJson = new JSONObject();
                            hashJson.put("type", type.toString());
                            hashJson.put("key", key);
                            hashJson.put("hashkey", value1.getKey());
                            hashJson.put("value", value1.getValue());
                            writer.write(hashJson.toString());
                            writer.write("\r\n");
                        }
                        break;
                    case STRING:
                        line++;
                        JSONObject stringJson = new JSONObject();
                        stringJson.put("type", type.toString());
                        stringJson.put("key", key);
                        stringJson.put("value", redisTemplate.opsForValue().get(key));
                        writer.write(stringJson.toString());
                        writer.write("\r\n");
                        break;
                    case LIST:
                        List<String> range = redisTemplate.opsForList().range(key, 0, -1);
                        for (String s : range) {
                            line++;
                            JSONObject listJson = new JSONObject();
                            listJson.put("type", type.toString());
                            listJson.put("key", key);
                            listJson.put("value", s);
                            writer.write(listJson.toString());
                            writer.write("\r\n");
                        }
                        break;
                    case SET:
                        Set<String> members = redisTemplate.opsForSet().members(key);
                        for (String member : members) {
                            line++;
                            JSONObject setJson = new JSONObject();
                            setJson.put("type", type.toString());
                            setJson.put("key", key);
                            setJson.put("value", member);
                            writer.write(setJson.toString());
                            writer.write("\r\n");
                        }
                        break;
                    case ZSET:
                        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
                        Iterator<ZSetOperations.TypedTuple<String>> iterator = typedTuples.iterator();
                        while (iterator.hasNext()) {
                            line++;
                            ZSetOperations.TypedTuple<String> next = iterator.next();
                            Double score = next.getScore();
                            String value1 = next.getValue();
                            JSONObject zsetJson = new JSONObject();
                            zsetJson.put("type", type.toString());
                            zsetJson.put("key", key);
                            zsetJson.put("value", value1);
                            zsetJson.put("score", score);
                            writer.write(zsetJson.toString());
                            writer.write("\r\n");
                        }
                        break;
                }


            }
            logger.info("导出数据的条数为：{}", line);
            writer.flush();
        } catch (Exception e) {
            logger.info("method:dataExport,handle OutputStream exception:{}", e);
            return "handle OutputStream exception";
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    logger.info("method:dataExport,close OutputStream exception:{}", e);
                    return "close OutputStream exception";
                }
            }

        }
        logger.info("method:dataExport,success,line为：{}", line);
        return "success";
    }

    @Override
    public String dataImport(MultipartFile multipartFile) {
        BufferedReader bufferedReader = null;//字符输入流进行读取操作读取
        Map<String, String> hashMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        String tempString = null;//每一行的内容
        String key = "";
        String hashOldKey = "";
        String listOldKey = "";
        String hashKey;
        String value;
        double score;
        int line = 0;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
            while ((tempString = bufferedReader.readLine()) != null) {
                //logger.info("第{}行数据为：{}", line, tempString);
                String dataType = null;
                JSONObject jsonObject;
                try {
                    //将数据转成JSON
                    jsonObject = JSONObject.parseObject(tempString);
                    dataType = jsonObject.getString("type");
                    key = jsonObject.getString("key");
                    line++;
                    if ("HASH".equals(dataType) && StringUtils.isBlank(hashOldKey)) {
                        hashOldKey = key;
                    }
                    if ("LIST".equals(dataType) && StringUtils.isBlank(listOldKey)) {
                        listOldKey = key;
                    }
                } catch (Exception e) {
                    logger.info("第{}行数据为tempString:{}数据转换成json异常:{},", line, tempString, e);
                    continue;
                }
                if (StringUtils.isAnyBlank(dataType, key)) {
                    continue;
                }
                switch (dataType) {
                    case "HASH":
                        hashKey = jsonObject.getString("hashkey");
                        value = jsonObject.getString("value");
                        if (key.equals(hashOldKey)) {
                            hashMap.put(hashKey, value);
                        } else {
                            redisTemplate.opsForHash().putAll(hashOldKey, hashMap);
                            hashMap.clear();
                            hashMap.put(hashKey, value);
                            hashOldKey = key;
                        }
                        break;
                    case "STRING":
                        value = jsonObject.getString("value");
                        redisTemplate.opsForValue().set(key, value);
                        break;
                    case "ZSET":
                        value = jsonObject.getString("value");
                        score = jsonObject.getDouble("score");
                        redisTemplate.opsForZSet().add(key, value, score);
                        break;
                    case "SET":
                        value = jsonObject.getString("value");
                        redisTemplate.opsForSet().add(key, value);
                        break;
                    case "LIST":
                        value = jsonObject.getString("value");
                        if (key.equals(listOldKey)) {
                            list.add(value);
                        } else {
                            redisTemplate.opsForList().rightPushAll(listOldKey, list);
                            list.clear();
                            list.add(value);
                            listOldKey = key;
                        }
                        break;
                }


            }
            if (list != null && list.size() > 0) {
                redisTemplate.opsForList().leftPushAll(listOldKey, list);
            }
            if (hashMap != null && !hashMap.isEmpty()) {
                redisTemplate.opsForHash().putAll(hashOldKey, hashMap);
            }

        } catch (Exception e) {
            logger.info("method:dataImport,第{}行数据,handle OutputStream exception:{}", line, e);
            return "handle OutputStream exception";
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    logger.info("method:dataImport,close OutputStream exception:{}", e);
                    return "close OutputStream exception";
                }
            }
        }
        logger.info("method:dataImport,success,line为：{}", line);
        return "success";
    }
}
