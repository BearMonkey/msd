package org.monkey.msd.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Dto
 *
 * @author cc
 * @since 2024/12/13 18:09
 */
@Data
@AllArgsConstructor
public class Dto {
    private String id;
    private String name;
    private BigDecimal oriWeight;
}
