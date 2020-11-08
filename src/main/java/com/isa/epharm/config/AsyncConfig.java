package com.isa.epharm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    private final CustomProperties customProperties;

    public AsyncConfig(CustomProperties customProperties) {
        this.customProperties = customProperties;
    }

    @Override
    public Executor getAsyncExecutor() {
        final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(customProperties.getAsync().getMaxPoolSize());
        threadPoolTaskExecutor.setCorePoolSize(customProperties.getAsync().getCorePoolSize());
        threadPoolTaskExecutor.setQueueCapacity(Integer.MAX_VALUE);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setThreadNamePrefix("async-clinical-");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
