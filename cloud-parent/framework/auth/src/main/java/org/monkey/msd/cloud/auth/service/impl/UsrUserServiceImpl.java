package org.monkey.msd.cloud.auth.service.impl;

import org.monkey.msd.cloud.auth.pojo.UsrUser;
import org.monkey.msd.cloud.auth.mapper.UsrUserMapper;
import org.monkey.msd.cloud.auth.service.IUsrUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
