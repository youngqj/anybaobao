package com.interesting.modules.accmaterial.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.accmaterial.dto.EditStatusDTO;
import com.interesting.modules.accmaterial.dto.accpurchase.InstUptAccPurchaseDTO;
import com.interesting.modules.accmaterial.dto.accpurchase.QueryPageAccPurchaseDTO;
import com.interesting.modules.accmaterial.entity.XqAccessoryPurchaseInventory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.accmaterial.vo.accpurchase.AccessoryPurchaseDetailVO;
import com.interesting.modules.accmaterial.vo.accpurchase.AccessoryPurchasePageVO;

/**
* @author 26773
* @description 针对表【xq_accessory_purchase_inventory】的数据库操作Service
* @createDate 2023-07-20 14:09:42
*/
public interface XqAccessoryPurchaseInventoryService extends IService<XqAccessoryPurchaseInventory> {

    AccessoryPurchaseDetailVO getAccPurchaseById(String id);

    boolean addAccPurchase(InstUptAccPurchaseDTO dto);

    IPage<AccessoryPurchasePageVO> pageAccPurchase(QueryPageAccPurchaseDTO dto);

    boolean deleteAccPurchase(String ids);

    boolean editAccPurchase(InstUptAccPurchaseDTO dto);

    boolean editAccPurchaseStatus(EditStatusDTO dto);

}
