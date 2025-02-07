package org.monkey.msd.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.security.domain.MsdRoleAuth;
import org.monkey.msd.security.mapper.MsdRoleAuthMapper;
import org.monkey.msd.security.service.MsdRoleAuthSerice;
import org.springframework.stereotype.Service;

/**
 * MsdRoleAuthServiceImpl
 *
 * @author cc
 * @since 2025/2/5 12:00
 */
@Service
@Slf4j
public class MsdRoleAuthServiceImpl extends ServiceImpl<MsdRoleAuthMapper, MsdRoleAuth> implements MsdRoleAuthSerice {
}
