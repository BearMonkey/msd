package org.monkey.msd.security.service;

import org.monkey.msd.security.domain.MsdRole;

import java.util.List;

/**
 * MsdRoleService
 *
 * @author cc
 * @since 2025/2/5 11:56
 */
public interface MsdRoleService {
    /**
     * 批量查询角色
     * @param roldIds 角色id列表
     * @return List<MsdRole>
     */
    List<MsdRole> selectAuthByRoleIds(List<Integer> roldIds);
}
