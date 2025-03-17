package org.monkey.msd.seata.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.monkey.msd.seata.order.pojo.OrdOrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 订单明细表 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2025-02-27
 */
@Mapper
public interface OrdOrderDetailMapper extends BaseMapper<OrdOrderDetail> {

}
