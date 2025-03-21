package org.monkey.msd.cloud.user.service;

import org.monkey.msd.cloud.api.framework.dto.usr.UsrUserRoleDto;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
public interface IUsrUserRoleService extends IService<UsrUserRole> {

    /**
     * 增加用户角色关系
     * @param usrUserRoleDto UsrUserRoleDto
     * @return boolean
     */
    boolean addUserRole(UsrUserRoleDto usrUserRoleDto);

    /**
     * 删除用户角色关系
     * @param id Long
     * @return boolean
     */
    boolean delUserRole(Long id);
}
