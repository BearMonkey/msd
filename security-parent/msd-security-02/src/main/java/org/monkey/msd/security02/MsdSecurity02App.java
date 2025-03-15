package org.monkey.msd.security02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * MsdSecurity02App
 *
 * @author cc
 * @since 2025/2/7 11:10
 */
@EnableWebSecurity
@SpringBootApplication
public class MsdSecurity02App {
    public static void main(String[] args) {
        SpringApplication.run(MsdSecurity02App.class, args);
    }
}
