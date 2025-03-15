package org.monkey.msd.seata.order.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * OrderDetailDto
 *
 * @author cc
 * @since 2025/2/27 15:30
 */
@Data
public class AddOrderDetailDto {

    @ApiModelProperty(value = "商品Id")
    private Long stockId;

    @ApiModelProperty(value = "购买数量")
    private Integer quantity;
}
