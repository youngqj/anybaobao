package com.interesting.modules.accmaterial.mapper;

import com.interesting.modules.accmaterial.entity.XqAccessoryPurchaseInventoryDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_accessory_purchase_inventory_detail】的数据库操作Mapper
* @createDate 2023-07-20 14:42:56
* @Entity com.interesting.modules.accmaterial.entity.XqAccessoryPurchaseInventoryDetail
*/
public interface XqAccessoryPurchaseInventoryDetailMapper extends BaseMapper<XqAccessoryPurchaseInventoryDetail> {

    boolean physicalDeleteById(@Param("id") String id);

    boolean physicalDeleteBatch(List<String> ids);
}




