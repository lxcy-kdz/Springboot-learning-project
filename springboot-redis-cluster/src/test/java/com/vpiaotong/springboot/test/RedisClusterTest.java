package com.vpiaotong.springboot.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisClusterTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void redisClusterTest(){
        for(int a = 0 ; a< 100 ;a++) {
            redisTemplate.opsForValue().set("cluster"+a, a+"");
        }
    }
}
