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
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SysConfig sysConfig;

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
        log.info("login: {}", JSONObject.toJSONString(loginDto));
        if (loginDto == null || StrUtil.isBlank(loginDto.getUsername()) || StrUtil.isBlank(loginDto.getPassword())) {
            return Result.fail("用户名或密码不能为空");
        }

        try {
            // 生成token
            SecurityUser securityUser = userDetailService.loadUserByUsername(loginDto.getUsername());
            log.info("securityUser: {}", JSONObject.toJSONString(securityUser));

            LoginUser loginUser = new LoginUser();
            String username = securityUser.getUsername();
            loginUser.setAuthNames(securityUser.getAuthNames());
            loginUser.setUsername(username);
            Map<String, Object> headerMap = new HashMap<>();
            headerMap.put("username", username);
            Map<String, Object> claimsMap = new HashMap<>();
            claimsMap.put("securityUser", JSONObject.toJSONString(securityUser));
            String token = JwtUtil.generateToken2Claims(sysConfig.getSecret(), headerMap, claimsMap, sysConfig.getExpiration());

            // token 存储到redis
            redisTemplate.opsForValue().set("auth:token:" + username, token, sysConfig.getExpiration(), TimeUnit.MILLISECONDS);
            return Result.success(token);
        } catch (Exception e) {
            return Result.fail("登录失败!" + e.getMessage());
        }

    }

    @PostMapping("/out")
    public Result<String> logout() {
        return Result.success();
    }
}
