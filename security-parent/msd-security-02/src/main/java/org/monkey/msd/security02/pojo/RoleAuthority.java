package org.monkey.msd.security02.pojo;

import com.alibaba.fastjson2.JSONObject;
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

/**
 * <p>
 * 
 * </p>
 *
 * @author cc
 * @since 2025-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("role_authority")
@ApiModel(value="RoleAuthority对象", description="")
public class RoleAuthority implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "角色Id")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty(value = "权限Id")
    @TableField("authority_id")
    private Long authorityId;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }


}
