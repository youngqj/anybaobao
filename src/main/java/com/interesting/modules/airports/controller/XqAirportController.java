package com.interesting.modules.airports.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.airports.dto.AddXqAirportDTO;
import com.interesting.modules.airports.dto.EditXqAirportDTO;
import com.interesting.modules.airports.dto.QueryAirportsDTO;
import com.interesting.modules.airports.entity.XqAirport;
import com.interesting.modules.airports.service.XqAirportService;
import com.interesting.modules.airports.vo.SumAirPortVO;
import com.interesting.modules.airports.vo.XqAirportListVO;
import com.interesting.modules.reportform.vo.SumOrderAscrFinanceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Api(tags = "空运管理")
@RestController
@RequestMapping("/airport")
public class XqAirportController {

    @Autowired
    private XqAirportService xqAirportService;

    /**
     * 查询空运列表页面(分页列表)
     */
    @AutoLog(value = "查询空运列表页面")
    @ApiOperation(value = "查询空运列表页面", notes = "查询空运列表页面", response = XqAirportListVO.class)
    @GetMapping(value = "/page")
    public Result<?> page(QueryAirportsDTO dto) {
        if (StringUtils.isNotBlank(dto.getFactoryName())) {
            dto.setFactoryIdList(Arrays.asList(dto.getFactoryName().split(",")));
        }
        if (StringUtils.isNotBlank(dto.getCustomer())) {
            dto.setCustomerIdList(Arrays.asList(dto.getCustomer().split(",")));
        }
        IPage<XqAirportListVO> vo = xqAirportService.page(dto);
        List<SumAirPortVO> vo1 = xqAirportService.sumAirPort(dto);
        return Result.OK2(vo, vo1);
    }

    /**
     * 查询辅料采购页面详情
     */
    @AutoLog(value = "查询空运详情")
    @ApiOperation(value = "查询空运详情", notes = "查询空运详情", response = XqAirport.class)
    @GetMapping(value = "/getById")
    public Result<?> getById(@RequestParam("id") String id) {
        return Result.OK(xqAirportService.getById(id));
    }


    /**
     * 新增空运
     */
    @AutoLog(value = "新增空运")
    @ApiOperation(value = "新增空运", notes = "新增空运")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody @Valid AddXqAirportDTO dto) {
        boolean b = xqAirportService.add(dto);
        return b ? Result.OK("新增成功") : Result.error("新增失败");
    }


    /**
     * 删除辅料采购页面
     */
    @AutoLog(value = "删除空运")
    @ApiOperation(value = "删除空运", notes = "删除空运")
    @GetMapping(value = "/deleteByIds")
    public Result<?> deleteByIds(@RequestParam String ids) {
        boolean b = xqAirportService.deleteByIds(ids);
        return b == true ? Result.OK("删除成功") : Result.error("删除失败");
    }


    /**
     * 编辑空运
     */
    @AutoLog(value = "编辑空运")
    @ApiOperation(value = "编辑空运", notes = "编辑空运")
    @PostMapping(value = "/edit")
    public Result<?> edit(@RequestBody @Valid EditXqAirportDTO dto) {
        boolean b = xqAirportService.edit(dto);
        return b ? Result.OK("编辑成功") : Result.error("编辑失败");
    }

}
