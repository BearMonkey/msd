package org.monkey.msd.test.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void future() {
        testController.future();
    }

}