package org.monkey.msd.cloud.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
@Mapper
public interface UsrRoleMapper extends BaseMapper<UsrRole> {

    List<UsrRole> selectRoleByRoleId(@Param("roleIdList") Set<Long> roleIdList);
}
