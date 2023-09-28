package com.abing.config.job;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @Author CaptainBing
 * @Date 2023/8/5 9:54
 * @Description
 */
@Configuration
public class QuartzConfig {

    /**
     * 初始注入scheduler
     * @return
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        return new SchedulerFactoryBean();
    }

}
