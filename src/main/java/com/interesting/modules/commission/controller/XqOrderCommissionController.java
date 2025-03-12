package com.interesting.modules.commission.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.interesting.common.api.vo.Result;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.system.query.QueryGenerator;
import com.interesting.common.aspect.annotation.AutoLog;

import com.interesting.common.util.StringUtils;
import com.interesting.modules.commission.entity.XqCommissionDistribution;
import com.interesting.modules.commission.entity.XqOrderCommission;
import com.interesting.modules.commission.mapper.XqCommissionDistributionMapper;
import com.interesting.modules.commission.service.IXqOrderCommissionService;
import com.interesting.modules.commission.dto.*;
import com.interesting.modules.commission.service.XqCommissionDistributionService;
import com.interesting.modules.commission.service.XqCommissionHistoryService;
import com.interesting.modules.commission.vo.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.order.mapper.XqOrderMapper;
import com.interesting.modules.order.vo.sub.XqOrderComsVO;
import com.interesting.modules.orderdetail.vo.XqCommissionOrderVO;
import com.interesting.modules.overseas.mapper.XqOverseasExitDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 佣金表
 * @Author: interesting-boot
 * @Date: 2023-06-02
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "佣金表")
@RestController
@RequestMapping("/commission/xqOrderCommission")
public class XqOrderCommissionController {
    @Autowired
    private IXqOrderCommissionService xqOrderCommissionService;
    @Autowired
    private XqOrderMapper xqOrderMapper;
    @Autowired
    private XqCommissionHistoryService xqCommissionHistoryService;

    @Resource
    private XqOverseasExitDetailMapper xqOverseasExitDetailMapper;

    @Resource
    private XqCommissionDistributionMapper xqCommissionDistributionMapper;
    @Resource
    private XqCommissionDistributionService xqCommissionDistributionService;

//    /**
//     * 根据所选用户关联出佣金公司
//     *
//     * @param xqOrderCommission
//     * @param pageNo
//     * @param pageSize
//     * @param req
//     * @return
//     */
//    @AutoLog(value = "根据所选客户关联出佣金公司")
//    @ApiOperation(value = "根据所选客户关联出佣金公司", notes = "根据所选客户关联出佣金公司", response = XqOrderComsVO.class)
//    @GetMapping(value = "/listByCustomerId")
//    public Result<?> listByCustomerId(@RequestParam String customerId) {
//        List<XqOrderComsVO> xqOrderComsVOS = xqOrderMapper.listOrderComsByCustomer(customerId);
//        return Result.OK(xqOrderComsVOS);
//    }
//
    /**
     * 相应客户下的历史记录
     */
    @AutoLog(value = "相应客户下的历史记录")
    @ApiOperation(value = "相应客户下的历史记录", notes = "相应客户下的历史记录")
    @GetMapping(value = "/pageCommissionTemp")
    public Result<?> pageCommissionTemp(@RequestParam String customerId, QueryCommissionTempDTO dto) {
        IPage<CommissionHistoryTmpVO> xqOrderComsVOS = xqOrderCommissionService.pageCommissionTemp(customerId, dto);
        return Result.OK(xqOrderComsVOS);
    }

    /**
     * 相应客户下的历史记录详情
     */
    @AutoLog(value = "历史记录详情")
    @ApiOperation(value = "历史记录详情", notes = "历史记录详情")
    @GetMapping(value = "/pageCommissionTempDetails")
    public Result<?> pageCommissionTempDetails(@RequestParam String templateId,
                                               QueryCommissionTempDetailDTO dto) {
        IPage<CommissionHistoryTmpDetailsVO> xqOrderComsVOS =
                xqOrderCommissionService.pageCommissionTempDetails(templateId, dto);
        return Result.OK(xqOrderComsVOS);
    }


    /**
     * 根据客户订单号找到出库金额
     */
    @AutoLog(value = "根据客户订单号找到出库金额")
    @ApiOperation(value = "根据客户订单号找到出库金额", notes = "根据客户订单号找到出库金额")
    @GetMapping(value = "/getPriceByCustomerNo")
    public Result<?> getPriceByCustomerNo(@RequestParam String customerNo,
                                          String orderNum) {
        BigDecimal value = xqOverseasExitDetailMapper.getPriceByCustomerNo(customerNo, orderNum);
        return Result.OK(value);
    }

    /**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "批量删除历史模板")
	@ApiOperation(value="批量删除历史模板", notes="批量删除历史模板")
	@GetMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids") String ids) {
		this.xqCommissionHistoryService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}

//	/**
//	 * 分页列表查询
//	 *
//	 * @param xqOrderCommission
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "佣金表-分页列表查询")
//	@ApiOperation(value="佣金表-分页列表查询", notes="佣金表-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<XqOrderCommissionVO>> queryPageList(QueryXqOrderCommissionDTO dto) {
//        Page<XqOrderCommissionVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
//		IPage<XqOrderCommissionVO> pageList = xqOrderCommissionService.pageList(page, dto);
//		return Result.OK(pageList);
//	}
//
//	/**
//	 * 添加
//	 *
//	 * @param xqOrderCommission
//	 * @return
//	 */
//	@AutoLog(value = "佣金表-添加")
//	@ApiOperation(value="佣金表-添加", notes="佣金表-添加")
//	@PostMapping(value = "/add")
//	public Result<?> add(@RequestBody AddXqOrderCommissionDTO dto) {
//        XqOrderCommission xqOrderCommission = new XqOrderCommission();
//        BeanUtils.copyProperties(dto, xqOrderCommission);
//		boolean b = xqOrderCommissionService.save(xqOrderCommission);
//		return b ? Result.OK("添加成功！") : Result.OK("添加失败！");
//	}
//
//	/**
//	 * 编辑
//	 *
//	 * @param xqOrderCommission
//	 * @return
//	 */
//	@AutoLog(value = "佣金表-编辑")
//	@ApiOperation(value="佣金表-编辑", notes="佣金表-编辑")
//	@PostMapping(value = "/edit")
//	public Result<?> edit(@RequestBody UpdateXqOrderCommissionDTO dto) {
//        String id = dto.getId();
//        XqOrderCommission byId = xqOrderCommissionService.getById(id);
//        if (byId == null) {
//            throw new InterestingBootException("该记录不存在");
//        }
//        BeanUtils.copyProperties(dto, byId);
//        boolean b = xqOrderCommissionService.updateById(byId);
//
//		return b ? Result.OK("编辑成功!") : Result.OK("编辑失败!");
//	}
//
//	/**
//	 * 通过id删除
//	 *
//	 * @param id
//	 * @return
//	 */
//	@AutoLog(value = "佣金表-通过id删除")
//	@ApiOperation(value="佣金表-通过id删除", notes="佣金表-通过id删除")
//	@GetMapping(value = "/delete")
//	public Result<?> delete(@RequestParam(name="id") String id) {
//		xqOrderCommissionService.removeById(id);
//		return Result.OK("删除成功!");
//	}
//
//	/**
//	 * 批量删除
//	 *
//	 * @param ids
//	 * @return
//	 */
//	@AutoLog(value = "佣金表-批量删除")
//	@ApiOperation(value="佣金表-批量删除", notes="佣金表-批量删除")
//	@GetMapping(value = "/deleteBatch")
//	public Result<?> deleteBatch(@RequestParam(name="ids") String ids) {
//		this.xqOrderCommissionService.removeByIds(Arrays.asList(ids.split(",")));
//		return Result.OK("批量删除成功！");
//	}
//
//	/**
//	 * 通过id查询
//	 *
//	 * @param id
//	 * @return
//	 */
//	@AutoLog(value = "佣金表-通过id查询")
//	@ApiOperation(value="佣金表-通过id查询", notes="佣金表-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<?> queryById(@RequestParam(name="id") String id) {
//		XqOrderCommission xqOrderCommission = xqOrderCommissionService.getById(id);
//		return Result.OK(xqOrderCommission);
//	}


    /**
     * 获取佣金产品信息
     */
    @AutoLog(value = "获取佣金产品信息")
    @ApiOperation(value = "获取佣金产品信息", notes = "获取佣金产品信息")
    @GetMapping(value = "/getOrderDetailByOrderNum")
    public Result<?> getOrderDetailByOrderNum(@RequestParam("orderId") String orderId) {
        List<XqCommissionOrderVO> list = xqCommissionDistributionMapper.commissionOrderList(orderId);
        if (list.size() == 0) {
            list = xqCommissionDistributionMapper.commissionOrderList1(orderId);
        }
        return Result.OK(list);
    }

    /**
     * 新增或者编辑佣金比例
     */
    @AutoLog(value = "新增或者编辑佣金比例")
    @ApiOperation(value = "新增或者编辑佣金比例", notes = "新增或者编辑佣金比例")
    @PostMapping(value = "/InsOrUptOrderCommission")
    public Result<?> editOrderCommission(String param) {
        JSONArray objects2 = JSON.parseArray(param);
        ArrayList<XqCommissionDistribution> list1 = new ArrayList<>();
        ArrayList<XqCommissionDistribution> list2 = new ArrayList<>();
        for (int i = 0; i < objects2.size(); i++) {
            JSONObject jsonObject = objects2.getJSONObject(i);
            AddCommissionDistributionDTO addCommissionDistributionDTO =
                    jsonObject.toJavaObject(AddCommissionDistributionDTO.class);
            XqCommissionDistribution xqCommissionDistribution = new XqCommissionDistribution();
            if (StringUtils.isBlank(addCommissionDistributionDTO.getId())) {
                BeanUtils.copyProperties(addCommissionDistributionDTO, xqCommissionDistribution);
                list1.add(xqCommissionDistribution);
            } else {
                BeanUtils.copyProperties(addCommissionDistributionDTO, xqCommissionDistribution);
                list2.add(xqCommissionDistribution);
            }

        }
        xqCommissionDistributionService.saveBatch(list1);
        xqCommissionDistributionService.updateBatchById(list2);
        return Result.OK();
    }
}
