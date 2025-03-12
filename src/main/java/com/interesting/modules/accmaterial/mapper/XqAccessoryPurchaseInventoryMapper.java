package com.interesting.modules.accmaterial.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.accmaterial.dto.accpurchase.QueryPageAccPurchaseDTO;
import com.interesting.modules.accmaterial.entity.XqAccessoryPurchaseInventory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.accmaterial.vo.accpurchase.AccessoryPurchaseDetailVO;
import com.interesting.modules.accmaterial.vo.accpurchase.AccessoryPurchaseDetailVO2;
import com.interesting.modules.accmaterial.vo.accpurchase.AccessoryPurchasePageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_accessory_purchase_inventory】的数据库操作Mapper
* @createDate 2023-07-20 14:09:42
* @Entity com.interesting.modules.accmaterial.entity.XqAccessoryPurchaseInventory
*/
public interface XqAccessoryPurchaseInventoryMapper extends BaseMapper<XqAccessoryPurchaseInventory> {

    AccessoryPurchaseDetailVO getAccPurchaseHeadById(@Param("id") String id);

    List<AccessoryPurchaseDetailVO2> getAccPurchaseDetailById(@Param("id") String id);

    IPage<AccessoryPurchasePageVO> pageAccPurchase(@Param("page") Page<AccessoryPurchasePageVO> page,
                                                   @Param("dto") QueryPageAccPurchaseDTO dto);
}




