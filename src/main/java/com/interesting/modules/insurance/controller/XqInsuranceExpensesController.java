package com.interesting.modules.insurance.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import com.interesting.common.api.vo.Result;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.system.query.QueryGenerator;
import com.interesting.common.aspect.annotation.AutoLog;

import com.interesting.modules.insurance.entity.XqInsuranceExpenses;
import com.interesting.modules.insurance.service.IXqInsuranceExpensesService;
import com.interesting.modules.insurance.dto.*;
import com.interesting.modules.insurance.vo.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 保险费用信息
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
@Slf4j
@Api(tags="保险费用信息")
//@RestController
@RequestMapping("/insurance/xqInsuranceExpenses")
public class XqInsuranceExpensesController {
	@Autowired
	private IXqInsuranceExpensesService xqInsuranceExpensesService;

	/**
	 * 分页列表查询
	 *
	 * @param xqInsuranceExpenses
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "保险费用信息-分页列表查询")
	@ApiOperation(value="保险费用信息-分页列表查询", notes="保险费用信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<XqInsuranceExpensesVO>> queryPageList(QueryXqInsuranceExpensesDTO dto) {
        Page<XqInsuranceExpensesVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
		IPage<XqInsuranceExpensesVO> pageList = xqInsuranceExpensesService.pageList(page, dto);
		return Result.OK(pageList);
	}

	/**
	 * 添加
	 *
	 * @param xqInsuranceExpenses
	 * @return
	 */
	@AutoLog(value = "保险费用信息-添加")
	@ApiOperation(value="保险费用信息-添加", notes="保险费用信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AddXqInsuranceExpensesDTO dto) {
        XqInsuranceExpenses xqInsuranceExpenses = new XqInsuranceExpenses();
        BeanUtils.copyProperties(dto, xqInsuranceExpenses);
		boolean b = xqInsuranceExpensesService.save(xqInsuranceExpenses);
		return b ? Result.OK("添加成功！") : Result.OK("添加失败！");
	}

	/**
	 * 编辑
	 *
	 * @param xqInsuranceExpenses
	 * @return
	 */
	@AutoLog(value = "保险费用信息-编辑")
	@ApiOperation(value="保险费用信息-编辑", notes="保险费用信息-编辑")
	@PostMapping(value = "/edit")
	public Result<?> edit(@RequestBody UpdateXqInsuranceExpensesDTO dto) {
        String id = dto.getId();
        XqInsuranceExpenses byId = xqInsuranceExpensesService.getById(id);
        if (byId == null) {
            throw new InterestingBootException("该记录不存在");
        }
        BeanUtils.copyProperties(dto, byId);
        boolean b = xqInsuranceExpensesService.updateById(byId);

		return b ? Result.OK("编辑成功!") : Result.OK("编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "保险费用信息-通过id删除")
	@ApiOperation(value="保险费用信息-通过id删除", notes="保险费用信息-通过id删除")
	@GetMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id") String id) {
		xqInsuranceExpensesService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "保险费用信息-批量删除")
	@ApiOperation(value="保险费用信息-批量删除", notes="保险费用信息-批量删除")
	@GetMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids") String ids) {
		this.xqInsuranceExpensesService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "保险费用信息-通过id查询")
	@ApiOperation(value="保险费用信息-通过id查询", notes="保险费用信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id") String id) {
		XqInsuranceExpenses xqInsuranceExpenses = xqInsuranceExpensesService.getById(id);
		return Result.OK(xqInsuranceExpenses);
	}
}
