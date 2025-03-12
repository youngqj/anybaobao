package com.interesting.modules.warehouse.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.accmaterial.entity.XqInventory;
import com.interesting.modules.accmaterial.service.XqInventoryService;
import com.interesting.modules.customer.vo.XqCustomerVO;
import com.interesting.modules.warehouse.dto.InstOrUpdtXqWarehouseDTO;
import com.interesting.modules.warehouse.dto.QueryXqWarehouseDTO;
import com.interesting.modules.warehouse.entity.XqWarehouse;
import com.interesting.modules.warehouse.service.XqWarehouseService;
import com.interesting.modules.warehouse.vo.WarehouseIdNameAndAddressVO;
import com.interesting.modules.warehouse.vo.XqWarehouseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Api(tags="仓库管理")
@RestController
@RequestMapping("/warehouse/xqWarehouse")
public class XqWarehouseController {
    @Autowired
    private XqWarehouseService xqWarehouseService;
    @Autowired
    private XqInventoryService xqInventoryService;

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
    @ApiOperation(value="分页列表查询", notes="分页列表查询")
    @GetMapping(value = "/warehousePage")
    public Result<IPage<XqWarehouseVO>> warehousePage(QueryXqWarehouseDTO dto) {
        IPage<XqWarehouseVO> page = xqWarehouseService.warehousePage(dto);
        return Result.OK(page);
    }

    @AutoLog(value = "通过id查询")
    @ApiOperation(value="通过id查询", notes="通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name="id") String id) {
        XqWarehouseVO xqWarehouseVO = xqWarehouseService.getDetailById(id);
        return Result.OK(xqWarehouseVO);
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "通过id删除")
    @ApiOperation(value="通过id删除", notes="通过id删除")
    @GetMapping(value = "/deleteById")
    public Result<?> deleteById(@RequestParam(name="id") String id) {
        if (xqInventoryService.lambdaQuery().eq(XqInventory::getWarehouseId, id).count() > 0) {
            return Result.error("该仓库下存在库存，无法删除！");
        }

        xqWarehouseService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "批量删除")
    @ApiOperation(value="批量删除", notes="批量删除")
    @GetMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids") String ids) {
        if (xqInventoryService.lambdaQuery().in(XqInventory::getWarehouseId,
                Arrays.asList(ids.split(","))).count() > 0) {
            return Result.error("所选仓库下存在库存，无法删除！");
        }

        xqWarehouseService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 添加
     *
     * @param xqAccessoriesPurchase
     * @return
     */
    @AutoLog(value = "添加")
    @ApiOperation(value="添加", notes="添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody @Valid InstOrUpdtXqWarehouseDTO dto) {
        return xqWarehouseService.addWarehouse(dto) ? Result.OK("添加成功！") : Result.error("添加失败！");
    }

    /**
     * 编辑
     *
     * @param xqAccessoriesPurchase
     * @return
     */
    @AutoLog(value = "编辑")
    @ApiOperation(value="编辑", notes="编辑")
    @PostMapping(value = "/edit")
    public Result<?> edit(@RequestBody @Valid InstOrUpdtXqWarehouseDTO dto) {
        XqWarehouse xqWarehouse = new XqWarehouse();
        BeanUtils.copyProperties(dto, xqWarehouse);
        return xqWarehouseService.updateWarehouseById(dto) ? Result.OK("编辑成功！") : Result.error("编辑失败！");
    }

    /**
     * 查询仓库名称
     *
     * @param id
     * @return
     */
    @AutoLog(value = "查询仓库名称")
    @ApiOperation(value="查询仓库名称", notes="查询仓库名称")
    @GetMapping(value = "/listWarehouseName")
    public Result<?> listWarehouseName(@RequestParam(required = false) String type) {
        List<XqWarehouse> list = xqWarehouseService.lambdaQuery()
                .eq(StringUtils.isNotBlank(type),XqWarehouse::getWarehouseType, type)
                .orderByAsc(XqWarehouse::getName)
                .list();
        List<JSONObject> collect = list.stream().map(i -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", i.getId());
            jsonObject.put("name", i.getName());
            jsonObject.put("address", i.getAddress());
            return jsonObject;
        }).collect(Collectors.toList());
        return Result.OK(collect);
    }


    /**
     * 导出模板
     *
     * @return
     */
    @AutoLog(value = "导出仓库模板")
    @ApiOperation(value = "导出仓库模板", notes = "导出仓库模板")
    @GetMapping(value = "/exportTemplate")
    public void exportTemplate(HttpServletResponse response) throws UnsupportedEncodingException {
        xqWarehouseService.exportTemplate(response);
    }

    /**
     * 导入供应商
     *
     * @return
     */
    @AutoLog(value = "导入仓库")
    @ApiOperation(value = "导入仓库", notes = "导入仓库")
    @PostMapping(value = "/importWarehouse")
    public Result<?> importWarehouse(@RequestPart MultipartFile file) throws Exception {
        return xqWarehouseService.importWarehouse(file);
    }

    /**
     * 查询付款方式列表
     */
    @AutoLog(value = "查询仓库列表")
    @ApiOperation(value = "查询仓库列表", notes = "查询仓库列表",response = WarehouseIdNameAndAddressVO.class)
    @GetMapping(value = "/listWarehouse")
    public Result<?> listWarehouse() {

        return Result.OK(xqWarehouseService.listWarehouse());
    }




}
