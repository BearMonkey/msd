package org.monkey.msd.test.controller;

import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.MDC;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.*;

/**
 * TestController
 *
 * @author cc
 * @since 2025/3/17 11:24
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/test")
    public String future() throws InterruptedException {

        extracted();

        return "ok";
    }

    @GetMapping("/sleep")
    public String sleep() throws InterruptedException {
        try {
            long s = System.currentTimeMillis();
            Thread.sleep(1200);
            long e = System.currentTimeMillis();
            return "sleep finished, s=" + s + ", e=" + e;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private void extracted() throws InterruptedException {
        List<List<Integer>> partition = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i + 1);
            if (list.size() == 10) {
                partition.add(list);
                list = new ArrayList<>();
            }
        }
        if (!CollectionUtils.isEmpty(list)) {
            partition.add(list);
        }


        ThreadPoolExecutor exec = new ThreadPoolExecutor(5, 5, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        List<Callable<String>> tasks = new ArrayList<Callable<String>>();
        int pushSize = partition.size();
        for (int i = 0; i < pushSize; i++) {
            String reqId = i + "";
            List<Integer> dtos = partition.get(i);
            //构建新增数据
            Callable<String> task = () -> {
                try {
                    MDC.put("TID", reqId);
                    doHandle(dtos, reqId);
                    return "^^^" + reqId + "^……^执行完毕";
                } catch (CancellationException e) {
                    System.out.println("任务被取消");
                    return "^^^" + reqId + "^……^任务被取消";
                } finally {
                    MDC.remove("TID");
                }
            };
            // 这里提交的任务容器列表和返回的Future列表存在顺序对应的关系
            tasks.add(task);
        }
        List<Future<String>> results = exec.invokeAll(tasks, 10, TimeUnit.SECONDS);
        for (Future<String> future : results) {
            try {
                System.out.println("[SysnShopeeOrderTask-拉取订单同步数据]---->执行结果：" + future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 关闭线程池
        exec.shutdown();
    }

    private void doHandle(List<Integer> dtos, String reqId) {
        System.out.println(reqId + ",nums:" + dtos);
        long start = System.currentTimeMillis();
        System.out.println(reqId + ",开始: " + start);
        String result;
        try {
            result = HttpUtil.get("http://localhost:8080/test/sleep");
            System.out.println("handle结束：" + result);
        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
            System.out.println("handle异常，" + result);
        }

    }
}
