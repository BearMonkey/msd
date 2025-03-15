package org.monkey.msd.seata.order.pojo;

import java.math.BigDecimal;

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
import org.monkey.msd.seata.common.pojo.BaseEntity;

/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author cc
 * @since 2025-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ord_order_detail")
@ApiModel(value="OrdOrderDetail对象", description="订单明细表")
public class OrdOrderDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "订单号")
    @TableField("order_no")
    private String orderNo;

    @ApiModelProperty(value = "库存id")
    @TableField("stock_id")
    private Long stockId;

    @ApiModelProperty(value = "商品名称")
    @TableField("goods_name")
    private String goodsName;

    @ApiModelProperty(value = "商品数量")
    @TableField("quantity")
    private Integer quantity;

    @ApiModelProperty(value = "单价")
    @TableField("unit_price")
    private BigDecimal unitPrice;


}
