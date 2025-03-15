package org.monkey.msd.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.monkey.msd.security.domain.MsdRole;

import java.util.List;

/**
 * MsdRoleMapper
 *
 * @author cc
 * @since 2025/2/5 11:51
 */
@Mapper
public interface MsdRoleMapper extends BaseMapper<MsdRole> {
    List<MsdRole> selectAuthByRoleId(@Param("roleId") Integer roleId);

    @Select("select * from usr_role")
    List<MsdRole> selectAll();
}
