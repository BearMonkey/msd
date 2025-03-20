package org.monkey.msd.cloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * AuthApp
 *
 * @author cc
 * @since 2025/3/18 19:17
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "org.monkey.msd")
public class AuthApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class, args);
    }
}
