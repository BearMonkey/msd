package org.monkey.msd.security.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Authority
 *
 * @author cc
 * @since 2024/12/16 14:04
 */
@TableName("usr_user_auth")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class MsdUserAuth extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "user_id")
    private Integer userId;

    @TableField(value = "auth_id")
    private Integer authId;
}
