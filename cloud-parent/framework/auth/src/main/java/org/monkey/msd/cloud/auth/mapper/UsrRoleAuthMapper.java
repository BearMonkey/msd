package org.monkey.msd.cloud.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.monkey.msd.cloud.auth.pojo.UsrRoleAuth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 角色权限表 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
@Mapper
public interface UsrRoleAuthMapper extends BaseMapper<UsrRoleAuth> {

}
