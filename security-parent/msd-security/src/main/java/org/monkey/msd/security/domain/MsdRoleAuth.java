package org.monkey.msd.security.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * MsdRoleAuth
 *
 * @author cc
 * @since 2025/2/5 11:50
 */
@TableName("usr_role_auth")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class MsdRoleAuth extends BaseEntity{

    @TableField(value = "role_id")
    private Integer roleId;

    @TableField(value = "auth_id")
    private Integer authId;
}
