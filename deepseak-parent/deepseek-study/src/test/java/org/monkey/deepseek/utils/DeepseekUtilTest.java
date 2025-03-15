package org.monkey.deepseek.utils;


import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;
import org.monkey.deepseek.DeepseekModelEnums;
import org.monkey.deepseek.dto.DeepseekReq;
import org.monkey.deepseek.dto.DeepseekMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DeepseekUtilTest
 *
 * @author cc
 * @since 2025/2/8 14:09
 */
public class DeepseekUtilTest {

    @Test
    public void deepseek() {
        DeepseekReq deepseekReq = new DeepseekReq();
        deepseekReq.setModel(DeepseekModelEnums.CHAT.getModel());
        List<DeepseekMessage> messages = new ArrayList<>();

        /*DeepseekMessage message0 = new DeepseekMessage();
        message0.setRole("system");
        message0.setContent("You are a helpful assistant.");
        messages.add(message0);*/

        DeepseekMessage message1 = new DeepseekMessage();
        message1.setRole("user");
        message1.setContent("用Java语言实现9x9乘法表");
        messages.add(message1);
        deepseekReq.setMessages(messages);
        deepseekReq.setStream(false);
        DeepseekUtil.deepseek(deepseekReq);
    }

    @Test
    public void test() {
        String str1 = "";
    }
}