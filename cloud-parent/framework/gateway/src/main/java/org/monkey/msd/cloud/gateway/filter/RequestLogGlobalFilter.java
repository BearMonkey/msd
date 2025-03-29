package org.monkey.msd.cloud.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * RequestLogGlobalFilter
 *
 * @author cc
 * @since 2025/3/29 14:46
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RequestLogGlobalFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long start = System.currentTimeMillis();
        ServerHttpRequest request = exchange.getRequest();
        // 记录请求基本信息
        String path = request.getURI().getPath();
        String method = request.getMethod().name();
        String query = request.getURI().getQuery();
        Map<String, String> headers = request.getHeaders().toSingleValueMap();
        log.info("Request Start => Method: {}, Path: {}, Query: {}, Headers: {}", method, path, query, headers);
        // 在响应完成后记录耗时和状态码
        return chain.filter(exchange).doFinally(signalType -> {
            long duration = System.currentTimeMillis() - start;
            HttpStatusCode status = exchange.getResponse().getStatusCode();
            int statusCode = (status != null) ? status.value() : 500;
            log.info("Request End => Path: {}, Status: {}, Duration: {}ms", path, statusCode, duration);
        });
    }
}
