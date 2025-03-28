package org.monkey.msd.test.controller;

import cn.hutool.core.collection.CollUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TestControllerTest
 *
 * @author cc
 * @since 2025/3/17 11:40
 */
@SpringBootTest
public class TestControllerTest {
    @Autowired
    private TestController testController;

    @Test
    public void future() throws InterruptedException {
        testController.future();
    }

    @Test
    public void testStream() {

        /*List<String> noList = Collections.singletonList("1");
        List<String> existList = new ArrayList<>();
        existList.add("1");
        noList.remove("1");
        noList.removeAll(existList);
        if (CollUtil.isEmpty(noList)) {
            System.out.println("1111");
        } else {
            System.out.println("2222");
        }*/
    }

}