package com.interesting.modules.overseas.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.modules.overseas.dto.*;
import com.interesting.modules.overseas.entity.XqInventoryOverseas;
import com.interesting.modules.overseas.service.XqInventoryOverseasService;
import com.interesting.modules.overseas.mapper.XqInventoryOverseasMapper;
import com.interesting.modules.overseas.vo.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
* @author 26773
* @description 针对表【xq_inventory_overseas(海外仓-库存表)】的数据库操作Service实现
* @createDate 2023-07-26 18:05:17
*/
@Service
public class XqInventoryOverseasServiceImpl extends ServiceImpl<XqInventoryOverseasMapper, XqInventoryOverseas>
    implements XqInventoryOverseasService{

    @Override
    public boolean physicalRemoveBySourceIds(List<String> strings) {
        return this.baseMapper.physicalRemoveBySourceIds(strings);
    }

    @Override
    public IPage<OverSeasInventoryPageVO> pageOverSeasInventory(QueryPageOverSeasInventoryDTO dto) {
        Page<OverSeasInventoryPageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        IPage<OverSeasInventoryPageVO> results = this.baseMapper.pageOverSeasInventory(page, dto);
        results.getRecords().forEach(i -> {
            i.setId(UUID.randomUUID().toString().replace("-", ""));
        });
        return results;
    }

    @Override
    public Integer getInventoryNum(String productId, String warehouseId, String warehouseLot) {
        return this.baseMapper.getInventoryNum(productId, warehouseId, warehouseLot);
    }

    @Override
    public Integer getInventoryNum(String productId, String warehouseId, String warehouseLot, String sourceRepNum) {
        Page<BatchInventoryPageVO> page = new Page<>(1, Integer.MAX_VALUE);
        QueryPageBatchInventoryDTO queryPageBatchInventoryDTO = new QueryPageBatchInventoryDTO();
        queryPageBatchInventoryDTO.setProductId(productId);
        queryPageBatchInventoryDTO.setWarehouseId(warehouseId);
        queryPageBatchInventoryDTO.setWarehouseLot(warehouseLot);
        queryPageBatchInventoryDTO.setSourceRepNum(sourceRepNum);
        IPage<BatchInventoryPageVO> results = this.baseMapper.pageBatchInventory(page, queryPageBatchInventoryDTO);
        if (CollectionUtil.isNotEmpty(results.getRecords())) {
            BatchInventoryPageVO batchInventoryPageVO = results.getRecords().get(0);
            if (batchInventoryPageVO == null) {
                return 0;
            } else {
                return batchInventoryPageVO.getInventoryNum();
            }
        } else {
            return 0;
        }
    }

    @Override
    public IPage<BatchInventoryPageVO> pageBatchInventory(QueryPageBatchInventoryDTO dto) {
        Page<BatchInventoryPageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        IPage<BatchInventoryPageVO> results = this.baseMapper.pageBatchInventory(page, dto);
        results.getRecords().forEach(i -> {
            i.setId(UUID.randomUUID().toString().replace("-", ""));
        });
        return results;
    }

    @Override
    public IPage<OverSeasInventoryDetailPageVO> pageInventoryDetails(QueryPageInventoryDetailsDTO dto) {
        Page<OverSeasInventoryDetailPageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        return this.baseMapper.pageInventoryDetails(page, dto);
    }

    @Override
    public List<Map<String, Integer>> getInventoryMapList(List<String> productIds, String warehouseId) {
        return this.baseMapper.getInventoryMapList(productIds, warehouseId);
    }

    @Override
    public IPage<HuoZhiDetailVO> getHuoZhiDetail(QueryHuoZhiDTO dto) {
        Page<HuoZhiDetailVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        IPage<HuoZhiDetailVO> results = this.baseMapper.getHuoZhiDetail(page, dto);
        results.getRecords().forEach(i -> {
            i.setId(UUID.randomUUID().toString().replace("-", ""));
        });
        return results;
    }

    @Override
    public IPage<HuoZhiWarehouseVO> getHuoZhiByWarehouse(QueryHuoZhiWarehouseDTO dto) {
        Page<HuoZhiWarehouseVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        return this.baseMapper.getHuoZhiByWarehouse(page, dto);
    }

    @Override
    public List<HuoZhiWarehouseVO> getTotalPrice(QueryHuoZhiWarehouseDTO dto) {
        return this.baseMapper.getTotalPrice(dto);
    }

    @Override
    public List<TotalNumVO> getTotalNum(QueryPageInventoryDetailsDTO dto) {
        List<TotalNumVO> voList = new ArrayList<>();
        TotalNumVO totalNum = this.baseMapper.getTotalNum(dto);
        if(totalNum == null){
            totalNum = new TotalNumVO();
        }
        voList.add(totalNum);
        return voList;
    }
}




