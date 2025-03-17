package org.monkey.msd.seata.order.controller;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.seata.common.dto.Result;
import org.monkey.msd.seata.order.dto.AddOrderDto;
import org.monkey.msd.seata.order.service.IOrdOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author cc
 * @since 2025-02-27
 */
@RestController
@RequestMapping("/ord-order")
@Slf4j
public class OrdOrderController {

    @Autowired
    private IOrdOrderService orderService;

    @PostMapping("/add")
    public Result<Boolean> addOrder(@RequestBody AddOrderDto addOrderDto) {
        log.info("order-add: {}", JSONObject.toJSONString(addOrderDto));
        Boolean result = orderService.addOrder(addOrderDto);
        return Result.success(result);
    }

}
