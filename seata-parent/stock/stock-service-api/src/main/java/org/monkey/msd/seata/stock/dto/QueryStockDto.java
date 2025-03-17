package org.monkey.msd.seata.stock.dto;

import lombok.Data;

import java.util.List;

/**
 * QueryStockDto
 *
 * @author cc
 * @since 2025/2/27 16:14
 */
@Data
public class QueryStockDto {
    private List<Long> stockIds;
}
