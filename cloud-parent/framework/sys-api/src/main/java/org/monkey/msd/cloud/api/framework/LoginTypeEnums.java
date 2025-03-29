package org.monkey.msd.cloud.api.framework;

import lombok.Getter;

/**
 * LoginTypeEnums
 *
 * @author cc
 * @since 2025/3/29 15:25
 */
@Getter
public enum LoginTypeEnums {
    /**
     * 用户名密码登录
     */
    USERNAME_PASSWORD(0, "用户名密码登录"),
    /**
     * 手机号登录
     */
    PHONE(1, "手机号登录"),
    /**
     * 邮箱登录
     */
    EMAIL(2, "邮箱登录"),
    /**
     * 微信登录
     */
    WECHAT(3, "微信登录"),
    ;

    private final Integer code;
    private final String desc;

    LoginTypeEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
