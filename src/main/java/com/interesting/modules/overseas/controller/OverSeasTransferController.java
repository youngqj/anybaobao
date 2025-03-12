package com.interesting.modules.overseas.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.modules.overseas.dto.*;
import com.interesting.modules.overseas.service.XqOverseasTransferService;
import com.interesting.modules.overseas.vo.OverSeasInventoryCheckDetailVO;
import com.interesting.modules.overseas.vo.OverSeasInventoryCheckPageVO;
import com.interesting.modules.overseas.vo.OverSeasInventoryTransferDetailVO;
import com.interesting.modules.overseas.vo.OverSeasInventoryTransferPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.Set;

@Slf4j
@Api(tags = "海外仓调拨管理")
@RestController
@RequestMapping("/overseas/transfer")
public class OverSeasTransferController {
    @Autowired
    private XqOverseasTransferService xqOverseasTransferService;

    /**
     * 查询海外仓盘点分页
     *
     * @param id
     * @return
     */
    @AutoLog(value = "查询海外仓调拨分页")
    @ApiOperation(value = "查询海外仓调拨分页", notes = "查询海外仓调拨分页", response = OverSeasInventoryTransferPageVO.class)
    @GetMapping(value = "/pageInventoryTransfer")
    public Result<?> pageInventoryTransfer(QueryPageOverSeasInventoryTransferDTO dto) {
        IPage<OverSeasInventoryTransferPageVO> pageList = xqOverseasTransferService.pageInventoryTransfer(dto);
        return Result.OK(pageList);
    }

    /**
     * 查询盘点详情
     */
    @AutoLog(value = "查询调拨详情")
    @ApiOperation(value = "查询调拨详情", notes = "查询调拨详情", response = OverSeasInventoryTransferDetailVO.class)
    @GetMapping(value = "/getInventoryTransferDetById")
    public Result<?> getInventoryTransferDetById(@RequestParam String id) {
        OverSeasInventoryTransferDetailVO vo = xqOverseasTransferService.getInventoryTransferDetById(id);
        return Result.OK(vo);
    }

    /**
     * 删除盘点
     */
    @AutoLog(value = "删除调拨")
    @ApiOperation(value = "删除调拨", notes = "删除调拨")
    @GetMapping(value = "/deleteBatchInventorytransfer")
    public Result<?> deleteBatchInventoryCheck(@RequestParam String ids) {
        boolean b = xqOverseasTransferService.deleteBatchInventoryTransfer(ids);
        return b ? Result.OK("删除成功") : Result.error("删除失败");
    }


    /**
     * 新增调拨记录
     */
    @AutoLog(value = "新增调拨记录")
    @ApiOperation(value = "新增调拨记录", notes = "新增调拨记录")
    @PostMapping(value = "/addInventoryTransfer")
    public Result<?> addInventoryCheck(@RequestBody @Valid InstOverSeasTransferDTO dto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        if (CollectionUtil.isNotEmpty(dto.getTransferDetails())) {
            for (InstOverSeasTransferDetailDTO detail : dto.getTransferDetails()) {
                Set<ConstraintViolation<InstOverSeasTransferDetailDTO>> violations =
                        validator.validate(detail);

                if (violations.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (ConstraintViolation<InstOverSeasTransferDetailDTO> violation : violations) {
                        sb.append(violation.getMessage()).append(",");
                    }
                    return Result.error(sb.toString());
                }
            }
        }

        boolean b = xqOverseasTransferService.addInventoryTransfer(dto);
        factory.close();
        return b ? Result.OK("盘点成功") : Result.error("盘点失败");
    }

    /**
     * 编辑盘点单审核状态
     */
    @AutoLog(value = "编辑调拨单审核状态")
    @ApiOperation(value = "编辑调拨单审核状态", notes = "编辑调拨单审核状态")
    @PostMapping(value = "/editCheckStatus")
    public Result<?> editCheckStatus(@RequestBody @Valid EditEnterOrderStatusDTO dto) {
        boolean b = xqOverseasTransferService.editCheckStatus(dto);
        return b ? Result.OK("审核成功") : Result.error("审核失败");
    }


}
