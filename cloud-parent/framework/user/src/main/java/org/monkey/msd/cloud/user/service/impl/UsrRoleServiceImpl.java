package org.monkey.msd.cloud.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.monkey.msd.cloud.api.framework.dto.usr.UsrRoleDto;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrRole;
import org.monkey.msd.cloud.user.mapper.UsrRoleMapper;
import org.monkey.msd.cloud.user.service.IUsrRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
@Service
public class UsrRoleServiceImpl extends ServiceImpl<UsrRoleMapper, UsrRole> implements IUsrRoleService {
    @Override
    public boolean addRole(UsrRoleDto usrRoleDto) {
        UsrRole usrRole = new UsrRole();
        BeanUtil.copyProperties(usrRoleDto, usrRole);
        return baseMapper.insert(usrRole) > 0;
    }

    @Override
    public boolean delRole(Long id) {
        LambdaQueryWrapper<UsrRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UsrRole::getId, id);
        return baseMapper.delete(wrapper) > 0;
    }

    @Override
    public List<UsrRole> listUsrAuth(UsrRoleDto usrRoleDto) {
        Assert.isTrue(usrRoleDto != null, "参数不能为空");

        LambdaQueryWrapper<UsrRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(usrRoleDto.getRoleName()), UsrRole::getRoleName, usrRoleDto.getRoleName());
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<UsrRole> selectRoleByRoleId(Set<Long> roleIdList) {
        Assert.notEmpty(roleIdList, "角色id列表不能为空");
        return baseMapper.selectRoleByRoleId(roleIdList);
    }
}
