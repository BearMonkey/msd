package org.monkey.msd.security02.controller;


import org.monkey.msd.security02.common.utils.Result;
import org.monkey.msd.security02.common.utils.UserConstants;
import org.monkey.msd.security02.pojo.User;
import org.monkey.msd.security02.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cc
 * @since 2025-02-07
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/create")
    public Result createUser(@RequestBody User user){
        if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return Result.error().message("手机号已存在");
        }
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkUserNameUnique(user))) {
            return Result.error().message("用户名已存在");
        }
        return userService.createUser(user);
    }

    @GetMapping("/hello")
    public String hello(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return "Hello," + authentication.getName() + "!";
    }

    @GetMapping("/error")
    public String error(){
        return "403 error";
    }
}
