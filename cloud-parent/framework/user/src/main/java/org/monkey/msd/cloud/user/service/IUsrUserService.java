package org.monkey.msd.cloud.user.service;

import org.monkey.msd.cloud.api.framework.dto.usr.UsrUserDto;
import org.monkey.msd.cloud.user.pojo.UsrUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
public interface IUsrUserService extends IService<UsrUser> {

    /**
     * 增加用户
     * @param usrUserDto UsrUserDto
     * @return boolean
     */
    boolean addUser(UsrUserDto usrUserDto);

    /**
     * 删除用户
     * @param id Long
     * @return boolean
     */
    boolean delUser(Long id);

    /**
     * 查询用户
     * @param usrUserDto UsrUserDto
     * @return List<UsrUser>
     */
    List<UsrUser> listUsrUser(UsrUserDto usrUserDto);
}
