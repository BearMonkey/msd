package org.monkey.msd.tracing.api.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * Service1FeignClient
 *
 * @author cc
 * @since 2025/4/1 13:41
 */
@FeignClient(value = "service1", path = "/service1")
public interface Service1FeignClient {

}
