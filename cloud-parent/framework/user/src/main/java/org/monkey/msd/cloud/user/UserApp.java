package org.monkey.msd.cloud.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * UserApp
 *
 * @author cc
 * @since 2025/3/18 19:17
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"org.monkey.msd"})
public class UserApp {
    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
    }
}
