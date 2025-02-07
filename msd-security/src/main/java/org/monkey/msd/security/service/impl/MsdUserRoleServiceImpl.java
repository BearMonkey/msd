package org.monkey.msd.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.security.domain.MsdRole;
import org.monkey.msd.security.domain.MsdUserRole;
import org.monkey.msd.security.mapper.MsdUserRoleMapper;
import org.monkey.msd.security.service.MsdUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * MsdUserRoleServiceImpl
 *
 * @author cc
 * @since 2025/2/5 11:59
 */
@Service
@Slf4j
public class MsdUserRoleServiceImpl extends ServiceImpl<MsdUserRoleMapper, MsdUserRole> implements MsdUserRoleService {
    @Override
    public void batchInsert(List<MsdUserRole> userRoleList) {
        if (CollectionUtils.isEmpty(userRoleList)) {
            log.info("用户角色关系映射列表为空");
            return;
        }
        try {
            boolean saved = this.saveBatch(userRoleList);
            log.info("增加用户角色关系映射结果:{}", saved);
            if (!saved) {
                throw new RuntimeException("增加用户角色关系映射失败");
            }
        } catch (Exception e) {
            log.error("批量查询角色异常:", e);
            throw e;
        }
    }
}
