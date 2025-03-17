package org.monkey.msd.seata.stock.service;

import org.monkey.msd.seata.stock.dto.AddUpdateStockDto;
import org.monkey.msd.seata.stock.dto.DelStockDto;
import org.monkey.msd.seata.stock.dto.OperateInventoryDto;
import org.monkey.msd.seata.stock.dto.QueryStockDto;
import org.monkey.msd.seata.stock.pojo.StcStock;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author cc
 * @since 2025-02-27
 */
public interface IStcStockService extends IService<StcStock> {

    /**
     * 根据商品id 查询商品信息
     * @param queryStockDto QueryStockDto
     * @return List<StcStock>
     */
    List<StcStock> selectStockListByIds(QueryStockDto queryStockDto);

    /**
     * 增加库存商品
     * @param dto AddUpdateStockDto
     * @return Boolean
     */
    Boolean addStock(AddUpdateStockDto dto);

    /**
     * 修改库存商品
     * @param dto AddUpdateStockDto
     * @return Boolean
     */
    Boolean updateStock(AddUpdateStockDto dto);

    /**
     * 逻辑删除库存商品
     * @param id Long
     * @return Boolean
     */
    Boolean delStock(Long id);

    /**
     * 批量逻辑删除库存商品
     * @param dto DelStockDto
     * @return Boolean
     */
    Boolean delStockBatch(DelStockDto dto);

    /**
     * 查询全部库存商品
     * @return Boolean
     */
    List<StcStock> getAllStock();

    /**
     * 操作库存
     * @param dto OperateInventoryDto
     * @return Boolean
     */
    Boolean operateInventory(OperateInventoryDto dto);
}
