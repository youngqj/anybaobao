package com.interesting.modules.commission.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.commission.dto.QueryCommissionTempDTO;
import com.interesting.modules.commission.dto.QueryCommissionTempDetailDTO;
import com.interesting.modules.commission.entity.XqOrderCommission;
import com.interesting.modules.commission.vo.CommissionHistoryTmpDetailsVO;
import com.interesting.modules.commission.vo.CommissionHistoryTmpVO;
import com.interesting.modules.order.dto.sub.AddOrderComsDTO;

import java.util.List;

/**
 * @Description: 佣金表
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
public interface IXqOrderCommissionService extends IService<XqOrderCommission> {

//    IPage<XqOrderCommissionVO> pageList(Page<XqOrderCommissionVO> page, QueryXqOrderCommissionDTO dto);

    IPage<CommissionHistoryTmpVO> pageCommissionTemp(String customerId, QueryCommissionTempDTO dto);

    IPage<CommissionHistoryTmpDetailsVO> pageCommissionTempDetails(String templateId, QueryCommissionTempDetailDTO dto);

    void updateOrderCommissions(String id, List<AddOrderComsDTO> orderComsVOS, String customerId);


}
