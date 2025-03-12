package com.interesting.modules.insurance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.util.BeanUtils;
import com.interesting.modules.insurance.dto.QueryXqInsuranceExpensesDTO;
import com.interesting.modules.insurance.entity.XqInsuranceExpenses;
import com.interesting.modules.insurance.mapper.XqInsuranceExpensesMapper;
import com.interesting.modules.insurance.service.IXqInsuranceExpensesService;
import com.interesting.modules.insurance.vo.XqInsuranceExpensesVO;
import com.interesting.modules.order.dto.sub.AddOrderInsuranceDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 保险费用信息
 * @Author: interesting-boot
 * @Date: 2023-06-02
 * @Version: V1.0
 */
@Service
public class XqInsuranceExpensesServiceImpl extends ServiceImpl<XqInsuranceExpensesMapper, XqInsuranceExpenses> implements IXqInsuranceExpensesService {
    @Override
    public IPage<XqInsuranceExpensesVO> pageList(Page<XqInsuranceExpensesVO> page, QueryXqInsuranceExpensesDTO dto) {
        return this.baseMapper.pageList(page, dto);
    }


    /**
     * 差异化处理编辑 保险费用
     *
     * @param orderId
     * @param addOrderInsuranceDTOS
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderInsurance(String orderId, List<AddOrderInsuranceDTO> addOrderInsuranceDTOS) {
        AddOrderInsuranceDTO addOrderInsuranceDTO = addOrderInsuranceDTOS.get(0);
        if (addOrderInsuranceDTO != null) {
            XqInsuranceExpenses xqInsuranceExpenses = this.getOne(new LambdaQueryWrapper<XqInsuranceExpenses>().eq(XqInsuranceExpenses::getOrderId, orderId));
            if (xqInsuranceExpenses == null) {
                xqInsuranceExpenses = new XqInsuranceExpenses();
                BeanUtils.copyProperties(addOrderInsuranceDTO, xqInsuranceExpenses);
                xqInsuranceExpenses.setOrderId(orderId);
                this.save(xqInsuranceExpenses);
            } else {
                this.lambdaUpdate()
                        .set(XqInsuranceExpenses::getInsuranceCompany, addOrderInsuranceDTO.getInsuranceCompany())
                        .set(XqInsuranceExpenses::getInsuranceFee, addOrderInsuranceDTO.getInsuranceFee())
                        .set(XqInsuranceExpenses::getRemark, addOrderInsuranceDTO.getRemark())
                        .eq(XqInsuranceExpenses::getId, addOrderInsuranceDTO.getId())
                        .update();
            }

        }


    }


}
