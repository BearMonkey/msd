package org.monkey.msd.cloud.api.framework.dto.usr;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * UsrRoleDto
 *
 * @author cc
 * @since 2025/3/19 16:22
 */
@Data
public class UsrRoleDto {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色拥有的权限列表")
    private List<UsrAuthDto> authList = new ArrayList<>();

    @ApiModelProperty(value = "角色列表")
    private Set<Long> roleIdList;
}
