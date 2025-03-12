package com.interesting.modules.expense.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.expense.dto.QueryExpensesDTO;
import com.interesting.modules.expense.dto.WarehouseExpensesVO;
import com.interesting.modules.expense.entity.XqWarehouseExpenses;
import com.interesting.modules.expense.vo.WarehouseTotalAmountVO;
import org.apache.ibatis.annotations.Param;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/12 9:00
 * @Project: trade-manage
 * @Description:
 */
public interface XqWarehouseExpensesMapper extends BaseMapper<XqWarehouseExpenses> {
    IPage<WarehouseExpensesVO> getWarehouseList(@Param("page") Page<WarehouseExpensesVO> page, @Param("dto") QueryExpensesDTO dto);

    WarehouseTotalAmountVO getWarehouseTotalAmount(@Param("dto") QueryExpensesDTO dto);
}
