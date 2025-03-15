package org.monkey.msd.seata.stock.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * DelStockDto
 *
 * @author cc
 * @since 2025/3/3 13:54
 */
@Data
public class DelStockDto {

    @ApiModelProperty(value = "批量操作id列表")
    private List<Long> stockIds;
}
