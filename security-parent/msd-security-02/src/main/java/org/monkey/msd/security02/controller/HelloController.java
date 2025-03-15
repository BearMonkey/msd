package org.monkey.msd.security02.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 *
 * @author cc
 * @since 2025/2/8 11:41
 */
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello(){
        return "hello world";
    }

    @GetMapping("/xiao")
    public String xiao(){
        return "英雄形态的宵宫姐姐登场！";
    }

    @GetMapping("/giao")
    public String giao(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return "giao,"+name;
    }

}
