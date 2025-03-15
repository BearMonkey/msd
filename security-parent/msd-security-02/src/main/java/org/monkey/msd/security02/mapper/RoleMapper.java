package org.monkey.msd.security02.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.monkey.msd.security02.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2025-02-07
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> queryAllRoleByRoleName();
    /**
     * 根据用户名获取角色
     *
     * @param username
     * @return List<SysRole>
     */
    List<Role> loadRolesByUsername(@Param("username") String username);
}
