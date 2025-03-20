package org.monkey.msd.cloud.user.service;

import org.monkey.msd.cloud.api.framework.dto.usr.UsrRoleAuthDto;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrRoleAuth;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色权限表 服务类
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
public interface IUsrRoleAuthService extends IService<UsrRoleAuth> {

    /**
     * 增加角色权限绑定关系
     * @param usrRoleAuthDto UsrRoleAuthDto
     * @return boolean
     */
    boolean addRoleAuth(UsrRoleAuthDto usrRoleAuthDto);

    /**
     * 删除角色权限绑定关系
     * @param id Long
     * @return boolean
     */
    boolean delRoleAuth(Long id);
}
