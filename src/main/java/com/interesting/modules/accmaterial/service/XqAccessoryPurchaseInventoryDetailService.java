package com.interesting.modules.accmaterial.service;

import com.interesting.modules.accmaterial.entity.XqAccessoryPurchaseInventoryDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_accessory_purchase_inventory_detail】的数据库操作Service
* @createDate 2023-07-20 14:42:56
*/
public interface XqAccessoryPurchaseInventoryDetailService extends IService<XqAccessoryPurchaseInventoryDetail> {

    boolean physicalDeleteById(String id);

    boolean physicalDeleteBatch(List<String> ids);
}
