package org.monkey.msd.cloud.user.controller;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.cloud.api.framework.dto.usr.UsrUserDto;
import org.monkey.msd.cloud.common.dto.Result;
import org.monkey.msd.cloud.api.framework.pojo.usr.UsrUser;
import org.monkey.msd.cloud.user.service.IUsrUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
@RestController
@RequestMapping("/usr-user")
@Slf4j
public class UsrUserController {

    @Autowired
    private IUsrUserService usrUserService;

    @PostMapping("/add")
    public Result<Boolean> addUser(@RequestBody UsrUserDto usrUserDto) {
        log.info("添加用户:{}", JSONObject.toJSONString(usrUserDto));
        if (usrUserService.addUser(usrUserDto)) {
            return Result.success(true);
        } else {
            return Result.fail(false);
        }
    }

    @DeleteMapping("/del/{id}")
    public Result<Boolean> delUser(@PathVariable("id") Long id) {
        if (usrUserService.delUser(id)) {
            return Result.success(true);
        } else {
            return Result.fail(false);
        }
    }

    @PostMapping("/list")
    public Result<List<UsrUserDto>> list(@RequestBody UsrUserDto usrUserDto) {
        return Result.success(usrUserService.listUsrUser(usrUserDto));
    }

    @PostMapping("/selectByUsername")
    public Result<List<UsrUser>> selectByUsername(@RequestParam("username") String username) {
        return Result.success(usrUserService.selectByUsername(username));
    }

    @GetMapping("/selectUsernameBy")
    public Result<String> selectUsernameBy(@RequestParam("type")Integer type, @RequestParam("val") String val) {
        return Result.success(usrUserService.selectUsernameBy(type, val));
    }
}
