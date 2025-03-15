package org.monkey.deepseek.dto;

import com.alibaba.fastjson2.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DeepseekResponseFormat
 *
 * @author cc
 * @since 2025/2/8 16:10
 */
@Data
public class DeepseekResponseFormat {

    @ApiModelProperty("type: Must be one of text or json_object, Possible values: [text, json_object] Default value: text")
    private String type;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
