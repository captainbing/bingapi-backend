package com.abing.config;

import cn.hutool.core.util.RuntimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @Author CaptainBing
 * @Date 2023/12/25 14:06
 * @Description
 */
@Configuration
@Slf4j
public class ThreadPoolExecutorConfig {

    /**
     * AI 生成图表的线程池
     * @return
     */
    @Bean
    public ThreadPoolExecutor biThreadPoolExecutor(){
        int processorCount = RuntimeUtil.getProcessorCount();
        log.info("核心线程数:{}",processorCount);
        int corePoolSize = processorCount + 1;
        int maximumPoolSize = 2 * corePoolSize;
        // 阻塞队列
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(4);
        ThreadFactory threadFactory = new ThreadFactory() {
            /**
             * @param r a runnable to be executed by new thread instance
             * @return
             */
            private int count = 0;
            @Override
            public Thread newThread(@NotNull Runnable r) {
                return new Thread(r,"biWorker :" + count++);
            }
        };

        return new ThreadPoolExecutor(corePoolSize,maximumPoolSize,100, TimeUnit.SECONDS,workQueue,threadFactory);
    }

}
