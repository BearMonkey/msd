package org.monkey.msd.cloud.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestCotroller
 *
 * @author cc
 * @since 2025/3/18 13:36
 */
@RestController
@RequestMapping("/test")
public class TestCotroller {
    @RequestMapping("/test")
    public String test() {
        return "hello";
    }
}
