package org.monkey.msd.cloud.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * SysConfig
 *
 * @author cc
 * @since 2025/3/21 11:54
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "sys.auth.jwt")
public class SysConfig {

    // 这条配置无法热刷，因为旧数据使用的是旧秘钥
    private String secret = "your-256-bit-secret11111111111112222222222222222";

    private long expiration = 86400000; // 24小时
}
