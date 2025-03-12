package com.interesting.modules.paymentcompany.controller;

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
import com.interesting.modules.paymentcompany.entity.XqPaymentCompany;
import com.interesting.modules.paymentcompany.service.IXqPaymentCompanyService;
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
 * @Description: 付款公司
 * @Author: jeecg-boot
 * @Date:   2025-03-06
 * @Version: V1.0
 */
@Api(tags="付款公司")
@RestController
@RequestMapping("/paymentcompany/xqPaymentCompany")
@Slf4j
public class XqPaymentCompanyController extends InterestingBootController<XqPaymentCompany, IXqPaymentCompanyService> {
	@Autowired
	private IXqPaymentCompanyService xqPaymentCompanyService;
	
	/**
	 * 分页列表查询
	 *
	 * @param xqPaymentCompany
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "付款公司-分页列表查询")
	@ApiOperation(value="付款公司-分页列表查询", notes="付款公司-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<XqPaymentCompany>> queryPageList(XqPaymentCompany xqPaymentCompany,
														 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
														 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
														 HttpServletRequest req) {
        QueryWrapper<XqPaymentCompany> queryWrapper = QueryGenerator.initQueryWrapper(xqPaymentCompany, req.getParameterMap());
		Page<XqPaymentCompany> page = new Page<XqPaymentCompany>(pageNo, pageSize);
		IPage<XqPaymentCompany> pageList = xqPaymentCompanyService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param xqPaymentCompany
	 * @return
	 */
	@AutoLog(value = "付款公司-添加")
	@ApiOperation(value="付款公司-添加", notes="付款公司-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody XqPaymentCompany xqPaymentCompany) {
		xqPaymentCompanyService.save(xqPaymentCompany);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param xqPaymentCompany
	 * @return
	 */
	@AutoLog(value = "付款公司-编辑")
	@ApiOperation(value="付款公司-编辑", notes="付款公司-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody XqPaymentCompany xqPaymentCompany) {
		xqPaymentCompanyService.updateById(xqPaymentCompany);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "付款公司-通过id删除")
	@ApiOperation(value="付款公司-通过id删除", notes="付款公司-通过id删除")
	@GetMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		xqPaymentCompanyService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "付款公司-批量删除")
	@ApiOperation(value="付款公司-批量删除", notes="付款公司-批量删除")
	@GetMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.xqPaymentCompanyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "付款公司-通过id查询")
	@ApiOperation(value="付款公司-通过id查询", notes="付款公司-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<XqPaymentCompany> queryById(@RequestParam(name="id",required=true) String id) {
		XqPaymentCompany xqPaymentCompany = xqPaymentCompanyService.getById(id);
		return Result.OK(xqPaymentCompany);
	}

	 @AutoLog(value = "付款公司-获取付款公司列表")
	 @ApiOperation(value="付款公司-获取付款公司列表", notes="付款公司-获取付款公司列表")
	 @GetMapping(value = "/queryList")
	 public Result<List<XqPaymentCompany>> queryList() {
		 List<XqPaymentCompany> xqPaymentCompanyList = xqPaymentCompanyService.list();
		 return Result.OK(xqPaymentCompanyList);
	 }
}
