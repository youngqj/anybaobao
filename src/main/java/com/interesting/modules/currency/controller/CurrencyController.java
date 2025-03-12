package com.interesting.modules.currency.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.business.system.entity.SysDictItem;
import com.interesting.business.system.service.ISysDictItemService;
import com.interesting.common.api.vo.Result;
import com.interesting.common.constant.CacheConstant;
import com.interesting.common.system.query.QueryGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("currency")
@Slf4j
@Api(tags = "币种")
public class CurrencyController {
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
        sysDictItem.setDictId("1668528025509593089");
        QueryWrapper<SysDictItem> queryWrapper = QueryGenerator.initQueryWrapper(sysDictItem, req.getParameterMap());
        queryWrapper.orderByAsc("sort_order");
        Page<SysDictItem> page = new Page<SysDictItem>(pageNo, pageSize);
        IPage<SysDictItem> pageList = sysDictItemService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * @return
     * @功能：新增
     */
    //@RequiresRoles({"admin"})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增字典", notes = "新增字典")
    public Result<SysDictItem> add(@RequestBody SysDictItem sysDictItem) {
        Result<SysDictItem> result = new Result<SysDictItem>();
        try {
            SysDictItem izExis = sysDictItemService.getOne(new LambdaQueryWrapper<SysDictItem>().eq(SysDictItem::getItemValue, sysDictItem.getItemValue()).eq(SysDictItem::getStatus, 1).last("limit 1"));
            if (izExis != null) {
                return result.error500("操作失败,该币种已存在");
            }
            sysDictItem.setDictId("1668528025509593089");
            sysDictItem.setCreateTime(new Date());
            sysDictItem.setStatus(1);
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
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
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
}
