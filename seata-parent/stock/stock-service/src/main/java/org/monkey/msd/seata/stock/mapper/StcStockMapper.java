package org.monkey.msd.seata.stock.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.monkey.msd.seata.stock.pojo.StcStock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2025-02-27
 */
@Mapper
public interface StcStockMapper extends BaseMapper<StcStock> {

}
