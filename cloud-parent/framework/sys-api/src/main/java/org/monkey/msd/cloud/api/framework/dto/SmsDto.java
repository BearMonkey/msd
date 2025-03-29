package org.monkey.msd.cloud.api.framework.dto;

import lombok.Data;

/**
 * SmsDto
 *
 * @author cc
 * @since 2025/3/29 16:23
 */
@Data
public class SmsDto {
    private String phone;
    private String code;
    private boolean success;
    private String errMsg;
}
