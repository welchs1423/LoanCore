package com.finance.service;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class DistributedLockService {

    private final RedisTemplate<String, Object> redisTemplate;

    public DistributedLockService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean acquireLock(String lockKey, String value, long expireTimeSeconds) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey, value, expireTimeSeconds, TimeUnit.SECONDS);
        return success != null && success;
    }

    public void releaseLock(String lockKey) {
        redisTemplate.delete(lockKey);
    }
}