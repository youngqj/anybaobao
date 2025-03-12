package com.interesting.modules.overseas.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.overseas.dto.*;
import com.interesting.modules.overseas.entity.XqInventoryOverseas;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.overseas.vo.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
* @author 26773
* @description 针对表【xq_inventory_overseas(海外仓-库存表)】的数据库操作Mapper
* @createDate 2023-07-26 18:05:17
* @Entity com.interesting.modules.overseas.entity.XqInventoryOverseas
*/
public interface XqInventoryOverseasMapper extends BaseMapper<XqInventoryOverseas> {

    boolean physicalRemoveBySourceIds(@Param("ids") List<String> ids);

    IPage<OverSeasInventoryPageVO> pageOverSeasInventory(@Param("page") Page<OverSeasInventoryPageVO> page,
                                                         @Param("dto") QueryPageOverSeasInventoryDTO dto);

    Integer getInventoryNum(@Param("productId") String productId,
                            @Param("warehouseId") String warehouseId,
                            @Param("warehouseLot") String warehouseLot);

    IPage<BatchInventoryPageVO> pageBatchInventory(@Param("page") Page<BatchInventoryPageVO> page,
                                                   @Param("dto") QueryPageBatchInventoryDTO dto);

    IPage<OverSeasInventoryDetailPageVO> pageInventoryDetails(@Param("page") Page<OverSeasInventoryDetailPageVO> page,
                                                              @Param("dto") QueryPageInventoryDetailsDTO dto);

    List<Map<String, Integer>> getInventoryMapList(@Param("productIds") List<String> productIds,
                                                   @Param("warehouseId") String warehouseId);

    IPage<HuoZhiDetailVO> getHuoZhiDetail(Page<HuoZhiDetailVO> page, QueryHuoZhiDTO dto);

    IPage<HuoZhiWarehouseVO> getHuoZhiByWarehouse(Page<HuoZhiWarehouseVO> page, QueryHuoZhiWarehouseDTO dto);

    List<HuoZhiWarehouseVO> getTotalPrice(@Param("dto") QueryHuoZhiWarehouseDTO dto);

    TotalNumVO getTotalNum(@Param("dto") QueryPageInventoryDetailsDTO dto);
}




