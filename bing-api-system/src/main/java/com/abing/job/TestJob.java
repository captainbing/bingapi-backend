package com.abing.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @Author CaptainBing
 * @Date 2023/9/22 21:46
 * @Description
 */
@Slf4j
public class TestJob extends QuartzJobBean {

    /**
     * @param context
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        log.info("Quartz ================> 启动");

    }
}
