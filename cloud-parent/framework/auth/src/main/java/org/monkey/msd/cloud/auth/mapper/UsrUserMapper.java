package org.monkey.msd.cloud.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.monkey.msd.cloud.auth.pojo.UsrUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
@Mapper
public interface UsrUserMapper extends BaseMapper<UsrUser> {

}
