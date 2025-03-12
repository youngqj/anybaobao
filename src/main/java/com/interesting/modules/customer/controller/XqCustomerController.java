package com.interesting.modules.customer.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.commissioncompany.service.XqCommissionCompanyService;
import com.interesting.modules.customer.dto.AddXqCustomerDTO;
import com.interesting.modules.customer.dto.QueryXqCustomerDTO;
import com.interesting.modules.customer.dto.UpdateXqCustomerDTO;
import com.interesting.modules.customer.entity.XqCustomer;
import com.interesting.modules.customer.service.IXqCustomerService;
import com.interesting.modules.customer.vo.CustomerIdNameAndAddressVO;
import com.interesting.modules.customer.vo.XqCustomerVO;
import com.interesting.modules.reportform.dto.QueryOrderAcsrDTO;
import com.interesting.modules.reportform.vo.OrderAscrListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
 * @Description: 客户表
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Slf4j
@Api(tags="客户表")
@RestController
@RequestMapping("/customer/xqCustomer")
public class XqCustomerController {
	@Autowired
	private IXqCustomerService xqCustomerService;
	@Autowired
	private XqCommissionCompanyService xqCommissionCompanyService;

	/**
	 * 分页列表查询
	 *
	 * @param xqCustomer
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "客户表-分页列表查询")
	@ApiOperation(value="客户表-分页列表查询", notes="客户表-分页列表查询")
	@GetMapping(value = "/page")
	public Result<IPage<XqCustomerVO>> queryPageList(QueryXqCustomerDTO dto) {
        Page<XqCustomerVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
		IPage<XqCustomerVO> pageList = xqCustomerService.pageList(page, dto);
		return Result.OK(pageList);
	}




	 /**
	  * 客户导出
	  */
	 @AutoLog(value = "客户导出")
	 @ApiOperation(value = "客户导出", notes = "客户导出")
	 @GetMapping(value = "/customerExport", produces = "application/octet-stream")
	 public void customerExport(QueryXqCustomerDTO dto, HttpServletResponse response) {
		 Page<XqCustomerVO> page = new Page<>(1, 9999);
		 xqCustomerService.customerExport(page, dto, response);

	 }

	/**
	 * 添加
	 *
	 * @param xqCustomer
	 * @return
	 */
	@AutoLog(value = "客户表-添加")
	@ApiOperation(value="客户表-添加", notes="客户表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody @Valid AddXqCustomerDTO dto) {
		boolean b = xqCustomerService.saveCustomer(dto);
		return b ? Result.OK("添加成功！") : Result.OK("添加失败！");
	}

//	 /**
//	  * 添加佣金公司
//	  *
//	  * @param xqCustomer
//	  * @return
//	  */
//	 @AutoLog(value = "佣金公司-添加佣金公司")
//	 @ApiOperation(value="佣金公司-添加佣金公司", notes="佣金公司-添加佣金公司")
//	 @PostMapping(value = "/addCommissionCompany")
//	 public Result<?> addCommissionCompany(@RequestBody @Valid AddCommissionCompanyDTO dto) {
//		 boolean b = xqCommissionCompanyService.addCommissionCompany(dto);
//		 return b ? Result.OK("添加成功！") : Result.OK("添加失败！");
//	 }
//
//	 /**
//	  * 编辑佣金公司
//	  *
//	  * @param xqCustomer
//	  * @return
//	  */
//	 @AutoLog(value = "佣金公司-编辑佣金公司")
//	 @ApiOperation(value="佣金公司-编辑佣金公司", notes="佣金公司-编辑佣金公司")
//	 @PostMapping(value = "/editCommissionCompany")
//	 public Result<?> editCommissionCompany(@RequestBody AddCommissionCompanyDTO dto) {
//		 boolean b = xqCommissionCompanyService.editCommissionCompany(dto);
//		 return b ? Result.OK("添加成功！") : Result.OK("添加失败！");
//	 }
//
//	 /**
//	  * 删除佣金公司
//	  *
//	  * @param id
//	  * @return
//	  */
//	 @AutoLog(value = "佣金公司-删除佣金公司")
//	 @ApiOperation(value="佣金公司-删除佣金公司", notes="佣金公司-删除佣金公司")
//	 @GetMapping(value = "/deleteCommissionCompany")
//	 public Result<?> deleteCommissionCompany(@RequestParam(name="id") String id) {
//		 boolean b = xqCommissionCompanyService.removeById(id);
//		 return b ? Result.OK("删除成功！") : Result.OK("删除失败！");
//	 }

//	 /**
//	  * 佣金公司-分页列表查询
//	  *
//	  * @param xqCustomer
//	  * @param pageNo
//	  * @param pageSize
//	  * @param req
//	  * @return
//	  */
//	 @AutoLog(value = "佣金公司-分页列表查询")
//	 @ApiOperation(value="佣金公司-分页列表查询", notes="佣金公司-分页列表查询")
//	 @GetMapping(value = "/commissionPage")
//	 public Result<IPage<XqCommissionCompany>> commissionPage(
//			 @RequestParam String customerId,
//			 @RequestParam(defaultValue = "1") Integer pageNo,
//			 @RequestParam(defaultValue = "10") Integer pageSize,
//			 @RequestParam(required = false) String name
//	 ) {
//		 Page<XqCommissionCompany> page = new Page<>(pageNo, pageSize);
//		 IPage<XqCommissionCompany> pageList = xqCommissionCompanyService.lambdaQuery()
//				 .eq(XqCommissionCompany::getCustomerId, customerId)
//				 .like(StringUtils.isNotBlank(name), XqCommissionCompany::getName, name)
//				 .page(page);
//		 return Result.OK(pageList);
//	 }


	/**
	 * 编辑
	 *
	 * @param xqCustomer
	 * @return
	 */
	@AutoLog(value = "客户表-编辑")
	@ApiOperation(value="客户表-编辑", notes="客户表-编辑")
	@PostMapping(value = "/edit")
	public Result<?> edit(@RequestBody @Valid UpdateXqCustomerDTO dto) {

        boolean b = xqCustomerService.updateCustomerById(dto);

		return b ? Result.OK("编辑成功!") : Result.OK("编辑失败!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "客户表-通过id删除")
	@ApiOperation(value="客户表-通过id删除", notes="客户表-通过id删除")
	@GetMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id") String id) {
		xqCustomerService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "客户表-批量删除")
	@ApiOperation(value="客户表-批量删除", notes="客户表-批量删除")
	@GetMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids") String ids) {
		this.xqCustomerService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "客户表-通过id查询")
	@ApiOperation(value="客户表-通过id查询", notes="客户表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id") String id) {
		XqCustomerVO xqCustomer = xqCustomerService.getDetailById(id);
		return Result.OK(xqCustomer);
	}

	 /**
	  * 查询客户名称列表
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "客户表-查询客户名称列表")
	 @ApiOperation(value="客户表-查询客户名称列表", notes="客户表-查询客户名称列表")
	 @GetMapping(value = "/queryNameList")
	 public Result<?> queryNameList(String name) {
//		 List<XqCustomer> list = xqCustomerService.list();
		 List<XqCustomer> list = xqCustomerService.lambdaQuery()
				 .like(StringUtils.isNotBlank(name), XqCustomer::getName, name)
				 .orderByAsc(XqCustomer::getName).list();
		 List<JSONObject> collect = list.stream().map(i -> {
			 JSONObject js = new JSONObject();
			 js.put("id", i.getId());
			 js.put("name", i.getName() == null ? "" : i.getName());
			 js.put("address",i.getAddress());
			 return js;
		 }).collect(Collectors.toList());

		 return Result.OK(collect);
	 }


	 /**
	  * 导出模板
	  *
	  * @return
	  */
	 @AutoLog(value = "导出客户信息模板")
	 @ApiOperation(value = "导出客户信息模板", notes = "导出客户信息模板")
	 @GetMapping(value = "/exportTemplate")
	 public void exportTemplate(HttpServletResponse response) throws UnsupportedEncodingException {
		 xqCustomerService.exportTemplate(response);
	 }

	 /**
	  * 导入供应商
	  *
	  * @return
	  */
	 @AutoLog(value = "导入客户信息模板")
	 @ApiOperation(value = "导入客户模板", notes = "导入客户模板")
	 @PostMapping(value = "/importCustomer")
	 public Result<?> importCustomer(@RequestPart MultipartFile file) throws Exception {
		 return xqCustomerService.importCustomer(file);
	 }

	 /**
	  * 查询付款方式列表
	  */
	 @AutoLog(value = "查询客户列表")
	 @ApiOperation(value = "查询客户列表", notes = "查询客户列表",response = CustomerIdNameAndAddressVO.class)
	 @GetMapping(value = "/listCustomer")
	 public Result<?> listCustomer() {

		 return Result.OK(xqCustomerService.listCustomer());
	 }


}
