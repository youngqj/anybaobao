package com.interesting.modules.commissioncompany.controller;

import com.alibaba.fastjson.JSONObject;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.commission.service.XqCommissionHistoryService;
import com.interesting.modules.commission.vo.CommissionHistoryTmpDetailsVO;
import com.interesting.modules.commissioncompany.dto.AddCommissionHistoryTmpDTO;
import com.interesting.modules.commissioncompany.dto.AddCommissionHistoryTmpDTO2;
import com.interesting.modules.commissioncompany.dto.EditCommissionHistoryTmpDTO;
import com.interesting.modules.commissioncompany.dto.EditCommissionHistoryTmpDTO2;
import com.interesting.modules.commissioncompany.entity.XqCommissionCompany;
import com.interesting.modules.commissioncompany.mapper.XqCommissionCompanyMapper;
import com.interesting.modules.commissioncompany.service.XqCommissionCompanyService;
import com.interesting.modules.order.mapper.XqOrderMapper;
import com.interesting.modules.order.vo.sub.XqOrderComsVO;
import com.interesting.modules.unit.entity.XqUnit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Api(tags="佣金公司管理")
@RestController
@RequestMapping("/commission/company")
public class XqCommissionCompanyController {
    @Autowired
    private XqCommissionCompanyService xqCommissionCompanyService;
    @Autowired
    private XqOrderMapper xqOrderMapper;
    @Autowired
    private XqCommissionHistoryService xqCommissionHistoryService;

    /**
     * 查询佣金公司名称列表
     *
     * @param id
     * @return
     */
    @AutoLog(value = "查询佣金公司名称列表")
    @ApiOperation(value="查询佣金公司名称列表", notes="查询佣金公司名称列表")
    @GetMapping(value = "/queryCompanyList")
    public Result<?> queryCompanyList() {
        List<XqCommissionCompany> list = xqCommissionCompanyService.list();
        List<JSONObject> collect = list.stream().map(i -> {
            JSONObject js = new JSONObject();
            js.put("id", i.getId());
            js.put("name", i.getName());
            return js;
        }).collect(Collectors.toList());

        return Result.OK(collect);
    }

    /**
     * 查询可选佣金公司
     *
     * @param id
     * @return
     */
    @AutoLog(value = "查询可选佣金公司")
    @ApiOperation(value="查询可选佣金公司", notes="查询可选佣金公司", response = XqOrderComsVO.class)
    @GetMapping(value = "/queryCompanyListByCustomerId")
    public Result<?> queryCompanyListByCustomerId(@RequestParam String id) {
        List<XqOrderComsVO> xqOrderComsVOS = xqOrderMapper.listOrderComsByCustomer(id);
        return Result.OK(xqOrderComsVOS);
    }

    /**
     * 修改佣金模板详情
     */
    @AutoLog(value = "修改佣金模板详情")
    @ApiOperation(value="修改佣金模板详情", notes="修改佣金模板详情")
    @PostMapping(value = "/editCommissionTemp")
    public Result<?> editCommissionTemp(@RequestBody @Valid EditCommissionHistoryTmpDTO dto) {
        boolean b = xqCommissionCompanyService.editCommissionTemp(dto);
        return b ? Result.OK("修改成功") : Result.error("修改失败");
    }

    /**
     * 新增佣金模板详情
     */
    @AutoLog(value = "新增佣金模板详情")
    @ApiOperation(value="新增佣金模板详情", notes="新增佣金模板详情")
    @PostMapping(value = "/addCommissionTemp")
    public Result<?> addCommissionTemp(@RequestBody @Valid AddCommissionHistoryTmpDTO dto) {
        boolean b = xqCommissionCompanyService.addCommissionTemp(dto);
        return b ? Result.OK("新增成功") : Result.error("新增失败");
    }

    /**
     * 删除佣金模板详情
     */
    @AutoLog(value = "删除佣金模板详情")
    @ApiOperation(value="删除佣金模板详情", notes="删除佣金模板详情")
    @GetMapping(value = "/deleteBatchCommissionTemp")
    public Result<?> deleteBatchCommissionTemp(@RequestParam String ids) {
        boolean b = xqCommissionCompanyService.removeByIds(Arrays.asList(ids.split(",")));
        return b ? Result.OK("删除成功") : Result.error("删除失败");
    }

    /**
     * 修改佣金模板名称
     */
    @AutoLog(value = "修改佣金模板名称")
    @ApiOperation(value="修改佣金模板名称", notes="修改佣金模板名称")
    @PostMapping(value = "/editCommissionTempOuter")
    public Result<?> editCommissionTempOuter(@RequestBody @Valid EditCommissionHistoryTmpDTO2 dto) {
        boolean b = xqCommissionHistoryService.editCommissionTemp(dto);
        return b ? Result.OK("修改成功") : Result.error("修改失败");
    }

    /**
     * 新增佣金模板
     */
    @AutoLog(value = "新增佣金模板")
    @ApiOperation(value="新增佣金模板", notes="新增佣金模板")
    @PostMapping(value = "/addCommissionTempOuter")
    public Result<?> addCommissionTempOuter(@RequestBody @Valid AddCommissionHistoryTmpDTO2 dto) {
        boolean b = xqCommissionHistoryService.addCommissionTemp(dto);
        return b ? Result.OK("新增成功") : Result.error("新增失败");
    }


    /**
     * 删除佣金模板
     */
    @AutoLog(value = "删除佣金模板")
    @ApiOperation(value="删除佣金模板", notes="删除佣金模板")
    @GetMapping (value = "/deleteBatchCommissionTempOuter")
    public Result<?> deleteBatchCommissionTempOuter(@RequestParam String ids) {
        boolean b = xqCommissionCompanyService.deleteBatchCommissionTempOuter(ids);
        return b ? Result.OK("删除成功") : Result.error("删除失败");
    }


}
