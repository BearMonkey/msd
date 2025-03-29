package org.monkey.msd.cloud.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.cloud.api.framework.dto.SmsDto;
import org.monkey.msd.cloud.auth.service.ISmsService;
import org.monkey.msd.cloud.common.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SmsController 短信认证
 *
 * @author cc
 * @since 2025/3/29 15:12
 */
@RestController
@RequestMapping("/sms")
@Slf4j
public class SmsController {

    @Autowired
    private ISmsService smsService;

    @PostMapping
    public Result<SmsDto> sendSms(String phone) {
        return Result.success(smsService.sendSms(phone));
    }
}
