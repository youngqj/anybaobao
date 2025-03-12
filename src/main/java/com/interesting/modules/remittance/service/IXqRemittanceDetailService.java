package com.interesting.modules.remittance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.order.dto.sub.AddOrderComsDTO;
import com.interesting.modules.order.dto.sub.AddOrderProdDTO;
import com.interesting.modules.order.dto.sub.AddOrderRemiDTO;
import com.interesting.modules.order.dto.sub.XqOrderExtraCostDTO;
import com.interesting.modules.remittance.entity.XqRemittanceDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.remittance.dto.QueryXqRemittanceDetailDTO;
import com.interesting.modules.remittance.vo.XqRemittanceDetailVO;

import java.util.List;

/**
 * @Description: 收汇情况表
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
public interface IXqRemittanceDetailService extends IService<XqRemittanceDetail> {
    /**
     * 分页列表查询
     *
     * @param page
     * @return
     */
    IPage<XqRemittanceDetailVO> pageList(Page<XqRemittanceDetailVO> page, QueryXqRemittanceDetailDTO dto);

    void updateRemittance(String orderId, List<AddOrderRemiDTO> orderRemiVOS);

}
