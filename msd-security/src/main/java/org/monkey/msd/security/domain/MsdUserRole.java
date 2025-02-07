package org.monkey.msd.security.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * MsdUserRole
 *
 * @author cc
 * @since 2025/2/5 11:49
 */
@TableName("usr_user_role")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class MsdUserRole extends BaseEntity{

    @TableField(value = "user_id")
    private Integer userId;

    @TableField(value = "role_id")
    private Integer roleId;
}
