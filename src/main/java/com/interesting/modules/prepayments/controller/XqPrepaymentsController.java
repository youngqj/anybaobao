package com.interesting.modules.prepayments.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.system.base.controller.InterestingBootController;
import com.interesting.common.system.query.QueryGenerator;
import com.interesting.modules.prepayments.dto.XqPrepaymentsDto;
import com.interesting.modules.prepayments.dto.XqPrepaymentsQueryDto;
import com.interesting.modules.prepayments.dto.XqPrepaymentsResDto;
import com.interesting.modules.prepayments.dto.XqPrepaymentsStatisticsDto;
import com.interesting.modules.prepayments.entity.XqPrepayments;
import com.interesting.modules.prepayments.service.IXqPrepaymentsService;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 预付款
 * @Author: jeecg-boot
 * @Date:   2025-03-06
 * @Version: V1.0
 */
@Api(tags="预付款")
@RestController
@RequestMapping("/prepayments/xqPrepayments")
@Slf4j
public class XqPrepaymentsController extends InterestingBootController<XqPrepayments, IXqPrepaymentsService> {
	@Autowired
	private IXqPrepaymentsService xqPrepaymentsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "预付款-分页列表查询")
	@ApiOperation(value="预付款-分页列表查询", notes="预付款-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(XqPrepaymentsQueryDto xqPrepaymentsQueryDto,
													  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													  HttpServletRequest req) {
		Page<XqPrepaymentsResDto> page = new Page<>(pageNo, pageSize);
		IPage<XqPrepaymentsResDto> pageList = xqPrepaymentsService.queryPageList(page, xqPrepaymentsQueryDto);
		List<XqPrepaymentsStatisticsDto> dto = xqPrepaymentsService.prepaymentsStatistics(xqPrepaymentsQueryDto);
		return Result.OK2(pageList, dto);
	}
	
	/**
	 *   添加
	 *
	 * @param xqPrepayments
	 * @return
	 */
	@AutoLog(value = "预付款-添加")
	@ApiOperation(value="预付款-添加", notes="预付款-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody XqPrepayments xqPrepayments) {
		xqPrepaymentsService.save(xqPrepayments);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param xqPrepayments
	 * @return
	 */
	@AutoLog(value = "预付款-编辑")
	@ApiOperation(value="预付款-编辑", notes="预付款-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody XqPrepayments xqPrepayments) {
		xqPrepaymentsService.edit(xqPrepayments);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "预付款-通过id删除")
	@ApiOperation(value="预付款-通过id删除", notes="预付款-通过id删除")
	@GetMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		xqPrepaymentsService.deleteById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "预付款-批量删除")
	@ApiOperation(value="预付款-批量删除", notes="预付款-批量删除")
	@GetMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.xqPrepaymentsService.deleteByIds(ids);
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "预付款-通过id查询")
	@ApiOperation(value="预付款-通过id查询", notes="预付款-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<XqPrepayments> queryById(@RequestParam(name="id",required=true) String id) {
		XqPrepayments xqPrepayments = xqPrepaymentsService.getById(id);
		return Result.OK(xqPrepayments);
	}

	 @AutoLog(value = "预付款-根据供应商和币种获取预付款列表")
	 @ApiOperation(value="预付款-根据供应商和币种获取预付款列表", notes="预付款-根据供应商和币种获取预付款列表")
	 @GetMapping(value = "/queryListBySupplierAndCurrency")
	 public Result<IPage<XqPrepaymentsDto>> queryListBySupplierAndCurrency(@ApiParam(required = true, name = "supplierId", value = "供应商id") @RequestParam(name="supplierId",required=true) String supplierId,
																		   @ApiParam(required = true, name = "currency", value = "币种") @RequestParam(name="currency",required=true) String currency,
																		   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
																		   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
		 IPage<XqPrepaymentsDto> xqPrepaymentsDtoIPage = xqPrepaymentsService.queryListBySupplierAndCurrency(supplierId, currency, pageNo, pageSize);
		 return Result.OK(xqPrepaymentsDtoIPage);
	 }

}
