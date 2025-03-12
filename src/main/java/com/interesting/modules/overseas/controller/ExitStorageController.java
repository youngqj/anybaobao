package com.interesting.modules.overseas.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.modules.overseas.dto.*;
import com.interesting.modules.overseas.service.XqOverseasExitHeadService;
import com.interesting.modules.overseas.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Api(tags="海外仓出库管理")
@RestController
@RequestMapping("/exit/storage")
public class ExitStorageController {
    @Autowired
    private XqOverseasExitHeadService xqOverseasExitHeadService;

    /**
     * 查询出库单(分页列表)
     *
     */
    @AutoLog(value = "查询出库单(分页列表)")
    @ApiOperation(value="查询出库单(分页列表)", notes="查询出库单(分页列表)", response = ExitStoragePageVO.class)
    @GetMapping(value = "/pageExitStorage")
    public Result<?> pageExitStorage(QueryPageExitStorageDTO dto) {
        IPage<ExitStoragePageVO> vo = xqOverseasExitHeadService.pageExitStorage(dto);
        return Result.OK(vo);
    }

    /**
     * 查询出库单页面详情
     *
     */
    @AutoLog(value = "查询出库单页面详情")
    @ApiOperation(value="查询出库单页面详情", notes="查询出库单页面详情", response = ExitStorageDetailVO.class)
    @GetMapping(value = "/getExitStorage")
    public Result<?> getExitStorage(@RequestParam String id) {
        ExitStorageDetailVO vo = xqOverseasExitHeadService.getExitStorage(id);
        return Result.OK(vo);
    }


    /**
     * 新增出库单
     */
    @AutoLog(value = "新增出库单")
    @ApiOperation(value="新增出库单", notes="新增出库单")
    @PostMapping(value = "/addExitStorage")
    public Result<?> addExitStorage(@RequestBody @Valid InstUptExitStorageDTO dto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        if (CollectionUtil.isNotEmpty(dto.getDetails())) {
            for (InstUptExitStorageDetailItemDTO detail : dto.getDetails()) {
                Set<ConstraintViolation<InstUptExitStorageDetailItemDTO>> violations = validator.validate(detail);
                if (violations.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (ConstraintViolation<InstUptExitStorageDetailItemDTO> violation : violations) {
                        sb.append(violation.getMessage()).append(",");
                    }
                    return Result.error(sb.toString());
                }
            }
        }

        boolean b = xqOverseasExitHeadService.addExitStorage(dto);
        factory.close();
        return b ? Result.OK("新增成功") : Result.error("新增失败");
    }

    /**
     * 删除出库单
     */
    @AutoLog(value = "删除出库单")
    @ApiOperation(value="删除出库单", notes="删除出库单")
    @GetMapping(value = "/deleteExitStorage")
    public Result<?> deleteExitStorage(@RequestParam String ids) {
        boolean b = xqOverseasExitHeadService.deleteExitStorage(ids);
        return b ? Result.OK("删除成功") : Result.error("删除失败");
    }


    /**
     * 编辑出库单
     */
    @AutoLog(value = "编辑出库单")
    @ApiOperation(value="编辑出库单", notes="编辑出库单")
    @PostMapping(value = "/editExitStorage")
    public Result<?> editExitStorage(@RequestBody @Valid InstUptExitStorageDTO dto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        if (CollectionUtil.isNotEmpty(dto.getDetails())) {
            for (InstUptExitStorageDetailItemDTO detail : dto.getDetails()) {
                Set<ConstraintViolation<InstUptExitStorageDetailItemDTO>> violations = validator.validate(detail);
                if (violations.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (ConstraintViolation<InstUptExitStorageDetailItemDTO> violation : violations) {
                        sb.append(violation.getMessage()).append(",");
                    }
                    return Result.error(sb.toString());
                }
            }
        }

        boolean b = xqOverseasExitHeadService.editExitStorage(dto);
        factory.close();
        return b ? Result.OK("编辑成功") : Result.error("编辑失败");
    }

    /**
     * 选择关联lot(分页列表)
     */
    @AutoLog(value = "选择关联lot(分页列表)")
    @ApiOperation(value = "选择关联lot(分页列表)", notes = "选择关联lot(分页列表)", response = RelativeInventoryLotPageVO.class)
    @GetMapping(value = "/pageRelativeLot")
    public Result<?> pageRelativeLot(QueryPageRelativeLotDTO dto) {
        IPage<RelativeInventoryLotPageVO> pageList = xqOverseasExitHeadService.pageRelativeLot(dto);
        return Result.OK(pageList);
    }
    

    /**
     * 选择关联订单(分页列表)
     */
    @AutoLog(value = "选择关联订单(分页列表)")
    @ApiOperation(value="选择关联订单(分页列表)", notes="选择关联订单(分页列表)", response = RelativeOrderPageVO.class)
    @GetMapping(value = "/pageRelativeOrder")
    public Result<?> pageRelativeOrder(QueryPageRelativeOrderDTO dto) {
        IPage<RelativeOrderPageVO> pageList = xqOverseasExitHeadService.pageRelativeOrder(dto);
        return Result.OK(pageList);
    }

    /**
     * 订单下的剩余产品(分页列表)
     */
    @AutoLog(value = "订单下的剩余产品(分页列表)")
    @ApiOperation(value="订单下的剩余产品(分页列表)", notes="订单下的剩余产品(分页列表)", response = SurplusOrderPageVO2.class)
    @GetMapping(value = "/pageSurplusOrder")
    public Result<?> pageSurplusOrder(QueryPageSurplusOrderDTO dto) {
        IPage<SurplusOrderPageVO2> pageList = xqOverseasExitHeadService.pageSurplusOrder(dto);
        return Result.OK(pageList);
    }

    /**
     * 编辑订单状态
     */
    @AutoLog(value = "编辑订单状态")
    @ApiOperation(value="编辑订单状态", notes="编辑订单状态")
    @PostMapping(value = "/editOrderStatus")
    public Result<?> editOrderStatus(@RequestBody @Valid EditEnterOrderStatusDTO dto) {
        boolean b = xqOverseasExitHeadService.editOrderStatus(dto);
        return b ? Result.OK("审核成功") : Result.error("审核失败");
    }

    /**
     * 查询出库明细(分页列表)
     *
     */
    @AutoLog(value = "查询出库明细(分页列表)")
    @ApiOperation(value="查询出库明细(分页列表)", notes="查询出库明细(分页列表)", response = ExitStorageDetailPageVO.class)
    @GetMapping(value = "/pageExitStorageDetail")
    public Result<?> pageExitStorageDetail(QueryPageExitStorageDetailDTO dto) {
        IPage<ExitStorageDetailPageVO> vo = xqOverseasExitHeadService.pageExitStorageDetail(dto);
        List<SumRemmitAmountVO> voList = xqOverseasExitHeadService.sumRemmitAmount(dto);
        if (CollectionUtil.isEmpty(voList)) {
            voList = Collections.emptyList();
        }
        return Result.OK2(vo, voList);
    }

    @AutoLog(value = "发货明细导出")
    @ApiOperation(value="发货明细导出", notes="发货明细导出")
    @GetMapping(value = "/exitStorageDetailExport")
    public void exitStorageDetailExport(QueryPageExitStorageDetailDTO dto, HttpServletResponse response) {
        Page<ExitStorageDetailPageVO> page = new Page<>(1, 9999);
        xqOverseasExitHeadService.exitStorageDetailExport(dto, page, response);
    }
}
