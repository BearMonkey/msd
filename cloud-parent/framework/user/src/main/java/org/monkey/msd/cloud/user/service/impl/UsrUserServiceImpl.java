package org.monkey.msd.cloud.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.monkey.msd.cloud.api.framework.dto.usr.UsrUserDto;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrRole;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrUser;
import org.monkey.msd.cloud.user.mapper.UsrUserMapper;
import org.monkey.msd.cloud.user.service.IUsrRoleService;
import org.monkey.msd.cloud.user.service.IUsrUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Override
    public boolean addUser(UsrUserDto usrUserDto) {
        UsrUser usrUser = new UsrUser();
        BeanUtil.copyProperties(usrUserDto, usrUser);
        return baseMapper.insert(usrUser) > 0;
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
}
