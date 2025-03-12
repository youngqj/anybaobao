package com.interesting.modules.insurance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.insurance.entity.XqInsuranceExpenses;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.insurance.dto.QueryXqInsuranceExpensesDTO;
import com.interesting.modules.insurance.vo.XqInsuranceExpensesVO;
import com.interesting.modules.order.dto.sub.AddOrderInsuranceDTO;

import java.util.List;

/**
 * @Description: 保险费用信息
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
public interface IXqInsuranceExpensesService extends IService<XqInsuranceExpenses> {
    /**
     * 分页列表查询
     *
     * @param page
     * @return
     */
    IPage<XqInsuranceExpensesVO> pageList(Page<XqInsuranceExpensesVO> page, QueryXqInsuranceExpensesDTO dto);

    void updateOrderInsurance(String id, List<AddOrderInsuranceDTO> orderInsuranceVOS);

}
