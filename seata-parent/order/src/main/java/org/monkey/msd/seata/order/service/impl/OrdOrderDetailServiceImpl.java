package org.monkey.msd.seata.order.service.impl;

import org.monkey.msd.seata.order.mapper.OrdOrderDetailMapper;
import org.monkey.msd.seata.order.pojo.OrdOrderDetail;
import org.monkey.msd.seata.order.service.IOrdOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-02-27
 */
@Service
public class OrdOrderDetailServiceImpl extends ServiceImpl<OrdOrderDetailMapper, OrdOrderDetail> implements IOrdOrderDetailService {

}
