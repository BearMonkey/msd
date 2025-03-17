package org.monkey.msd.seata.stock.pojo;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.monkey.msd.seata.common.pojo.BaseEntity;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author cc
 * @since 2025-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("stc_stock")
@ApiModel(value="StcStock对象", description="商品表")
public class StcStock extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "商品名称")
    @TableField("goods_name")
    private String goodsName;

    @ApiModelProperty(value = "商品描述")
    @TableField("goods_desc")
    private String goodsDesc;

    @ApiModelProperty(value = "库存数量")
    @TableField("amount")
    private Integer amount;

    @ApiModelProperty(value = "单价")
    @TableField("unit_price")
    private BigDecimal unitPrice;


}
