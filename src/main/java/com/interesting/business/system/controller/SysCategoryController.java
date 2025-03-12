package com.interesting.business.system.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.business.system.entity.SysCategory;
import com.interesting.business.system.model.TreeSelectModel;
import com.interesting.business.system.service.ISysCategoryService;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.system.query.QueryGenerator;
import com.interesting.common.system.vo.DictModel;
import com.interesting.common.util.StringUtils;
import com.interesting.common.util.oConvertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

 /**
 * @Description: 分类字典
 * @Author: interesting-boot
 * @Date:   2019-05-29
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sys/category")
@Slf4j
@Api(tags = "分类字典")
public class SysCategoryController {
	@Autowired
	private ISysCategoryService sysCategoryService;

	/**
	  * 分页列表查询
	 * @param sysCategory
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="分类字典-分页列表查询", notes="分类字典-分页列表查询")
	@AutoLog(value = "分类字典-分页列表查询")
	@GetMapping(value = "/rootList")
	public Result<IPage<SysCategory>> queryPageList(SysCategory sysCategory,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		if(oConvertUtils.isEmpty(sysCategory.getPid())){
			sysCategory.setPid("0");
		}
		Result<IPage<SysCategory>> result = new Result<IPage<SysCategory>>();

		//--author:os_chengtgen---date:20190804 -----for: 分类字典页面显示错误,issues:377--------start
		//QueryWrapper<SysCategory> queryWrapper = QueryGenerator.initQueryWrapper(sysCategory, req.getParameterMap());
		QueryWrapper<SysCategory> queryWrapper = new QueryWrapper<SysCategory>();
		queryWrapper.eq("pid", sysCategory.getPid());
		//--author:os_chengtgen---date:20190804 -----for: 分类字典页面显示错误,issues:377--------end

		Page<SysCategory> page = new Page<SysCategory>(pageNo, pageSize);
		IPage<SysCategory> pageList = sysCategoryService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	@GetMapping(value = "/childList")
	@ApiOperation(value="获取所有数据", notes="获取所有数据")
	@AutoLog(value = "获取所有数据")
	public Result<List<SysCategory>> queryPageList(SysCategory sysCategory,HttpServletRequest req) {
		Result<List<SysCategory>> result = new Result<List<SysCategory>>();
		QueryWrapper<SysCategory> queryWrapper = QueryGenerator.initQueryWrapper(sysCategory, req.getParameterMap());
		List<SysCategory> list = sysCategoryService.list(queryWrapper);
		result.setSuccess(true);
		result.setResult(list);
		return result;
	}


	/**
	  *   添加
	 * @param sysCategory
	 * @return
	 */
	@PostMapping(value = "/add")
	@ApiOperation(value="分类字典-添加", notes="分类字典-添加")
	@AutoLog(value = "分类字典-添加")
	public Result<SysCategory> add(@RequestBody SysCategory sysCategory) {
		Result<SysCategory> result = new Result<SysCategory>();
		try {
			sysCategoryService.addSysCategory(sysCategory);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}

	/**
	  *  编辑
	 * @param sysCategory
	 * @return
	 */
	@PutMapping(value = "/edit")
	@ApiOperation(value = "分类字典-编辑", notes = "分类字典-编辑")
	@AutoLog(value = "分类字典-编辑")
	public Result<SysCategory> edit(@RequestBody SysCategory sysCategory) {
		Result<SysCategory> result = new Result<SysCategory>();
		SysCategory sysCategoryEntity = sysCategoryService.getById(sysCategory.getId());
		if(sysCategoryEntity==null) {
			result.error500("未找到对应实体");
		}else {
			sysCategoryService.updateSysCategory(sysCategory);
			result.success("修改成功!");
		}
		return result;
	}

	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/delete")
	@ApiOperation(value = "分类字典-通过id删除", notes = "分类字典-通过id删除")
	@AutoLog(value = "分类字典-通过id删除")
	public Result<SysCategory> delete(@RequestParam(name="id",required=true) String id) {
		Result<SysCategory> result = new Result<SysCategory>();
		SysCategory sysCategory = sysCategoryService.getById(id);
		if(sysCategory==null) {
			result.error500("未找到对应实体");
		}else {
			this.sysCategoryService.deleteSysCategory(id);
			result.success("删除成功!");
		}

		return result;
	}

	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping(value = "/deleteBatch")
	@ApiOperation(value = "分类字典-批量删除", notes = "分类字典-批量删除")
	@AutoLog(value = "分类字典-批量删除")
	public Result<SysCategory> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<SysCategory> result = new Result<SysCategory>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.sysCategoryService.deleteSysCategory(ids);
			result.success("删除成功!");
		}
		return result;
	}

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	@ApiOperation(value="分类字典-通过id查询", notes="分类字典-通过id查询")
	@AutoLog(value = "分类字典-通过id查询")
	public Result<SysCategory> queryById(@RequestParam(name="id",required=true) String id) {
		Result<SysCategory> result = new Result<SysCategory>();
		SysCategory sysCategory = sysCategoryService.getById(id);
		if(sysCategory==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysCategory);
			result.setSuccess(true);
		}
		return result;
	}


  /**
     * 加载单个数据 用于回显
   */
    @RequestMapping(value = "/loadOne", method = RequestMethod.GET)
	@ApiOperation(value="根据field与value查询单个值", notes="根据field与value查询单个值")
	@AutoLog(value = "根据field与value查询单个值")
 	public Result<SysCategory> loadOne(@RequestParam(name="field") String field,@RequestParam(name="val") String val) {
 		Result<SysCategory> result = new Result<SysCategory>();
 		try {

 			QueryWrapper<SysCategory> query = new QueryWrapper<SysCategory>();
 			query.eq(field, val);
 			List<SysCategory> ls = this.sysCategoryService.list(query);
 			if(ls==null || ls.size()==0) {
 				result.setMessage("查询无果");
 	 			result.setSuccess(false);
 			}else if(ls.size()>1) {
 				result.setMessage("查询数据异常,["+field+"]存在多个值:"+val);
 	 			result.setSuccess(false);
 			}else {
 				result.setSuccess(true);
 				result.setResult(ls.get(0));
 			}
 		} catch (Exception e) {
 			e.printStackTrace();
 			result.setMessage(e.getMessage());
 			result.setSuccess(false);
 		}
 		return result;
 	}

    /**
          * 加载节点的子数据
     */
    @RequestMapping(value = "/loadTreeChildren", method = RequestMethod.GET)
	@ApiOperation(value="根据pid查询一级子数据", notes="根据pid查询一级子数据")
	@AutoLog(value = "根据pid查询一级子数据")
	public Result<List<TreeSelectModel>> loadTreeChildren(@RequestParam(name="pid") String pid) {
		Result<List<TreeSelectModel>> result = new Result<List<TreeSelectModel>>();
		try {
			List<TreeSelectModel> ls = this.sysCategoryService.queryListByPid(pid);
			result.setResult(ls);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

    /**
         * 加载一级节点/如果是同步 则所有数据
     */
    @RequestMapping(value = "/loadTreeRoot", method = RequestMethod.GET)
	@ApiOperation(value="分类字典-加载一级节点/async=false 则所有数据", notes="分类字典-加载一级节点/async=false 则所有数据")
	@AutoLog(value = "分类字典-加载一级节点/async=false 则所有数据")
   	public Result<List<TreeSelectModel>> loadTreeRoot(@RequestParam(name="async") Boolean async,
														 @RequestParam(name="pcode") String pcode) {
   		Result<List<TreeSelectModel>> result = new Result<List<TreeSelectModel>>();
   		try {
   			List<TreeSelectModel> ls = this.sysCategoryService.queryListByCode(pcode);
   			if(!async) {
   				loadAllCategoryChildren(ls);
   			}
   			result.setResult(ls);
   			result.setSuccess(true);
   		} catch (Exception e) {
   			e.printStackTrace();
   			result.setMessage(e.getMessage());
   			result.setSuccess(false);
   		}
   		return result;
   	}

    /**
         * 递归求子节点 同步加载用到
     */
  	private void loadAllCategoryChildren(List<TreeSelectModel> ls) {
  		for (TreeSelectModel tsm : ls) {
			List<TreeSelectModel> temp = this.sysCategoryService.queryListByPid(tsm.getKey());
			if(temp!=null && temp.size()>0) {
				tsm.setChildren(temp);
				loadAllCategoryChildren(temp);
			}
		}
  	}

	 /**
	  * 校验编码
	  * @param pid
	  * @param code
	  * @return
	  */
	 @GetMapping(value = "/checkCode")
	 @ApiOperation(value="分类字典-校验编码", notes="分类字典-校验编码")
	 @AutoLog(value = "分类字典-校验编码")
	 public Result<?> checkCode(@RequestParam(name="pid",required = false) String pid,@RequestParam(name="code",required = false) String code) {
		if(oConvertUtils.isEmpty(code)){
			return Result.error("错误,类型编码为空!");
		}
		if(oConvertUtils.isEmpty(pid)){
			return Result.ok();
		}
		SysCategory parent = this.sysCategoryService.getById(pid);
		if(code.startsWith(parent.getCode())){
			return Result.ok();
		}else{
			return Result.error("编码不符合规范,须以\""+parent.getCode()+"\"开头!");
		}

	 }


	 /**
	  * 分类字典树控件 加载节点
	  * @param pid
	  * @param pcode
	  * @param condition
	  * @return
	  */
	 @RequestMapping(value = "/loadTreeData", method = RequestMethod.GET)
	 @ApiOperation(value="根据pid或pcode查询一级子节点", notes="根据pid或pcode查询一级子节点")
	 @AutoLog(value = "根据pid或pcode查询一级子节点")
	 public Result<List<TreeSelectModel>> loadDict(@RequestParam(name="pid",required = false) String pid,@RequestParam(name="pcode",required = false) String pcode, @RequestParam(name="condition",required = false) String condition) {
		 Result<List<TreeSelectModel>> result = new Result<List<TreeSelectModel>>();
		 //pid如果传值了 就忽略pcode的作用
		 if(oConvertUtils.isEmpty(pid)){
		 	if(oConvertUtils.isEmpty(pcode)){
				result.setSuccess(false);
				result.setMessage("加载分类字典树参数有误.[null]!");
				return result;
			}else{
		 		if(ISysCategoryService.ROOT_PID_VALUE.equals(pcode)){
					pid = ISysCategoryService.ROOT_PID_VALUE;
				}else{
					pid = this.sysCategoryService.queryIdByCode(pcode);
				}
				if(oConvertUtils.isEmpty(pid)){
					result.setSuccess(false);
					result.setMessage("加载分类字典树参数有误.[code]!");
					return result;
				}
			}
		 }
		 Map<String, String> query = null;
		 if(oConvertUtils.isNotEmpty(condition)) {
			 query = JSON.parseObject(condition, Map.class);
		 }
		 List<TreeSelectModel> ls = sysCategoryService.queryListByPid(pid,query);
		 result.setSuccess(true);
		 result.setResult(ls);
		 return result;
	 }

	 /**
	  * 分类字典控件数据回显[表单页面]
	  *
	  * @param ids
	  * @return
	  */
	 @RequestMapping(value = "/loadDictItem", method = RequestMethod.GET)
	 @ApiOperation(value="多个id翻译多个文本", notes="多个id翻译多个文本")
	 @AutoLog(value = "多个id翻译多个文本")
	 public Result<List<String>> loadDictItem(@RequestParam(name = "ids") String ids) {
		 Result<List<String>> result = new Result<>();
		 // 非空判断
		 if (StringUtils.isBlank(ids)) {
			 result.setSuccess(false);
			 result.setMessage("ids 不能为空");
			 return result;
		 }
		 String[] idArray = ids.split(",");
		 LambdaQueryWrapper<SysCategory> query = new LambdaQueryWrapper<>();
		 query.in(SysCategory::getId, Arrays.asList(idArray));
		 // 查询数据
		 List<SysCategory> list = this.sysCategoryService.list(query);
		 // 取出name并返回
		 List<String> textList = list.stream().map(SysCategory::getName).collect(Collectors.toList());
		 result.setSuccess(true);
		 result.setResult(textList);
		 return result;
	 }

	 /**
	  * [列表页面]加载分类字典数据 用于值的替换
	  * @param code
	  * @return
	  */
	 @RequestMapping(value = "/loadAllData", method = RequestMethod.GET)
	 @ApiOperation(value="分类字典-根据code加载单一节点", notes="分类字典-根据code加载单一节点")
	 @AutoLog(value = "分类字典-根据code加载单一节点")
	 public Result<List<DictModel>> loadAllData(@RequestParam(name="code",required = true) String code) {
		 Result<List<DictModel>> result = new Result<List<DictModel>>();
		 LambdaQueryWrapper<SysCategory> query = new LambdaQueryWrapper<SysCategory>();
		 if(oConvertUtils.isNotEmpty(code) && !"0".equals(code)){
			 query.likeRight(SysCategory::getCode,code);
		 }
		 List<SysCategory> list = this.sysCategoryService.list(query);
		 if(list==null || list.size()==0) {
			 result.setMessage("无数据,参数有误.[code]");
			 result.setSuccess(false);
			 return result;
		 }
		 List<DictModel> rdList = new ArrayList<DictModel>();
		 for (SysCategory c : list) {
			 rdList.add(new DictModel(c.getId(),c.getName()));
		 }
		 result.setSuccess(true);
		 result.setResult(rdList);
		 return result;
	 }

	 /**
	  * 根据父级id批量查询子节点
	  * @param parentIds
	  * @return
	  */
	 @GetMapping("/getChildListBatch")
	 @ApiOperation(value="根据父级id查询一级子数据", notes="根据父级id查询一级子数据")
	 @AutoLog(value = "根据父级id查询一级子数据")
	 public Result getChildListBatch(@RequestParam("parentIds") String parentIds) {
		 try {
			 QueryWrapper<SysCategory> queryWrapper = new QueryWrapper<>();
			 List<String> parentIdList = Arrays.asList(parentIds.split(","));
			 queryWrapper.in("pid", parentIdList);
			 List<SysCategory> list = sysCategoryService.list(queryWrapper);
			 IPage<SysCategory> pageList = new Page<>(1, 10, list.size());
			 pageList.setRecords(list);
			 return Result.OK(pageList);
		 } catch (Exception e) {
			 log.error(e.getMessage(), e);
			 return Result.error("批量查询子节点失败：" + e.getMessage());
		 }
	 }


}
