package com.interesting.modules.expense.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.common.api.vo.Result;
import com.interesting.modules.expense.dto.QueryExpensesDTO;
import com.interesting.modules.expense.dto.WarehouseExpensesVO;
import com.interesting.modules.expense.entity.XqWarehouseExpenses;
import com.interesting.modules.expense.vo.WarehouseTotalAmountVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/12 9:01
 * @Project: trade-manage
 * @Description:
 */
public interface XqWarehouseExpensesService extends IService<XqWarehouseExpenses> {

    void expenseTemplateExport(HttpServletResponse response);

    Result<?> importExpenseTemplate(MultipartFile file);

    IPage<WarehouseExpensesVO> pageList(Page<WarehouseExpensesVO> page, QueryExpensesDTO dto);

    List<WarehouseTotalAmountVO> getWarehouseTotalAmount(QueryExpensesDTO dto);

}
