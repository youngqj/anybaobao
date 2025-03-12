package com.interesting.modules.overseas.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.overseas.dto.*;
import com.interesting.modules.overseas.entity.XqInventoryOverseas;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.overseas.vo.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
* @author 26773
* @description 针对表【xq_inventory_overseas(海外仓-库存表)】的数据库操作Service
* @createDate 2023-07-26 18:05:17
*/
public interface XqInventoryOverseasService extends IService<XqInventoryOverseas> {


    boolean physicalRemoveBySourceIds(List<String> strings);

    IPage<OverSeasInventoryPageVO> pageOverSeasInventory(QueryPageOverSeasInventoryDTO dto);

    Integer getInventoryNum(String productId, String warehouseId, String warehouseLot);

    Integer getInventoryNum(String productId, String warehouseId, String warehouseLot, String sourceRepNum);

    IPage<BatchInventoryPageVO> pageBatchInventory(QueryPageBatchInventoryDTO dto);

    IPage<OverSeasInventoryDetailPageVO> pageInventoryDetails(QueryPageInventoryDetailsDTO dto);

    List<Map<String, Integer>> getInventoryMapList(List<String> productIds, String warehouseId);

    IPage<HuoZhiDetailVO> getHuoZhiDetail(QueryHuoZhiDTO dto);

    IPage<HuoZhiWarehouseVO> getHuoZhiByWarehouse(QueryHuoZhiWarehouseDTO dto);

    List<HuoZhiWarehouseVO> getTotalPrice(QueryHuoZhiWarehouseDTO dto);

    List<TotalNumVO> getTotalNum(QueryPageInventoryDetailsDTO dto);
}

