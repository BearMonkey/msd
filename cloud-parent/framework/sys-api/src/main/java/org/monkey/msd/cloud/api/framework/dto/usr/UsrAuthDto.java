package org.monkey.msd.cloud.api.framework.dto.usr;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * UsrAuthDto
 *
 * @author cc
 * @since 2025/3/19 15:16
 */
@Data
public class UsrAuthDto {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "权限名称")
    private String authName;
}
