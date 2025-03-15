package org.monkey.msd.security.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * Authority
 *
 * @author cc
 * @since 2024/12/16 14:04
 */
@TableName("usr_auth")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class MsdAuth extends BaseEntity implements GrantedAuthority {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "auth_code")
    private String authCode;

    @TableField(value = "auth_name")
    private String authName;

    @Override
    public String getAuthority() {
        return authName;
    }
}
