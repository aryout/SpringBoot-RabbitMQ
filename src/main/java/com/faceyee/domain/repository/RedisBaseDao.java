package com.faceyee.domain.repository;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by 97390 on 8/26/2018.
 */
/*
* 简单的对redisTemplate封装
* */
@Repository
public class RedisBaseDao {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource(name = "redisTemplate")
    protected ValueOperations<String,Object> valueOperations;

    public void addValue(String key,String value){
        valueOperations.set(key,value);
    }

    public Object getValue(String key){
        return  valueOperations.get(key);
    }
}