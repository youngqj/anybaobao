package com.interesting.modules.prepayments.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.prepayments.dto.XqPrepaymentsDto;
import com.interesting.modules.prepayments.dto.XqPrepaymentsQueryDto;
import com.interesting.modules.prepayments.dto.XqPrepaymentsResDto;
import com.interesting.modules.prepayments.dto.XqPrepaymentsStatisticsDto;
import org.apache.ibatis.annotations.Param;
import com.interesting.modules.prepayments.entity.XqPrepayments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 预付款
 * @Author: jeecg-boot
 * @Date:   2025-03-06
 * @Version: V1.0
 */
public interface XqPrepaymentsMapper extends BaseMapper<XqPrepayments> {

    IPage<XqPrepaymentsResDto> queryPageList(Page<XqPrepaymentsResDto> page, XqPrepaymentsQueryDto dto);

    List<XqPrepaymentsStatisticsDto> prepaymentsStatistics(@Param("dto") XqPrepaymentsQueryDto dto);

    IPage<XqPrepaymentsDto> queryListBySupplierAndCurrency(Page<XqPrepaymentsDto> page, String supplierId, String currency);

}
