package com.interesting.modules.prepayments.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.prepayments.dto.XqPrepaymentsDto;
import com.interesting.modules.prepayments.dto.XqPrepaymentsQueryDto;
import com.interesting.modules.prepayments.dto.XqPrepaymentsResDto;
import com.interesting.modules.prepayments.dto.XqPrepaymentsStatisticsDto;
import com.interesting.modules.prepayments.entity.XqPrepayments;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 预付款
 * @Author: jeecg-boot
 * @Date:   2025-03-06
 * @Version: V1.0
 */
public interface IXqPrepaymentsService extends IService<XqPrepayments> {

    IPage<XqPrepaymentsResDto> queryPageList(Page<XqPrepaymentsResDto> page, XqPrepaymentsQueryDto xqPrepaymentsQueryDto);

    List<XqPrepaymentsStatisticsDto> prepaymentsStatistics(XqPrepaymentsQueryDto xqPrepaymentsQueryDto);

    void edit(XqPrepayments xqPrepayments);

    void deleteById(String id);

    void deleteByIds(String ids);

    IPage<XqPrepaymentsDto> queryListBySupplierAndCurrency(String supplierId, String currency, Integer pageNo, Integer pageSize);

}
