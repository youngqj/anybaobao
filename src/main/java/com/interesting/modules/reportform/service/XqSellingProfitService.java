package com.interesting.modules.reportform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.reportform.entity.XqSellingProfit;
import com.interesting.modules.reportform.vo.SellProfitPageVO;

import java.util.List;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/6 17:07
 * @Project: trade-manage
 * @Description:
 */
public interface XqSellingProfitService extends IService<XqSellingProfit> {

    /**
     * 初始化销售利润表数据
     *
     * @param records
     * @return
     */
    boolean init(List<SellProfitPageVO> records);


    /**
     * 根据订单id 同步销售利润表数据
     * @param orderId
     */
   public void synchroData(String orderId);
   /**
     * 根据订单id 更新单价
     * @param orderId
     */
   public void updatePrice(String orderId,String orderNum);
}
