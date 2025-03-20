package org.monkey.msd.cloud.auth.dto;

import lombok.Data;
import org.monkey.msd.cloud.api.framework.dto.usr.UsrAuthDto;
import org.monkey.msd.cloud.api.framework.dto.usr.UsrRoleDto;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrAuth;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrRole;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * SecurityUser
 *
 * @author cc
 * @since 2025/3/20 11:30
 */
@Data
public class SecurityUser implements UserDetails {

    private UsrUser usrUser;

    private List<UsrRole> roles;

    private List<UsrAuth> auths;

    private Set<String> authNames;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authNames.forEach(authorityName->authorities.add(new SimpleGrantedAuthority(authorityName)));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.usrUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.usrUser.getUsername();
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
        return usrUser.getEnabled();
    }
}
