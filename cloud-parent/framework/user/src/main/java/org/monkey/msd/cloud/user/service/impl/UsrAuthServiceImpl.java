package org.monkey.msd.cloud.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.monkey.msd.cloud.api.framework.dto.usr.UsrAuthDto;
import org.monkey.msd.cloud.user.pojo.UsrAuth;
import org.monkey.msd.cloud.user.mapper.UsrAuthMapper;
import org.monkey.msd.cloud.user.service.IUsrAuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
@Service
public class UsrAuthServiceImpl extends ServiceImpl<UsrAuthMapper, UsrAuth> implements IUsrAuthService {
    @Override
    public Boolean addAuth(UsrAuthDto usrAuthDto) {
        UsrAuth usrAuth = new UsrAuth();
        BeanUtil.copyProperties(usrAuthDto, usrAuth);
        return baseMapper.insert(usrAuth) > 0;
    }

    @Override
    public Boolean delAuth(Long id) {
        LambdaQueryWrapper<UsrAuth> countQuery = new LambdaQueryWrapper<UsrAuth>().eq(UsrAuth::getId, id);
        Long cnt = baseMapper.selectCount(countQuery);
        if (cnt > 0) {
            return baseMapper.deleteById(id) == 1;
        }
        return true;
    }

    @Override
    public List<UsrAuth> listUsrAuth(UsrAuthDto usrAuthDto) {
        Assert.isTrue(usrAuthDto != null, "参数不能为空");

        LambdaQueryWrapper<UsrAuth> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(usrAuthDto.getAuthName()), UsrAuth::getAuthName, usrAuthDto.getAuthName());
        return baseMapper.selectList(queryWrapper);
    }
}
