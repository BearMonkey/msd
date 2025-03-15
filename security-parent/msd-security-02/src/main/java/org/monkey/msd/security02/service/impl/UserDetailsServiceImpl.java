package org.monkey.msd.security02.service.impl;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.security02.dto.JwtUserDto;
import org.monkey.msd.security02.mapper.AuthorityMapper;
import org.monkey.msd.security02.pojo.Authority;
import org.monkey.msd.security02.pojo.Role;
import org.monkey.msd.security02.pojo.User;
import org.monkey.msd.security02.service.IRoleService;
import org.monkey.msd.security02.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserDetailsServiceImpl
 *
 * @author cc
 * @since 2025/2/7 14:05
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public JwtUserDto loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名获取用户
        User user = userService.getUserByName(username);
        if (user == null ){
            throw new BadCredentialsException("用户名或密码错误");
        }
        List<Role> roles = roleService.loadRolesByUsername(username);
        Set<String> roleInfos = roles.stream().map(Role::getRoleName).collect(Collectors.toSet());
        List<Authority> authorities = authorityMapper.loadPermissionByRoleCode(roleInfos);
        List<String> authorityNames = authorities.stream().map(Authority::getAuthorityName).filter(StrUtil::isNotEmpty).collect(Collectors.toList());
        authorityNames.addAll(roleInfos.stream().map(roleName->"ROLE_"+roleName).collect(Collectors.toList()));
        return new JwtUserDto(user, new HashSet<>(roles), authorityNames);
    }
}
