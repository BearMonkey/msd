package org.monkey.msd.security02.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.monkey.msd.security02.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2025-02-07
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User queryUserByUsername(String username);
    User checkUsernameUnique(String userName);
    User checkPhoneUnique(String phone);
}
