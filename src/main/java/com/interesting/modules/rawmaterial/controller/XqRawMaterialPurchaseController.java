package com.interesting.modules.rawmaterial.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import com.interesting.common.api.vo.Result;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.system.query.QueryGenerator;
import com.interesting.common.aspect.annotation.AutoLog;

import com.interesting.modules.rawmaterial.entity.XqRawMaterialPurchase;
import com.interesting.modules.rawmaterial.service.IXqRawMaterialPurchaseService;
import com.interesting.modules.rawmaterial.dto.*;
import com.interesting.modules.rawmaterial.service.XqWithholdDetailService;
import com.interesting.modules.rawmaterial.vo.*;

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
 * @Description: 原料采购表
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
@Slf4j
@Api(tags="原料采购表")
//@RestController
@RequestMapping("/rawmaterial/xqRawMaterialPurchase")
public class XqRawMaterialPurchaseController {
	@Autowired
	private IXqRawMaterialPurchaseService xqRawMaterialPurchaseService;

	/**
	 * 分页列表查询
	 *
	 * @param xqRawMaterialPurchase
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "原料采购表-分页列表查询")
	@ApiOperation(value="原料采购表-分页列表查询", notes="原料采购表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<XqRawMaterialPurchaseVO>> queryPageList(QueryXqRawMaterialPurchaseDTO dto) {
        Page<XqRawMaterialPurchaseVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
		IPage<XqRawMaterialPurchaseVO> pageList = xqRawMaterialPurchaseService.pageList(page, dto);
		return Result.OK(pageList);
	}

	/**
	 * 添加
	 *
	 * @param xqRawMaterialPurchase
	 * @return
	 */
	@AutoLog(value = "原料采购表-添加")
	@ApiOperation(value="原料采购表-添加", notes="原料采购表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AddXqRawMaterialPurchaseDTO dto) {
        XqRawMaterialPurchase xqRawMaterialPurchase = new XqRawMaterialPurchase();
        BeanUtils.copyProperties(dto, xqRawMaterialPurchase);
		boolean b = xqRawMaterialPurchaseService.save(xqRawMaterialPurchase);
		return b ? Result.OK("添加成功！") : Result.OK("添加失败！");
	}

	/**
	 * 编辑
	 *
	 * @param xqRawMaterialPurchase
	 * @return
	 */
	@AutoLog(value = "原料采购表-编辑")
	@ApiOperation(value="原料采购表-编辑", notes="原料采购表-编辑")
	@PostMapping(value = "/edit")
	public Result<?> edit(@RequestBody UpdateXqRawMaterialPurchaseDTO dto) {
        String id = dto.getId();
        XqRawMaterialPurchase byId = xqRawMaterialPurchaseService.getById(id);
        if (byId == null) {
            throw new InterestingBootException("该记录不存在");
        }
        BeanUtils.copyProperties(dto, byId);
        boolean b = xqRawMaterialPurchaseService.updateById(byId);

		return b ? Result.OK("编辑成功!") : Result.OK("编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "原料采购表-通过id删除")
	@ApiOperation(value="原料采购表-通过id删除", notes="原料采购表-通过id删除")
	@GetMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id") String id) {
		xqRawMaterialPurchaseService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "原料采购表-批量删除")
	@ApiOperation(value="原料采购表-批量删除", notes="原料采购表-批量删除")
	@GetMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids") String ids) {
		this.xqRawMaterialPurchaseService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "原料采购表-通过id查询")
	@ApiOperation(value="原料采购表-通过id查询", notes="原料采购表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id") String id) {
		XqRawMaterialPurchase xqRawMaterialPurchase = xqRawMaterialPurchaseService.getById(id);
		return Result.OK(xqRawMaterialPurchase);
	}


 }
