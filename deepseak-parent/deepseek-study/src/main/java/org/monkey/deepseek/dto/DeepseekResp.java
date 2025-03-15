package org.monkey.deepseek.dto;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * DeepseekResp
 *
 * @author cc
 * @since 2025/2/8 14:00
 */
@Data
public class DeepseekResp {

    @ApiModelProperty("使用的模型的 ID。您可以使用 deepseek-chat。")
    private String model;

    @ApiModelProperty("错误内容")
    private DeepseekError error;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
