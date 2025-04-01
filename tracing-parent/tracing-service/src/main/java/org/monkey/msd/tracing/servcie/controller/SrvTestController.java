package org.monkey.msd.tracing.servcie.controller;

import org.monkey.msd.tracing.api.feign.Service2FeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SrvTestController
 *
 * @author cc
 * @since 2025/4/1 9:53
 */
@RestController
@RequestMapping("/srv")
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"org.monkey.msd"})
public class SrvTestController {
    private static final Logger log = LoggerFactory.getLogger(SrvTestController.class);

    @Autowired
    private Service2FeignClient service2FeignClient;

    @RequestMapping("/test")
    public String test() {
        log.info("srv test===================");
        log.info("srv2 test: {}", service2FeignClient.hello());
        return "srv test:" + System.currentTimeMillis();
    }
}
