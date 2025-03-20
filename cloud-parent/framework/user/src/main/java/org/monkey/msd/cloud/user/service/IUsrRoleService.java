package org.monkey.msd.cloud.user.service;

import org.monkey.msd.cloud.api.framework.dto.usr.UsrRoleDto;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
public interface IUsrRoleService extends IService<UsrRole> {

    /**
     * 添加角色
     * @param usrRoleDto UsrRoleDto
     * @return boolean
     */
    boolean addRole(UsrRoleDto usrRoleDto);

    /**
     * 删除角色
     * @param id id
     * @return boolean
     */
    boolean delRole(Long id);

    /**
     * 查询权限列表
     * @param usrRoleDto UsrRoleDto
     * @return List<UsrRole>
     */
    List<UsrRole> listUsrAuth(UsrRoleDto usrRoleDto);

    /**
     * 根据角色id查询角色+权限
     * @param roleIdList Set<Long>
     * @return List<UsrRole>
     */
    List<UsrRole> selectRoleByRoleId(Set<Long> roleIdList);
}
