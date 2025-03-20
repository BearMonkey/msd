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
 * 权限表
 * </p>
 *
 * @author cc
 * @since 2025-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("usr_auth")
@ApiModel(value="UsrAuth对象", description="权限表")
public class UsrAuth extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "权限名称")
    @TableField("auth_name")
    private String authName;


}
