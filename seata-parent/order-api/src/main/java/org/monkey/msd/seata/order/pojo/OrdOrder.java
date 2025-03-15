package org.monkey.msd.seata.order.pojo;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.monkey.msd.seata.common.pojo.BaseEntity;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author cc
 * @since 2025-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ord_order")
@ApiModel(value="OrdOrder对象", description="订单表")
public class OrdOrder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "订单号")
    @TableField("order_no")
    private String orderNo;

    @ApiModelProperty(value = "总价")
    @TableField("total_price")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "订单状态")
    @TableField("status")
    private Integer status;


}
