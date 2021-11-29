package com.example.dsregister.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void add(String key, String value) {
        if (value != null) {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    public String getRandom() {
        return redisTemplate.randomKey() == null ? null : redisTemplate.opsForValue().get(Objects.requireNonNull(redisTemplate.randomKey()));
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void deleteAll() {
        //redisTemplate.opsForValue().
    }




}
