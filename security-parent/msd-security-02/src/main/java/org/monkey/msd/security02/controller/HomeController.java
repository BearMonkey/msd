package org.monkey.msd.security02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * HomeController
 *
 * @author cc
 * @since 2025/2/7 13:57
 */
@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }
}
