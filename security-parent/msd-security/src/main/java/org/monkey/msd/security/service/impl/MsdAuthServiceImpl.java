package org.monkey.msd.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.security.domain.MsdAuth;
import org.monkey.msd.security.mapper.MsdAuthMapper;
import org.monkey.msd.security.service.MsdAuthService;
import org.springframework.stereotype.Service;

/**
 * MsdAuthServiceImpl
 *
 * @author cc
 * @since 2025/2/5 11:58
 */
@Service
@Slf4j
public class MsdAuthServiceImpl extends ServiceImpl<MsdAuthMapper, MsdAuth> implements MsdAuthService {
}
