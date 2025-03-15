package org.monkey.msd.seata.stock.controller;


import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.seata.common.dto.Result;
import org.monkey.msd.seata.stock.dto.AddUpdateStockDto;
import org.monkey.msd.seata.stock.dto.DelStockDto;
import org.monkey.msd.seata.stock.dto.OperateInventoryDto;
import org.monkey.msd.seata.stock.dto.QueryStockDto;
import org.monkey.msd.seata.stock.pojo.StcStock;
import org.monkey.msd.seata.stock.service.IStcStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author cc
 * @since 2025-02-27
 */
@RestController
@RequestMapping("/stc-stock")
@Slf4j
public class StcStockController {

    @Autowired
    private IStcStockService stockService;

    @PostMapping()
    public Result<Boolean> addStock(@RequestBody AddUpdateStockDto dto) {
        return Result.success(stockService.addStock(dto));
    }

    @PutMapping()
    public Result<Boolean> updateStock(@RequestBody AddUpdateStockDto dto) {
        return Result.success(stockService.updateStock(dto));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delStock(@PathVariable("id") Long id) {
        return Result.success(stockService.delStock(id));
    }

    @DeleteMapping()
    public Result<Boolean> delStockBatch(@RequestBody DelStockDto dto) {
        return Result.success(stockService.delStockBatch(dto));
    }

    @GetMapping()
    public Result<List<StcStock>> getAllStock() {
        return Result.success(stockService.getAllStock());
    }


    // *************************************** feign client start ********************************************

    @PostMapping("/getStockListByIds")
    public Result<List<StcStock>> getStockListByIds(@RequestBody QueryStockDto queryStockDto) {
        return Result.success(stockService.selectStockListByIds(queryStockDto));
    }

    @PostMapping("/operateInventory")
    public Result<Boolean> operateInventory(@RequestBody OperateInventoryDto dto) {
        return Result.success(stockService.operateInventory(dto));
    }

    // *************************************** feign client end **********************************************
}
