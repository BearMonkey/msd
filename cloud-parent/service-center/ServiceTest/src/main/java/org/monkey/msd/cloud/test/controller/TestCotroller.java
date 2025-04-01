package org.monkey.msd.cloud.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.cloud.common.dto.Result;
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
@Slf4j
public class TestCotroller {
    @RequestMapping("/test")
    public Result<String> test() {
        log.info("test 服务test请求");
        return Result.success("hello");
    }

    @RequestMapping("/index.html")
    public Result<String> index() {
        return Result.success("index");
    }
}
