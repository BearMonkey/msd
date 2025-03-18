package org.monkey.msd.cloud.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.monkey.msd.cloud.auth.pojo.UsrAuth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
@Mapper
public interface UsrAuthMapper extends BaseMapper<UsrAuth> {

}
