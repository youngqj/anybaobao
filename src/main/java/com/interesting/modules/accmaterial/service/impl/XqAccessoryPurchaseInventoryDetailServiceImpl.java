package com.interesting.modules.accmaterial.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.modules.accmaterial.entity.XqAccessoryPurchaseInventoryDetail;
import com.interesting.modules.accmaterial.service.XqAccessoryPurchaseInventoryDetailService;
import com.interesting.modules.accmaterial.mapper.XqAccessoryPurchaseInventoryDetailMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_accessory_purchase_inventory_detail】的数据库操作Service实现
* @createDate 2023-07-20 14:42:56
*/
@Service
public class XqAccessoryPurchaseInventoryDetailServiceImpl extends ServiceImpl<XqAccessoryPurchaseInventoryDetailMapper, XqAccessoryPurchaseInventoryDetail>
    implements XqAccessoryPurchaseInventoryDetailService{

    @Override
    public boolean physicalDeleteById(String id) {
        return this.baseMapper.physicalDeleteById(id);

    }

    @Override
    public boolean physicalDeleteBatch(List<String> ids) {
        return this.baseMapper.physicalDeleteBatch(ids);
    }
}




