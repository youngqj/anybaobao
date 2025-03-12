package com.interesting.modules.expense.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.customer.dto.QueryXqCustomerDTO;
import com.interesting.modules.customer.vo.XqCustomerVO;
import com.interesting.modules.expense.dto.QueryExpensesDTO;
import com.interesting.modules.expense.dto.WarehouseExpensesVO;
import com.interesting.modules.expense.service.XqWarehouseExpensesService;
import com.interesting.modules.expense.vo.WarehouseTotalAmountVO;
import com.interesting.modules.reportform.dto.QueryFinanceDTO;
import com.interesting.modules.warehouse.service.XqWarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/12 8:56
 * @Project: trade-manage
 * @Description:
 */



@Api(tags = "仓库费用登记")
@RestController
@RequestMapping("/expense")
public class XqWarehouseExpensesController {

    @Autowired
    private XqWarehouseExpensesService xqWarehouseExpensesService;


    /**
     * 下载仓库费用登记模板
     */
    @AutoLog(value = "下载仓库费用登记模板")
    @ApiOperation(value = "下载仓库费用登记模板", notes = "下载仓库费用登记模板")
    @GetMapping(value = "/expenseTemplateExport")
    public void expenseTemplateExport(HttpServletResponse response) {
        xqWarehouseExpensesService.expenseTemplateExport(response);
    }

    /**
     * 导入仓库费用登记
     */
    @AutoLog(value = "导入仓库费用登记")
    @ApiOperation(value = "导入仓库费用登记", notes = "导入仓库费用登记")
    @PostMapping(value = "/importExpenseTemplate")
    public Result<?> importExpenseTemplate(@RequestBody MultipartFile file) {
       return xqWarehouseExpensesService.importExpenseTemplate(file);
    }


    @AutoLog(value = "仓库费用-分页列表查询")
    @ApiOperation(value="仓库费用-分页列表查询", notes="仓库费用-分页列表查询")
    @GetMapping(value = "/queryPageList")
    public Result<IPage<WarehouseExpensesVO>> queryPageList(QueryExpensesDTO dto) {
        Page<WarehouseExpensesVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        if (!StringUtils.isEmpty(dto.getWarehouseName())) {
            List<String> warehouseNames = Arrays.asList(dto.getWarehouseName().split(","));
            dto.setWarehouseNames(warehouseNames);
        }
        IPage<WarehouseExpensesVO> pageList = xqWarehouseExpensesService.pageList(page, dto);
        List<WarehouseTotalAmountVO>  vo = xqWarehouseExpensesService.getWarehouseTotalAmount(dto);
        return Result.OK2(pageList,vo);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @GetMapping(value = "/deleteBatch")
    @AutoLog(value = "仓库费用-批量删除")
    @ApiOperation(value="仓库费用-批量删除", notes="仓库费用-批量删除")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        xqWarehouseExpensesService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("删除成功！");
    }


}
