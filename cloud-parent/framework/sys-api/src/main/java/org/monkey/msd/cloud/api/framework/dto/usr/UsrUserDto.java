package org.monkey.msd.cloud.api.framework.dto.usr;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * UsrUserDto
 *
 * @author cc
 * @since 2025/3/19 16:22
 */
@Data
public class UsrUserDto {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "启用标识")
    private Boolean enabled;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建人ID")
    private String createById;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    private String updateBy;

    @ApiModelProperty(value = "修改人ID")
    private String updateById;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除标识；1-已删除，0-未删除")
    private Integer delFlag;

    @ApiModelProperty(value = "用户拥有的角色列表")
    private List<UsrRoleDto> roleList = new ArrayList<>();
}
