package com.interesting.modules.freightcost.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.interesting.common.api.vo.Result;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.system.query.QueryGenerator;
import com.interesting.common.aspect.annotation.AutoLog;

import com.interesting.modules.freightcost.entity.XqFreightCostInfo;
import com.interesting.modules.freightcost.service.IXqFreightCostInfoService;
import com.interesting.modules.freightcost.dto.*;
import com.interesting.modules.freightcost.vo.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.order.mapper.XqOrderMapper;
import com.interesting.modules.order.vo.sub.XqOrderFretCostVO;
import com.interesting.modules.orderdetail.entity.XqOrderDetail;
import com.interesting.modules.product.entity.XqProductInfo;
import com.interesting.modules.product.vo.XqProductInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 货运费用信息
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
@Slf4j
@Api(tags="货运费用信息")
@RestController
@RequestMapping("/freightcost/xqFreightCostInfo")
public class XqFreightCostInfoController {
	@Autowired
	private IXqFreightCostInfoService xqFreightCostInfoService;
	@Autowired
	private XqOrderMapper xqOrderMapper;

	 /**
	  * 查询默认国内货运费用
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "查询默认国内货运费用")
	 @ApiOperation(value="查询默认国内货运费用", notes="查询默认国内货运费用", response = XqOrderFretCostVO.class)
	 @GetMapping(value = "/defaultDomesticFretList")
	 public Result<?> listDefaultDomesticFretCost(@RequestParam String supplierId) {
		 List<XqOrderFretCostVO> customerFretCost = xqOrderMapper.listCustomerFretCost(supplierId,null);
		 return Result.OK(customerFretCost);
	 }

	 /**
	  * 查询默认国外货运费用
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "查询默认国外货运费用")
	 @ApiOperation(value="查询默认国外货运费用", notes="查询默认国外货运费用", response = XqOrderFretCostVO.class)
	 @GetMapping(value = "/defaultForeignFretList")
	 public Result<?> listDefaultForeignFretCost(@RequestParam String customerId) {
		 List<XqOrderFretCostVO> customerFretCost = xqOrderMapper.listCustomerFretCost(customerId,null);
		 return Result.OK(customerFretCost);
	 }


//	/**
//	 * 分页列表查询
//	 *
//	 * @param xqFreightCostInfo
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "货运费用信息-分页列表查询")
//	@ApiOperation(value="货运费用信息-分页列表查询", notes="货运费用信息-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<XqFreightCostInfoVO>> queryPageList(QueryXqFreightCostInfoDTO dto) {
//        Page<XqFreightCostInfoVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
//		IPage<XqFreightCostInfoVO> pageList = xqFreightCostInfoService.pageList(page, dto);
//		return Result.OK(pageList);
//	}
//
//	/**
//	 * 添加
//	 *
//	 * @param xqFreightCostInfo
//	 * @return
//	 */
//	@AutoLog(value = "货运费用信息-添加")
//	@ApiOperation(value="货运费用信息-添加", notes="货运费用信息-添加")
//	@PostMapping(value = "/add")
//	public Result<?> add(@RequestBody AddXqFreightCostInfoDTO dto) {
//        XqFreightCostInfo xqFreightCostInfo = new XqFreightCostInfo();
//        BeanUtils.copyProperties(dto, xqFreightCostInfo);
//		boolean b = xqFreightCostInfoService.save(xqFreightCostInfo);
//		return b ? Result.OK("添加成功！") : Result.OK("添加失败！");
//	}
//
//	/**
//	 * 编辑
//	 *
//	 * @param xqFreightCostInfo
//	 * @return
//	 */
//	@AutoLog(value = "货运费用信息-编辑")
//	@ApiOperation(value="货运费用信息-编辑", notes="货运费用信息-编辑")
//	@PostMapping(value = "/edit")
//	public Result<?> edit(@RequestBody UpdateXqFreightCostInfoDTO dto) {
//        String id = dto.getId();
//        XqFreightCostInfo byId = xqFreightCostInfoService.getById(id);
//        if (byId == null) {
//            throw new InterestingBootException("该记录不存在");
//        }
//        BeanUtils.copyProperties(dto, byId);
//        boolean b = xqFreightCostInfoService.updateById(byId);
//
//		return b ? Result.OK("编辑成功!") : Result.OK("编辑失败!");
//	}
//
//	/**
//	 * 通过id删除
//	 *
//	 * @param id
//	 * @return
//	 */
//	@AutoLog(value = "货运费用信息-通过id删除")
//	@ApiOperation(value="货运费用信息-通过id删除", notes="货运费用信息-通过id删除")
//	@GetMapping(value = "/delete")
//	public Result<?> delete(@RequestParam(name="id") String id) {
//		xqFreightCostInfoService.removeById(id);
//		return Result.OK("删除成功!");
//	}
//
//	/**
//	 * 批量删除
//	 *
//	 * @param ids
//	 * @return
//	 */
//	@AutoLog(value = "货运费用信息-批量删除")
//	@ApiOperation(value="货运费用信息-批量删除", notes="货运费用信息-批量删除")
//	@GetMapping(value = "/deleteBatch")
//	public Result<?> deleteBatch(@RequestParam(name="ids") String ids) {
//		this.xqFreightCostInfoService.removeByIds(Arrays.asList(ids.split(",")));
//		return Result.OK("批量删除成功！");
//	}
//
//	/**
//	 * 通过id查询
//	 *
//	 * @param id
//	 * @return
//	 */
//	@AutoLog(value = "货运费用信息-通过id查询")
//	@ApiOperation(value="货运费用信息-通过id查询", notes="货运费用信息-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<?> queryById(@RequestParam(name="id") String id) {
//		XqFreightCostInfo xqFreightCostInfo = xqFreightCostInfoService.getById(id);
//		return Result.OK(xqFreightCostInfo);
//	}
}
