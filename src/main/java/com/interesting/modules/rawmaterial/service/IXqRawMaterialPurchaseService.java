package com.interesting.modules.rawmaterial.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.order.dto.sub.AddOrderFretCostDTO;
import com.interesting.modules.order.dto.sub.AddOrderRawDTO;
import com.interesting.modules.rawmaterial.entity.XqRawMaterialPurchase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.rawmaterial.dto.QueryXqRawMaterialPurchaseDTO;
import com.interesting.modules.rawmaterial.vo.XqRawMaterialPurchaseVO;
import com.interesting.modules.remittance.vo.QueryDataNotInDetailVO;

import java.util.List;

/**
 * @Description: 原料采购表
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
public interface IXqRawMaterialPurchaseService extends IService<XqRawMaterialPurchase> {
    /**
     * 分页列表查询
     *
     * @param page
     * @return
     */
    IPage<XqRawMaterialPurchaseVO> pageList(Page<XqRawMaterialPurchaseVO> page, QueryXqRawMaterialPurchaseDTO dto);

    void updateOrderRaw(String id, List<AddOrderRawDTO> orderRawVOS,Integer roleCode);

    List<QueryDataNotInDetailVO> queryNotPayOrder();
}
