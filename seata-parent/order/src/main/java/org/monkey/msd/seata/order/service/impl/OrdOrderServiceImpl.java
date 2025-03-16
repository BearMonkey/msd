package org.monkey.msd.seata.order.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.seata.common.dto.Result;
import org.monkey.msd.seata.exception.exception.BusinessException;
import org.monkey.msd.seata.order.OrderStatusEnums;
import org.monkey.msd.seata.order.dto.AddOrderDetailDto;
import org.monkey.msd.seata.order.dto.AddOrderDto;
import org.monkey.msd.seata.order.pojo.OrdOrder;
import org.monkey.msd.seata.order.mapper.OrdOrderMapper;
import org.monkey.msd.seata.order.pojo.OrdOrderDetail;
import org.monkey.msd.seata.order.service.IOrdOrderDetailService;
import org.monkey.msd.seata.order.service.IOrdOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.monkey.msd.seata.stock.dto.AddUpdateStockDto;
import org.monkey.msd.seata.stock.dto.OperateInventoryDto;
import org.monkey.msd.seata.stock.dto.QueryStockDto;
import org.monkey.msd.seata.stock.feign.StockFeignClient;
import org.monkey.msd.seata.stock.pojo.StcStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-02-27
 */
@Service
@Slf4j
public class OrdOrderServiceImpl extends ServiceImpl<OrdOrderMapper, OrdOrder> implements IOrdOrderService {

    @Autowired
    private IOrdOrderDetailService orderDetailService;

    @Autowired
    private StockFeignClient stockFeignClient;

    @Autowired
    private Snowflake snowflake;

    @Override
//    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional(rollbackFor = Exception.class)
    public Boolean addOrder(AddOrderDto addOrderDto) {
        // 参数校验
        this.addOrderCheck(addOrderDto);
        List<AddOrderDetailDto> detailDtoList = addOrderDto.getDetailDtoList();
        List<Long> stockIds = detailDtoList.stream().map(AddOrderDetailDto::getStockId).collect(Collectors.toList());
        QueryStockDto queryStockDto = new QueryStockDto();
        queryStockDto.setStockIds(stockIds);
        Result<List<StcStock>> getStockListRet = stockFeignClient.getStockListByIds(queryStockDto);
        Objects.requireNonNull(getStockListRet, "获取商品信息失败");
        List<StcStock> stockList = getStockListRet.getData();
        if (CollUtil.isEmpty(stockList)) {
            throw new BusinessException("商品信息为空");
        }
        Map<Long, StcStock> stockByIdMap = stockList
                .stream()
                .collect(Collectors.toMap(StcStock::getId, stcStock -> stcStock, (s1, s2) -> s1));
        // 扣库存
        List<AddUpdateStockDto> stockDtoList = new ArrayList<>();
        for (AddOrderDetailDto detailDto : detailDtoList) {
            Long goodsId = detailDto.getStockId();
            Integer quantity = detailDto.getQuantity();
            if (!stockByIdMap.containsKey(goodsId)) {
                throw new BusinessException("商品不存在:" + goodsId);
            }
            StcStock stcStock = stockByIdMap.get(goodsId);
            if (stcStock.getAmount() < quantity) {
                throw new BusinessException("库存不足:" + goodsId);
            }
            AddUpdateStockDto stockDto = new AddUpdateStockDto();
            stockDto.setId(detailDto.getStockId());
            stockDto.setAmount(stcStock.getAmount() - detailDto.getQuantity());
            stockDtoList.add(stockDto);
        }
        OrdOrder ordOrder = new OrdOrder();
        String orderNo = "ORD" + snowflake.nextIdStr();
        ordOrder.setOrderNo(orderNo);
        ordOrder.setStatus(OrderStatusEnums.CREATED.getStatus());
        ordOrder.setTotalPrice(this.calcTotalPrice(stockByIdMap, detailDtoList));

        List<OrdOrderDetail> detailList = new ArrayList<>();
        for (AddOrderDetailDto detailDto : detailDtoList) {
            StcStock stcStock = stockByIdMap.get(detailDto.getStockId());
            detailList.add(buildOrderDetail(detailDto, orderNo, stcStock));
        }
        OperateInventoryDto operateInventoryDto = new OperateInventoryDto();
        operateInventoryDto.setStockDtoList(stockDtoList);
        stockFeignClient.operateInventory(operateInventoryDto);
        this.save(ordOrder);
        orderDetailService.saveBatch(detailList);
        return true;
    }

    private static OrdOrderDetail buildOrderDetail(AddOrderDetailDto detailDto, String orderNo, StcStock stcStock) {
        OrdOrderDetail orderDetail = new OrdOrderDetail();
        orderDetail.setOrderNo(orderNo);
        orderDetail.setStockId(detailDto.getStockId());
        orderDetail.setGoodsName(stcStock.getGoodsName());
        orderDetail.setQuantity(detailDto.getQuantity());
        orderDetail.setUnitPrice(stcStock.getUnitPrice());
        return orderDetail;
    }

    private BigDecimal calcTotalPrice(Map<Long, StcStock> stockByIdMap, List<AddOrderDetailDto> detailDtoList) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (AddOrderDetailDto detailDto : detailDtoList) {
            StcStock stcStock = stockByIdMap.get(detailDto.getStockId());
            BigDecimal unitPrice = stcStock.getUnitPrice();
            totalPrice = totalPrice.add(unitPrice.multiply(BigDecimal.valueOf(detailDto.getQuantity())));
        }
        return totalPrice;
    }

    private void addOrderCheck(AddOrderDto addOrderDto) {
        Objects.requireNonNull(addOrderDto, "订单信息不能为空");
        List<AddOrderDetailDto> detailDtoList = addOrderDto.getDetailDtoList();
        if(CollUtil.isEmpty(detailDtoList)){
            throw new IllegalArgumentException("订单明细不能为空");
        }
        detailDtoList.forEach(detailDto -> {
            Objects.requireNonNull(detailDto.getStockId(), "商品Id不能为空");
            Objects.requireNonNull(detailDto.getQuantity(), "商品购买数量不能为空");
        });
    }

    public static void main(String[] args) {
        List<String> lines = FileUtil.readLines(new File("E:\\download\\cusOrderNo1.txt"), StandardCharsets.UTF_8.name());
        Map<String, Integer> map = new HashMap<>();
        for (String line : lines) {
            JSONArray jsonArray = JSONArray.parseArray(line);
            for (Object o : jsonArray) {
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(o));
                String cusOrderNo = jsonObject.getString("cusOrderNo");
                if (map.containsKey(cusOrderNo)) {
                    map.put(cusOrderNo, map.get(cusOrderNo) + 1);
                } else {
                    map.put(cusOrderNo, 1);
                }
            }
        }
        AtomicReference<Integer> cnt = new AtomicReference<>(0);
        map.forEach((k, v) -> {
            if (v > 1) {
                cnt.getAndSet(cnt.get() + 1);
            }
        });
        System.out.println(cnt);
    }
}
