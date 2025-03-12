package com.interesting.modules.overseas.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.overseas.dto.*;
import com.interesting.modules.overseas.service.XqInventoryOverseasService;
import com.interesting.modules.overseas.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(tags = "海外仓库存管理")
@RestController
@RequestMapping("/overseas/storage")
public class OverSeasInventoryController {
    @Autowired
    private XqInventoryOverseasService xqInventoryOverseasService;

    /**
     * 查询海外仓库存(分页列表)
     */
    @AutoLog(value = "查询海外仓库存(分页列表)")
    @ApiOperation(value = "查询海外仓库存(分页列表)", notes = "查询海外仓库存(分页列表)", response = OverSeasInventoryPageVO.class)
    @GetMapping(value = "/pageOverSeasInventory")
    public Result<?> pageOverSeasInventory(QueryPageOverSeasInventoryDTO dto) {
        IPage<OverSeasInventoryPageVO> vo = xqInventoryOverseasService.pageOverSeasInventory(dto);
        return Result.OK(vo);
    }

    /**
     * 查询库存明细
     */
    @AutoLog(value = "查询库存明细")
    @ApiOperation(value = "查询库存明细", notes = "查询库存明细", response = OverSeasInventoryDetailPageVO.class)
    @GetMapping(value = "/pageInventoryDetails")
    public Result<?> pageInventoryDetails(QueryPageInventoryDetailsDTO dto) {
        if( StringUtils.isNotBlank(dto.getProductId()) && StringUtils.isNotBlank(dto.getWarehouseId())){
            Result.error("查询失败，仓库和商品不可为空");
        }

        return Result.OK2(xqInventoryOverseasService.pageInventoryDetails(dto),xqInventoryOverseasService.getTotalNum(dto));
    }


    /**
     * 获取可用库存
     */
    @AutoLog(value = "获取可用库存")
    @ApiOperation(value = "获取可用库存", notes = "获取可用库存")
    @GetMapping("/getInventoryNum")
    public Result<?> getInventoryNum(@RequestParam(name = "warehouseId", required = false) String warehouseId,
                                     @RequestParam(name = "warehouseLot", required = false) String warehouseLot,
                                     @RequestParam(name = "productId") String productId) {
        Integer a = xqInventoryOverseasService.getInventoryNum(productId, warehouseId, warehouseLot);
        return Result.OK(a);
    }

    /**
     * 获取可选批号库存(分页列表)
     */
    @AutoLog(value = "获取可选批号库存(分页列表)")
    @ApiOperation(value = "获取可选批号库存(分页列表)", notes = "获取可选批号库存(分页列表)")
    @GetMapping("/pageBatchInventory")
    public Result<?> pageBatchInventory(@Valid QueryPageBatchInventoryDTO dto) {
        IPage<BatchInventoryPageVO> vo = xqInventoryOverseasService.pageBatchInventory(dto);
        return Result.OK(vo);
    }

    /**
     * 批量选择仓库返回库存
     *
     * @return 库存
     */
    @AutoLog(value = "批量选择仓库返回库存")
    @ApiOperation(value = "批量选择仓库返回库存", notes = "批量选择仓库返回库存")
    @GetMapping("/getInventoryMapList")
    public Result<?> getInventoryMapList(@RequestParam(name = "warehouseId") String warehouseId,
                                         @RequestParam(name = "productIds") List<String> productIds) {
        List<Map<String, Integer>> res =
                xqInventoryOverseasService.getInventoryMapList(productIds, warehouseId);

        return Result.OK(res);
    }



    /**
     * 海外仓库库存-货值明细
     */
    @AutoLog(value = "海外仓库库存-货值")
    @ApiOperation(value = "海外仓库库存-货值", notes = "海外仓库库存-货值", response = HuoZhiWarehouseVO.class)
    @GetMapping(value = "/getHuoZhiByWarehouse")
    public Result<?> getHuoZhiByWarehouse(QueryHuoZhiWarehouseDTO dto) {
        IPage<HuoZhiWarehouseVO> vo = xqInventoryOverseasService.getHuoZhiByWarehouse(dto);
        List<HuoZhiWarehouseVO> vo2 = xqInventoryOverseasService.getTotalPrice(dto);
        return Result.OK2(vo, vo2);
    }

    /**
     * 海外仓库库存-货值明细
     */
    @AutoLog(value = "海外仓库库存-货值明细")
    @ApiOperation(value = "海外仓库库存-货值明细", notes = "海外仓库库存-货值明细", response = HuoZhiDetailVO.class)
    @GetMapping(value = "/getHuoZhiDetail")
    public Result<?> getHuoZhiDetail(QueryHuoZhiDTO dto) {
        IPage<HuoZhiDetailVO> vo = xqInventoryOverseasService.getHuoZhiDetail(dto);
        return Result.OK(vo);
    }
}
