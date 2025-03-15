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
public class DeepseekError {

    @ApiModelProperty("message")
    private String message;

    @ApiModelProperty("type")
    private String type;

    @ApiModelProperty("param")
    private String param;

    @ApiModelProperty("code")
    private String code;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
