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
@Component("Task4")
@Slf4j
@EnableAsync
public class Task4 {

    @Async
//    @Scheduled(cron = "${cron.Task3:20 * * * * ?}")
    public void run() {
        log.info("start Task4");
        try {
            Thread.sleep(60 * 1000L);
        } catch (InterruptedException e) {
            log.error("Task4 interrupted: {}", e.getMessage());
        } finally {
            log.info("end Task4");
        }
    }
}
