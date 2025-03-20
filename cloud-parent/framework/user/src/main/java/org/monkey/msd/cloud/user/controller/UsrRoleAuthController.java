package org.monkey.msd.cloud.user.controller;


import org.monkey.msd.cloud.api.framework.dto.usr.UsrRoleAuthDto;
import org.monkey.msd.cloud.common.dto.Result;
import org.monkey.msd.cloud.user.service.IUsrRoleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 角色权限表 前端控制器
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
@RestController
@RequestMapping("/usr-role-auth")
public class UsrRoleAuthController {

    @Autowired
    private IUsrRoleAuthService usrRoleAuthService;

    @PostMapping("/add")
    public Result<Boolean> addRoleAuth(@RequestBody UsrRoleAuthDto usrRoleAuthDto) {
        if (usrRoleAuthService.addRoleAuth(usrRoleAuthDto)) {
            return Result.success(true);
        } else {
            return Result.fail(false);
        }
    }

    @PostMapping("/del/{id}")
    public Result<Boolean> delRoleAuth(@PathVariable("id") Long id) {
        if (usrRoleAuthService.delRoleAuth(id)) {
            return Result.success(true);
        } else {
            return Result.fail(false);
        }
    }
}
