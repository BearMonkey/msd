package org.monkey.msd.seata.stock.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * AddUpdateStockDto
 *
 * @author cc
 * @since 2025/3/3 13:54
 */
@Data
public class AddUpdateStockDto {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品描述")
    private String goodsDesc;

    @ApiModelProperty(value = "库存数量")
    private Integer amount;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "批量操作id列表")
    private List<Long> stockIds;
}
