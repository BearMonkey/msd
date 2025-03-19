package org.monkey.msd.cloud.user.controller;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.cloud.api.framework.dto.usr.UsrAuthDto;
import org.monkey.msd.cloud.common.dto.Result;
import org.monkey.msd.cloud.user.pojo.UsrAuth;
import org.monkey.msd.cloud.user.service.IUsrAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
@RestController
@RequestMapping("/usr-auth")
@Slf4j
public class UsrAuthController {

    @Autowired
    private IUsrAuthService usrAuthService;

    @PostMapping("/add")
    public Result<Boolean> addAuth(@RequestBody UsrAuthDto usrAuthDto) {
        log.info("添加权限:{}", JSONObject.toJSONString(usrAuthDto));
        if (usrAuthService.addAuth(usrAuthDto)) {
            return Result.success(true);
        } else {
            return Result.fail(false);
        }
    }

    @PostMapping("/del/{id}")
    public Result<Boolean> addAuth(@PathVariable("id") Long id) {
        if (usrAuthService.delAuth(id)) {
            return Result.success(true);
        } else {
            return Result.fail(false);
        }
    }

    @PostMapping("/list")
    public Result<List<UsrAuth>> list(@RequestBody UsrAuthDto usrAuthDto) {
        return Result.success(usrAuthService.listUsrAuth(usrAuthDto));
    }
}
