package org.monkey.msd.cloud.gateway.provider;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.cloud.api.framework.dto.SecurityUser;
import org.monkey.msd.cloud.common.constants.LoginConstants;
import org.monkey.msd.cloud.common.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * JwtuthenticationProvider
 *
 * @author cc
 * @since 2025/3/31 14:35
 */
@Component
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    private final RedisTemplate<String, String> redisTemplate;

    private final String SECRET_KEY = "your-256-bit-secret11111111111112222222222222222";

    public JwtAuthenticationProvider(RedisTemplate<String, String> redisTemplate, ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String token = (String) authentication.getCredentials();
        log.info("JwtAuthenticationProvider: token={}", token);
        Claims claims;
        try {
            claims = JwtUtil.parseToken2Claims(token, SECRET_KEY);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid token");
        }
        SecurityUser securityUser = JSONObject.parseObject(JSON.toJSONString(claims), SecurityUser.class);
        String redisKey = LoginConstants.AUTH_TOKEN_PREFIX + securityUser.getUsername();
        String caechToken = redisTemplate.opsForValue().get(redisKey);
        if (StrUtil.isBlank(caechToken) || !caechToken.equals(token)) {
            throw new BadCredentialsException("Token mismatch");
        }
        return new JwtAuthenticationToken(securityUser, securityUser.getAuthorities());
        /*return Mono.fromCallable(() -> claims)
                .onErrorResume(e -> Mono.error(new BadCredentialsException("Invalid JWT")))
                .flatMap(claims -> {
                    SecurityUser securityUser = JSONObject.parseObject(JSON.toJSONString(claims), SecurityUser.class);

                    String redisKey = LoginConstants.AUTH_TOKEN_PREFIX + securityUser.getUsername();
                    return reactiveRedisTemplate.opsForValue().get(redisKey)
                            .filter(storedToken -> storedToken.equals(token))
                            .switchIfEmpty(Mono.error(new BadCredentialsException("Token mismatch")))
                            .thenReturn(securityUser);
                })
                .map(securityUser -> new JwtAuthenticationToken(securityUser, securityUser.getAuthorities()));*/
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
