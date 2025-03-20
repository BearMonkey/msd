package org.monkey.msd.cloud.user.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.monkey.msd.cloud.api.framework.dto.usr.UsrUserRoleDto;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrUserRole;
import org.monkey.msd.cloud.user.mapper.UsrUserRoleMapper;
import org.monkey.msd.cloud.user.service.IUsrUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
@Service
public class UsrUserRoleServiceImpl extends ServiceImpl<UsrUserRoleMapper, UsrUserRole> implements IUsrUserRoleService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUserRole(UsrUserRoleDto usrUserRoleDto) {
        Assert.notNull(usrUserRoleDto, "请求参数不能为空");
        Assert.notNull(usrUserRoleDto.getUserId(), "用户id不能为空");
        Assert.notEmpty(usrUserRoleDto.getRoleIdList(), "角色id列表不能为空");

        List<Long> roleIdList = usrUserRoleDto.getRoleIdList();
        List<UsrUserRole> userRoleList = new ArrayList<>();
        for (Long roleId : roleIdList) {
            UsrUserRole usrUserRole = new UsrUserRole();
            usrUserRole.setUserId(usrUserRoleDto.getUserId());
            usrUserRole.setRoleId(roleId);
            userRoleList.add(usrUserRole);
        }
        return this.saveBatch(userRoleList);
    }

    @Override
    public boolean delUserRole(Long id) {
        LambdaQueryWrapper<UsrUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UsrUserRole::getId, id);
        return baseMapper.delete(wrapper) > 0;
    }
}
