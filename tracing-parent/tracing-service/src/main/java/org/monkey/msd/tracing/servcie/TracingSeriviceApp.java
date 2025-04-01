package org.monkey.msd.tracing.servcie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * TracingSeriviceApp
 *
 * @author cc
 * @since 2025/4/1 9:52
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "org.monkey.msd")
@EnableDiscoveryClient
@ComponentScan(basePackages = "org.monkey.msd")
public class TracingSeriviceApp {
    public static void main(String[] args) {
        SpringApplication.run(TracingSeriviceApp.class, args);
    }
}
