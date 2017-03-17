package com.dslcode.sessoin.redis.test;

import com.dslcode.sessoin.redis.DemoSessionRedisApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by dongsilin on 2017/3/8.
 */
@Slf4j
public class RedisTest extends DemoSessionRedisApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        log.debug("aaa={}", stringRedisTemplate.opsForValue().get("aaa"));
    }

}
