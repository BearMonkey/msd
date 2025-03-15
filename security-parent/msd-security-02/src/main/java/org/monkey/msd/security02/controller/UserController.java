package org.monkey.msd.security02.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.security02.common.utils.Result;
import org.monkey.msd.security02.common.utils.UserConstants;
import org.monkey.msd.security02.pojo.User;
import org.monkey.msd.security02.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
@Slf4j
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



    @PostMapping("/remove/{usernames}")
    public String remove(@PathVariable("usernames") String usernames){
        try {
            String[] split = usernames.split(",");
            List<User> users = new ArrayList<>();
            for (String s : split) {
                User user1 = new User();
                user1.setUsername(s);
                users.add(user1);
            }

            log.info("users={}", users);
            List<String> collect = users.stream().map(User::getUsername).collect(Collectors.toList());
            boolean remove = userService.remove(Wrappers.<User>lambdaQuery().in(User::getUsername, collect));
            log.info("remove user : {}", remove);
            return "success";
        } catch (Exception e) {
            log.info("remove user error:{}", e.getMessage());
            return e.getMessage();
        }
    }

    @PostMapping("/remove1/{usernames}")
    public String remove1(@PathVariable("usernames") String usernames){
        return userService.addUser();
    }
}
