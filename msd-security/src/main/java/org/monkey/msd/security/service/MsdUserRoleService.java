package org.monkey.msd.security.service;

import org.monkey.msd.security.domain.MsdUserRole;

import java.util.List;

/**
 * MsdUserRoleService
 *
 * @author cc
 * @since 2025/2/5 11:56
 */
public interface MsdUserRoleService {
    /**
     * 查询用户角色映射关系
     * @param userRoleList userRoleList
     */
    void batchInsert(List<MsdUserRole> userRoleList);
}
