package com.interesting.modules.reportform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.reportform.dto.QueryPageSellProfitDTO;
import com.interesting.modules.reportform.entity.XqSellingProfit;
import com.interesting.modules.reportform.vo.SellProfitPageVO;
import com.interesting.modules.reportform.vo.SellProfitTotalVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/6 17:05
 * @Project: trade-manage
 * @Description:
 */
public interface XqSellingProfitMapper extends BaseMapper<XqSellingProfit> {

    // 查询销售利润表
    IPage<SellProfitPageVO> pageSellProfitNew(@Param("page") Page<SellProfitPageVO> page,
                                              @Param("dto") QueryPageSellProfitDTO dto,
                                              @Param("customerIds") List<String> customerIds);

    SellProfitTotalVO  getTotal(@Param("dto")QueryPageSellProfitDTO dto, @Param("customerIds")List<String> customerIds);

    List<XqSellingProfit> getSellingProfitListByOrderId(String orderId);
}
