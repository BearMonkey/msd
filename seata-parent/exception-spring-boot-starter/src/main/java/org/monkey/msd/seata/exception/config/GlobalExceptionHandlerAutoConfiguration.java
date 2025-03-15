package org.monkey.msd.seata.exception.config;

import org.monkey.msd.seata.exception.handler.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * GlobalExceptionHandlerAutoConfiguration
 *
 * @author cc
 * @since 2025/3/3 9:08
 */
@Configuration
@ConditionalOnWebApplication
public class GlobalExceptionHandlerAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
