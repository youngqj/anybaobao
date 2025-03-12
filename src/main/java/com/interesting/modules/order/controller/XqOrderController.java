package com.interesting.modules.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.business.message.websocket.WebSocket;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.constant.CommonConstant;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.system.vo.LoginUser;
import com.interesting.common.util.BeanCopyUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.config.FilterContextHandler;
import com.interesting.modules.accmaterial.entity.XqAccessoriesPurchase;
import com.interesting.modules.accmaterial.service.IXqAccessoriesPurchaseService;
import com.interesting.modules.base.IdAndNameVO;
import com.interesting.modules.freightInfo.entity.XqFreightInfo;
import com.interesting.modules.freightInfo.service.IXqFreightInfoService;
import com.interesting.modules.insurance.dto.AddXqInsuranceExpensesDTO;
import com.interesting.modules.insurance.dto.QueryXqInsuranceExpensesDTO;
import com.interesting.modules.insurance.dto.UpdateXqInsuranceExpensesDTO;
import com.interesting.modules.insurance.entity.XqInsuranceExpenses;
import com.interesting.modules.insurance.vo.XqInsuranceExpensesVO;
import com.interesting.modules.notes.entity.XqProblemNote;
import com.interesting.modules.notes.service.XqProblemNoteService;
import com.interesting.modules.notes.vo.ProblemNoteVO;
import com.interesting.modules.order.dto.*;
import com.interesting.modules.order.dto.sub.*;
import com.interesting.modules.order.entity.XqOrder;
import com.interesting.modules.order.service.IXqOrderFinallyConfirmService;
import com.interesting.modules.order.service.IXqOrderService;
import com.interesting.modules.order.service.IXqOrderTransferRecordService;
import com.interesting.modules.order.vo.*;
import com.interesting.modules.overseas.entity.XqOverseasEnterHead;
import com.interesting.modules.overseas.service.XqOverseasEnterHeadService;
import com.interesting.modules.paymentmethod.service.IXqOrderPaymentMethodService;
import com.interesting.modules.remittance.entity.XqRemittanceDetail;
import com.interesting.modules.remittance.service.IXqRemittanceDetailService;
import com.interesting.modules.reportform.service.XqSellingProfitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.list.PredicatedList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 面单管理
 * @Author: interesting-boot
 * : 2023-06-01
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "面单管理")
@RestController
@RequestMapping("/order/xqOrder")
public class XqOrderController {
    @Autowired
    private IXqOrderService xqOrderService;
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private XqOverseasEnterHeadService xqOverseasEnterHeadService;
    @Autowired
    private IXqAccessoriesPurchaseService xqAccessoriesPurchaseService;
    @Autowired
    private IXqRemittanceDetailService xqRemittanceDetailService;
    @Resource
    private IXqOrderFinallyConfirmService xqOrderFinallyConfirmService;
    @Resource
    private IXqOrderTransferRecordService xqOrderTransferRecordService;
    @Resource
    private IXqOrderPaymentMethodService xqOrderPaymentMethodService;
    @Autowired
    private XqSellingProfitService xqSellingProfitService;
    @Autowired
    private IXqFreightInfoService xqFreightInfoService;

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "面单管理-通过id删除")
    @ApiOperation(value = "面单管理-通过id删除", notes = "面单管理-通过id删除")
    @GetMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id") String id) {
        XqOrder xqOrder = xqOrderService.getById(id);
        if (xqOrder.getFollowerAudit().equals("1")) {
            throw new InterestingBootException("该面单已经被跟单经理审核，无法删除");
        }
        xqOrderService.removeDetail(id);
        xqOrderService.removeById(id);

        webSocket.sendCompleteMsg();
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "面单管理-批量删除")
    @ApiOperation(value = "面单管理-批量删除", notes = "面单管理-批量删除")
    @GetMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids") String ids) {
        List<String> strings = Arrays.asList(ids.split(","));
        Set<String> collect = xqOrderService.lambdaQuery().in(XqOrder::getId, strings)
                .select(XqOrder::getOrderNum)
                .list().stream().map(XqOrder::getOrderNum).collect(Collectors.toSet());

        if (xqOverseasEnterHeadService.lambdaQuery()
                .in(XqOverseasEnterHead::getOrderNum, collect)
                .count() > 0) {
            return Result.error("所选面单中有被入库单使用，无法删除！");
        }

        for (String string : strings) {
            XqOrder xqOrder = xqOrderService.getById(string);
            if (xqOrder.getFollowerAudit().equals("1")) {
                throw new InterestingBootException("该面单已经被跟单经理审核，无法删除");
            }
            xqOrderService.removeDetail(string);
        }
        this.xqOrderService.removeByIds(Arrays.asList(ids.split(",")));

        webSocket.sendCompleteMsg();
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询整个面单
     *
     * @param id
     * @return
     */
    @AutoLog(value = "通过id查询整个面单")
    @ApiOperation(value = "通过id查询整个面单", notes = "通过id查询整个面单")
    @GetMapping(value = "/queryAllById")
    public Result<XqOrderAllVO> queryAllById(@RequestParam(name = "id") String id) {
        XqOrderAllVO xqOrder = xqOrderService.queryAllById(id);
        return Result.OK(xqOrder);
    }

    /**
     * 添加整个面单
     *
     * @param dto
     * @return
     */
    @AutoLog(value = "添加整个面单")
    @ApiOperation(value = "添加整个面单", notes = "添加整个面单")
    @PostMapping(value = "/addAll")
    public Result<?> addAll(@RequestBody @Valid AddXqOrderAllDTO dto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        /* 校验产品信息 */
        List<AddOrderProdDTO> orderProdVOS = dto.getOrderProdVOS();
        if (orderProdVOS != null && orderProdVOS.size() > 0) {
            for (AddOrderProdDTO orderProdVO : orderProdVOS) {
                Set<ConstraintViolation<AddOrderProdDTO>> violations = validator.validate(orderProdVO);
                if (!violations.isEmpty()) {
                    throw new ConstraintViolationException(violations);
                }
            }
        }

//		 /* 校验佣金信息 */
//		 List<AddOrderComsDTO> orderComsVOS = dto.getOrderComsVOS();
//		 if (orderComsVOS != null && orderComsVOS.size() > 0) {
//			 for (AddOrderComsDTO orderProdVO : orderComsVOS) {
//				 Set<ConstraintViolation<AddOrderComsDTO>> violations = validator.validate(orderProdVO);
//				 if (!violations.isEmpty()) {
//					 throw new ConstraintViolationException(violations);
//				 }
//			 }
//		 }
//
//		 /* 校验收汇情况 */
//		 List<AddOrderRemiDTO> orderRemiVOS = dto.getOrderRemiVOS();
//		 if (orderRemiVOS != null && orderRemiVOS.size() > 0) {
//			 for (AddOrderRemiDTO orderProdVO : orderRemiVOS) {
//				 Set<ConstraintViolation<AddOrderRemiDTO>> violations = validator.validate(orderProdVO);
//				 if (!violations.isEmpty()) {
//					 throw new ConstraintViolationException(violations);
//				 }
//			 }
//		 }
//
//		 /* 校验原料信息 */
//		 List<AddOrderRawDTO> orderRawVOS = dto.getOrderRawVOS();
//		 if (orderRawVOS != null && orderRawVOS.size() > 0) {
//			 for (AddOrderRawDTO orderProdVO : orderRawVOS) {
//				 Set<ConstraintViolation<AddOrderRawDTO>> violations = validator.validate(orderProdVO);
//				 if (!violations.isEmpty()) {
//					 throw new ConstraintViolationException(violations);
//				 }
//			 }
//		 }
//
//		 /* 校验辅料信息 */
//		 List<AddOrderAcsrDTO> orderAcsrVOS = dto.getOrderAcsrVOS();
//		 if (orderAcsrVOS != null && orderAcsrVOS.size() > 0) {
//			 for (AddOrderAcsrDTO orderProdVO : orderAcsrVOS) {
//				 Set<ConstraintViolation<AddOrderAcsrDTO>> violations = validator.validate(orderProdVO);
//				 if (!violations.isEmpty()) {
//					 throw new ConstraintViolationException(violations);
//				 }
//			 }
//		 }

        boolean b = xqOrderService.addAll(dto);

        /* websocket 推送消息 */
        if (1 == dto.getCompleteFlag()) {
            webSocket.sendCompleteMsg();
        }
        factory.close();
        return b ? Result.OK("添加成功！") : Result.OK("添加失败！");
    }

    /**
     * 编辑整个面单
     *
     * @param dto
     * @return
     */
    @AutoLog(value = "编辑整个面单")
    @ApiOperation(value = "编辑整个面单", notes = "编辑整个面单")
    @PostMapping(value = "/editAll")
    public Result<?> editAll(@RequestBody @Valid EditXqOrderAllDTO dto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        /* 校验产品信息 */
        List<AddOrderProdDTO> orderProdVOS = dto.getOrderProdVOS();
        if (orderProdVOS != null && orderProdVOS.size() > 0) {
            for (AddOrderProdDTO orderProdVO : orderProdVOS) {
                Set<ConstraintViolation<AddOrderProdDTO>> violations = validator.validate(orderProdVO);
                if (!violations.isEmpty()) {
                    throw new ConstraintViolationException(violations);
                }
            }
        }

//        /* 校验佣金信息 */
//        List<AddOrderComsDTO> orderComsVOS = dto.getOrderComsVOS();
//        if (orderComsVOS != null && orderComsVOS.size() > 0) {
//            for (AddOrderComsDTO orderProdVO : orderComsVOS) {
//                Set<ConstraintViolation<AddOrderComsDTO>> violations = validator.validate(orderProdVO);
//                if (!violations.isEmpty()) {
//                    throw new ConstraintViolationException(violations);
//                }
//            }
//        }

        /* 校验收汇情况 */
        LoginUser userInfo = FilterContextHandler.getUserInfo();
        List<Integer> roleCodes23 = userInfo.getRoleCodes();
        if (!roleCodes23.contains(CommonConstant.FOREIGN_FINANCE)
                && xqRemittanceDetailService.lambdaQuery()
                .eq(XqRemittanceDetail::getOrderId, dto.getId()).count() < 1) {
            dto.setOrderRemiVOS(Collections.emptyList());
        }
        List<AddOrderRemiDTO> orderRemiVOS = dto.getOrderRemiVOS();
        if (roleCodes23.contains(CommonConstant.FOREIGN_FINANCE) && orderRemiVOS != null && orderRemiVOS.size() > 0) {
            for (AddOrderRemiDTO orderProdVO : orderRemiVOS) {
                if (orderProdVO.getRemittanceDate() != null) {
                    Set<ConstraintViolation<AddOrderRemiDTO>> violations = validator.validate(orderProdVO);
                    if (!violations.isEmpty()) {
                        throw new ConstraintViolationException(violations);
                    }
                }
            }
        }

        /* 校验原料信息 */
        List<AddOrderRawDTO> orderRawVOS = dto.getOrderRawVOS();
        if (roleCodes23.contains(CommonConstant.ACCESSORY_PURCHASE) && orderRawVOS != null && orderRawVOS.size() > 0) {
            for (AddOrderRawDTO orderProdVO : orderRawVOS) {
                if (orderProdVO.getWeight() != null) {
                    Set<ConstraintViolation<AddOrderRawDTO>> violations = validator.validate(orderProdVO);
                    if (!violations.isEmpty()) {
                        throw new ConstraintViolationException(violations);
                    }
                }
            }
        }

        /* 校验辅料信息 */
        List<Integer> roleCodes22 = userInfo.getRoleCodes();
        if (!roleCodes22.contains(CommonConstant.ACCESSORY_PURCHASE)
                && !roleCodes22.contains(CommonConstant.DOMESTIC_FINANCE)
                && xqAccessoriesPurchaseService.lambdaQuery()
                .eq(XqAccessoriesPurchase::getOrderId, dto.getId()).count() < 1) {

            dto.setOrderAcsrVOS(Collections.emptyList());
        }
        List<AddOrderAcsrDTO> orderAcsrVOS = dto.getOrderAcsrVOS();
        if (roleCodes22.contains(CommonConstant.ACCESSORY_PURCHASE)
                && roleCodes22.contains(CommonConstant.DOMESTIC_FINANCE) && orderAcsrVOS != null && orderAcsrVOS.size() > 0) {
            for (AddOrderAcsrDTO orderProdVO : orderAcsrVOS) {
                if (orderProdVO.getOrderQuantity() != null && orderProdVO.getOrderQuantity() > 0) {
                    Set<ConstraintViolation<AddOrderAcsrDTO>> violations = validator.validate(orderProdVO);
                    if (!violations.isEmpty()) {
                        throw new ConstraintViolationException(violations);
                    }
                }
            }
        }

        /* 校验收汇到期日 */
        List<AddOrderRemiExpDateDTO> remiExpDateVOS = dto.getRemiExpDateVOS();
        if (remiExpDateVOS != null && remiExpDateVOS.size() > 0) {
            for (AddOrderRemiExpDateDTO orderProdVO : remiExpDateVOS) {
                if (orderProdVO.getRemittanceExpireDate() != null) {
                    Set<ConstraintViolation<AddOrderRemiExpDateDTO>> violations = validator.validate(orderProdVO);
                    if (!violations.isEmpty()) {
                        throw new ConstraintViolationException(violations);
                    }
                }
            }
        }

        /* 校验文件信息 */
//        List<InstOrUpdtXqFilesDTO> orderFiles1 = dto.getOrderFiles1();
//        if (orderFiles1 != null && orderFiles1.size() > 0) {
//            for (InstOrUpdtXqFilesDTO orderProdVO : orderFiles1) {
//                Set<ConstraintViolation<InstOrUpdtXqFilesDTO>> violations = validator.validate(orderProdVO);
//                if (!violations.isEmpty()) {
//                    throw new ConstraintViolationException(violations);
//                }
//            }
//        }
//        List<InstOrUpdtXqFilesDTO> orderFiles2 = dto.getOrderFiles2();
//        if (orderFiles2 != null && orderFiles2.size() > 0) {
//            for (InstOrUpdtXqFilesDTO orderProdVO : orderFiles2) {
//                Set<ConstraintViolation<InstOrUpdtXqFilesDTO>> violations = validator.validate(orderProdVO);
//                if (!violations.isEmpty()) {
//                    throw new ConstraintViolationException(violations);
//                }
//            }
//        }
//        List<InstOrUpdtXqFilesDTO> orderFiles3 = dto.getOrderFiles3();
//        if (orderFiles3 != null && orderFiles3.size() > 0) {
//            for (InstOrUpdtXqFilesDTO orderProdVO : orderFiles3) {
//                Set<ConstraintViolation<InstOrUpdtXqFilesDTO>> violations = validator.validate(orderProdVO);
//                if (!violations.isEmpty()) {
//                    throw new ConstraintViolationException(violations);
//                }
//            }
//        }
//        List<InstOrUpdtXqFilesDTO> orderFiles4 = dto.getOrderFiles4();
//        if (orderFiles4 != null && orderFiles4.size() > 0) {
//            for (InstOrUpdtXqFilesDTO orderProdVO : orderFiles4) {
//                Set<ConstraintViolation<InstOrUpdtXqFilesDTO>> violations = validator.validate(orderProdVO);
//                if (!violations.isEmpty()) {
//                    throw new ConstraintViolationException(violations);
//                }
//            }
//        }
//        List<InstOrUpdtXqFilesDTO> orderFiles5 = dto.getOrderFiles5();
//        if (orderFiles5 != null && orderFiles5.size() > 0) {
//            for (InstOrUpdtXqFilesDTO orderProdVO : orderFiles5) {
//                Set<ConstraintViolation<InstOrUpdtXqFilesDTO>> violations = validator.validate(orderProdVO);
//                if (!violations.isEmpty()) {
//                    throw new ConstraintViolationException(violations);
//                }
//            }
//        }

        boolean b = xqOrderService.editAll(dto);

        /* websocket 推送消息 */
        if (1 == dto.getCompleteFlag()) {
            webSocket.sendCompleteMsg();
        }
        factory.close();


        // 更新单价
        AddOrderFretDTO addOrderFretDTO = dto.getOrderFretVOS().get(0);
        if (dto.getOrderType().equals("1") && addOrderFretDTO.getLoadingDate() != null) {
            xqSellingProfitService.updatePrice(dto.getId(), dto.getOrderNum());
        }

        // 异步调用 同步利润表数据
        xqSellingProfitService.synchroData(dto.getId());

        return b ? Result.OK("编辑成功！") : Result.OK("编辑失败！");
    }

    /**
     * 分页列表查询
     *
     * @param dto
     * @return
     */
    @AutoLog(value = "面单管理-分页列表查询")
    @ApiOperation(value = "面单管理-分页列表查询", notes = "面单管理-分页列表查询")
    @GetMapping(value = "/page")
    public Result<IPage<XqOrderVO>> queryPageList(QueryXqOrderDTO dto) {
        Page<XqOrderVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        IPage<XqOrderVO> pageList = xqOrderService.pageList(page, dto);
        return Result.OK(pageList);
    }

    /**
     * 跟单审核
     *
     * @param dto
     * @return
     */
    @AutoLog(value = "跟单审核")
    @ApiOperation(value = "跟单审核", notes = "跟单审核")
    @PostMapping(value = "/auditFollowerById")
    public Result<?> auditFollowerById(@RequestBody @Valid AuditDTO dto) {
        boolean b = xqOrderService.auditFollowerById(dto);
        webSocket.sendCompleteMsg();
        return b ? Result.OK("审核成功！") : Result.OK("审核失败！");
    }

    /**
     * 复审
     *
     * @param dto
     * @return
     */
    @AutoLog(value = "复审")
    @ApiOperation(value = "复审", notes = "复审")
    @PostMapping(value = "/auditRepeatById")
    public Result<?> auditFinanceById(@RequestBody @Valid AuditDTO dto) {
        boolean b = xqOrderService.auditRepeatById(dto);
        return b ? Result.OK("审核成功！") : Result.OK("审核失败！");
    }

    /**
     * 复制面单
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "复制面单")
    @ApiOperation(value = "复制面单", notes = "复制面单")
    @GetMapping(value = "/copyOrders")
    public Result<?> copyOrder(@RequestParam(name = "ids") String ids) {
        List<String> strings = Arrays.asList(ids.split(","));
        for (String string : strings) {
            boolean b = xqOrderService.copyOrder(string);
        }

        return Result.OK("复制成功！");
    }

    /**
     * 复制面单页面
     *
     * @param id
     * @return
     */
    @AutoLog(value = "复制面单页面")
    @ApiOperation(value = "复制面单页面", notes = "复制面单页面")
    @GetMapping(value = "/copyOrdersViews")
    public Result<XqOrderAllVO> copyOrdersViews(@RequestParam(name = "id") String id) {
        XqOrderAllVO xqOrder = xqOrderService.copyOrdersViews(id);
        return Result.OK(xqOrder);
    }

    /**
     * 查询完成情况接口
     */
    @AutoLog(value = "查询完成情况")
    @ApiOperation(value = "查询完成情况", notes = "查询完成情况")
    @GetMapping(value = "/queryCompleteState")
    public Result<?> queryCompleteState() {
        XqOrderCompleteMsgVO xqOrderCompleteMsgVO = xqOrderService.queryCompleteState();
        if (xqOrderCompleteMsgVO == null) {
            return Result.OK(Collections.EMPTY_MAP);
        }
        return Result.OK(xqOrderCompleteMsgVO);
    }


    /**
     * 查询完成情况接口
     */
    @AutoLog(value = "查询确认情况列表")
    @ApiOperation(value = "查询确认情况列表", notes = "查询确认情况列表", response = ListXqOrderFinallyConfirmVO.class)
    @GetMapping(value = "/listCompleteState")
    public Result<?> listCompleteState(@RequestParam("orderId") String orderId) {

        return Result.OK(xqOrderFinallyConfirmService.listCompleteState(orderId));
    }

    /**
     * 转让
     */
    @AutoLog(value = "转让")
    @ApiOperation(value = "转让", notes = "转让")
    @PostMapping(value = "/transferOrder")
    public Result<?> transferOrder(@Valid @RequestBody TransferDTO dto) {

        return xqOrderService.transferOrder(dto) ? Result.OK("转让成功") : Result.error("转让失败");
    }


    /**
     * 查询转让记录列表
     */
    @AutoLog(value = "查询转让记录列表")
    @ApiOperation(value = "查询转让记录列表", notes = "查询转让记录列表", response = ListXqOrderTransferRecordVO.class)
    @GetMapping(value = "/listTransferRecord")
    public Result<?> listTransferRecord(@RequestParam("orderId") String orderId) {

        return Result.OK(xqOrderTransferRecordService.listTransferRecord(orderId));
    }

    /**
     * 查询所有跟单员
     */
    @AutoLog(value = "查询所有跟单员(已排除自己)")
    @ApiOperation(value = "查询所有跟单员(已排除自己)", notes = "查询所有跟单员(已排除自己)", response = IdAndNameVO.class)
    @GetMapping(value = "/listOrderFollower")
    public Result<?> listOrderFollower() {

        return Result.OK(xqOrderTransferRecordService.listOrderFollower());
    }

    /**
     * 查询付款方式列表
     */
    @AutoLog(value = "查询付款方式列表")
    @ApiOperation(value = "查询付款方式列表", notes = "查询付款方式列表", response = IdAndNameVO.class)
    @GetMapping(value = "/listPaymentMethod")
    public Result<?> listPaymentMethod() {

        return Result.OK(xqOrderPaymentMethodService.listPaymentMethod());
    }


    /**
     * 刷新仓库订单价格
     */
    @AutoLog(value = "刷新仓库订单价格")
    @ApiOperation(value = "刷新仓库订单价格", notes = "刷新仓库订单价格")
    @GetMapping(value = "/refreshOrderPrice")
    public Result<?> refreshOrderPrice() {
        List<XqOrder> list = xqOrderService.lambdaQuery().eq(XqOrder::getOrderType, "1").list();
        if (list != null && list.size() > 0) {
            for (XqOrder xqOrder : list) {
                XqFreightInfo freightInfo = xqFreightInfoService
                        .lambdaQuery()
                        .eq(XqFreightInfo::getOrderId, xqOrder.getId())
                        .last("limit 1").one();
                if (freightInfo != null && freightInfo.getLoadingDate() != null) {
                    // 更新单价
                    xqSellingProfitService.updatePrice(xqOrder.getId(), xqOrder.getOrderNum());
                }
            }


        }
        return Result.OK();

    }

    /**
     * 查询客户地址列表
     */
    @AutoLog(value = "查询客户地址列表")
    @ApiOperation(value = "查询客户地址列表", notes = "查询客户地址列表")
    @GetMapping(value = "/listDeliveryAddress")
    public Result<?> listDeliveryAddress(@RequestParam("customerId") String customerId) {

        return xqOrderService.listDeliveryAddress(customerId);

    }

    @AutoLog(value = "查询客户地址列表")
    @ApiOperation(value = "查询客户地址列表", notes = "查询客户地址列表")
    @GetMapping(value = "/listDeliveryAddressByWarehouseId")
    public Result<?> listDeliveryAddressByWarehouseId(@RequestParam("warehouseId") String warehouseId) {
        return xqOrderService.listDeliveryAddressByWarehouseId(warehouseId);

    }


    /**
     * 设置不再提醒
     */
    @AutoLog(value = "设置不再提醒")
    @ApiOperation(value = "设置不再提醒", notes = "设置不再提醒")
    @GetMapping(value = "/doNotRemind")
    public Result<?> doNotRemind(@RequestParam("orderId") String orderId) {

        xqOrderService.doNotRemind(orderId);
        return Result.OK();

    }

    /**
     * 设置不再提醒
     */
    @AutoLog(value = "设置不再提醒")
    @ApiOperation(value = "设置不再提醒", notes = "设置不再提醒")
    @GetMapping(value = "/doNotRemindPayment")
    public Result<?> doNotRemindPayment(@RequestParam("orderId") String orderId) {

        xqOrderService.doNotRemindPayment(orderId);
        return Result.OK();

    }


    /**
     * 面单
     */
    @AutoLog(value = "面单总览")
    @ApiOperation(value = "面单总览", notes = "所见即所得", response = ShowOrderVO.class)
    @GetMapping(value = "/showOrder")
    public Result<?> showOrder(@RequestParam("orderId") String orderId) {


        return Result.OK(xqOrderService.showOrder(orderId));

    }

    /**
     * 面单
     */
    @AutoLog(value = "副总取消小红点")
    @ApiOperation(value = "副总取消小红点", notes = "副总取消小红点")
    @GetMapping(value = "/cancelOrderStatus")
    public Result<?> cancelOrderStatus(@RequestParam("orderNum") String orderNum) {

        return xqOrderService.cancelOrderStatus(orderNum) ? Result.OK() : Result.error("操作失败！");

    }

//    /**
//     * 收汇到期日小于10天的列表查询
//     */
//    @AutoLog(value = "收汇到期日小于10天的列表查询")
//    @ApiOperation(value = "收汇到期日小于10天的列表查询", notes = "收汇到期日小于10天的列表查询",response = QueryDataNotInDetailVO.class)
//    @GetMapping(value = "/queryRemittanceLessThanTenDays")
//    public Result<?> queryRemittanceLessThanTenDays() {
//
//        return Result.OK(xqOrderService.queryRemittanceLessThanTenDays());
//
//    }


    @AutoLog("导出excel")
    @ApiOperation(value = "导出EXCEL", httpMethod = "GET")
    @GetMapping(value = "/exportInvoiceExcel", produces = "application/octet-stream")
    public void exportInvoiceExcel(@RequestParam("orderId") String orderId, HttpServletRequest request, HttpServletResponse response) throws Exception {

        xqOrderService.orderExportVO(orderId, request, response);
    }


    @Autowired
    private XqProblemNoteService xqProblemNoteService;

    @AutoLog(value = "面单-特殊情况说明列表")
    @ApiOperation(value = "面单-特殊情况说明列表", notes = "面单-特殊情况说明列表")
    @GetMapping(value = "/getProblemNoteList")
    public Result<List<ProblemNoteVO>> getProblemNoteList(@RequestParam("orderId") String orderId) {
        List<XqProblemNote> list = xqProblemNoteService.lambdaQuery()
                .eq(XqProblemNote::getOrderId, orderId)
                .eq(XqProblemNote::getModuleType, "1")
                .orderByAsc(XqProblemNote::getCreateTime)
                .list();

        List<ProblemNoteVO> listVo = new ArrayList<>();

        if (list != null && list.size() > 0) {
            for (XqProblemNote xqProblemNote : list) {
                ProblemNoteVO vo = new ProblemNoteVO();
                vo.setNoteId(xqProblemNote.getId());
                vo.setNote(xqProblemNote.getNote());
                listVo.add(vo);
            }
        }


        return Result.OK(listVo);
    }

    @AutoLog(value = "特殊情况说明-编辑")
    @ApiOperation(value = "特殊情况说明-编辑", notes = "特殊情况说明-编辑")
    @PostMapping(value = "/editProblemNote")
    public Result<?> editProblemNote(@RequestBody ProblemNoteVO dto) {
        boolean b = false;

        XqProblemNote problemNote = xqProblemNoteService.getById(dto.getNoteId());

        if (StringUtils.isBlank(dto.getNote())) {
            return Result.OK();
        }

        if (problemNote == null) {
            XqProblemNote note = new XqProblemNote();
            note.setNote(dto.getNote());
            note.setModuleType("1");
            note.setIzDelete(0);
            note.setOrderId(dto.getOrderId());
            b = xqProblemNoteService.save(note);
        } else {
            if (StringUtils.isNotBlank(dto.getNote()) && !problemNote.getNote().equals(dto.getNote())) {
                problemNote.setNote(dto.getNote());
                b = xqProblemNoteService.updateById(problemNote);
            }
        }

        return b ? Result.OK("操作成功!") : Result.OK("操作失败!");
    }


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "特殊情况说明-批量删除")
    @ApiOperation(value = "特殊情况说明-批量删除", notes = "特殊情况说明-批量删除")
    @GetMapping(value = "/deleteBatchNoteIds")
    public Result<?> deleteBatchNoteIds(@RequestParam(name = "ids") String ids) {
        this.xqProblemNoteService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("删除成功！");
    }
}
