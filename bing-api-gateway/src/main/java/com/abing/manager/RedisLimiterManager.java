package com.abing.manager;

import lombok.RequiredArgsConstructor;
import org.redisson.api.*;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * @Author CaptainBing
 * @Date 2023/12/13 13:21
 * @Description
 */
@Service
@RequiredArgsConstructor
public class RedisLimiterManager {

    private final RedissonClient redissonClient;


    /**
     * redisson分布式限流
     * @param key
     * @return
     */
    public boolean doRateLimit(String key){
        // 获取限流器
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        // 一秒内只能接受一次请求
        rateLimiter.setRate(RateType.OVERALL,1,1, RateIntervalUnit.SECONDS);
        return rateLimiter.tryAcquire(1);
    }

    /**
     * 存入nonce值 5分钟过期
     * @param key
     */
    public void setNonceFor5Min(String key){
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.set("nonce", Duration.ofMinutes(5));
    }

    /**
     * 取nonce值
     * @param key
     * @return
     */
    public String getNonce(String key){
        RBucket<String> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }


}
