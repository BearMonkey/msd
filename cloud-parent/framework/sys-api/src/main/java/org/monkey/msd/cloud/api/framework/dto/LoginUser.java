package org.monkey.msd.cloud.api.framework.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * LoginUser
 *
 * @author cc
 * @since 2025/3/28 10:21
 */
@Data
public class LoginUser {
    private String username;
    private String password;
    private Set<String> authNames;
}
