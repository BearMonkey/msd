package org.monkey.msd.security02.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.security02.common.utils.Result;
import org.monkey.msd.security02.common.utils.UserConstants;
import org.monkey.msd.security02.mapper.RoleMapper;
import org.monkey.msd.security02.mapper.UserRoleMapper;
import org.monkey.msd.security02.pojo.Role;
import org.monkey.msd.security02.pojo.User;
import org.monkey.msd.security02.mapper.UserMapper;
import org.monkey.msd.security02.pojo.UserRole;
import org.monkey.msd.security02.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-02-07
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserByName(String userName) {
        return userMapper.queryUserByUsername(userName);
    }

    @Override
    public String checkPhoneUnique(User user) {
        Long userId = ObjectUtil.isEmpty(user.getId()) ? -1: user.getId();
        User info = userMapper.checkPhoneUnique(user.getMobile());
        if (ObjectUtil.isNotEmpty(info) && !info.getId().equals(userId)) {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    @Override
    public String checkUserNameUnique(User user) {
        Long userId = ObjectUtil.isEmpty(user.getId()) ? -1: user.getId();
        User info = userMapper.checkUsernameUnique(user.getUsername());
        if (ObjectUtil.isNotEmpty(info) && !info.getId().equals(userId))
        {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    @Override
    public Result createUser(User user) {
        Set<Role> roles = user.getRoles();
        if(CollUtil.isNotEmpty(roles)){
            String passwordNotEncode = user.getPassword();
            String passwordEncode = passwordEncoder.encode(passwordNotEncode);
            user.setPassword(passwordEncode);
            userMapper.insert(user);
            Stream<Long> roleIds = roles.stream().map(Role::getId);
            roleIds.forEach(roleId->{
                Role role = roleMapper.selectById(roleId);
                if(role != null){
                    Long userId = user.getId();
                    UserRole userRole = new UserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(roleId);
                    userRoleMapper.insert(userRole);
                }
            });
            return Result.ok().message("添加成功");
        }

        return Result.error().message("添加失败");
    }

    @Override
    @Transactional
    public String addUser() {
        List<String> list = CollUtil.newArrayList();
        list.add("1111");
        list.add("2222");
        list.add("3333");
        list.add("4444");
        for (String item : list) {
            test(item);
            /*try {
            } catch (Exception e) {
                log.info("异常11111：", e);
            }*/
        }
        log.info("finished");
        return "";
    }

    @Override
    @Transactional
    public void test(String num) {
        try {
            System.out.println(num);
            if (num.equals("3333")) {
                throw new IllegalArgumentException("3333 不是期望数字");
            }
        } catch (Exception e) {
            log.info("异常：", e);
            throw e;
        }
    }
}
