package org.monkey.msd.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * ThreadPoolTaskConfig
 *
 * @author cc
 * @since 2025/2/7 9:44
 */
@Configuration
public class ThreadPoolTaskConfig {
    /** 核心线程数（默认线程数） */
    private static final int CORE_POOL_SIZE = 20;
    /** 最大线程数 */
    private static final int MAX_POOL_SIZE = 100;
    /** 允许线程空闲时间（单位：默认为秒） */
    private static final int KEEP_ALIVE_TIME = 10;
    /** 缓冲队列大小 */
    private static final int QUEUE_CAPACITY = 200;
    /** 线程池名前缀 */
    private static final String THREAD_NAME_PREFIX = "msd-Async-";
    @Bean("taskExecutor") // bean的名称，默认为首字母小写的方法名
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.setTaskDecorator(runnable -> {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            return () -> {
                try {
                    SecurityContextHolder.setContext(securityContext);
                    runnable.run();
                } finally {
                    SecurityContextHolder.clearContext();
                }
            };
        });

        // 线程池对拒绝任务的处理策略
        // CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }
}

