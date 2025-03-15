package org.monkey.msd.seata.stock.dto;

import lombok.Data;

import java.util.List;

/**
 * OperateInventoryDto
 *
 * @author cc
 * @since 2025/3/3 16:51
 */
@Data
public class OperateInventoryDto {
    private List<AddUpdateStockDto> stockDtoList;
}
