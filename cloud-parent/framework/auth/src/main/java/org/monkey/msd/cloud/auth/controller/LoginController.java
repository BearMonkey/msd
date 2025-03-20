package org.monkey.msd.cloud.auth.controller;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.cloud.api.framework.dto.LoginDto;
import org.monkey.msd.cloud.api.framework.dto.usr.UsrUserDto;
import org.monkey.msd.cloud.api.framework.feign.UserFeignClient;
import org.monkey.msd.cloud.auth.dto.SecurityUser;
import org.monkey.msd.cloud.auth.exception.AuthException;
import org.monkey.msd.cloud.auth.service.impl.UserDetailServiceImpl;
import org.monkey.msd.cloud.common.constants.CommonResult;
import org.monkey.msd.cloud.common.dto.Result;
import org.monkey.msd.cloud.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/up")
    public Result<Boolean> regist(@RequestBody UsrUserDto userDto) {
        Assert.notNull(userDto, "请求参数不能为空");
        Assert.notNull(userDto.getUsername(), "用户名不能为空");
        Assert.notNull(userDto.getPassword(), "密码不能为空");

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Result<Boolean> addUserResult = userFeignClient.addUser(userDto);
        if (addUserResult == null || !CommonResult.SUCCESS.equals(addUserResult.getCode())) {
            throw new AuthException("注册用户失败");
        }
        return Result.success(true);
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
