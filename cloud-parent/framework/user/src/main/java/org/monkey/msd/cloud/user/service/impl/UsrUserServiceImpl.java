package org.monkey.msd.cloud.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.monkey.msd.cloud.api.framework.dto.usr.UsrUserDto;
import org.monkey.msd.cloud.user.pojo.UsrRole;
import org.monkey.msd.cloud.user.pojo.UsrUser;
import org.monkey.msd.cloud.user.mapper.UsrUserMapper;
import org.monkey.msd.cloud.user.service.IUsrUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

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
    public List<UsrUser> listUsrUser(UsrUserDto usrUserDto) {
        Assert.isTrue(usrUserDto != null, "参数不能为空");

        LambdaQueryWrapper<UsrUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(usrUserDto.getUsername()), UsrUser::getUsername, usrUserDto.getUsername());
        wrapper.eq(StrUtil.isNotBlank(usrUserDto.getMobile()), UsrUser::getMobile, usrUserDto.getMobile());
        wrapper.eq(StrUtil.isNotBlank(usrUserDto.getEmail()), UsrUser::getEmail, usrUserDto.getEmail());
        wrapper.eq((usrUserDto.getId() != null), UsrUser::getId, usrUserDto.getId());
        return baseMapper.selectList(wrapper);
    }
}
