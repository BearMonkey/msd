package org.monkey.msd.cloud.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.cloud.api.framework.LoginTypeEnums;
import org.monkey.msd.cloud.api.framework.dto.LoginDto;
import org.monkey.msd.cloud.api.framework.dto.LoginUser;
import org.monkey.msd.cloud.api.framework.dto.SecurityUser;
import org.monkey.msd.cloud.api.framework.dto.SmsDto;
import org.monkey.msd.cloud.api.framework.feign.UserFeignClient;
import org.monkey.msd.cloud.auth.config.SysConfig;
import org.monkey.msd.cloud.auth.service.ILoginService;
import org.monkey.msd.cloud.auth.service.ISmsService;
import org.monkey.msd.cloud.common.constants.LoginConstants;
import org.monkey.msd.cloud.common.dto.Result;
import org.monkey.msd.cloud.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * LoginServiceImpl
 *
 * @author cc
 * @since 2025/3/29 15:27
 */
@Service
@Slf4j
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SysConfig sysConfig;

    @Autowired
    private ISmsService smsService;

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public LoginDto login(LoginDto loginDto) {
        LoginDto result = new LoginDto();
        result.setSuccess(false);
        if (loginDto == null) {
            result.setErrMsg("登录失败");
            return result;
        }
        if (repeatLogin(loginDto, result)) {
            return result;
        }
        switch (loginDto.getLoginType()) {
            case 1:
                loginByPhone(loginDto, result);
                break;
            case 2:
                loginByEmail(loginDto, result);
                break;
            case 3:
                loginByWechat(loginDto, result);
                break;
            default:
                loginByPass(loginDto, result);
                break;
        }

        return result;
    }

    private void loginByWechat(LoginDto loginDto, LoginDto result) {
        // todo 实现微信登录
        result.setErrMsg("未实现该登录方式");
    }

    private void loginByEmail(LoginDto loginDto, LoginDto result) {
        // todo 实现邮箱登录
        result.setErrMsg("未实现该登录方式");
    }

    private void loginByPhone(LoginDto loginDto, LoginDto result) {
        if (StrUtil.isBlank(loginDto.getPhone())) {
            result.setErrMsg("手机号不能为空");
            return;
        }
        if (StrUtil.isBlank(loginDto.getCode())) {
            result.setErrMsg("验证码不能为空");
            return;
        }
        SmsDto verifyResult = smsService.verifySmsCode(loginDto.getPhone(), loginDto.getCode());
        if (verifyResult == null || !verifyResult.isSuccess()) {
            result.setErrMsg("验证码错误");
            return;
        }
        result.setSuccess(true);
    }

    private void loginByPass(LoginDto loginDto, LoginDto result) {
        String username = loginDto.getUsername();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(loginDto.getPassword())) {
            result.setErrMsg("用户名或密码不能为空");
            return;
        }

        try {
            // 生成token
            String token = this.handleToken(username);
            result.setSuccess(true);
            result.setToken(token);
        } catch (Exception e) {
            result.setErrMsg(e.getMessage());
        }
    }

    private String handleToken(String username) {
        SecurityUser securityUser = userDetailService.loadUserByUsername(username);
        log.info("securityUser: {}", JSONObject.toJSONString(securityUser));
        LoginUser loginUser = new LoginUser();
        loginUser.setAuthNames(securityUser.getAuthNames());
        loginUser.setUsername(username);

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("username", username);
        Map<String, Object> claimsMap = JSON.parseObject(JSON.toJSONString(loginUser));
        String token = JwtUtil.generateToken2Claims(sysConfig.getSecret(), headerMap, claimsMap, sysConfig.getExpiration());

        // token 存储到redis
        redisTemplate.opsForValue().set("auth:token:" + username, token, sysConfig.getExpiration(), TimeUnit.MILLISECONDS);
        return token;
    }

    private boolean repeatLogin(LoginDto loginDto, LoginDto result) {
        String username = loginDto.getUsername();
        Result<String> getUsernameResult = null;
        if (!LoginTypeEnums.USERNAME_PASSWORD.getCode().equals(loginDto.getLoginType())) {
            getUsernameResult = userFeignClient.selectUsernameBy(loginDto.getLoginType(), loginDto.getPhone());
        }
        if (getUsernameResult != null && getUsernameResult.getData() != null) {
            username = getUsernameResult.getData();
        }
        if (StrUtil.isBlank(username)) {
            return false;
        }
        String cacheToken = redisTemplate.opsForValue().get(LoginConstants.AUTH_TOKEN_PREFIX + username);
        if (StrUtil.isBlank(cacheToken)) {
            return false;
        }
        result.setUsername(username);
        result.setToken(cacheToken);
        result.setSuccess(true);
        return JwtUtil.checkJwt(cacheToken, sysConfig.getSecret());
    }
}
