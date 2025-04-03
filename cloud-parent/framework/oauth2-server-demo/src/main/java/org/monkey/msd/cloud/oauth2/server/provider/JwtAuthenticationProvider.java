package org.monkey.msd.cloud.oauth2.server.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * JwtuthenticationProvider
 *
 * @author cc
 * @since 2025/3/31 14:35
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final String SECRET_KEY = "your-256-bit-secret11111111111112222222222222222";

    @Override
    public Authentication authenticate(Authentication authentication) {
        /*String token = (String) authentication.getCredentials();
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
        return new JwtAuthenticationToken(securityUser, securityUser.getAuthorities());*/
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
