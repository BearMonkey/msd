package org.monkey.msd.cloud.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.monkey.msd.cloud.api.framework.LoginTypeEnums;
import org.monkey.msd.cloud.api.framework.dto.usr.UsrRoleDto;
import org.monkey.msd.cloud.api.framework.dto.usr.UsrUserDto;
import org.monkey.msd.cloud.api.framework.dto.usr.UsrUserRoleDto;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrRole;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrUser;
import org.monkey.msd.cloud.user.mapper.UsrUserMapper;
import org.monkey.msd.cloud.user.service.IUsrRoleService;
import org.monkey.msd.cloud.user.service.IUsrUserRoleService;
import org.monkey.msd.cloud.user.service.IUsrUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
@Service
public class UsrUserServiceImpl extends ServiceImpl<UsrUserMapper, UsrUser> implements IUsrUserService {

    @Autowired
    private IUsrRoleService usrRoleService;

    @Autowired
    private IUsrUserRoleService usrUserRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(UsrUserDto usrUserDto) {
        Assert.notNull(usrUserDto, "参数不能为空");
        List<Long> roleIdList = usrUserDto.getRoleIdList();
        Assert.notEmpty(roleIdList, "角色ID列表不能为空");

        // 校验用户存在
        LambdaQueryWrapper<UsrUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UsrUser::getUsername, usrUserDto.getUsername());
        Long cnt = baseMapper.selectCount(wrapper);
        if (cnt > 0) {
            throw new RuntimeException("用户名已存在");
        }

        // 校验角色存在
        List<UsrRole> usrRoles = usrRoleService.selectRoleByRoleId(new HashSet<>(roleIdList));
        if (CollUtil.isEmpty(usrRoles)) {
            throw new RuntimeException("角色不存在:" + roleIdList);
        }
        List<Long> notExistId = new ArrayList<>();
        Map<Long, List<UsrRole>> roleIdMap = usrRoles.stream().collect(Collectors.groupingBy(UsrRole::getId));
        roleIdList.forEach(roleId -> {
            if (CollUtil.isEmpty(roleIdMap.get(roleId))) {
                notExistId.add(roleId);
            }
        });
        if (CollUtil.isNotEmpty(notExistId)) {
            throw new RuntimeException("角色不存在:" + notExistId);
        }

        // 插入用户
        UsrUser usrUser = new UsrUser();
        BeanUtil.copyProperties(usrUserDto, usrUser);
        boolean insertUser = baseMapper.insert(usrUser) > 0;
        if (!insertUser) {
            throw new RuntimeException("插入用户失败");
        }

        // 插入用户角色关系
        UsrUserRoleDto userRoleDto = new UsrUserRoleDto();
        userRoleDto.setUserId(usrUser.getId());
        userRoleDto.setRoleIdList(roleIdList);
        boolean insertUserRole = usrUserRoleService.addUserRole(userRoleDto);
        if (!insertUserRole) {
            throw new RuntimeException("插入用户角色失败");
        }
        return true;
    }

    @Override
    public boolean delUser(Long id) {
        LambdaQueryWrapper<UsrUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UsrUser::getId, id);
        return baseMapper.delete(wrapper) > 0;
    }

    @Override
    public List<UsrUserDto> listUsrUser(UsrUserDto usrUserDto) {
        Assert.isTrue(usrUserDto != null, "参数不能为空");

        LambdaQueryWrapper<UsrUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(usrUserDto.getUsername()), UsrUser::getUsername, usrUserDto.getUsername());
        wrapper.eq(StrUtil.isNotBlank(usrUserDto.getMobile()), UsrUser::getMobile, usrUserDto.getMobile());
        wrapper.eq(StrUtil.isNotBlank(usrUserDto.getEmail()), UsrUser::getEmail, usrUserDto.getEmail());
        wrapper.eq((usrUserDto.getId() != null), UsrUser::getId, usrUserDto.getId());
        List<UsrUser> usrUsers = baseMapper.selectList(wrapper);
        return BeanUtil.copyToList(usrUsers, UsrUserDto.class);
    }

    @Override
    public List<UsrUser> selectByUsername(String username) {

        List<UsrUser> usrUserList = baseMapper.selectByUsername(username);

        if (CollUtil.isEmpty(usrUserList)) {
            return new ArrayList<>();
        }

        Map<String, UsrUser> usernameMap = usrUserList.stream().collect(Collectors.toMap(UsrUser::getUsername, item->item, (v1, v2) -> v1, HashMap::new));
        UsrUser usrUser = usernameMap.get(username);
        List<UsrRole> roles = usrUser.getRoles();
        if (CollUtil.isEmpty(roles)) {
            return Collections.singletonList(usrUser);
        }

        Set<Long> roleIdSet = roles.stream().map(UsrRole::getId).collect(Collectors.toSet());
        List<UsrRole> roleList = usrRoleService.selectRoleByRoleId(roleIdSet);

        usrUser.setRoles(roleList);
        return Collections.singletonList(usrUser);
    }

    @Override
    public String selectUsernameBy(Integer type, String val) {
        LambdaQueryWrapper<UsrUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(type.equals(LoginTypeEnums.PHONE.getCode()), UsrUser::getMobile, val);
        wrapper.eq(type.equals(LoginTypeEnums.EMAIL.getCode()), UsrUser::getEmail, val);
//        wrapper.eq(type.equals(LoginTypeEnums.WECHAT.getCode()), UsrUser::getId, val);
        List<UsrUser> usrUsers = baseMapper.selectList(wrapper);
        if (CollUtil.isEmpty(usrUsers) || usrUsers.size() > 1) {
            return null;
        }
        return usrUsers.get(0).getUsername();
    }
}
