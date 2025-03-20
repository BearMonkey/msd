package org.monkey.msd.cloud.user.controller;


import org.monkey.msd.cloud.api.framework.dto.usr.UsrUserRoleDto;
import org.monkey.msd.cloud.common.dto.Result;
import org.monkey.msd.cloud.user.service.IUsrUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户角色表 前端控制器
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
@RestController
@RequestMapping("/usr-user-role")
public class UsrUserRoleController {

    @Autowired
    private IUsrUserRoleService usrUserRoleService;

    @PostMapping("/add")
    public Result<Boolean> addUserRole(@RequestBody UsrUserRoleDto usrUserRoleDto) {
        if (usrUserRoleService.addUserRole(usrUserRoleDto)) {
            return Result.success(true);
        } else {
            return Result.fail(false);
        }
    }

    @PostMapping("/del/{id}")
    public Result<Boolean> delUserRole(@PathVariable("id") Long id) {
        if (usrUserRoleService.delUserRole(id)) {
            return Result.success(true);
        } else {
            return Result.fail(false);
        }
    }
}
