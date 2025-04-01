package org.monkey.msd.cloud.api.framework.dto;

import lombok.Data;

/**
 * LoginVo
 *
 * @author cc
 * @since 2025/3/31 9:31
 */
@Data
public class LoginVo {
    private String username;

    private String phone;

    private String email;

    private String wechatOpenId;

    private String token;

    private int loginType;
}
