package org.monkey.msd.seata.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * OrderApp
 *
 * @author cc
 * @since 2025/2/27 16:27
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "org.monkey.msd")
@ComponentScan({"org.monkey.msd"})
public class OrderApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class, args);
    }
}
