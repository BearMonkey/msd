package org.monkey.msd.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.monkey.msd.security.domain.MsdUser;

import java.util.List;

/**
 * MsdUserMapper
 *
 * @author cc
 * @since 2024/12/16 14:27
 */
@Mapper
public interface MsdUserMapper extends BaseMapper<MsdUser> {
    /**
     * 根据username查询用户
     * @param username username
     * @return List<MsdUser>
     */
    List<MsdUser> queryUserByUsername(String username);
}
