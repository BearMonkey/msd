package org.monkey.msd.security.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.security.domain.*;
import org.monkey.msd.security.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * MsdUserDetailsService
 *
 * @author cc
 * @since 2024/12/16 14:38
 */
@Service
@Slf4j
public class MsdUserDetailsService extends ServiceImpl<MsdUserMapper, MsdUser> implements UserDetailsService {

    @Autowired
    private MsdUserMapper msdUserMapper;

    @Autowired
    private MsdAuthMapper msdAuthMapper;

    @Autowired
    private MsdUserAuthMapper msdUserAuthMapper;

    @Autowired
    private MsdUserRoleMapper userRoleMapper;

    @Autowired
    private MsdRoleMapper roleMapper;

    @Autowired
    private MsdAuthMapper authMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<MsdUser> users = msdUserMapper.queryUserByUsername(username);
        for (MsdUser user : users) {
            Set<MsdRole> roles = user.getRoles();
            if (CollectionUtils.isEmpty(roles)) {
                continue;
            }
            for (MsdRole role : roles) {
                role.setAuths(authMapper.selectAuthByRoleId(role.getId()));
            }
        }
        log.info("user:{}", JSONObject.toJSONString(users));
        return users.stream().findFirst().orElseThrow(()->new UsernameNotFoundException("User Not Found"));
    }

    /**
     * 增加新用户
     * @param user MsdUser
     * @return boolean
     */
    @Override
    @Transactional
    public boolean save(MsdUser user) {
        log.info("4444444");
        try {
            String passwordNotEncode = user.getPassword();
            String passwordEncoded = passwordEncoder.encode(passwordNotEncode);
            user.setPassword(passwordEncoded);
            msdUserMapper.insert(user);
            Set<MsdRole> roles = user.getRoles();
            Set<Integer> roldIds = roles.stream().map(MsdRole::getId).collect(Collectors.toSet());
            roldIds.forEach(roldId -> {
                MsdRole msdRole = roleMapper.selectById(roldId);
                if (msdRole == null) {
                    return;
                }
                MsdUserRole msdUserRole = new MsdUserRole();
                msdUserRole.setUserId(user.getId());
                msdUserRole.setRoleId(roldId);
                msdUserRole.setCreateTime(LocalDateTime.now());
                msdUserRole.setCreateBy("Auto");
            });
            /*Set<MsdAuth> authorities = (Set<MsdAuth>) user.getAuthorities();
            Set<Integer> authorityIds = authorities.stream().map(MsdAuth::getId).collect(Collectors.toSet());
            authorityIds.forEach(id -> {
                MsdAuth authority = msdAuthMapper.selectById(id);
                if(authority != null){
                    Integer userId = user.getId();
                    MsdUserAuth userAuthority = new MsdUserAuth();
                    userAuthority.setUserId(userId);
                    userAuthority.setAuthId(id);
                    msdUserAuthMapper.insert(userAuthority);
                }
            });*/
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }
}
