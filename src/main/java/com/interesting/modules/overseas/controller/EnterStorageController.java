package com.interesting.modules.overseas.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.modules.overseas.dto.*;
import com.interesting.modules.overseas.service.XqOverseasEnterHeadService;
import com.interesting.modules.overseas.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.Set;

@Slf4j
@Api(tags="海外仓入库管理")
@RestController
@RequestMapping("/enter/storage")
public class EnterStorageController {
    @Autowired
    private XqOverseasEnterHeadService xqOverseasEnterHeadService;

    /**
     * 查询入库单(分页列表)
     *
     */
    @AutoLog(value = "查询入库单(分页列表)")
    @ApiOperation(value="查询入库单(分页列表)", notes="查询入库单(分页列表)", response = EnterStoragePageVO.class)
    @GetMapping(value = "/pageEnterStorage")
    public Result<?> pageEnterStorage(QueryPageEnterStorageDTO dto) {
        IPage<EnterStoragePageVO> vo = xqOverseasEnterHeadService.pageEnterStorage(dto);
        return Result.OK(vo);
    }

    /**
     * 查询入库单页面详情
     *
     */
    @AutoLog(value = "查询入库单页面详情")
    @ApiOperation(value="查询入库单页面详情", notes="查询入库单页面详情", response = EnterStorageDetailVO.class)
    @GetMapping(value = "/getEnterStorage")
    public Result<?> getEnterStorage(@RequestParam String id) {
        EnterStorageDetailVO vo = xqOverseasEnterHeadService.getEnterStorage(id);
        return Result.OK(vo);
    }


    /**
     * 新增入库单
     */
    @AutoLog(value = "新增入库单")
    @ApiOperation(value="新增入库单", notes="新增入库单")
    @PostMapping(value = "/addEnterStorage")
    public Result<?> addEnterStorage(@RequestBody @Valid InstUptEnterStorageDTO dto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        if (CollectionUtil.isNotEmpty(dto.getDetails())) {
            for (InstUptEnterStorageDetailItemDTO detail : dto.getDetails()) {
                Set<ConstraintViolation<InstUptEnterStorageDetailItemDTO>> violations = validator.validate(detail);
                if (violations.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (ConstraintViolation<InstUptEnterStorageDetailItemDTO> violation : violations) {
                        sb.append(violation.getMessage()).append(",");
                    }
                    return Result.error(sb.toString());
                }
            }
        }

        boolean b = xqOverseasEnterHeadService.addEnterStorage(dto);
        factory.close();
        return b ? Result.OK("新增成功") : Result.error("新增失败");
    }

    /**
     * 删除入库单
     */
    @AutoLog(value = "删除入库单")
    @ApiOperation(value="删除入库单", notes="删除入库单")
    @GetMapping(value = "/deleteEnterStorage")
    public Result<?> deleteEnterStorage(@RequestParam String ids) {
        boolean b = xqOverseasEnterHeadService.deleteEnterStorage(ids);
        return b ? Result.OK("删除成功") : Result.error("删除失败");
    }


    /**
     * 编辑入库单
     */
    @AutoLog(value = "编辑入库单")
    @ApiOperation(value="编辑入库单", notes="编辑入库单")
    @PostMapping(value = "/editEnterStorage")
    public Result<?> editEnterStorage(@RequestBody @Valid InstUptEnterStorageDTO dto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        if (CollectionUtil.isNotEmpty(dto.getDetails())) {
            for (InstUptEnterStorageDetailItemDTO detail : dto.getDetails()) {
                Set<ConstraintViolation<InstUptEnterStorageDetailItemDTO>> violations = validator.validate(detail);
                if (violations.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (ConstraintViolation<InstUptEnterStorageDetailItemDTO> violation : violations) {
                        sb.append(violation.getMessage()).append(",");
                    }
                    return Result.error(sb.toString());
                }
            }
        }

        boolean b = xqOverseasEnterHeadService.editEnterStorage(dto);
        factory.close();
        return b ? Result.OK("编辑成功") : Result.error("编辑失败");
    }


    /**
     * 选择关联订单(分页列表)
     */
    @AutoLog(value = "选择关联订单(分页列表)")
    @ApiOperation(value="选择关联订单(分页列表)", notes="选择关联订单(分页列表)", response = RelativeOrderPageVO.class)
    @GetMapping(value = "/pageRelativeOrder")
    public Result<?> pageRelativeOrder(QueryPageRelativeOrderDTO dto) {
        IPage<RelativeOrderPageVO> pageList = xqOverseasEnterHeadService.pageRelativeOrder(dto);
        return Result.OK(pageList);
    }

    /**
     * 订单下的剩余产品(分页列表)
     */
    @AutoLog(value = "订单下的剩余产品(分页列表)")
    @ApiOperation(value="订单下的剩余产品(分页列表)", notes="订单下的剩余产品(分页列表)", response = SurplusOrderPageVO.class)
    @GetMapping(value = "/pageSurplusOrder")
    public Result<?> pageSurplusOrder(QueryPageSurplusOrderDTO dto) {
        IPage<SurplusOrderPageVO> pageList = xqOverseasEnterHeadService.pageSurplusOrder(dto);
        return Result.OK(pageList);
    }

    /**
     * 编辑订单状态
     */
    @AutoLog(value = "编辑订单状态")
    @ApiOperation(value="编辑订单状态", notes="编辑订单状态")
    @PostMapping(value = "/editOrderStatus")
    public Result<?> editOrderStatus(@RequestBody @Valid EditEnterOrderStatusDTO dto) {
        boolean b = xqOverseasEnterHeadService.editOrderStatus(dto);
        return b ? Result.OK("审核成功") : Result.error("审核失败");
    }

    /**
     * 查询入库明细(分页列表)
     *
     */
    @AutoLog(value = "查询入库明细(分页列表)")
    @ApiOperation(value="查询入库明细(分页列表)", notes="查询入库明细(分页列表)", response = EnterStorageDetailPageVO.class)
    @GetMapping(value = "/pageEnterStorageDetail")
    public Result<?> pageEnterStorageDetail(QueryPageEnterStorageDetailDTO dto) {
        IPage<EnterStorageDetailPageVO> vo = xqOverseasEnterHeadService.pageEnterStorageDetail(dto);
        return Result.OK(vo);
    }


}
