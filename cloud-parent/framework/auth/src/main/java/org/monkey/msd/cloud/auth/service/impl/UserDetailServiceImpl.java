package org.monkey.msd.cloud.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.cloud.api.framework.feign.UserFeignClient;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrAuth;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrRole;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrUser;
import org.monkey.msd.cloud.api.framework.dto.SecurityUser;
import org.monkey.msd.cloud.auth.exception.AuthException;
import org.monkey.msd.cloud.common.constants.CommonResult;
import org.monkey.msd.cloud.common.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserDetailServiceImpl
 *
 * @author cc
 * @since 2025/3/20 11:37
 */
@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StrUtil.isBlank(username)) {
            throw new UsernameNotFoundException("用户名不能为空");
        }
        Result<List<UsrUser>> listUserResult = userFeignClient.selectByUsername(username);
        if (listUserResult == null || !CommonResult.SUCCESS.equals(listUserResult.getCode())) {
            throw new AuthException("查询用户失败");
        }
        List<UsrUser> usrUserDtoList = listUserResult.getData();
        if (CollUtil.isEmpty(usrUserDtoList)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        UsrUser usrUser = usrUserDtoList.get(0);
        log.info("查询用户:{}", JSON.toJSONString(usrUser));

        SecurityUser securityUser = new SecurityUser();
        securityUser.setId(usrUser.getId());
        securityUser.setUsername(usrUser.getUsername());
        securityUser.setPassword(usrUser.getPassword());
        securityUser.setEnabled(usrUser.getEnabled());
        List<UsrRole> roles = usrUser.getRoles();

        List<UsrAuth> auths = new ArrayList<>();
        roles.forEach(role -> auths.addAll(role.getAuths()));

        Set<String> authSet = new HashSet<>();
        authSet.addAll(auths.stream().map(UsrAuth::getAuthName).collect(Collectors.toSet()));
        authSet.addAll(roles.stream().map(role -> "ROLE_" + role.getRoleName()).collect(Collectors.toSet()));
        securityUser.setAuthNames(authSet);
        return securityUser;
    }
}
