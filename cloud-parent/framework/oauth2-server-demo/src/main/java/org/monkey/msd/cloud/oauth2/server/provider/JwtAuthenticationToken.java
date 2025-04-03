package org.monkey.msd.cloud.oauth2.server.provider;

import org.monkey.msd.cloud.oauth2.server.dto.SecurityUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * JwtAuthenticationToken 自定义 Authentication 对象
 *
 * @author cc
 * @since 2025/3/31 11:47
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private String credentials;
    private SecurityUser principal;

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public JwtAuthenticationToken(String token, SecurityUser principal) {
        super(null);
        this.credentials = token;
        this.principal = principal;
        setAuthenticated(false);
    }

    public JwtAuthenticationToken(SecurityUser principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = null;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
