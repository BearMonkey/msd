package org.monkey.msd.cloud.api.framework.dto.usr;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * UsrUserRoleDto
 *
 * @author cc
 * @since 2025/3/20 9:40
 */
@Data
public class UsrUserRoleDto {

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "角色id列表")
    private List<Long> roleIdList = new ArrayList<>();
}
