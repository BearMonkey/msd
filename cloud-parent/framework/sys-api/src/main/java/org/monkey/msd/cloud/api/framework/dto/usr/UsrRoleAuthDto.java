package org.monkey.msd.cloud.api.framework.dto.usr;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * UsrRoleAuthDto
 *
 * @author cc
 * @since 2025/3/20 9:25
 */
@Data
public class UsrRoleAuthDto {

    @ApiModelProperty(value = "角色Id")
    private Long roleId;

    @ApiModelProperty(value = "权限Id列表")
    private List<Long> authIdList = new ArrayList<>();
}
