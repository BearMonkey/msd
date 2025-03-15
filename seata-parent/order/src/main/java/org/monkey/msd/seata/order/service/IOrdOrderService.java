package org.monkey.msd.seata.order.service;

import org.monkey.msd.seata.order.dto.AddOrderDto;
import org.monkey.msd.seata.order.pojo.OrdOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author cc
 * @since 2025-02-27
 */
public interface IOrdOrderService extends IService<OrdOrder> {

    /**
     * 新增订单
     * @param addOrderDto AddOrderDto
     * @return Boolean
     */
    Boolean addOrder(AddOrderDto addOrderDto);
}
