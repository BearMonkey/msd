package org.monkey.msd.security02.service;

import org.monkey.msd.security02.common.utils.Result;
import org.monkey.msd.security02.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cc
 * @since 2025-02-07
 */
public interface IUserService extends IService<User> {

    User getUserByName(String userName);

    String checkPhoneUnique(User user);

    String checkUserNameUnique(User user);

    Result createUser(User user);
}
