package org.monkey.msd.cloud.api.framework.dto;

import lombok.Data;

/**
 * LoginDto
 *
 * @author cc
 * @since 2025/3/19 11:53
 */
@Data
public class LoginDto {
    private String username;
    private String password;

    private String phone;
    private String code;

    private String email;

    private String wechatOpenId;

    private int loginType;
    private Boolean success;
    private String token;
    private String errMsg;
}
