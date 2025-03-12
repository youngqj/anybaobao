package com.interesting.modules.unit.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.BeanUtils;
import com.interesting.modules.customer.entity.XqCustomer;
import com.interesting.modules.unit.dto.XqUnitDTO;
import com.interesting.modules.unit.entity.XqUnit;
import com.interesting.modules.unit.service.XqUnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Api(tags="计量单位管理")
@RestController
@RequestMapping("/unit/xqUnit")
public class XqUnitController {
    @Autowired
    private XqUnitService xqUnitService;

    /**
     * 查询单位名称列表
     *
     * @param id
     * @return
     */
    @AutoLog(value = "查询单位名称列表")
    @ApiOperation(value="查询单位名称列表", notes="查询单位名称列表")
    @GetMapping(value = "/queryUnitList")
    public Result<?> queryUnitList() {
        List<XqUnit> list = xqUnitService.list();
        List<JSONObject> collect = list.stream().map(i -> {
            JSONObject js = new JSONObject();
            js.put("id", i.getId());
            js.put("name", i.getName());
            return js;
        }).collect(Collectors.toList());

        return Result.OK(collect);
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
    @ApiOperation(value = "分页列表查询", notes = "分页列表查询")
    @GetMapping(value = "/unitPage")
    public Result<IPage<XqUnit>> queryPageList(XqUnitDTO dto) {
        IPage<XqUnit> pageList = xqUnitService.pageList(dto);
        return Result.OK(pageList);
    }


    /**
     * 添加
     *
     * @param unit
     * @return
     */
    @AutoLog(value = "单位表-添加")
    @ApiOperation(value = "单位表-添加", notes = "单位表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody @Valid XqUnit unit) {
        XqUnit unit1 = xqUnitService.getOne(new LambdaQueryWrapper<XqUnit>().eq(XqUnit::getName, unit.getName()).last("limit 1"));
        if (unit1 == null) {
            unit1 = new XqUnit();
        } else {
            throw new InterestingBootException("单位已经存在！");
        }
        BeanUtils.copyProperties(unit, unit1);
        boolean b = xqUnitService.save(unit1);
        return b ? Result.OK("添加成功！") : Result.OK("添加失败！");
    }

    /**
     * 编辑
     *
     * @param unit
     * @return
     */
    @AutoLog(value = "单位表-编辑")
    @ApiOperation(value = "单位表-编辑", notes = "单位表-编辑")
    @PostMapping(value = "/edit")
    public Result<?> edit(@RequestBody @Valid XqUnit unit) {
        XqUnit unit1 = xqUnitService.getById(unit.getId());
        if (unit1 == null) {
            throw new InterestingBootException("单位已经不存在！");
        }
        BeanUtils.copyProperties(unit, unit1);
        boolean b = xqUnitService.updateById(unit1);
        return b ? Result.OK("编辑成功！") : Result.OK("编辑成功！");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "单位表-通过id删除")
    @ApiOperation(value = "单位表-通过id删除", notes = "单位表-通过id删除")
    @GetMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id") String id) {
        xqUnitService.removeById(id);
        return Result.OK("删除成功!");
    }
}
