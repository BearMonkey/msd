package org.monkey.msd.cloud.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.cloud.api.framework.dto.SmsDto;
import org.monkey.msd.cloud.auth.service.ISmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * SmsServiceImpl
 *
 * @author cc
 * @since 2025/3/29 15:15
 */
@Service
@Slf4j
public class SmsServiceImpl implements ISmsService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static final String SMS_CODE_CACHE_KEY = "auth:sms:code:";

    @Override
    public SmsDto sendSms(String phone) {
        log.info("Recieve sendSms request: {}", phone);
        SmsDto result = new SmsDto();
        result.setSuccess(false);
        result.setPhone(phone);
        // 生成验证码
        String code = RandomUtil.randomNumbers(6);
        // 发送验证码
        try {
            log.info("Send sms to {} with code: {}", phone, code);
            // todo send sms
        } catch (Exception e) {
            log.error("Send sms failed: {}", e.getMessage());
            result.setErrMsg("发送失败，请重试");
            return result;
        }
        // 写缓存
        redisTemplate.opsForValue().set(SMS_CODE_CACHE_KEY + phone, code,  60, TimeUnit.SECONDS);
        result.setCode(code);
        result.setSuccess(true);
        return result;
    }

    public SmsDto verifySmsCode(String phone, String code) {
        SmsDto result = new SmsDto();
        result.setSuccess(false);
        result.setPhone(phone);
        if (!StrUtil.isAllNotBlank(phone, code)) {
            return result;
        }
        String cacheKey = SMS_CODE_CACHE_KEY + phone;
        if (!Boolean.TRUE.equals(redisTemplate.hasKey(cacheKey))) {
            return result;
        }
        String cacheCode = redisTemplate.opsForValue().get(cacheKey);
        boolean equals = code.equals(cacheCode);
        if (!equals) {
            result.setErrMsg("验证码错误");
        }
        result.setSuccess(equals);
        return result;
    }
}
