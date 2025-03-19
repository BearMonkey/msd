package org.monkey.msd.cloud.user.service;

import org.monkey.msd.cloud.api.framework.dto.usr.UsrAuthDto;
import org.monkey.msd.cloud.common.dto.Result;
import org.monkey.msd.cloud.user.pojo.UsrAuth;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
public interface IUsrAuthService extends IService<UsrAuth> {

    /**
     * 增加权限
     * @param usrAuthDto UsrAuthDto
     * @return Boolean
     */
    Boolean addAuth(UsrAuthDto usrAuthDto);

    Boolean delAuth(Long id);

    List<UsrAuth> listUsrAuth(UsrAuthDto usrAuthDto);
}
