package org.monkey.msd.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.monkey.msd.security.domain.MsdUserAuth;

/**
 * MsdUserAuthMapper
 *
 * @author cc
 * @since 2024/12/16 15:09
 */
@Mapper
public interface MsdUserAuthMapper extends BaseMapper<MsdUserAuth> {
}
