package com.interesting.modules.accmaterial.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.modules.accmaterial.dto.EditStatusDTO;
import com.interesting.modules.accmaterial.dto.accpurchase.InstUptAccPurchaseDTO;
import com.interesting.modules.accmaterial.dto.accpurchase.InstUptAccPurchaseDTO2;
import com.interesting.modules.accmaterial.dto.accpurchase.QueryPageAccPurchaseDTO;
import com.interesting.modules.accmaterial.service.XqAccessoryPurchaseInventoryService;
import com.interesting.modules.accmaterial.vo.accpurchase.AccessoryPurchaseDetailVO;
import com.interesting.modules.accmaterial.vo.accpurchase.AccessoryPurchasePageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.List;
import java.util.Set;

@Slf4j
@Api(tags="辅料采购管理")
@RestController
@RequestMapping("/accmaterial/accessoriesBuy")
public class XqAccessoriesBuyController {
    @Autowired
    private XqAccessoryPurchaseInventoryService xqAccessoryPurchaseInventoryService;

    /**
     * 查询辅料采购页面(分页列表)
     *
     */
    @AutoLog(value = "查询辅料采购页面")
    @ApiOperation(value="查询辅料采购页面", notes="查询辅料采购页面", response = AccessoryPurchasePageVO.class)
    @GetMapping(value = "/pageAccPurchase")
    public Result<?> pageAccPurchase(QueryPageAccPurchaseDTO dto) {
        IPage<AccessoryPurchasePageVO> vo = xqAccessoryPurchaseInventoryService.pageAccPurchase(dto);
        return Result.OK(vo);
    }

    /**
     * 查询辅料采购页面详情
     *
     */
    @AutoLog(value = "查询辅料采购页面详情")
    @ApiOperation(value="查询辅料采购页面详情", notes="查询辅料采购页面详情", response = AccessoryPurchaseDetailVO.class)
    @GetMapping(value = "/getAccPurchase")
    public Result<?> getAccPurchase(@RequestParam String id) {
        AccessoryPurchaseDetailVO vo = xqAccessoryPurchaseInventoryService.getAccPurchaseById(id);
        return Result.OK(vo);
    }


    /**
     * 新增辅料采购
     */
    @AutoLog(value = "新增辅料采购")
    @ApiOperation(value="新增辅料采购", notes="新增辅料采购")
    @PostMapping(value = "/addAccPurchase")
    public Result<?> addAccPurchase(@RequestBody @Valid InstUptAccPurchaseDTO dto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        List<InstUptAccPurchaseDTO2> details = dto.getDetails();
        if (details != null && details.size() > 0) {
            for (InstUptAccPurchaseDTO2 orderProdVO : details) {
                Set<ConstraintViolation<InstUptAccPurchaseDTO2>> violations = validator.validate(orderProdVO);
                if (!violations.isEmpty()) {
                    throw new ConstraintViolationException(violations);
                }
            }
        }

        boolean b = xqAccessoryPurchaseInventoryService.addAccPurchase(dto);
        factory.close();
        return b ? Result.OK("新增成功") : Result.error("新增失败");
    }

    /**
     * 删除辅料采购页面
     */
    @AutoLog(value = "删除辅料采购页面")
    @ApiOperation(value="删除辅料采购页面", notes="删除辅料采购页面")
    @GetMapping(value = "/deleteAccPurchase")
    public Result<?> deleteAccPurchase(@RequestParam String ids) {
        boolean b = xqAccessoryPurchaseInventoryService.deleteAccPurchase(ids);
        return Result.OK();
    }


    /**
     * 编辑辅料采购页面
     */
    @AutoLog(value = "编辑辅料采购页面")
    @ApiOperation(value="编辑辅料采购页面", notes="编辑辅料采购页面")
    @PostMapping(value = "/editAccPurchase")
    public Result<?> editAccPurchase(@RequestBody @Valid InstUptAccPurchaseDTO dto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        List<InstUptAccPurchaseDTO2> details = dto.getDetails();
        if (details != null && details.size() > 0) {
            for (InstUptAccPurchaseDTO2 orderProdVO : details) {
                Set<ConstraintViolation<InstUptAccPurchaseDTO2>> violations = validator.validate(orderProdVO);
                if (!violations.isEmpty()) {
                    throw new ConstraintViolationException(violations);
                }
            }
        }

        boolean b = xqAccessoryPurchaseInventoryService.editAccPurchase(dto);
        factory.close();
        return b ? Result.OK("编辑成功") : Result.error("编辑失败");
    }

    /**
     * 审核辅料采购
     */
    @AutoLog(value = "审核辅料采购")
    @ApiOperation(value="审核辅料采购", notes="审核辅料采购")
    @PostMapping(value = "/editAccPurchaseStatus")
    public Result<?> editAccPurchaseStatus(@RequestBody @Valid EditStatusDTO dto) {
        boolean b = xqAccessoryPurchaseInventoryService.editAccPurchaseStatus(dto);
        return b ? Result.OK("审核成功") : Result.error("审核失败");
    }
}
