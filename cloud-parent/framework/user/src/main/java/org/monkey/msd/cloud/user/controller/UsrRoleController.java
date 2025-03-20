package org.monkey.msd.cloud.user.controller;


import org.monkey.msd.cloud.api.framework.dto.usr.UsrRoleDto;
import org.monkey.msd.cloud.common.dto.Result;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrRole;
import org.monkey.msd.cloud.user.service.IUsrRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
@RestController
@RequestMapping("/usr-role")
public class UsrRoleController {

    @Autowired
    private IUsrRoleService usrRoleService;

    @PostMapping("/add")
    public Result<Boolean> addRole(@RequestBody UsrRoleDto usrRoleDto) {
        if (usrRoleService.addRole(usrRoleDto)) {
            return Result.success(true);
        } else {
            return Result.fail(false);
        }
    }

    @DeleteMapping("/del/{id}")
    public Result<Boolean> del(@PathVariable("id") Long id) {
        if (usrRoleService.delRole(id)) {
            return Result.success(true);
        } else {
            return Result.fail(false);
        }
    }

    @PostMapping("/list")
    public Result<List<UsrRole>> list(@RequestBody UsrRoleDto usrRoleDto) {
        return Result.success(usrRoleService.listUsrAuth(usrRoleDto));
    }
}
