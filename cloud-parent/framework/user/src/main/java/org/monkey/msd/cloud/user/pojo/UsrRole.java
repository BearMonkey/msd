package org.monkey.msd.cloud.user.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
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
 * 角色表
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("usr_role")
@ApiModel(value="UsrRole对象", description="角色表")
public class UsrRole extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.NONE)
    private Long id;

    @ApiModelProperty(value = "角色名称")
    @TableField("role_name")
    private String roleName;


}
