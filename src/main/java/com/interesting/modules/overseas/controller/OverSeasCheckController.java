package com.interesting.modules.overseas.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.modules.overseas.dto.EditEnterOrderStatusDTO;
import com.interesting.modules.overseas.dto.InstOverSeasInventoryCheckDTO;
import com.interesting.modules.overseas.dto.InstOverSeasInventoryCheckDetailItemDTO;
import com.interesting.modules.overseas.dto.QueryPageOverSeasInventoryCheckDTO;
import com.interesting.modules.overseas.service.XqOverseasInventoryCheckService;
import com.interesting.modules.overseas.vo.OverSeasInventoryCheckDetailVO;
import com.interesting.modules.overseas.vo.OverSeasInventoryCheckPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.Set;

@Slf4j
@Api(tags="海外仓盘点管理")
@RestController
@RequestMapping("/check/storage")
public class OverSeasCheckController {

    @Autowired
    private XqOverseasInventoryCheckService xqOverseasInventoryCheckService;

    /**
     * 查询海外仓盘点分页
     *
     * @param id
     * @return
     */
    @AutoLog(value = "查询海外仓盘点分页")
    @ApiOperation(value="查询海外仓盘点分页", notes="查询海外仓盘点分页", response = OverSeasInventoryCheckPageVO.class)
    @GetMapping(value = "/pageInventoryCheck")
    public Result<?> pageInventoryCheck(QueryPageOverSeasInventoryCheckDTO dto) {
        IPage<OverSeasInventoryCheckPageVO> pageList = xqOverseasInventoryCheckService.pageInventoryCheck(dto);
        return Result.OK(pageList);
    }

    /**
     * 查询盘点详情
     */
    @AutoLog(value = "查询盘点详情")
    @ApiOperation(value="查询盘点详情", notes="查询盘点详情", response = OverSeasInventoryCheckDetailVO.class)
    @GetMapping(value = "/getInventoryCheckDetById")
    public Result<?> getInventoryCheckDetById(@RequestParam String id) {

        OverSeasInventoryCheckDetailVO vo = xqOverseasInventoryCheckService.getInventoryCheckDetById(id);
        return Result.OK(vo);
    }

    /**
     * 删除盘点
     */
    @AutoLog(value = "删除盘点")
    @ApiOperation(value="删除盘点", notes="删除盘点")
    @GetMapping(value = "/deleteBatchInventoryCheck")
    public Result<?> deleteBatchInventoryCheck(@RequestParam String ids) {
        boolean b = xqOverseasInventoryCheckService.deleteBatchInventoryCheck(ids);
        return b ? Result.OK("删除成功") : Result.error("删除失败");
    }


    /**
     * 新增盘点记录
     */
    @AutoLog(value = "新增盘点记录")
    @ApiOperation(value="新增盘点记录", notes="新增盘点记录")
    @PostMapping(value = "/addInventoryCheck")
    public Result<?> addInventoryCheck(@RequestBody @Valid InstOverSeasInventoryCheckDTO dto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        if (CollectionUtil.isNotEmpty(dto.getPdDetails())) {
            for (InstOverSeasInventoryCheckDetailItemDTO detail : dto.getPdDetails()) {
                Set<ConstraintViolation<InstOverSeasInventoryCheckDetailItemDTO>> violations =
                        validator.validate(detail);

                if (violations.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (ConstraintViolation<InstOverSeasInventoryCheckDetailItemDTO> violation : violations) {
                        sb.append(violation.getMessage()).append(",");
                    }
                    return Result.error(sb.toString());
                }
            }
        }

        boolean b = xqOverseasInventoryCheckService.addInventoryCheck(dto);
        factory.close();
        return b ? Result.OK("盘点成功") : Result.error("盘点失败");
    }

    /**
     * 编辑盘点单审核状态
     */
    @AutoLog(value = "编辑盘点单审核状态")
    @ApiOperation(value="编辑盘点单审核状态", notes="编辑盘点单审核状态")
    @PostMapping(value = "/editCheckStatus")
    public Result<?> editCheckStatus(@RequestBody @Valid EditEnterOrderStatusDTO dto) {
        boolean b = xqOverseasInventoryCheckService.editCheckStatus(dto);
        return b ? Result.OK("审核成功") : Result.error("审核失败");
    }
}
