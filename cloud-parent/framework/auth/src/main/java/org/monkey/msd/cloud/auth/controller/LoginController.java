package org.monkey.msd.cloud.auth.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.cloud.api.framework.dto.LoginDto;
import org.monkey.msd.cloud.auth.dto.SecurityUser;
import org.monkey.msd.cloud.auth.service.impl.UserDetailServiceImpl;
import org.monkey.msd.cloud.common.dto.Result;
import org.monkey.msd.cloud.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * LoginController
 *
 * @author cc
 * @since 2025/3/19 11:38
 */
@RestController
@RequestMapping("/sign")
@Slf4j
public class LoginController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/up")
    public Result<Boolean> regist() {
        return Result.success();
    }

    @PostMapping("/in")
    public Result<String> login(@RequestBody LoginDto loginDto) {
        // 生成token
        SecurityUser securityUser = userDetailService.loadUserByUsername(loginDto.getUsername());
        log.info("login: {}", JSONObject.toJSONString(loginDto));
        return Result.success(JwtUtil.generateToken2Claims(securityUser.getUsername(), securityUser.getAuthNames()));
    }

    @PostMapping("/out")
    public Result<String> logout() {
        return Result.success();
    }
}
