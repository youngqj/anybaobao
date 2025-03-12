package com.interesting.modules.truck.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.modules.truck.dto.AddTruckDTO;
import com.interesting.modules.truck.dto.EditTruckDTO;
import com.interesting.modules.truck.dto.QueryTruckDTO;
import com.interesting.modules.truck.entity.XqTruck;
import com.interesting.modules.truck.service.XqTruckService;
import com.interesting.modules.truck.vo.XqTruckListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Api(tags = "特殊-国内外卡车费用")
@RestController
@RequestMapping("/truck")
public class XqTruckFeeController {


    @Autowired
    private XqTruckService xqTruckService;

    /**
     * 查询空运列表页面(分页列表)
     */
    @AutoLog(value = "查询卡车列表页面")
    @ApiOperation(value = "查询卡车列表页面", notes = "查询卡车列表页面", response = XqTruckListVO.class)
    @GetMapping(value = "/page")
    public Result<?> page(QueryTruckDTO dto) {
        IPage<XqTruckListVO> vo = xqTruckService.page(dto);
        return Result.OK(vo);
    }

    /**
     * 查询辅料采购页面详情
     */
    @AutoLog(value = "查询卡车详情")
    @ApiOperation(value = "查询卡车详情", notes = "查询卡车详情", response = XqTruck.class)
    @GetMapping(value = "/getById")
    public Result<?> getById(@RequestParam("id") String id) {
        return Result.OK(xqTruckService.getById(id));
    }


    /**
     * 新增卡车
     */
    @AutoLog(value = "新增卡车")
    @ApiOperation(value = "新增卡车", notes = "新增卡车")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody @Valid AddTruckDTO dto) {
        boolean b = xqTruckService.add(dto);
        return b ? Result.OK("新增成功") : Result.error("新增失败");
    }

    /**
     * 删除辅料采购页面
     */
    @AutoLog(value = "删除卡车")
    @ApiOperation(value = "删除卡车", notes = "删除卡车")
    @GetMapping(value = "/deleteByIds")
    public Result<?> deleteByIds(@RequestParam("ids") String ids) {
        boolean b = xqTruckService.deleteByIds(ids);
        return b == true ? Result.OK("删除成功") : Result.error("删除失败");
    }


    /**
     * 编辑空运
     */
    @AutoLog(value = "编辑卡车")
    @ApiOperation(value = "编辑卡车", notes = "编辑卡车")
    @PostMapping(value = "/edit")
    public Result<?> edit(@RequestBody @Valid EditTruckDTO dto) {
        boolean b = xqTruckService.edit(dto);
        return b ? Result.OK("编辑成功") : Result.error("编辑失败");
    }


}
