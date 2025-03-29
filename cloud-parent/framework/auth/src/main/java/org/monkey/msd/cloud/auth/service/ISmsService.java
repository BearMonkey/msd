package org.monkey.msd.cloud.auth.service;

import org.monkey.msd.cloud.api.framework.dto.SmsDto;

/**
 * ISmsService
 *
 * @author cc
 * @since 2025/3/29 15:14
 */
public interface ISmsService {
    /**
     * 发送短信验证码
     * @param phone 手机号
     * @return SmsDto
     */
    SmsDto sendSms(String phone);

    /**
     * 验证短信验证码
     * @param phone 手机号
     * @param code 用户输入的验证码
     * @return 验证结果
     */
    SmsDto verifySmsCode(String phone, String code);
}
