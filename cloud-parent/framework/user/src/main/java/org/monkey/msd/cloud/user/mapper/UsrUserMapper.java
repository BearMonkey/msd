package org.monkey.msd.cloud.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

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

    List<UsrUser> selectByUsername(@Param("username") String username);
}
