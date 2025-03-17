package org.monkey.msd.seata.stock.feign;

import org.monkey.msd.seata.common.dto.Result;
import org.monkey.msd.seata.stock.dto.OperateInventoryDto;
import org.monkey.msd.seata.stock.dto.QueryStockDto;
import org.monkey.msd.seata.stock.pojo.StcStock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * StockFeignClient
 *
 * @author cc
 * @since 2025/2/27 18:00
 */
@FeignClient(value = "stock", path = "/stc-stock")
public interface StockFeignClient {
    @PostMapping("/getStockListByIds")
    Result<List<StcStock>> getStockListByIds(@RequestBody QueryStockDto queryStockDto);

    @PostMapping("/operateInventory")
    public Result<Boolean> operateInventory(@RequestBody OperateInventoryDto dto);
}
