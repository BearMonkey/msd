package org.monkey.msd.cloud.oauth2.server.dto;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

/**
 * SecurityUser
 *
 * @author cc
 * @since 2025/4/3 14:32
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<String> getAuthNames() {
        return authNames;
    }

    public void setAuthNames(Set<String> authNames) {
        this.authNames = authNames;
    }
}
