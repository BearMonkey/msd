package org.monkey.msd.cloud.user.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.monkey.msd.cloud.api.framework.dto.usr.UsrRoleAuthDto;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrRoleAuth;
import org.monkey.msd.cloud.user.mapper.UsrRoleAuthMapper;
import org.monkey.msd.cloud.user.service.IUsrRoleAuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
@Service
public class UsrRoleAuthServiceImpl extends ServiceImpl<UsrRoleAuthMapper, UsrRoleAuth> implements IUsrRoleAuthService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRoleAuth(UsrRoleAuthDto usrRoleAuthDto) {
        Assert.notNull(usrRoleAuthDto, "请求参数不能为空");
        Assert.notNull(usrRoleAuthDto.getRoleId(), "角色id不能为空");
        Assert.notEmpty(usrRoleAuthDto.getAuthIdList(), "权限id列表不能为空");

        List<Long> authIdList = usrRoleAuthDto.getAuthIdList();
        List<UsrRoleAuth> roleAuthList = new ArrayList<>(authIdList.size());
        for (Long authId : authIdList) {
            UsrRoleAuth usrRoleAuth = new UsrRoleAuth();
            usrRoleAuth.setRoleId(usrRoleAuthDto.getRoleId());
            usrRoleAuth.setAuthId(authId);
            roleAuthList.add(usrRoleAuth);
        }
        return this.saveBatch(roleAuthList);
    }

    @Override
    public boolean delRoleAuth(Long id) {
        LambdaQueryWrapper<UsrRoleAuth> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UsrRoleAuth::getId, id);
        return baseMapper.delete(wrapper) > 0;
    }
}
