package com.abing.config;

import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author CaptainBing
 * @Date 2023/11/29 14:25
 * @Description
 */
@Configuration
public class RedisRateLimitConfig {


    /**
     * 默认redis限流器
     * @return
     */
    @Bean
    public RedisRateLimiter redisRateLimiter(){

        //redis-rate-limite.repullishRate表示令牌桶每秒的填充速率。
        //redis-rate-limite.burstCapacity表示允许用户在一秒钟内允许消耗的最大令牌数，同时也是令牌桶可以容纳的令牌数上限，将此值设置为零将阻止所有的请求。
        //redis-rate-limite.requestedTokens表示单次请求消耗多少个令牌，默认为1。
        return new RedisRateLimiter(5,10,1);

    }

}
