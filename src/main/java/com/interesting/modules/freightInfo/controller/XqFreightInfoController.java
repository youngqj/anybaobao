package com.interesting.modules.freightInfo.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import com.interesting.common.api.vo.Result;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.system.query.QueryGenerator;
import com.interesting.common.aspect.annotation.AutoLog;

import com.interesting.modules.freightInfo.entity.XqFreightInfo;
import com.interesting.modules.freightInfo.service.IXqFreightInfoService;
import com.interesting.modules.freightInfo.dto.*;
import com.interesting.modules.freightInfo.vo.*;

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
 * @Description: 货运信息表
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
@Slf4j
@Api(tags="货运信息表")
//@RestController
@RequestMapping("/freightInfo/xqFreightInfo")
public class XqFreightInfoController {
	@Autowired
	private IXqFreightInfoService xqFreightInfoService;

	/**
	 * 分页列表查询
	 *
	 * @param xqFreightInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "货运信息表-分页列表查询")
	@ApiOperation(value="货运信息表-分页列表查询", notes="货运信息表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<XqFreightInfoVO>> queryPageList(QueryXqFreightInfoDTO dto) {
        Page<XqFreightInfoVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
		IPage<XqFreightInfoVO> pageList = xqFreightInfoService.pageList(page, dto);
		return Result.OK(pageList);
	}

	/**
	 * 添加
	 *
	 * @param xqFreightInfo
	 * @return
	 */
	@AutoLog(value = "货运信息表-添加")
	@ApiOperation(value="货运信息表-添加", notes="货运信息表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AddXqFreightInfoDTO dto) {
        XqFreightInfo xqFreightInfo = new XqFreightInfo();
        BeanUtils.copyProperties(dto, xqFreightInfo);
		boolean b = xqFreightInfoService.save(xqFreightInfo);
		return b ? Result.OK("添加成功！") : Result.OK("添加失败！");
	}

	/**
	 * 编辑
	 *
	 * @param xqFreightInfo
	 * @return
	 */
	@AutoLog(value = "货运信息表-编辑")
	@ApiOperation(value="货运信息表-编辑", notes="货运信息表-编辑")
	@PostMapping(value = "/edit")
	public Result<?> edit(@RequestBody UpdateXqFreightInfoDTO dto) {
        String id = dto.getId();
        XqFreightInfo byId = xqFreightInfoService.getById(id);
        if (byId == null) {
            throw new InterestingBootException("该记录不存在");
        }
        BeanUtils.copyProperties(dto, byId);
        boolean b = xqFreightInfoService.updateById(byId);

		return b ? Result.OK("编辑成功!") : Result.OK("编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "货运信息表-通过id删除")
	@ApiOperation(value="货运信息表-通过id删除", notes="货运信息表-通过id删除")
	@GetMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id") String id) {
		xqFreightInfoService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "货运信息表-批量删除")
	@ApiOperation(value="货运信息表-批量删除", notes="货运信息表-批量删除")
	@GetMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids") String ids) {
		this.xqFreightInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "货运信息表-通过id查询")
	@ApiOperation(value="货运信息表-通过id查询", notes="货运信息表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id") String id) {
		XqFreightInfo xqFreightInfo = xqFreightInfoService.getById(id);
		return Result.OK(xqFreightInfo);
	}
}
