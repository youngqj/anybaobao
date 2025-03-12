package com.interesting.modules.accmaterial.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.accmaterial.entity.XqAccessoriesPurchase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.accmaterial.dto.QueryXqAccessoriesPurchaseDTO;
import com.interesting.modules.accmaterial.vo.XqAccessoriesExportVO;
import com.interesting.modules.accmaterial.vo.XqAccessoriesPurchaseVO;
import com.interesting.modules.order.dto.sub.AddOrderAcsrDTO;

import java.util.List;

/**
 * @Description: 辅料采购表
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
public interface IXqAccessoriesPurchaseService extends IService<XqAccessoriesPurchase> {
    /**
     * 分页列表查询
     *
     * @param page
     * @return
     */
    IPage<XqAccessoriesPurchaseVO> pageList(Page<XqAccessoriesPurchaseVO> page, QueryXqAccessoriesPurchaseDTO dto);

    void updateAccessories(String orderId, List<AddOrderAcsrDTO> orderAcsrVOS,String orderNum,Integer roleCode);

    List<XqAccessoriesExportVO> listByExport(String orderNum);
}
