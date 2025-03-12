package com.interesting.modules.orderdetail.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.orderdetail.entity.XqOrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.orderdetail.dto.QueryXqOrderDetailDTO;
import com.interesting.modules.orderdetail.vo.XqOrderDetailVO;

/**
 * @Description: 订单产品明细
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
public interface IXqOrderDetailService extends IService<XqOrderDetail> {
    /**
     * 分页列表查询
     *
     * @param page
     * @return
     */
    IPage<XqOrderDetailVO> pageList(Page<XqOrderDetailVO> page, QueryXqOrderDetailDTO dto);
}
