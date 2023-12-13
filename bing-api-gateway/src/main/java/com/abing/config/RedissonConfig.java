package com.abing.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author CaptainBing
 * @Date 2023/12/10 14:23
 * @Description
 */
@Configuration
public class RedissonConfig {


    @Bean
    public RedissonClient redissonClient(){

        Config config = new Config();
        config.useSingleServer()
                .setAddress("localhost")
                .setPassword("")
                .setDatabase(10);
        return Redisson.create(config);
    }

}
