package org.monkey.msd.cloud.gateway.filter.gateway;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * SigninExpireGatewayFilter 登录是否过期重新登录校验 应用与
 *
 * @author cc
 * @since 2025/3/29 16:47
 */
//@Component
@Slf4j
@Order(0)
public class SigninExpireGatewayFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 仅校验特定路由的请求
        if (exchange.getRequest().getPath().toString().startsWith("/auth/sign/in")) {
            log.info("非/auth/sign/in认证请求，登录重复登录校验: {}", exchange.getRequest().getPath());
            return chain.filter(exchange);
        }
        String token = resolveToken(exchange.getRequest());
        if (StrUtil.isBlank(token)) {
            log.info("登录重复登录校验, 未登录");
            return chain.filter(exchange);
        }
        return null;
    }

    private String resolveToken(ServerHttpRequest request) {
        // 从Header提取Token...
        String authorization = request.getHeaders().getFirst("Authorization");
        if (StrUtil.isBlank(authorization)) {
            return null;
        }
        return authorization.substring(authorization.indexOf(" ") + 1);
    }
}
