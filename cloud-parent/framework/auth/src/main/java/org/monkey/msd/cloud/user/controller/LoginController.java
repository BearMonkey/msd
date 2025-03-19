package org.monkey.msd.cloud.user.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.cloud.api.framework.dto.LoginDto;
import org.monkey.msd.cloud.common.dto.Result;
import org.monkey.msd.cloud.common.util.JwtUtil;
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

    @PostMapping("/up")
    public Result<String> regist() {
        return Result.success();
    }

    @PostMapping("/in")
    public Result<String> login(@RequestBody LoginDto loginDto) {
        // 生成token
        log.info("login: {}", JSONObject.toJSONString(loginDto));
        return Result.success(JwtUtil.generateToken2Claims(loginDto.getUsername(), List.of(loginDto.getPassword())));
    }

    @PostMapping("/out")
    public Result<String> logout() {
        return Result.success();
    }
}
