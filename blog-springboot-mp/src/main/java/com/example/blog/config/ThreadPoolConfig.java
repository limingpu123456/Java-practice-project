package com.example.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Description：
 * User: lmp
 * Date: 2023-10-21
 * Time: 15:27(李明浦)
 */
@Configuration
public class ThreadPoolConfig {
    @Bean
    //Springboot提供的线程池
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        taskExecutor.setCorePoolSize(5);
        //设置最大线程数
        taskExecutor.setMaxPoolSize(10);
        //设置队列长度，能存放多少个任务
        taskExecutor.setQueueCapacity(100000);
        //设置线程池的名称
        taskExecutor.setThreadNamePrefix("my-threadpool-");
        //线程池初始化
        taskExecutor.initialize();
        return taskExecutor;
    }
}
