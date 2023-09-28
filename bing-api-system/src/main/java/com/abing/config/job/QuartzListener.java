package com.abing.config.job;

import org.quartz.SchedulerException;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

/**
 * @author 阿炳亿点点帅
 */
@Configuration
public class QuartzListener implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private QuartzSchedulerManager quartzSchedulerManager;

    /**
     * 初始启动quartz
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            quartzSchedulerManager.startJob();
            System.out.println("任务已经启动...");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}