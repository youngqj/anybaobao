package com.interesting.modules.overseas.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.modules.overseas.dto.*;
import com.interesting.modules.overseas.service.XqOverseasWarehouseFeeService;
import com.interesting.modules.overseas.vo.ListWarehouseFeeVO;
import com.interesting.modules.overseas.vo.OverSeasInventoryCheckPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
@Api(tags="海外仓储费用管理")
@RestController
@RequestMapping("/warehouse/fee")
public class OverSeasWarehouseFeeController {

    @Autowired
    private XqOverseasWarehouseFeeService xqOverseasWarehouseFeeService;
    /**
     * 新增仓储费记录
     */
    @AutoLog(value = "新增仓储费记录")
    @ApiOperation(value="新增仓储费记录", notes="新增仓储费记录")
    @PostMapping(value = "/addWarehouseFee")
    public Result<?> addWarehouseFee(@RequestBody @Valid AddOverseasWarehouseFeeDTO dto) {
        return xqOverseasWarehouseFeeService.addOverseasWarehouseFee(dto) ? Result.OK("新增成功") : Result.error("新增失败");
    }


    /**
     * 编辑仓储费记录
     */
    @AutoLog(value = "编辑仓储费记录")
    @ApiOperation(value="编辑仓储费记录", notes="编辑仓储费记录")
    @PostMapping(value = "/editWarehouseFee")
    public Result<?> editWarehouseFee(@RequestBody @Valid EditOverseasWarehouseFeeDTO dto) {
        return xqOverseasWarehouseFeeService.editOverseasWarehouseFee(dto) ? Result.OK("新增成功") : Result.error("新增失败");
    }

    @AutoLog(value = "查询海外仓储费分页")
    @ApiOperation(value="查询海外仓储费分页", notes="查询海外仓储费分页", response = ListWarehouseFeeVO.class)
    @GetMapping(value = "/queryByPage")
    public Result<?> queryByPage(QueryWarehouseFeeDTO dto) {
        Page<ListWarehouseFeeVO> page= new Page<>(dto.getPageNo(), dto.getPageSize());
        IPage<ListWarehouseFeeVO> pageList = xqOverseasWarehouseFeeService
                .queryByPage(dto,page);
        return Result.OK(pageList);
    }

    /**
     * 查询仓储费详情
     */
    @AutoLog(value = "查询仓储费详情")
    @ApiOperation(value="查询仓储费详情", notes="查询仓储费详情")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam("id") String id) {
        return Result.OK(xqOverseasWarehouseFeeService.queryById(id));
    }

    /**
     * 删除盘点
     */
    @AutoLog(value = "删除仓储费")
    @ApiOperation(value = "删除仓储费", notes = "删除仓储费")
    @GetMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam String ids) {
        List<String> strings = Arrays.asList(ids.split(","));
        boolean b = xqOverseasWarehouseFeeService.removeByIds(strings);
        return b ? Result.OK("删除成功") : Result.error("删除失败");
    }

}
