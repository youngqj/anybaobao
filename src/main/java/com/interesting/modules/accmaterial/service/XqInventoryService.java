package com.interesting.modules.accmaterial.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.accmaterial.dto.InstOrUpdtAccInventoryDTO;
import com.interesting.modules.accmaterial.entity.XqInventory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.accmaterial.vo.AccInventoryDetails2VO;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 26773
* @description 针对表【xq_inventory(库存管理)】的数据库操作Service
* @createDate 2023-07-05 16:19:33
*/
public interface XqInventoryService extends IService<XqInventory> {

    IPage<AccInventoryDetails2VO> listInventoryDetails(Page<AccInventoryDetails2VO> page,
                                                       String warehouseId,
                                                       String accessoryId,
                                                       String relativeTimeBegin,
                                                       String relativeTimeEnd);
    Integer getInventoryNum(String warehouseId, String accessoryId, String receiptNum, BigDecimal unitPrice);

    BigDecimal getSkuNum(String accessoryId);
    void updateUseInventoryDetails(String id, List<InstOrUpdtAccInventoryDTO> useInventoryDetails, String accessoryId);

    void updateBackInventoryDetails(String id, List<InstOrUpdtAccInventoryDTO> backInventoryDetails, String accessoryId, String orderNum);
}
