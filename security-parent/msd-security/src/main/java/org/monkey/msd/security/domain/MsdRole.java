package org.monkey.msd.security.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * MsdRole
 *
 * @author cc
 * @since 2025/2/5 11:45
 */
@TableName("usr_role")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class MsdRole extends BaseEntity{

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "role_code")
    private String roleCode;

    @TableField(value = "role_name")
    private String roleName;

    @TableField(exist = false)
    private Set<MsdAuth> auths = new HashSet<>();
}
