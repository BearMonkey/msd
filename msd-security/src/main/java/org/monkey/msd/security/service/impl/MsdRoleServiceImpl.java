package org.monkey.msd.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.security.domain.MsdRole;
import org.monkey.msd.security.mapper.MsdRoleMapper;
import org.monkey.msd.security.service.MsdRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * MsdRoleServiceImpl
 *
 * @author cc
 * @since 2025/2/5 11:58
 */
@Service
@Slf4j
public class MsdRoleServiceImpl extends ServiceImpl<MsdRoleMapper, MsdRole> implements MsdRoleService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MsdRole> selectAuthByRoleIds(List<Integer> roldIds) {
        try {
            LambdaQueryWrapper<MsdRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MsdRole::getId, roldIds);
            return baseMapper.selectList(queryWrapper);
        } catch (Exception e) {
            log.error("批量查询角色异常:", e);
            throw e;
        }
    }
}
