package org.monkey.msd.cloud.api.framework.dto;

import lombok.Data;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrAuth;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrRole;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * SecurityUser
 *
 * @author cc
 * @since 2025/3/20 11:30
 */
@Data
public class SecurityUser implements UserDetails {

    private Long id;

    private String username;

    private String password;

    private boolean enabled;

    private Set<String> authNames;

    @Override
    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authNames.forEach(authorityName->authorities.add(new SimpleGrantedAuthority(authorityName)));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
