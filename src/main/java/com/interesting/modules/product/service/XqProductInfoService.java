package com.interesting.modules.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.order.dto.sub.AddOrderProdDTO;
import com.interesting.modules.product.dto.QueryProductInfoDTO;
import com.interesting.modules.product.entity.XqProductInfo;
import com.interesting.modules.product.vo.XqProductInfoVO;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_product_info(产品信息表)】的数据库操作Service
* @createDate 2023-06-07 16:50:57
*/
public interface XqProductInfoService extends IService<XqProductInfo> {

    IPage<XqProductInfoVO> queryXqProductInfoVOPage(QueryProductInfoDTO dto);


    void updateOrderProd(String id, List<AddOrderProdDTO> orderProdVOS);

}
