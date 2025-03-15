package org.monkey.msd.seata.common.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * SnowflakeConfig
 *
 * @author cc
 * @since 2025/3/3 9:48
 */
@Configuration
@ConfigurationProperties(prefix = "snow")
@Data
public class SnowflakeConfig {

    private int workId;

    private int dataCenterId;

    @Bean("snowflake")
    public Snowflake snowflake() {
        System.out.println("workId:" + workId + ", dataCenterId: " + dataCenterId);
        return IdUtil.getSnowflake(workId, dataCenterId);
    }
}
