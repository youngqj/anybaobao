package com.interesting.modules.prepayments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.modules.prepayments.dto.XqPrepaymentsDto;
import com.interesting.modules.prepayments.dto.XqPrepaymentsQueryDto;
import com.interesting.modules.prepayments.dto.XqPrepaymentsResDto;
import com.interesting.modules.prepayments.dto.XqPrepaymentsStatisticsDto;
import com.interesting.modules.prepayments.entity.XqPrepayments;
import com.interesting.modules.prepayments.mapper.XqPrepaymentsMapper;
import com.interesting.modules.prepayments.service.IXqPrepaymentsService;
import com.interesting.modules.rawmaterial.entity.XqPaymentDetail;
import com.interesting.modules.rawmaterial.service.XqPaymentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 预付款
 * @Author: jeecg-boot
 * @Date:   2025-03-06
 * @Version: V1.0
 */
@Service
public class XqPrepaymentsServiceImpl extends ServiceImpl<XqPrepaymentsMapper, XqPrepayments> implements IXqPrepaymentsService {

    @Autowired
    private XqPaymentDetailService xqPaymentDetailService;

    @Override
    public IPage<XqPrepaymentsResDto> queryPageList(Page<XqPrepaymentsResDto> page, XqPrepaymentsQueryDto xqPrepaymentsQueryDto) {
        return this.baseMapper.queryPageList(page, xqPrepaymentsQueryDto);
    }

    @Override
    public List<XqPrepaymentsStatisticsDto> prepaymentsStatistics(XqPrepaymentsQueryDto xqPrepaymentsQueryDto) {
        return this.baseMapper.prepaymentsStatistics(xqPrepaymentsQueryDto);
    }

    @Override
    public void edit(XqPrepayments xqPrepayments) {
        List<XqPaymentDetail> xqPaymentDetailList = xqPaymentDetailService.list(new LambdaQueryWrapper<XqPaymentDetail>().eq(XqPaymentDetail::getPrepaymentsId, xqPrepayments.getId()));
        if (!CollectionUtils.isEmpty(xqPaymentDetailList)) {
            throw new InterestingBootException("该预付款已在面单中，不允许修改！");
        }
        updateById(xqPrepayments);
    }

    @Override
    public void deleteById(String id) {
        List<XqPaymentDetail> xqPaymentDetailList = xqPaymentDetailService.list(new LambdaQueryWrapper<XqPaymentDetail>().eq(XqPaymentDetail::getPrepaymentsId, id));
        if (!CollectionUtils.isEmpty(xqPaymentDetailList)) {
            throw new InterestingBootException("该预付款已在面单中，不允许修改！");
        }
        removeById(id);
    }

    @Override
    public void deleteByIds(String ids) {
        List<XqPaymentDetail> xqPaymentDetailList = xqPaymentDetailService.list(new LambdaQueryWrapper<XqPaymentDetail>().in(XqPaymentDetail::getPrepaymentsId, Arrays.asList(ids.split(","))));
        if (!CollectionUtils.isEmpty(xqPaymentDetailList)) {
            throw new InterestingBootException("该预付款已在面单中，不允许修改！");
        }
        removeByIds(Arrays.asList(ids.split(",")));
    }

    @Override
    public IPage<XqPrepaymentsDto> queryListBySupplierAndCurrency(String supplierId, String currency, Integer pageNo, Integer pageSize) {
        Page<XqPrepaymentsDto> page = new Page<>(pageNo, pageSize);
        IPage<XqPrepaymentsDto> xqPrepaymentsDtoIPage = this.baseMapper.queryListBySupplierAndCurrency(page, supplierId, currency);
        return xqPrepaymentsDtoIPage;
    }
}
