package org.monkey.msd.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.monkey.msd.security.domain.MsdAuth;

import java.util.List;
import java.util.Set;

/**
 * MsdAuthMapper
 *
 * @author cc
 * @since 2024/12/16 15:09
 */
@Mapper
public interface MsdAuthMapper extends BaseMapper<MsdAuth> {
    Set<MsdAuth> selectAuthByRoleId(@Param("roleId") Integer roleId);
}
