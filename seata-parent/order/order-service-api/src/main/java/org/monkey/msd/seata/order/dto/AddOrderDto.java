package org.monkey.msd.seata.order.dto;

import lombok.Data;

import java.util.List;

/**
 * AddOrderDto
 *
 * @author cc
 * @since 2025/2/27 15:11
 */
@Data
public class AddOrderDto {
    private List<AddOrderDetailDto> detailDtoList;
}
