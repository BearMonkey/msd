package org.monkey.msd.test.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Task1
 *
 * @author cc
 * @since 2025/3/5 11:34
 */
@Component("Task1")
@Slf4j
@EnableAsync
public class Task1 {

    @Async
    @Scheduled(cron = "${cron.Task1:0 * * * * ?}")
    public void run() {
        log.info("start Task1");
        try {
            Thread.sleep(60 * 1000L);
        } catch (InterruptedException e) {
            log.error("Task1 interrupted: {}", e.getMessage());
        } finally {
            log.info("end Task1");
        }
    }
}
