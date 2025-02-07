package org.monkey.msd.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.security.domain.MsdUser;
import org.monkey.msd.security.service.MsdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * UserController
 *
 * @author cc
 * @since 2025/2/6 17:04
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private MsdUserService userService;

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    @GetMapping("/get/{username}")
    public List<MsdUser> get(@PathVariable("username") String username){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        log.info("hello, " + authentication.getName());
        return userService.getUser(username);
    }
    @GetMapping("/get/async/{username}")
    public List<MsdUser> getAsync(@PathVariable("username") String username) throws ExecutionException, InterruptedException {
        //创建Callable任务，并将其作为任务在单独线程上执行
        Callable<List<MsdUser>> task = () ->{
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            log.info("async hello, " + authentication.getName());
            return userService.getUser(username);
        };
        ExecutorService e = Executors.newCachedThreadPool();
        try{
            DelegatingSecurityContextCallable<List<MsdUser>> contextTask = new DelegatingSecurityContextCallable<>(task);
            return e.submit(contextTask).get();
        }finally {
            e.shutdown();
        }
    }
    @GetMapping("/get/threadpool/{username}")
    public List<MsdUser> getThreadpool(@PathVariable("username") String username) throws ExecutionException, InterruptedException {
        Callable<List<MsdUser>> task = () ->{
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            log.info("threadpool hello, " + authentication.getName());
            return userService.getUser(username);
        };
        ExecutorService e = Executors.newCachedThreadPool();
        //通过DelegatingSecurityContextExecutorService装饰线程池
        e = new DelegatingSecurityContextExecutorService(e);
        try{
            //在提交任务前DelegatingSecurityContextExecutorService会将安全上下文传播到执行此任务的线程
            return e.submit(task).get();
        }finally {
            e.shutdown();
        }
    }
    @GetMapping("/get/threadpool2/{username}")
    public List<MsdUser> getThreadpool2(@PathVariable("username") String username) throws ExecutionException, InterruptedException {
        Callable<List<MsdUser>> task = () ->{
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            log.info("threadpool2 hello, " + authentication.getName());
            return userService.getUser(username);
        };
        try{
            //在提交任务前DelegatingSecurityContextExecutorService会将安全上下文传播到执行此任务的线程
            return taskExecutor.submit(task).get();
        }finally {
            taskExecutor.shutdown();
        }
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
