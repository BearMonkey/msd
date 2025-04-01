package org.monkey.msd.tracing.service2.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Srv2Controller
 *
 * @author cc
 * @since 2025/4/1 10:29
 */
@RestController
@RequestMapping("/srv2")
public class Srv2Controller {
    private static final Logger log = LoggerFactory.getLogger(Srv2Controller.class);

    @RequestMapping("/hello")
    public String hello() {
        log.info("srv2 hello===================");
        return "hello srv2";
    }
}
