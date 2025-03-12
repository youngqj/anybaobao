package com.interesting.modules.freightcost.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.freightcost.dto.QueryXqFreightCostInfoDTO;
import com.interesting.modules.freightcost.entity.XqFreightCostInfo;
import com.interesting.modules.freightcost.vo.XqFreightCostInfoVO;
import com.interesting.modules.order.dto.sub.AddOrderFretCostDTO;

import java.util.List;

/**
 * @Description: 货运费用信息
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
public interface IXqFreightCostInfoService extends IService<XqFreightCostInfo> {
    /**
     * 分页列表查询
     *
     * @param page
     * @return
     */
    IPage<XqFreightCostInfoVO> pageList(Page<XqFreightCostInfoVO> page, QueryXqFreightCostInfoDTO dto);

    void updateOrderFretCost(String orderId, List<AddOrderFretCostDTO> orderFretCostVOS, Integer roleCode);

    void updateOrderFretCost2(String id, List<AddOrderFretCostDTO> orderFretCostVOS2, Integer roleCode);

    void updateOrderFretCostReturnFee(String id, List<AddOrderFretCostDTO> orderFretCostReturnFeeVOS, Integer roleCode);

}
