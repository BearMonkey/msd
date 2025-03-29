package org.monkey.msd.cloud.auth.service;

import org.monkey.msd.cloud.api.framework.dto.LoginDto;

/**
 * ILoginService
 *
 * @author cc
 * @since 2025/3/29 15:27
 */
public interface ILoginService {
    LoginDto login(LoginDto loginDto);
}
