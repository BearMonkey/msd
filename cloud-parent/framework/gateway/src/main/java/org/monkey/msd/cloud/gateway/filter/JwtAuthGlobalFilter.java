package org.monkey.msd.cloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.cloud.api.framework.dto.SecurityUser;
import org.monkey.msd.cloud.common.constants.LoginConstants;
import org.monkey.msd.cloud.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * JwtAuthGlobalFilter
 *
 * @author cc
 * @since 2025/3/29 15:02
 */
@Deprecated
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class JwtAuthGlobalFilter implements GlobalFilter {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 仅校验特定路由的请求
        if (exchange.getRequest().getPath().toString().startsWith("/auth/")) {
            log.info("/auth/**认证请求，跳过JWT校验: {}", exchange.getRequest().getPath());
            return chain.filter(exchange);
        }

        // 解析Token
        String token = resolveToken(exchange.getRequest());

        if (StrUtil.isBlank(token)) {
            log.warn("请求未携带token: {}", exchange.getRequest().getPath());
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //String deviceType = exchange.getRequest().getHeaders().getFirst("deviceType");

        // 基础校验
        Claims claims = null;
        try {
            claims = JwtUtil.parseToken2Claims(token, "your-256-bit-secret11111111111112222222222222222");
        } catch (Exception e) {
            log.warn("JWT 解析失败: token={}, error={}", token, e.getMessage());
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        String userStr = claims.get("securityUser", String.class);
        log.info("JWT 解析成功: userStr={}", userStr);
        SecurityUser securityUser = JSONObject.parseObject(userStr, SecurityUser.class);
        String username = securityUser.getUsername();

        String cacheToken = redisTemplate.opsForValue().get(LoginConstants.AUTH_TOKEN_PREFIX + username);
        // redis中的token与请求中的token不一致
        if (cacheToken == null || !cacheToken.equals(token)) {
            log.info("非法token: token={}, cache={}", token, cacheToken);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 创建 Authentication 对象
        Authentication auth = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
        // 校验通过，传递给下一个过滤器
        return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
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
