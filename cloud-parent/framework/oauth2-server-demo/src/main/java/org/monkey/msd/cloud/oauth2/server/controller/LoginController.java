package org.monkey.msd.cloud.oauth2.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * LoginController
 *
 * @author cc
 * @since 2025/4/3 14:09
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // 返回 login.html 模板
    }
}
