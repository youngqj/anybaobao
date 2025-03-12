package com.interesting.modules.commission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.commission.entity.XqCommissionDistribution;
import com.interesting.modules.orderdetail.vo.XqCommissionOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XqCommissionDistributionMapper extends BaseMapper<XqCommissionDistribution> {
    List<XqCommissionOrderVO> commissionOrderList(@Param("orderId") String orderId);

    List<XqCommissionOrderVO> commissionOrderList1(@Param("orderId") String orderId);
}
