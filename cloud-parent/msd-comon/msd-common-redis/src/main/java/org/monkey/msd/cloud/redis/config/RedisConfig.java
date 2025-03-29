package org.monkey.msd.cloud.redis.config;

import org.monkey.msd.cloud.redis.service.FastJson2JsonRedisSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisConfig
 *
 * @author cc
 * @since 2025/3/21 16:14
 */
@EnableCaching
@Configuration
public class RedisConfig {
    private static final int CACHE_EXPIRE = 60;

    @Bean
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new FastJson2JsonRedisSerializer(Object.class));
        template.afterPropertiesSet();
        return template;
    }
}
