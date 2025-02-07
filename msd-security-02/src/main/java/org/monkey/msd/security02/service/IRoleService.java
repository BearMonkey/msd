package org.monkey.msd.security02.service;

import org.monkey.msd.security02.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cc
 * @since 2025-02-07
 */
public interface IRoleService extends IService<Role> {

    List<Role> queryAllRoleByRoleName();

    void saveRole(Role role);

    List<Role> loadRolesByUsername(String username);
}
