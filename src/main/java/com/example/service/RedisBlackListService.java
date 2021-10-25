package com.example.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisBlackListService implements BlackListService {

    private RedisTemplate<String, String> redisTemplate;
    private ValueOperations<String, String> valueOperations; //to access Redis cache

    public RedisBlackListService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void saveAccessToken(String accessToken) {
        valueOperations.set(accessToken.toLowerCase(), "yes", 1, TimeUnit.MINUTES);
    }

    @Override
    public String find(String accessToken) {
        return valueOperations.get(accessToken.toLowerCase());
    }
}
