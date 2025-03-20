package org.monkey.msd.cloud.api.framework.pojo.usr;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.monkey.msd.cloud.common.pojo.BaseEntity;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("usr_user_role")
@ApiModel(value="UsrUserRole对象", description="用户角色表")
public class UsrUserRole extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.NONE)
    private Long id;

    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Long userId;


}
