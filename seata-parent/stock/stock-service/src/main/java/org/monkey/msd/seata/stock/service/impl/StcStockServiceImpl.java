package org.monkey.msd.seata.stock.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.monkey.msd.seata.exception.exception.BusinessException;
import org.monkey.msd.seata.stock.dto.AddUpdateStockDto;
import org.monkey.msd.seata.stock.dto.DelStockDto;
import org.monkey.msd.seata.stock.dto.OperateInventoryDto;
import org.monkey.msd.seata.stock.dto.QueryStockDto;
import org.monkey.msd.seata.stock.pojo.StcStock;
import org.monkey.msd.seata.stock.mapper.StcStockMapper;
import org.monkey.msd.seata.stock.service.IStcStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-02-27
 */
@Service
@Slf4j
public class StcStockServiceImpl extends ServiceImpl<StcStockMapper, StcStock> implements IStcStockService {

    @Override
    public Boolean addStock(AddUpdateStockDto dto) {
        log.info("Stock addStock: {}", JSONObject.toJSONString(dto));
        Objects.requireNonNull(dto, "库存信息不能为空");
        Objects.requireNonNull(dto.getGoodsName(), "goodsName不能为空");
        Objects.requireNonNull(dto.getAmount(), "amount不能为空");
        Objects.requireNonNull(dto.getUnitPrice(), "unitPrice不能为空");
        if (dto.getAmount() <= 0) {
            throw new BusinessException("amount必须大于0");
        }
        StcStock stcStock = new StcStock();
        BeanUtil.copyProperties(dto, stcStock);
        int rows = baseMapper.insert(stcStock);
        log.info("Stock addStock complete, rows={}", rows);
        return rows == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStock(AddUpdateStockDto dto) {
        log.info("Stock updateStock: {}", JSONObject.toJSONString(dto));
        Objects.requireNonNull(dto, "库存信息不能为空");
        Objects.requireNonNull(dto.getId(), "id不能为空");
        if (null != dto.getAmount() && dto.getAmount() <= 0) {
            throw new BusinessException("amount必须大于0");
        }
        StcStock existStock = baseMapper.selectById(dto.getId());
        Objects.requireNonNull(existStock, "商品id不存在:" + dto.getId());
        StcStock stcStock = new StcStock();
        BeanUtil.copyProperties(dto, stcStock);
        int rows = baseMapper.updateById(stcStock);
        log.info("Stock updateStock complete, rows={}", rows);
        return rows == 1;
    }

    @Override
    public Boolean delStock(Long id) {
        log.info("Stock delStock: {}", id);
        StcStock existStock = baseMapper.selectById(id);
        Objects.requireNonNull(existStock, "商品id不存在:" + id);
        Objects.requireNonNull(id, "id不能为空");
        LambdaUpdateWrapper<StcStock> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StcStock::getId, id);
        updateWrapper.set(StcStock::getDelFlag, 1);
        int rows = baseMapper.update(updateWrapper);
        log.info("Stock delStock complete, rows={}", rows);
        return rows == 1;
    }

    @Override
    public Boolean delStockBatch(DelStockDto dto) {
        log.info("Stock delStockBatch: {}", JSONObject.toJSONString(dto));
        Objects.requireNonNull(dto, "请求参数不能为空");
        List<Long> stockIds = dto.getStockIds();
        if (CollUtil.isEmpty(stockIds)) {
            throw new BusinessException("商品id列表不能为空");
        }
        LambdaQueryWrapper<StcStock> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(StcStock::getId, stockIds);
        long exist = baseMapper.selectCount(queryWrapper);
        int size = stockIds.size();
        log.info("Stock delStockBatch need delete:{}, actual exist:{}", size, exist);
        if (exist != size) {
            throw new BusinessException("删除失败, 传入:" + size + ", 实际存在:" + exist);
        }
        LambdaUpdateWrapper<StcStock> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(StcStock::getId, stockIds);
        updateWrapper.set(StcStock::getDelFlag, 1);
        int rows = baseMapper.update(updateWrapper);
        log.info("Stock delStockBatch complete, rows={}", rows);
        if (rows == exist) {
            return true;
        } else {
            throw new BusinessException("删除失败, 传入:" + size + ", 实际存在:" + exist + ", 实际删除: " + rows);
        }
    }

    @Override
    public List<StcStock> getAllStock() {
        log.info("Stock getAllStock");
        LambdaQueryWrapper<StcStock> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.in(StcStock::getDelFlag, Arrays.asList(0, 1));
        List<StcStock> stcStocks = baseMapper.selectList(queryWrapper);
        log.info("Stock getAllStock list={}", JSONObject.toJSONString(stcStocks));
        return stcStocks;
    }

    @Override
    public List<StcStock> selectStockListByIds(QueryStockDto queryStockDto) {

        List<StcStock> stcStocks;

        log.info("Stock selectStockListByIds: {}", JSONObject.toJSONString(queryStockDto));
        Objects.requireNonNull(queryStockDto, "queryStockDto不能为空");
        List<Long> stockIds = queryStockDto.getStockIds();
        if (CollUtil.isEmpty(stockIds)) {
            throw new BusinessException("stockIds不能为空");
        }
        stcStocks = baseMapper.selectBatchIds(stockIds);
        log.info("Stock selectStockListByIds complete: {}", JSONObject.toJSONString(stcStocks));
        return stcStocks;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean operateInventory(OperateInventoryDto dto) {
        log.info("Stock operateInventory: {}", JSONObject.toJSONString(dto));
        Objects.requireNonNull(dto, "请求参数不能为空");
        List<AddUpdateStockDto> stockDtoList = dto.getStockDtoList();
        if (CollUtil.isEmpty(stockDtoList)) {
            throw new BusinessException("stockDtoList不能为空");
        }
        for (AddUpdateStockDto addUpdateStockDto : stockDtoList) {
            updateStock(addUpdateStockDto);
        }



        return null;
    }
}
