package com.interesting.modules.remittance.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import com.interesting.common.api.vo.Result;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.system.query.QueryGenerator;
import com.interesting.common.aspect.annotation.AutoLog;

import com.interesting.modules.remittance.entity.XqRemittanceDetail;
import com.interesting.modules.remittance.service.IXqRemittanceDetailService;
import com.interesting.modules.remittance.dto.*;
import com.interesting.modules.remittance.vo.*;

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
 * @Description: 收汇情况表
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Slf4j
@Api(tags="收汇情况表")
//@RestController
@RequestMapping("/remittance/xqRemittanceDetail")
public class XqRemittanceDetailController {
	@Autowired
	private IXqRemittanceDetailService xqRemittanceDetailService;

	/**
	 * 分页列表查询
	 *
	 * @param xqRemittanceDetail
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "收汇情况表-分页列表查询")
	@ApiOperation(value="收汇情况表-分页列表查询", notes="收汇情况表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<XqRemittanceDetailVO>> queryPageList(QueryXqRemittanceDetailDTO dto) {
        Page<XqRemittanceDetailVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
		IPage<XqRemittanceDetailVO> pageList = xqRemittanceDetailService.pageList(page, dto);
		return Result.OK(pageList);
	}

	/**
	 * 添加
	 *
	 * @param xqRemittanceDetail
	 * @return
	 */
	@AutoLog(value = "收汇情况表-添加")
	@ApiOperation(value="收汇情况表-添加", notes="收汇情况表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AddXqRemittanceDetailDTO dto) {
        XqRemittanceDetail xqRemittanceDetail = new XqRemittanceDetail();
        BeanUtils.copyProperties(dto, xqRemittanceDetail);
		boolean b = xqRemittanceDetailService.save(xqRemittanceDetail);
		return b ? Result.OK("添加成功！") : Result.OK("添加失败！");
	}

	/**
	 * 编辑
	 *
	 * @param xqRemittanceDetail
	 * @return
	 */
	@AutoLog(value = "收汇情况表-编辑")
	@ApiOperation(value="收汇情况表-编辑", notes="收汇情况表-编辑")
	@PostMapping(value = "/edit")
	public Result<?> edit(@RequestBody UpdateXqRemittanceDetailDTO dto) {
        String id = dto.getId();
        XqRemittanceDetail byId = xqRemittanceDetailService.getById(id);
        if (byId == null) {
            throw new InterestingBootException("该记录不存在");
        }
        BeanUtils.copyProperties(dto, byId);
        boolean b = xqRemittanceDetailService.updateById(byId);

		return b ? Result.OK("编辑成功!") : Result.OK("编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "收汇情况表-通过id删除")
	@ApiOperation(value="收汇情况表-通过id删除", notes="收汇情况表-通过id删除")
	@GetMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id") String id) {
		xqRemittanceDetailService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "收汇情况表-批量删除")
	@ApiOperation(value="收汇情况表-批量删除", notes="收汇情况表-批量删除")
	@GetMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids") String ids) {
		this.xqRemittanceDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "收汇情况表-通过id查询")
	@ApiOperation(value="收汇情况表-通过id查询", notes="收汇情况表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id") String id) {
		XqRemittanceDetail xqRemittanceDetail = xqRemittanceDetailService.getById(id);
		return Result.OK(xqRemittanceDetail);
	}
}
