package com.dslcode.demo.order.traditional.service.impl;

import com.dslcode.demo.order.traditional.service.RedisDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by dongsilin on 2017/4/8.
 */
@Slf4j
@Service
public class RedisDataServiceImpl implements RedisDataService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void stringPut(String key, String value) {
        this.stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String stringGet(String key) {
        return this.stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void listPut(String key, String value) {
        this.stringRedisTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public String listGet(String key) {
        return this.stringRedisTemplate.opsForList().leftPop(key);
    }

    @Override
    public boolean setHasNext(String key) {
        Long size = this.stringRedisTemplate.opsForSet().size(key);
        return null != size && size > 0;
    }

    @Override
    public void setPut(String key, String value) {
        this.stringRedisTemplate.opsForSet().add(key, value);
    }

    @Override
    public String setPop(String key) {
        return this.stringRedisTemplate.opsForSet().pop(key);
    }




}
