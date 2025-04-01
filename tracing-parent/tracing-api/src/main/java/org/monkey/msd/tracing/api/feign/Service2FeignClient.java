package org.monkey.msd.tracing.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Service2FeignClient
 *
 * @author cc
 * @since 2025/4/1 13:41
 */
@FeignClient(value = "service2", path = "/service2")
public interface Service2FeignClient {

    @RequestMapping("/srv2/hello")
    String hello();
}
