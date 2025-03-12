package com.interesting.modules.supplier.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.supplier.dto.AddXqSupplierDTO;
import com.interesting.modules.supplier.dto.QueryXqSupplierDTO;
import com.interesting.modules.supplier.dto.UpdateXqSupplierDTO;
import com.interesting.modules.supplier.entity.XqSupplier;
import com.interesting.modules.supplier.service.IXqSupplierService;
import com.interesting.modules.supplier.vo.XqSupplierVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 供应商表
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Slf4j
@Api(tags="供应商表")
@RestController
@RequestMapping("/supplier/xqSupplier")
public class XqSupplierController {
	@Autowired
	private IXqSupplierService xqSupplierService;

	/**
	 * 分页列表查询
	 *
	 * @param xqSupplier
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "供应商表-分页列表查询")
	@ApiOperation(value="供应商表-分页列表查询", notes="供应商表-分页列表查询")
	@GetMapping(value = "/page")
	public Result<IPage<XqSupplierVO>> queryPageList(QueryXqSupplierDTO dto) {
        Page<XqSupplierVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
		IPage<XqSupplierVO> pageList = xqSupplierService.pageList(page, dto);

		return Result.OK(pageList);
	}

	/**
	 * 添加
	 *
	 * @param xqSupplier
	 * @return
	 */
	@AutoLog(value = "供应商表-添加")
	@ApiOperation(value="供应商表-添加", notes="供应商表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody @Valid AddXqSupplierDTO dto) {
        XqSupplier xqSupplier = new XqSupplier();
        BeanUtils.copyProperties(dto, xqSupplier);
		boolean b = xqSupplierService.saveSupplier(dto);
		return b ? Result.OK("添加成功！") : Result.OK("添加失败！");
	}

	/**
	 * 编辑
	 *
	 * @param xqSupplier
	 * @return
	 */
	@AutoLog(value = "供应商表-编辑")
	@ApiOperation(value="供应商表-编辑", notes="供应商表-编辑")
	@PostMapping(value = "/edit")
	public Result<?> edit(@RequestBody @Valid UpdateXqSupplierDTO dto) {

        boolean b = xqSupplierService.updateSupplierById(dto);

		return b ? Result.OK("编辑成功!") : Result.OK("编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "供应商表-通过id删除")
	@ApiOperation(value="供应商表-通过id删除", notes="供应商表-通过id删除")
	@GetMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id") String id) {
		xqSupplierService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "供应商表-批量删除")
	@ApiOperation(value="供应商表-批量删除", notes="供应商表-批量删除")
	@GetMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids") String ids) {
		this.xqSupplierService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "供应商表-通过id查询")
	@ApiOperation(value="供应商表-通过id查询", notes="供应商表-通过id查询", response = XqSupplierVO.class)
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id") String id) {
		XqSupplierVO xqSupplier = xqSupplierService.getCustomerById(id);
		return Result.OK(xqSupplier);
	}

	 /**
	  * 查询供应商名称列表
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "供应商表-查询供应商名称列表")
	 @ApiOperation(value="供应商表-查询供应商名称列表", notes="客户表-查询供应商名称列表")
	 @GetMapping(value = "/queryNameList")
	 public Result<?> queryNameList(@RequestParam(defaultValue = "1") String type) {
		 List<XqSupplier> list = xqSupplierService.lambdaQuery().eq(XqSupplier::getType, type).list();
		 List<JSONObject> collect = list.stream().map(i -> {
			 JSONObject js = new JSONObject();
			 js.put("name", i.getName());
			 js.put("id", i.getId());
			 js.put("taxRate",i.getTaxRate());
			 return js;
		 }).collect(Collectors.toList());

		 return Result.OK(collect);
	 }

	@AutoLog(value = "供应商表-查询供应商列表")
	@ApiOperation(value="供应商表-查询供应商列表", notes="客户表-查询供应商列表")
	@GetMapping(value = "/querySupplierList")
	public Result<?> querySupplierList(String name) {
		List<XqSupplier> list = xqSupplierService.lambdaQuery()
				.like(StringUtils.isNotBlank(name), XqSupplier::getName, name).list();
		List<JSONObject> collect = list.stream().map(i -> {
			JSONObject js = new JSONObject();
			js.put("name", i.getName());
			js.put("id", i.getId());
			return js;
		}).collect(Collectors.toList());

		return Result.OK(collect);
	}


	 /**
	  * 导出模板
	  *
	  * @return
	  */
	 @AutoLog(value = "导出供应商模板")
	 @ApiOperation(value = "导出供应商模板", notes = "导出供应商模板")
	 @GetMapping(value = "/exportTemplate")
	 public void exportTemplate(HttpServletResponse response) throws UnsupportedEncodingException {
		 xqSupplierService.exportTemplate(response);
	 }

	/**
	 * 导入供应商
	 *
	 * @return
	 */
	@AutoLog(value = "导入供应商")
	@ApiOperation(value = "导入供应商", notes = "导入供应商")
	@PostMapping(value = "/importSupplier")
	public Result<?> importSupplier(@RequestPart MultipartFile file) throws Exception {
		return xqSupplierService.importSupplier(file);
	}


	@AutoLog(value = "供应商表-导出")
	@ApiOperation(value="供应商表-导出", notes="供应商表-导出")
	@GetMapping(value = "/supplierExport", produces = "application/octet-stream")
	public void supplierExport(QueryXqSupplierDTO dto, HttpServletResponse response) {
		Page<XqSupplierVO> page = new Page<>(1, 9999);
		xqSupplierService.supplierExport(page, dto,response);

	}

}
