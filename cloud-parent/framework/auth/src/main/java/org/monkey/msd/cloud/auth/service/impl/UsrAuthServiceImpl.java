package org.monkey.msd.cloud.auth.service.impl;

import org.monkey.msd.cloud.auth.pojo.UsrAuth;
import org.monkey.msd.cloud.auth.mapper.UsrAuthMapper;
import org.monkey.msd.cloud.auth.service.IUsrAuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
