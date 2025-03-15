package org.monkey.deepseek.dto;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * DeepseekReq
 *
 * @author cc
 * @since 2025/2/8 14:00
 */
@Data
public class DeepseekReq {

    @ApiModelProperty("使用的模型的 ID。您可以使用 deepseek-chat。")
    private String model;

    @ApiModelProperty("对话的消息列表。")
    private List<DeepseekMessage> messages = new ArrayList<>();

    @ApiModelProperty("介于 -2.0 和 2.0 之间的数字。如果该值为正，那么新 token 会根据其在已有文本中的出现频率受到相应的惩罚，降低模型重复相同内容的可能性 Possible values: >= -2 and <= 2 Default value: 0")
    @JSONField(name = "frequency_penalty")
    private Double frequencyPenalty;

    @ApiModelProperty("介于 1 到 8192 间的整数，限制一次请求中模型生成 completion 的最大 token 数。输入 token 和输出 token 的总长度受模型的上下文长度的限制 Possible values: > 1 如未指定 max_tokens参数，默认使用 4096")
    @JSONField(name = "max_tokens")
    private Integer maxTokens;

    @ApiModelProperty("介于 -2.0 和 2.0 之间的数字。如果该值为正，那么新 token 会根据其是否已在已有文本中出现受到相应的惩罚，从而增加模型谈论新主题的可能性。 Possible values: >= -2 and <=  Default value: 0")
    @JSONField(name = "presence_penalty")
    private Double presencePenalty;

    @ApiModelProperty("一个 string 或最多包含 16 个 string 的 list，在遇到这些词时，API 将停止生成更多的 token。")
    private Object stop;

    @ApiModelProperty("流式输出相关选项。只有在 stream 参数为 true 时，才可设置此参数。")
    private DeepseekStreamOption stream_options;

    @ApiModelProperty("采样温度，介于 0 和 2 之间。更高的值，如 0.8，会使输出更随机，而更低的值，如 0.2，会使其更加集中和确定。 我们通常建议可以更改这个值或者更改 top_p，但不建议同时对两者进行修改")
    private Double temperature;

    @ApiModelProperty("采样温度，介于 0 和 2 之间。更高的值，如 0.8，会使输出更随机，而更低的值，如 0.2，会使其更加集中和确定。 我们通常建议可以更改这个值或者更改 top_p，但不建议同时对两者进行修改 Possible values: <= 1 Default value: 1")
    @JSONField(name = "top_p")
    private Double topp;

    // 注意: 使用 JSON 模式时，你还必须通过系统或用户消息指示模型生成 JSON。否则，模型可能会生成不断的空白字符，直到生成达到令牌限制，从而导致请求长时间运行并显得“卡住”。此外，如果 finish_reason="length"，这表示生成超过了 max_tokens 或对话超过了最大上下文长度，消息内容可能会被部分截断。
    @ApiModelProperty("指定模型必须输出的格式 设置为 { \"type\": \"json_object\" } 以启用 JSON 模式，该模式保证模型生成的消息是有效的 JSON")
    @JSONField(name = "response_format")
    private DeepseekResponseFormat response_format;

    private Boolean stream;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
