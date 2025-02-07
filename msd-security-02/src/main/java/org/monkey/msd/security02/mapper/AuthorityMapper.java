package org.monkey.msd.security02.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.monkey.msd.security02.pojo.Authority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2025-02-07
 */
@Mapper
public interface AuthorityMapper extends BaseMapper<Authority> {
    /**
     * 通过角色名称list查询菜单权限
     */
    List<Authority> loadPermissionByRoleCode(@Param("roleInfos") Set<String> roleInfos);
}
