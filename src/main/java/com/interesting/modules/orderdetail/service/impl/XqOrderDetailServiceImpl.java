package com.interesting.modules.orderdetail.service.impl;

import com.interesting.modules.orderdetail.entity.XqOrderDetail;
import com.interesting.modules.orderdetail.mapper.XqOrderDetailMapper;
import com.interesting.modules.orderdetail.service.IXqOrderDetailService;
import com.interesting.modules.orderdetail.vo.XqOrderDetailVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.orderdetail.dto.QueryXqOrderDetailDTO;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 订单产品明细
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Service
public class XqOrderDetailServiceImpl extends ServiceImpl<XqOrderDetailMapper, XqOrderDetail> implements IXqOrderDetailService {
    @Override
    public IPage<XqOrderDetailVO> pageList(Page<XqOrderDetailVO> page, QueryXqOrderDetailDTO dto) {
        return this.baseMapper.pageList(page, dto);
    }
}
