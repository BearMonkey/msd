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
public class DeepseekStreamOption {

    // 如果设置为 true，在流式消息最后的 data: [DONE] 之前将会传输一个额外的块。此块上的 usage 字段显示整个请求的 token 使用统计信息，而 choices 字段将始终是一个空数组。所有其他块也将包含一个 usage 字段，但其值为 null。
    @ApiModelProperty("流式输出相关选项。只有在 stream 参数为 true 时，才可设置此参数")
    private Boolean include_usage;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
