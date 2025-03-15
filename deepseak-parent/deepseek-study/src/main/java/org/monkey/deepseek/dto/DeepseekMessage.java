package org.monkey.deepseek.dto;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

/**
 * DeepseekMessage
 *
 * @author cc
 * @since 2025/2/8 14:01
 */
@Data
public class DeepseekMessage {

    private String role;

    private String content;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
