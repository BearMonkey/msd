package org.monkey.msd.security.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.security.domain.*;
import org.monkey.msd.security.mapper.MsdUserAuthMapper;
import org.monkey.msd.security.mapper.MsdUserMapper;
import org.monkey.msd.security.mapper.MsdUserRoleMapper;
import org.monkey.msd.security.service.MsdAuthService;
import org.monkey.msd.security.service.MsdRoleService;
import org.monkey.msd.security.service.MsdUserRoleService;
import org.monkey.msd.security.service.MsdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * MsdUserServiceImpl
 *
 * @author cc
 * @since 2025/2/5 11:57
 */
@Service
@Slf4j
public class MsdUserServiceImpl extends ServiceImpl<MsdUserMapper, MsdUser> implements MsdUserService {
    @Autowired
    private MsdUserMapper userMapper;

    @Autowired
    private MsdUserRoleService userRoleService;

    @Autowired
    private MsdRoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<MsdUser> getUser(String name) {
        return userMapper.queryUserByUsername(name);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(MsdUser user) {
        if (user == null){
            throw new RuntimeException("入参不能为空");
        }
        if (StringUtils.isEmpty(user.getUsername())){
            throw new RuntimeException("用户名不能为空");
        }
        if (StringUtils.isEmpty(user.getPassword())){
            throw new RuntimeException("用户名不能为空");
        }
        Set<MsdRole> roles = user.getRoles();
        if (CollectionUtils.isEmpty(roles)){
            throw new RuntimeException("角色不能为空");
        }
        try {
            List<Integer> roldIds = roles.stream().map(MsdRole::getId).collect(Collectors.toList());
            List<MsdRole> existRoles = roleService.selectAuthByRoleIds(roldIds);
            if (CollectionUtils.isEmpty(existRoles) || roles.size() != existRoles.size()){
                throw new RuntimeException("检查角色是否正确:" + JSONObject.toJSONString(roldIds));
            }

            MsdUser msdUser = this.selectUserByUserName(user.getUsername());
            if (msdUser != null){
                throw new RuntimeException("用户已存在:" + user.getUsername());
            }
            LocalDateTime now = LocalDateTime.now();
            user.setCreateTime(now);
            user.setCreateBy("Auto");
            String passwordNotEncode = user.getPassword();
            String passwordEncoded = passwordEncoder.encode(passwordNotEncode);
            user.setPassword(passwordEncoded);
            userMapper.insert(user);
            List<MsdUserRole> userRoleList = new ArrayList<>();
            for (Integer roldId : roldIds) {
                MsdUserRole userRole = new MsdUserRole();
                userRole.setRoleId(roldId);
                userRole.setUserId(user.getId());
                userRole.setCreateTime(now);
                userRole.setCreateBy("Auto");
                userRoleList.add(userRole);
            }
            userRoleService.batchInsert(userRoleList);
        } catch (Exception e) {
            throw new RuntimeException("添加用户失败," + e.getMessage());
        }
    }

    @Override
    public MsdUser selectUserByUserName(String username) {
        LambdaQueryWrapper<MsdUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MsdUser::getUsername, username);
        List<MsdUser> users = userMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(users)){
            return null;
        }
        return users.get(0);
    }
}
