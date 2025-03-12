package com.interesting.modules.orderdetail.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import com.interesting.common.api.vo.Result;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.system.query.QueryGenerator;
import com.interesting.common.aspect.annotation.AutoLog;

import com.interesting.modules.orderdetail.entity.XqOrderDetail;
import com.interesting.modules.orderdetail.service.IXqOrderDetailService;
import com.interesting.modules.orderdetail.dto.*;
import com.interesting.modules.orderdetail.vo.*;

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
 * @Description: 订单产品明细
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Slf4j
@Api(tags="订单产品明细")
//@RestController
@RequestMapping("/orderdetail/xqOrderDetail")
public class XqOrderDetailController {
	@Autowired
	private IXqOrderDetailService xqOrderDetailService;

//	/**
//	 * 分页列表查询
//	 *
//	 * @param xqOrderDetail
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "订单产品明细-分页列表查询")
//	@ApiOperation(value="订单产品明细-分页列表查询", notes="订单产品明细-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<XqOrderDetailVO>> queryPageList(QueryXqOrderDetailDTO dto) {
//        Page<XqOrderDetailVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
//		IPage<XqOrderDetailVO> pageList = xqOrderDetailService.pageList(page, dto);
//		return Result.OK(pageList);
//	}

	/**
	 * 添加
	 *
	 * @param xqOrderDetail
	 * @return
	 */
	@AutoLog(value = "订单产品明细-添加")
	@ApiOperation(value="订单产品明细-添加", notes="订单产品明细-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AddXqOrderDetailDTO dto) {
        XqOrderDetail xqOrderDetail = new XqOrderDetail();
        BeanUtils.copyProperties(dto, xqOrderDetail);
		boolean b = xqOrderDetailService.save(xqOrderDetail);
		return b ? Result.OK("添加成功！") : Result.OK("添加失败！");
	}

	/**
	 * 编辑
	 *
	 * @param xqOrderDetail
	 * @return
	 */
	@AutoLog(value = "订单产品明细-编辑")
	@ApiOperation(value="订单产品明细-编辑", notes="订单产品明细-编辑")
	@PostMapping(value = "/edit")
	public Result<?> edit(@RequestBody UpdateXqOrderDetailDTO dto) {
        String id = dto.getId();
        XqOrderDetail byId = xqOrderDetailService.getById(id);
        if (byId == null) {
            throw new InterestingBootException("该记录不存在");
        }
        BeanUtils.copyProperties(dto, byId);
        boolean b = xqOrderDetailService.updateById(byId);

		return b ? Result.OK("编辑成功!") : Result.OK("编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单产品明细-通过id删除")
	@ApiOperation(value="订单产品明细-通过id删除", notes="订单产品明细-通过id删除")
	@GetMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id") String id) {
		xqOrderDetailService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "订单产品明细-批量删除")
	@ApiOperation(value="订单产品明细-批量删除", notes="订单产品明细-批量删除")
	@GetMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids") String ids) {
		this.xqOrderDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单产品明细-通过id查询")
	@ApiOperation(value="订单产品明细-通过id查询", notes="订单产品明细-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id") String id) {
		XqOrderDetail xqOrderDetail = xqOrderDetailService.getById(id);
		return Result.OK(xqOrderDetail);
	}
}
