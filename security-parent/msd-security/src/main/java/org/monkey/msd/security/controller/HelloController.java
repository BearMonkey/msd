package org.monkey.msd.security.controller;

import cn.hutool.core.codec.Base64;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.security.domain.MsdUser;
import org.monkey.msd.security.dto.Dto;
import org.monkey.msd.security.service.MsdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * HelloController
 *
 * @author cc
 * @since 2024/12/13 14:53
 */
@RestController
@Slf4j
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private MsdUserService userService;

    @GetMapping("/hello")
    public String hello(){
        return "hello!";
    }

    @GetMapping("/get/{name}")
    public List<MsdUser> get(@PathVariable("name") String name){
        return userService.getUser(name);
    }

    @PostMapping("/add")
    public String addUser(@RequestBody MsdUser user){
        try {
            userService.addUser(user);
            return "success";
        } catch (Exception e) {
            log.info("add user error:{}", e.getMessage());
            return e.getMessage();
        }
    }

}
