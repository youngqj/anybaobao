package com.interesting.modules.accmaterial.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.accmaterial.dto.*;
import com.interesting.modules.accmaterial.entity.*;
import com.interesting.modules.accmaterial.service.*;
import com.interesting.modules.accmaterial.vo.*;
import com.interesting.modules.reportform.service.IReportFormService;
import com.interesting.modules.warehouse.service.XqWarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.util.*;
import java.util.stream.Collectors;

 /**
 * @Description: 辅料采购表
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
@Slf4j
@Api(tags="辅料信息")
@RestController
@RequestMapping("/accmaterial/xqAccessoryInfo")
public class XqAccessoriesPurchaseController {
	@Autowired
	private XqAccessoryInfoService xqAccessoryInfoService;
	@Autowired
	private XqAccessoryCategoryService xqAccessoryCategoryService;
	@Autowired
	private XqWarehouseService xqWarehouseService;
	@Autowired
	private XqInventoryCheckService xqInventoryCheckService;
	@Autowired
	private XqInventoryCheckDetailService xqInventoryCheckDetailService;
	@Autowired
	private XqInventoryService xqInventoryService;
	@Autowired
	private IXqAccessoriesPurchaseService xqAccessoriesPurchaseService;
     @Resource
     private IReportFormService xqReportService;


	 /**
	  * 查询产品信息
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "查询辅料信息")
	 @ApiOperation(value="查询辅料信息", notes="查询辅料信息")
	 @GetMapping(value = "/queryAccessoryList")
	 public Result<?> queryAccessoryList() {
         List<XqAccessoryInfoVO> list = xqAccessoryInfoService.queryAccessInfo();

         return Result.OK(list);
	 }

     /**
      * 查询产品信息分页
      *
      * @param id
      * @return
      */
     @AutoLog(value = "查询辅料信息分页")
     @ApiOperation(value = "查询辅料信息分页", notes = "查询辅料信息分页")
     @GetMapping(value = "/queryAccessoryPage")
     public Result<?> queryAccessoryPage(@Param("pageSize") Integer pageSize, @Param("pageNo") Integer pageNo) {

         IPage<XqAccessoryInfoVO> page = xqAccessoryInfoService.queryAccessInfoByPage(pageNo, pageSize);

         return Result.OK(page);
     }



	 /**
	  * 分页列表查询
	  *
	  * @param xqAccessoriesPurchase
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "分页列表查询")
	 @ApiOperation(value="分页列表查询", notes="分页列表查询")
	 @GetMapping(value = "/list")
	 public Result<IPage<XqAccessoryInfoVO>> queryPageList(QueryAccessoryInfoDTO dto) {
		 Page<XqAccessoryInfo> page = new Page<>(dto.getPageNo(), dto.getPageSize());
		 xqAccessoryInfoService.lambdaQuery()
				 .like(StringUtils.isNotBlank(dto.getAccessoryName()), XqAccessoryInfo::getAccessoryName,
						 dto.getAccessoryName())
				 .like(StringUtils.isNotBlank(dto.getSize()), XqAccessoryInfo::getSize, dto.getSize())
				 .orderByDesc(XqAccessoryInfo::getCreateTime)
				 .page(page);

		 IPage<XqAccessoryInfoVO> pageList = page.convert(xqAccessoryInfo -> {
			 XqAccessoryInfoVO vo = new XqAccessoryInfoVO();
			 BeanUtils.copyProperties(xqAccessoryInfo, vo);

			 String categoryId = vo.getCategoryId();
			 XqAccessoryCategory byId = xqAccessoryCategoryService.getById(categoryId);
			 if (byId != null) {
				 vo.setCategoryName(byId.getName());
			 }

			 return vo;
		 });

		 return Result.OK(pageList);
	 }

	 /**
	  * 添加
	  *
	  * @param xqAccessoriesPurchase
	  * @return
	  */
	 @AutoLog(value = "添加")
	 @ApiOperation(value="添加", notes="添加")
	 @PostMapping(value = "/addAccessory")
	 public Result<?> addAccessory(@RequestBody @Valid InstOrUdtXqAccessoryInfoDTO dto) {
		 XqAccessoryInfo xqAccessoryInfo = new XqAccessoryInfo();
		 BeanUtils.copyProperties(dto, xqAccessoryInfo);
         xqAccessoryInfo.setAccessoryName(xqAccessoryInfo.getAccessoryName().trim().replace("\t", ""));
         xqAccessoryInfo.setSize(xqAccessoryInfo.getSize().trim().replace("\t", ""));
         xqAccessoryInfo.setMaterialSpecification(xqAccessoryInfo.getMaterialSpecification().trim().replace("\t", ""));
		 xqAccessoryInfo.setId(null);
		 return Result.OK(xqAccessoryInfoService.save(xqAccessoryInfo));
	 }

	 /**
	  * 编辑
	  *
	  * @param xqAccessoriesPurchase
	  * @return
	  */
	 @AutoLog(value = "编辑")
	 @ApiOperation(value="编辑", notes="编辑")
	 @PostMapping(value = "/edit")
	 public Result<?> edit(@RequestBody @Valid InstOrUdtXqAccessoryInfoDTO dto) {
		 String id = dto.getId();
		 XqAccessoryInfo byId = xqAccessoryInfoService.getById(id);
		 if (byId == null) {
			 throw new InterestingBootException("辅料信息不存在");
		 }
		 XqAccessoryInfo xqAccessoryInfo = new XqAccessoryInfo();
		 org.springframework.beans.BeanUtils.copyProperties(dto, xqAccessoryInfo);
         xqAccessoryInfo.setAccessoryName(xqAccessoryInfo.getAccessoryName().trim().replace("\t", ""));
         xqAccessoryInfo.setSize(xqAccessoryInfo.getSize().trim().replace("\t", ""));
         xqAccessoryInfo.setMaterialSpecification(xqAccessoryInfo.getMaterialSpecification().trim().replace("\t", ""));
		 return Result.OK(xqAccessoryInfoService.updateById(xqAccessoryInfo));
	 }

	 /**
	  * 辅料信息-通过id删除
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "辅料信息-通过id删除")
	 @ApiOperation(value="辅料信息-通过id删除", notes="辅料信息-通过id删除")
	 @GetMapping(value = "/delete")
	 public Result<?> delete(@RequestParam(name="id") String id) {
		 if (xqAccessoriesPurchaseService.lambdaQuery().eq(XqAccessoriesPurchase::getAccessoryId, id).count() > 0
		 	|| xqInventoryCheckDetailService.lambdaQuery().eq(XqInventoryCheckDetail::getAccessoryId, id).count() > 0
		 	|| xqInventoryService.lambdaQuery().eq(XqInventory::getItemId, id).count() > 0) {
			 throw new InterestingBootException("该辅料有相关记录，无法删除");
		 }

		 xqAccessoryInfoService.removeById(id);
		 return Result.OK("删除成功!");
	 }

	 /**
	  * 批量删除
	  *
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "辅料采购表-批量删除")
	 @ApiOperation(value="辅料采购表-批量删除", notes="辅料采购表-批量删除")
	 @GetMapping(value = "/deleteBatch")
	 public Result<?> deleteBatch(@RequestParam(name="ids") String ids) {
		 List<String> strings = Arrays.asList(ids.split(","));
		 if (xqAccessoriesPurchaseService.lambdaQuery().in(XqAccessoriesPurchase::getAccessoryId, strings).count() > 0
		 	|| xqInventoryCheckDetailService.lambdaQuery().in(XqInventoryCheckDetail::getAccessoryId, strings).count() > 0
		 	|| xqInventoryService.lambdaQuery().in(XqInventory::getItemId, strings).count() > 0) {
			 throw new InterestingBootException("该辅料有相关记录，无法删除");
		 }

		 xqAccessoryInfoService.removeByIds(Arrays.asList(ids.split(",")));
		 return Result.OK("批量删除成功！");
	 }

	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "辅料采购表-通过id查询")
	 @ApiOperation(value="辅料采购表-通过id查询", notes="辅料采购表-通过id查询")
	 @GetMapping(value = "/queryById")
	 public Result<?> queryById(@RequestParam(name="id") String id) {
		 XqAccessoryInfo xqAccessoryInfo = xqAccessoryInfoService.getById(id);
		 if(xqAccessoryInfo==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(xqAccessoryInfo);
	 }

	 /**
	  * 查询辅料分类
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "查询辅料分类")
	 @ApiOperation(value="查询辅料分类", notes="查询辅料分类")
	 @GetMapping(value = "/listAccCategory")
	 public Result<?> listAccCategory() {
		 List<XqAccessoryCategory> list = xqAccessoryCategoryService.list();
		 if(list == null) {
			 return Result.error("未找到对应数据");
		 }
		 List<XqAccessoryCategory> collect1 = list.stream().sorted(Comparator.comparing(XqAccessoryCategory::getCreateTime).reversed())
				 .collect(Collectors.toList());
		 List<JSONObject> collect = collect1.stream().map(i -> {
			 JSONObject jsonObject = new JSONObject();
			 jsonObject.put("id", i.getId());
			 jsonObject.put("name", i.getName());
			 jsonObject.put("createTime", i.getCreateTime());
			 jsonObject.put("createBy", i.getCreateBy());

			 return jsonObject;
		 }).collect(Collectors.toList());
		 return Result.OK(collect);
	 }

	 /**
	  * 编辑辅料分类
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "编辑辅料分类")
	 @ApiOperation(value="编辑辅料分类", notes="编辑辅料分类")
	 @PostMapping(value = "/editAccCategory")
	 public Result<?> editAccCategory(@RequestBody @Valid AddUptAccCategoryDTO dto) {
		 boolean b = xqAccessoryCategoryService.editAccCategory(dto);
		 return b ? Result.OK("编辑成功") : Result.error("编辑失败");
	 }

	 /**
	  * 查询辅料分类下的辅料
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "查询辅料分类下的辅料")
	 @ApiOperation(value="查询辅料分类下的辅料", notes="查询辅料分类下的辅料")
	 @GetMapping(value = "/listAccByCategoryId")
	 public Result<?> listAccByCategoryId(@RequestParam String id) {
		 List<JSONObject> objs = xqAccessoryCategoryService.listAccByCategoryId(id);
		 return Result.OK(objs);
	 }

	 /**
	  * 删除辅料分类
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "删除辅料分类")
	 @ApiOperation(value="删除辅料分类", notes="删除辅料分类")
	 @GetMapping(value = "/deleteBatchAccCategory")
	 public Result<?> deleteBatchAccCategory(@RequestParam String ids) {
		 boolean b = xqAccessoryCategoryService.deleteBatchAccCategory(ids);
		 return b ? Result.OK("删除成功") : Result.error("删除失败");
	 }

	 /**
	  * 新增辅料分类
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "新增辅料分类")
	 @ApiOperation(value="新增辅料分类", notes="新增辅料分类")
	 @PostMapping(value = "/addAccCategory")
	 public Result<?> addAccCategory(@RequestBody @Valid AddUptAccCategoryDTO dto) {

		 boolean b = xqAccessoryCategoryService.addAccCategory(dto);
		 return b ? Result.OK("新增成功") : Result.error("新增失败");
	 }

	 /**
	  * 查询辅料库存分页
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "查询辅料库存分页")
	 @ApiOperation(value="查询辅料库存分页", notes="查询辅料库存分页")
	 @GetMapping(value = "/listAccInventoryPage")
	 public Result<?> listAccInventoryPage(QueryXqAccInventoryDTO dto) {
		 IPage<AccInventoryPageVO> pageList = xqWarehouseService.listAccInventoryPage(dto);
		 return Result.OK(pageList);
	 }



	 /**
	  * 查询辅料盘点分页
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "查询辅料盘点分页")
	 @ApiOperation(value="查询辅料盘点分页", notes="查询辅料盘点分页", response = InventoryCheckPageVO.class)
	 @GetMapping(value = "/pageInventoryCheck")
	 public Result<?> pageInventoryCheck(QueryInventoryCheckPageDTO dto) {
		 IPage<InventoryCheckPageVO> pageList = xqInventoryCheckService.pageInventoryCheck(dto);
		 return Result.OK(pageList);
	 }

	 /**
	  * 查询盘点详情
	  */
	 @AutoLog(value = "查询盘点详情")
	 @ApiOperation(value="查询盘点详情", notes="查询盘点详情", response = InventoryCheckDetailVO.class)
	 @GetMapping(value = "/getInventoryCheckDetById")
	 public Result<?> getInventoryCheckDetById(@RequestParam String id) {
		 InventoryCheckDetailVO vo = xqInventoryCheckService.getInventoryCheckDetById(id);
		 return Result.OK(vo);
	 }

	 /**
	  * 编辑盘点详情  不让使用
	  */

	 /**
	  * 删除盘点
	  */
	 @AutoLog(value = "删除盘点")
	 @ApiOperation(value="删除盘点", notes="删除盘点", response = InventoryCheckDetailVO.class)
	 @GetMapping(value = "/deleteBatchInventoryCheck")
	 public Result<?> deleteBatchInventoryCheck(@RequestParam String ids) {
		 boolean b = xqInventoryCheckService.deleteBatchInventoryCheck(ids);
		 return b ? Result.OK("删除成功") : Result.error("删除失败");
	 }


	 /**
	  * 新增盘点记录
	  */
	 @AutoLog(value = "新增盘点记录")
	 @ApiOperation(value="新增盘点记录", notes="新增盘点记录")
	 @PostMapping(value = "/addInventoryCheck")
	 public Result<?> addInventoryCheck(@RequestBody @Valid AddInventoryCheckDTO dto) {
		 ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		 Validator validator = factory.getValidator();

		 List<AddInventoryCheckDetailRecordDTO> prjAmountList = dto.getPdDetails();
		 for (AddInventoryCheckDetailRecordDTO prjAmount : prjAmountList) {
			 Set<ConstraintViolation<AddInventoryCheckDetailRecordDTO>> violations = validator.validate(prjAmount);
			 if (!violations.isEmpty()) {
				 factory.close();
				 throw new ConstraintViolationException(violations);
			 }

		 }

		 factory.close();
		 boolean r = xqInventoryCheckService.addInventoryCheck(dto);
		 return r ? Result.OK("新增成功") : Result.error("新增失败");
	 }

	 /**
	  * 审核盘点记录
	  */
	 @AutoLog(value = "审核盘点记录")
	 @ApiOperation(value="审核盘点记录", notes="审核盘点记录")
	 @PostMapping(value = "/auditInventoryCheck")
	 public Result<?> auditInventoryCheck(@RequestBody @Valid EditStatusDTO dto) {
		 boolean b = xqInventoryCheckService.auditInventoryCheck(dto);
		 return b ? Result.OK("审核成功") : Result.error("审核失败");
	 }

	 /**
	  * 查询库存明细
	  */
	 @AutoLog(value = "查询库存明细")
	 @ApiOperation(value="查询库存明细", notes="查询库存明细", response = AccInventoryDetails2VO.class)
	 @GetMapping(value = "/listInventoryDetails")
	 public Result<?> listInventoryDetails(@RequestParam String warehouseId,
										   @RequestParam String accessoryId,
										   @RequestParam(required = false) String relativeTimeStart,
										   @RequestParam(required = false) String relativeTimeEnd,
										   @RequestParam(required = false, defaultValue = "1") Integer pageNo,
										   @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
		 Page<AccInventoryDetails2VO> pgae = new Page<>(pageNo, pageSize);
		 IPage<AccInventoryDetails2VO> vo = xqInventoryService.listInventoryDetails(pgae, warehouseId, accessoryId,
				 relativeTimeStart,relativeTimeEnd);
		 return Result.OK(vo);
	 }

	 /**
	  * 查询辅料库存分页（价格、面单号分组）
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "查询辅料库存分页（用料）")
	 @ApiOperation(value="查询辅料库存分页（用料）", notes="查询辅料库存分页（用料）")
	 @GetMapping(value = "/listAccInventoryPage2")
	 public Result<?> listAccInventoryPage2(QueryXqAccInventoryDTO2 dto) {
		 IPage<AccInventoryPageVO2> pageList = xqWarehouseService.listAccInventoryPage2(dto);
		 return Result.OK(pageList);
	 }


//	 @AutoLog(value = "新增盘点记录")
//	 @ApiOperation(value="新增盘点记录", notes="新增盘点记录")
//	 @PostMapping(value = "/addInventoryCheck")
//	 public Result<?> getInventoryCheckDet(QueryInventoryCheckPageDTO dto) {
//		 IPage<InventoryCheckPageVO> pageList = xqInventoryCheckService.pageInventoryCheck(dto);
//		 return Result.OK(pageList);
//	 }


//	/**
//	 * 分页列表查询
//	 *
//	 * @param xqAccessoriesPurchase
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "辅料采购表-分页列表查询")
//	@ApiOperation(value="辅料采购表-分页列表查询", notes="辅料采购表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<XqAccessoriesPurchaseVO>> queryPageList(QueryXqAccessoriesPurchaseDTO dto) {
//        Page<XqAccessoriesPurchaseVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
//		IPage<XqAccessoriesPurchaseVO> pageList = xqAccessoriesPurchaseService.pageList(page, dto);
//		return Result.OK(pageList);
//	}
//
//	/**
//	 * 添加
//	 *
//	 * @param xqAccessoriesPurchase
//	 * @return
//	 */
//	@AutoLog(value = "辅料采购表-添加")
//	@ApiOperation(value="辅料采购表-添加", notes="辅料采购表-添加")
//	@PostMapping(value = "/add")
//	public Result<?> add(@RequestBody AddXqAccessoriesPurchaseDTO dto) {
//        XqAccessoriesPurchase xqAccessoriesPurchase = new XqAccessoriesPurchase();
//        BeanUtils.copyProperties(dto, xqAccessoriesPurchase);
//		boolean b = xqAccessoriesPurchaseService.save(xqAccessoriesPurchase);
//		return b ? Result.OK("添加成功！") : Result.OK("添加失败！");
//	}
//
//	/**
//	 * 编辑
//	 *
//	 * @param xqAccessoriesPurchase
//	 * @return
//	 */
//	@AutoLog(value = "辅料采购表-编辑")
//	@ApiOperation(value="辅料采购表-编辑", notes="辅料采购表-编辑")
//	@PostMapping(value = "/edit")
//	public Result<?> edit(@RequestBody UpdateXqAccessoriesPurchaseDTO dto) {
//        String id = dto.getId();
//        XqAccessoriesPurchase byId = xqAccessoriesPurchaseService.getById(id);
//        if (byId == null) {
//            throw new InterestingBootException("该记录不存在");
//        }
//        BeanUtils.copyProperties(dto, byId);
//        boolean b = xqAccessoriesPurchaseService.updateById(byId);
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
//	@AutoLog(value = "辅料采购表-通过id删除")
//	@ApiOperation(value="辅料采购表-通过id删除", notes="辅料采购表-通过id删除")
//	@GetMapping(value = "/delete")
//	public Result<?> delete(@RequestParam(name="id") String id) {
//		xqAccessoriesPurchaseService.removeById(id);
//		return Result.OK("删除成功!");
//	}
//
//	/**
//	 * 批量删除
//	 *
//	 * @param ids
//	 * @return
//	 */
//	@AutoLog(value = "辅料采购表-批量删除")
//	@ApiOperation(value="辅料采购表-批量删除", notes="辅料采购表-批量删除")
//	@GetMapping(value = "/deleteBatch")
//	public Result<?> deleteBatch(@RequestParam(name="ids") String ids) {
//		this.xqAccessoriesPurchaseService.removeByIds(Arrays.asList(ids.split(",")));
//		return Result.OK("批量删除成功！");
//	}
//
//	/**
//	 * 通过id查询
//	 *
//	 * @param id
//	 * @return
//	 */
//	@AutoLog(value = "辅料采购表-通过id查询")
//	@ApiOperation(value="辅料采购表-通过id查询", notes="辅料采购表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<?> queryById(@RequestParam(name="id") String id) {
//		XqAccessoriesPurchase xqAccessoriesPurchase = xqAccessoriesPurchaseService.getById(id);
//		return Result.OK(xqAccessoriesPurchase);
//	}

     /**
      * 导入辅料信息
      *
      * @return
      */
     @AutoLog(value = "辅料信息导入")
     @ApiOperation(value = "辅料信息导入", notes = "辅料信息导入")
     @PostMapping(value = "/orderAcsrImportByOrderNum")
     public Result<?> orderAcsrImportByOrderNum(@RequestParam("orderNum") String orderNum, @RequestPart MultipartFile file) throws Exception {
         return xqReportService.orderAcsrImportByOrderNum(orderNum, file);
     }

	 /**
	  * 导入辅料信息
	  *
	  * @return
	  */
	 @AutoLog(value = "通过orderNum辅料信息导出")
	 @ApiOperation(value = "通过orderNum辅料信息导出", notes = "通过orderNum辅料信息导出")
	 @GetMapping(value = "/orderAcsrExportByOrderNum")
	 public void orderAcsrExportByOrderNum(@RequestParam("orderNum") String orderNum, HttpServletResponse response) throws Exception {
		 xqReportService.orderAcsrExportByOrderNum(orderNum, response);
	 }


 }
