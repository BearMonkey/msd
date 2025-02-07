package org.monkey.msd.security.service;

import org.monkey.msd.security.domain.MsdUser;

import java.util.List;

/**
 * MsdUserService
 *
 * @author cc
 * @since 2025/2/5 11:56
 */
public interface MsdUserService {

    List<MsdUser> getUser(String name);

    void addUser(MsdUser user);

    MsdUser selectUserByUserName(String username);
}
