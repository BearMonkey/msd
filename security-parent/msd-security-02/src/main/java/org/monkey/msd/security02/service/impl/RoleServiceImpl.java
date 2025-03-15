package org.monkey.msd.security02.service.impl;

import cn.hutool.core.collection.CollUtil;
import org.monkey.msd.security02.mapper.AuthorityMapper;
import org.monkey.msd.security02.mapper.RoleAuthorityMapper;
import org.monkey.msd.security02.pojo.Authority;
import org.monkey.msd.security02.pojo.Role;
import org.monkey.msd.security02.mapper.RoleMapper;
import org.monkey.msd.security02.pojo.RoleAuthority;
import org.monkey.msd.security02.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-02-07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;

    @Override
    public List<Role> queryAllRoleByRoleName(){
        return roleMapper.queryAllRoleByRoleName();
    }

    @Override
    public void saveRole(Role role){
        Set<Authority> authorities = role.getAuthorities();
        if(CollUtil.isNotEmpty(authorities)){
            Stream<Long> authorityIds = authorities.stream().map(Authority::getId);
            roleMapper.insert(role);
            authorityIds.forEach(authorityId->{
                Authority authority = authorityMapper.selectById(authorityId);
                if(authority != null){
                    RoleAuthority roleAuthority = new RoleAuthority();
                    roleAuthority.setRoleId(role.getId());
                    roleAuthority.setAuthorityId(authorityId);
                    roleAuthorityMapper.insert(roleAuthority);
                }
            });
        }
    }

    @Override
    public List<Role> loadRolesByUsername(String username){
        return roleMapper.loadRolesByUsername(username);
    }
}
