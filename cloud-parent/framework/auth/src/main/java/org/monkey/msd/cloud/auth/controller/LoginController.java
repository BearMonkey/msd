package org.monkey.msd.cloud.auth.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.cloud.api.framework.dto.LoginDto;
import org.monkey.msd.cloud.api.framework.dto.LoginUser;
import org.monkey.msd.cloud.api.framework.dto.usr.UsrUserDto;
import org.monkey.msd.cloud.api.framework.feign.UserFeignClient;
import org.monkey.msd.cloud.auth.config.SysConfig;
import org.monkey.msd.cloud.api.framework.dto.SecurityUser;
import org.monkey.msd.cloud.auth.exception.AuthException;
import org.monkey.msd.cloud.auth.service.ILoginService;
import org.monkey.msd.cloud.auth.service.impl.UserDetailServiceImpl;
import org.monkey.msd.cloud.common.constants.CommonResult;
import org.monkey.msd.cloud.common.dto.Result;
import org.monkey.msd.cloud.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private UserFeignClient userFeignClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ILoginService loginService;

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
    public Result<LoginDto> login(@RequestBody LoginDto loginDto) {
        log.info("login: {}", JSONObject.toJSONString(loginDto));
        LoginDto result = loginService.login(loginDto);
        if (result.isSuccess()) {
            return Result.success("登录成功", result);
        } else {
            return Result.fail(result.getErrMsg(), result);
        }
    }

    @PostMapping("/out")
    public Result<String> logout() {
        return Result.success();
    }
}
