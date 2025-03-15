package org.monkey.deepseek.dto;

import com.alibaba.fastjson2.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DeepseekError
 *
 * @author cc
 * @since 2025/2/8 16:10
 */
@Data
public class DeepseekTool {

    @ApiModelProperty("tool 的类型。目前仅支持 function。")
    private String tool;

    @ApiModelProperty("function 的功能描述，供模型理解何时以及如何调用该 function。")
    private String function;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
