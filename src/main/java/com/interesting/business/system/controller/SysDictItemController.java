package com.interesting.business.system.controller;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.interesting.business.system.entity.SysDict;
import com.interesting.common.constant.CacheConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.interesting.common.api.vo.Result;
import com.interesting.common.system.query.QueryGenerator;
import com.interesting.common.util.oConvertUtils;
import com.interesting.business.system.entity.SysDictItem;
import com.interesting.business.system.service.ISysDictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @Author Gcc
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/sys/dictItem")
@Slf4j
@Api(tags = "字典配置")
public class SysDictItemController {

    @Autowired
    private ISysDictItemService sysDictItemService;

    /**
     * @param sysDictItem
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     * @功能：查询字典数据
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "查询字典数据page", notes = "查询字典数据page")
    public Result<IPage<SysDictItem>> queryPageList(SysDictItem sysDictItem, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<IPage<SysDictItem>> result = new Result<IPage<SysDictItem>>();
        QueryWrapper<SysDictItem> queryWrapper = QueryGenerator.initQueryWrapper(sysDictItem, req.getParameterMap());
        queryWrapper.orderByAsc("sort_order");
        Page<SysDictItem> page = new Page<SysDictItem>(pageNo, pageSize);
        IPage<SysDictItem> pageList = sysDictItemService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    @RequestMapping(value = "/getDictByCode", method = RequestMethod.GET)
    @ApiOperation(value = "通过字典code查询字典值", notes = "通过字典code查询字典值")
    public Result<List<SysDictItem>> getDictByCode(
            @ApiParam(name = "code", value = "定义的code") @RequestParam(name = "code", defaultValue = "1") String code) {
        List<SysDictItem> cc = sysDictItemService.selectItemsByMainCode(code);
        return Result.OK(cc);
    }

    /**
     * @return
     * @功能：新增
     */
    //@RequiresRoles({"admin"})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @CacheEvict(value = CacheConstant.SYS_DICT_CACHE, allEntries = true)
    @ApiOperation(value = "新增字典", notes = "新增字典")
    public Result<SysDictItem> add(@RequestBody SysDictItem sysDictItem) {
        Result<SysDictItem> result = new Result<SysDictItem>();
        try {
            sysDictItem.setCreateTime(new Date());
            sysDictItemService.save(sysDictItem);
            result.success("保存成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * @param sysDictItem
     * @return
     * @功能：编辑
     */
    //@RequiresRoles({"admin"})
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    @CacheEvict(value = CacheConstant.SYS_DICT_CACHE, allEntries = true)
    @ApiOperation(value = "编辑字典", notes = "编辑字典")
    public Result<SysDictItem> edit(@RequestBody SysDictItem sysDictItem) {
        Result<SysDictItem> result = new Result<SysDictItem>();
        SysDictItem sysdict = sysDictItemService.getById(sysDictItem.getId());
        if (sysdict == null) {
            result.error500("未找到对应实体");
        } else {
            sysDictItem.setUpdateTime(new Date());
            boolean ok = sysDictItemService.updateById(sysDictItem);
            //TODO 返回false说明什么？
            if (ok) {
                result.success("编辑成功!");
            }
        }
        return result;
    }

    /**
     * @param id
     * @return
     * @功能：删除字典数据
     */
    //@RequiresRoles({"admin"})
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @CacheEvict(value = CacheConstant.SYS_DICT_CACHE, allEntries = true)
    @ApiOperation(value = "删除字典", notes = "删除字典")
    public Result<SysDictItem> delete(@RequestParam(name = "id", required = true) String id) {
        Result<SysDictItem> result = new Result<SysDictItem>();
        SysDictItem joinSystem = sysDictItemService.getById(id);
        if (joinSystem == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = sysDictItemService.removeById(id);
            if (ok) {
                result.success("删除成功!");
            }
        }
        return result;
    }

    /**
     * @param ids
     * @return
     * @功能：批量删除字典数据
     */
    //@RequiresRoles({"admin"})
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.GET)
    @CacheEvict(value = CacheConstant.SYS_DICT_CACHE, allEntries = true)
    @ApiOperation(value = "批量删除字典", notes = "批量删除字典")
    public Result<SysDictItem> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<SysDictItem> result = new Result<SysDictItem>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.sysDictItemService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 字典值重复校验
     *
     * @param sysDictItem
     * @param request
     * @return
     */
    @RequestMapping(value = "/dictItemCheck", method = RequestMethod.GET)
    @ApiOperation("字典重复校验接口")
    public Result<Object> doDictItemCheck(SysDictItem sysDictItem, HttpServletRequest request) {
        int num = 0;
        LambdaQueryWrapper<SysDictItem> queryWrapper = new LambdaQueryWrapper<SysDictItem>();
        queryWrapper.eq(SysDictItem::getItemValue, sysDictItem.getItemValue());
        queryWrapper.eq(SysDictItem::getDictId, sysDictItem.getDictId());
        if (StringUtils.isNotBlank(sysDictItem.getId())) {
            // 编辑页面校验
            queryWrapper.ne(SysDictItem::getId, sysDictItem.getId());
        }
        num = sysDictItemService.count(queryWrapper);
        if (num == 0) {
            // 该值可用
            return Result.ok("该值可用！");
        } else {
            // 该值不可用
            log.info("该值不可用，系统中已存在！");
            return Result.error("该值不可用，系统中已存在！");
        }
    }

}
