package com.interesting.modules.order.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.business.system.entity.SysCategory;
import com.interesting.business.system.entity.SysRole;
import com.interesting.business.system.entity.SysUserRole;
import com.interesting.business.system.service.ISysCategoryService;
import com.interesting.business.system.service.ISysUserRoleService;
import com.interesting.common.api.vo.Result;
import com.interesting.common.constant.CommonConstant;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.system.vo.LoginUser;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.CommonUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.common.util.oConvertUtils;
import com.interesting.config.FilterContextHandler;
import com.interesting.modules.accmaterial.entity.XqAccessoriesPurchase;
import com.interesting.modules.accmaterial.entity.XqInventory;
import com.interesting.modules.accmaterial.service.IXqAccessoriesPurchaseService;
import com.interesting.modules.accmaterial.service.XqInventoryService;
import com.interesting.modules.accmaterial.vo.AccInventoryDetailsVO;
import com.interesting.modules.commission.entity.XqCommissionDistribution;
import com.interesting.modules.commission.entity.XqCommissionHistory;
import com.interesting.modules.commission.entity.XqOrderCommission;
import com.interesting.modules.commission.service.IXqOrderCommissionService;
import com.interesting.modules.commission.service.XqCommissionDistributionService;
import com.interesting.modules.commission.service.XqCommissionHistoryService;
import com.interesting.modules.commission.vo.XqOrderCommissionVO;
import com.interesting.modules.commissioncompany.entity.XqCommissionCompany;
import com.interesting.modules.commissioncompany.service.XqCommissionCompanyService;
import com.interesting.modules.customer.entity.XqCustomer;
import com.interesting.modules.customer.service.IXqCustomerService;
import com.interesting.modules.files.dto.InstOrUpdtXqFilesDTO;
import com.interesting.modules.files.entity.XqFiles;
import com.interesting.modules.files.service.XqFilesService;
import com.interesting.modules.freightInfo.entity.XqFreightInfo;
import com.interesting.modules.freightInfo.service.IXqFreightInfoService;
import com.interesting.modules.freightInfo.service.XqFreightNoteService;
import com.interesting.modules.freightInfo.vo.XqOrderFreightNoteVO;
import com.interesting.modules.freightcost.entity.XqFreightCostInfo;
import com.interesting.modules.freightcost.service.IXqFreightCostInfoService;
import com.interesting.modules.insurance.entity.XqInsuranceExpenses;
import com.interesting.modules.insurance.service.IXqInsuranceExpensesService;
import com.interesting.modules.notes.dto.InstOrUpdtNotesDTO;
import com.interesting.modules.notes.entity.XqProblemNote;
import com.interesting.modules.notes.service.XqProblemNoteService;
import com.interesting.modules.notes.vo.XqProblemNoteVO;
import com.interesting.modules.order.dto.*;
import com.interesting.modules.order.dto.sub.*;
import com.interesting.modules.order.entity.XqOrder;
import com.interesting.modules.order.entity.XqOrderExtraCost;
import com.interesting.modules.order.entity.XqOrderFinallyConfirm;
import com.interesting.modules.order.entity.XqOrderTransferRecord;
import com.interesting.modules.order.mapper.XqOrderFinallyConfirmMapper;
import com.interesting.modules.order.mapper.XqOrderMapper;
import com.interesting.modules.order.service.IXqOrderExtraCostService;
import com.interesting.modules.order.service.IXqOrderFinallyConfirmService;
import com.interesting.modules.order.service.IXqOrderService;
import com.interesting.modules.order.service.IXqOrderTransferRecordService;
import com.interesting.modules.order.vo.*;
import com.interesting.modules.order.vo.sub.*;
import com.interesting.modules.orderconfig.entity.XqOrderConfig;
import com.interesting.modules.orderconfig.service.IXqOrderConfigService;
import com.interesting.modules.orderdetail.entity.XqOrderDetail;
import com.interesting.modules.orderdetail.service.IXqOrderDetailService;
import com.interesting.modules.orderdetail.vo.XqCommissionOrderVO;
import com.interesting.modules.overseas.dto.InstUptExitStorageDetailItemDTO;
import com.interesting.modules.overseas.dto.QueryPageEnterStorageDetailDTO;
import com.interesting.modules.overseas.dto.QueryPageExitStorageDetailDTO;
import com.interesting.modules.overseas.mapper.XqOverseasEnterHeadMapper;
import com.interesting.modules.overseas.mapper.XqOverseasExitHeadMapper;
import com.interesting.modules.overseas.service.XqOverseasExitDetailService;
import com.interesting.modules.overseas.service.XqOverseasExitHeadService;
import com.interesting.modules.overseas.vo.EnterStorageDetailPageVO;
import com.interesting.modules.overseas.vo.ExitStorageDetailPageVO;
import com.interesting.modules.paymentmethod.entity.XqOrderPaymentMethod;
import com.interesting.modules.paymentmethod.service.IXqOrderPaymentMethodService;
import com.interesting.modules.product.entity.XqProductCategory;
import com.interesting.modules.product.entity.XqProductInfo;
import com.interesting.modules.product.service.XqProductCategoryService;
import com.interesting.modules.product.service.XqProductInfoService;
import com.interesting.modules.rawmaterial.dto.AddWithholdDTO;
import com.interesting.modules.rawmaterial.entity.XqRawMaterialPurchase;
import com.interesting.modules.rawmaterial.entity.XqWithholdDetail;
import com.interesting.modules.rawmaterial.service.IXqRawMaterialPurchaseService;
import com.interesting.modules.rawmaterial.service.XqWithholdDetailService;
import com.interesting.modules.rawmaterial.vo.XqCutAmountDetailVO;
import com.interesting.modules.rawmaterial.vo.XqPaymentDetailVO;
import com.interesting.modules.remittance.entity.XqCreditsInsurance;
import com.interesting.modules.remittance.entity.XqRemittanceDetail;
import com.interesting.modules.remittance.entity.XqRemittanceExpire;
import com.interesting.modules.remittance.service.IXqRemittanceDetailService;
import com.interesting.modules.remittance.service.XqCreditsInsuranceService;
import com.interesting.modules.remittance.service.XqRemittanceExpireService;
import com.interesting.modules.reportform.dto.QueryPageSellProfitDTO;
import com.interesting.modules.reportform.mapper.XqSellingProfitMapper;
import com.interesting.modules.reportform.vo.FinanceTemplateVO;
import com.interesting.modules.reportform.vo.SellProfitPageVO;
import com.interesting.modules.reportform.vo.SellProfitPageVO1;
import com.interesting.modules.reportform.vo.SellProfitTotalVO;
import com.interesting.modules.truck.service.XqTruckInfoService;
import com.interesting.modules.truck.vo.XqTruckInfoVO;
import com.interesting.modules.util.LowCaseUtils;
import com.interesting.modules.util.MoneyUtils;
import com.interesting.modules.warehouse.entity.XqWarehouse;
import com.interesting.modules.warehouse.service.XqWarehouseService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: 订单表
 * @Author: interesting-boot
 * @Date: 2023-06-01
 * @Version: V1.0
 */
@Service
public class XqOrderServiceImpl extends ServiceImpl<XqOrderMapper, XqOrder> implements IXqOrderService {
    @Autowired
    private IXqOrderDetailService xqOrderDetailService;
    @Autowired
    private IXqOrderCommissionService xqOrderCommissionService;
    @Autowired
    private IXqRemittanceDetailService xqRemittanceDetailService;
    @Autowired
    private IXqRawMaterialPurchaseService xqRawMaterialPurchaseService;
    @Autowired
    private IXqAccessoriesPurchaseService xqAccessoriesPurchaseService;
    @Autowired
    private IXqFreightInfoService xqFreightInfoService;
    @Autowired
    private IXqFreightCostInfoService xqFreightCostInfoService;
    @Autowired
    private IXqInsuranceExpensesService xqInsuranceExpensesService;
    @Autowired
    private XqProductInfoService xqProductInfoService;
    @Autowired
    private XqCommissionCompanyService xqCommissionCompanyService;
    @Autowired
    private XqFilesService xqFilesService;
    @Autowired
    private IXqCustomerService xqCustomerService;
    @Autowired
    private XqProblemNoteService xqProblemNoteService;
    @Autowired
    private XqInventoryService xqInventoryService;
    @Autowired
    private ISysCategoryService sysCategoryService;
    @Autowired
    private XqCommissionHistoryService xqCommissionHistoryService;
    @Autowired
    private XqTruckInfoService xqTruckInfoService;
    @Autowired
    private XqRemittanceExpireService xqRemittanceExpireService;
    @Autowired
    private XqCreditsInsuranceService xqCreditsInsuranceService;
    @Resource
    private IXqOrderFinallyConfirmService xqOrderFinallyConfirmService;
    @Resource
    private IXqOrderTransferRecordService xqOrderTransferRecordService;
    @Resource
    private IXqOrderPaymentMethodService xqOrderPaymentMethodService;
    @Resource
    private ISysUserRoleService sysUserRoleService;
    @Resource
    private XqWarehouseService xqWarehouseService;
    @Resource
    private IXqOrderExtraCostService xqOrderExtraCostService;
    @Resource
    private XqFreightNoteService xqFreightNoteService;

    @Resource
    private XqProductCategoryService xqProductCategoryService;
    @Resource
    private XqOverseasEnterHeadMapper xqOverseasEnterHeadMapper;
    @Resource
    private XqOverseasExitHeadMapper xqOverseasExitHeadMapper;

    @Resource
    private XqCommissionDistributionService xqCommissionDistributionService;

    @Value(value = "${jeecg.oss.endpoint}")
    private String ossPath;
    @Resource
    private XqOverseasExitDetailService xqOverseasExitDetailService;
    @Resource
    private XqOverseasExitHeadService xqOverseasExitHeadService;
    @Autowired
    private XqWithholdDetailService xqWithholdDetailService;
    @Autowired
    private IXqOrderConfigService xqOrderConfigService;

    @Override
    public IPage<XqOrderVO> pageList(Page<XqOrderVO> page, QueryXqOrderDTO dto) {
        String sortOrder = LowCaseUtils.camelToUnderscore(dto.getColumn()) + " " + dto.getOrder();
        IPage<XqOrderVO> xqOrderVOIPage = this.baseMapper.pageList(page, dto, sortOrder);
        String userId = FilterContextHandler.getUserId();
        LoginUser userInfo = FilterContextHandler.getUserInfo();
        List<Integer> roleCodes = userInfo.getRoleCodes();
        // 判断当前用户是不是跟单员
        boolean izOrderFollower = roleCodes.contains(1);

        xqOrderVOIPage.setRecords(xqOrderVOIPage.getRecords().stream().peek(xqOrderVO -> {
            // 编辑按钮
            if (izOrderFollower && roleCodes.size() == 1) {
                if (userId.equals(xqOrderVO.getTransferBy()) || "1463691138900930562".equals(userId)) {
                    xqOrderVO.setHasEditButton(1);
                    xqOrderVO.setHasTransferButton(1);
                } else {
                    xqOrderVO.setHasEditButton(0);
                    xqOrderVO.setHasTransferButton(0);
                }

            } else {
                if (userId.equals(xqOrderVO.getTransferBy()) || "1463691138900930562".equals(userId)) {
                    xqOrderVO.setHasEditButton(1);
                    xqOrderVO.setHasTransferButton(1);
                } else {
                    xqOrderVO.setHasEditButton(1);
                    xqOrderVO.setHasTransferButton(0);
                }
            }
        }).collect(Collectors.toList()));


        return xqOrderVOIPage;
    }


    @Override
    public XqOrderAllVO queryAllById(String id) {
        XqOrderAllVO vo = this.baseMapper.getOrderHead(id);
        if (vo == null) throw new InterestingBootException("查询失败，该记录不存在");
        String orderFollowUpPerson = vo.getOrderFollowUpPerson();
        if (StringUtils.isBlank(orderFollowUpPerson)) {
            vo.setOrderFollowUpPerson(FilterContextHandler.getUserInfo().getRealname());
        }
        String warehouseId = vo.getWarehouseId();
        // 并行执行接口调用
        XqWarehouse xqWarehouse = CompletableFuture.supplyAsync(() -> xqWarehouseService.getById(warehouseId)).join();
        if (xqWarehouse != null) {
            vo.setWarehouseName(xqWarehouse.getName());
        }
        List<XqOrderRemiExpDateVO> remiExpDateVOS = CompletableFuture.supplyAsync(() -> this.baseMapper.listRemiExpDateVOS(id)).join();
        vo.setRemiExpDateVOS(CollectionUtil.isNotEmpty(remiExpDateVOS) ? remiExpDateVOS : Collections.emptyList());
        vo.setOrderProdVOS(this.baseMapper.listOrderProds(id));
        vo.setXqOrderExtraCostDTOS(this.baseMapper.listOrderExtraCosts(id));
        List<XqOrderComsVO> xqOrderComsVOS = CompletableFuture.supplyAsync(() -> this.baseMapper.listOrderComs(id)).join();
        if (xqOrderComsVOS != null && !xqOrderComsVOS.isEmpty()) {
            xqOrderComsVOS.parallelStream().forEach(i -> {
                List<XqCommissionOrderVO> commissionVOS = this.baseMapper.listOrderComsDetail(i.getId());
                BigDecimal total = commissionVOS != null && !commissionVOS.isEmpty()
                        ? commissionVOS.parallelStream()
                        .map(XqCommissionOrderVO::getCommissionPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        : BigDecimal.ZERO;
                if (commissionVOS != null && !commissionVOS.isEmpty()) {
                    String s = JSON.toJSONStringWithDateFormat(commissionVOS, "yyyy-MM-dd",
                            SerializerFeature.WriteDateUseDateFormat);
                    i.setCommissionDetailsStr(s);
                }
                commissionVOS.forEach(j -> {
                    j.setCommissionPrice(BigDecimal.ZERO);
                });
                i.setCommissionVOList(commissionVOS);
                ;
            });

        }

        vo.setOrderComsVOS(xqOrderComsVOS != null && !xqOrderComsVOS.isEmpty() ? xqOrderComsVOS : Collections.emptyList());
        /* 收汇情况 */
        CompletableFuture<List<XqOrderRemiVO>> future = CompletableFuture.supplyAsync(() -> this.baseMapper.listOrderRemi(id));
        future.thenAccept(xqOrderRemiVOS -> {
            //去查下额外费用有没有
            //  List<XqOrderExtraCost> list=xqOrderExtraCostService.list().
            vo.setOrderRemiVOS(xqOrderRemiVOS != null && !xqOrderRemiVOS.isEmpty() ? xqOrderRemiVOS : Collections.emptyList());
        });
        //信保费用
        vo.setOrderCreditsInsuranceVOS(this.baseMapper.listOrderCreditsInsuranceVOS(id));

        //原料采购
        List<XqOrderRawVO> xqOrderRawVOS = this.baseMapper.listOrderRaw(id);
        //原料采购财务模块
        // List<XqOrderRawFinanceVO> xqOrderRawFinanceVOS = this.baseMapper.listOrderRawFinance(id);

        if (xqOrderRawVOS != null && !xqOrderRawVOS.isEmpty()) {
            xqOrderRawVOS.parallelStream().forEach(i -> {
                List<XqPaymentDetailVO> paymentDetailVOS = this.baseMapper.listPaymentDetails(i.getId());
                BigDecimal total = paymentDetailVOS != null && !paymentDetailVOS.isEmpty()
                        ? paymentDetailVOS.parallelStream()
                        .map(XqPaymentDetailVO::getPayMoney)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        : BigDecimal.ZERO;
                i.setPaymentAmount(total);
                i.setPaymentDetails(paymentDetailVOS);
                if (paymentDetailVOS != null && !paymentDetailVOS.isEmpty()) {
                    String s = JSON.toJSONStringWithDateFormat(paymentDetailVOS, "yyyy-MM-dd",
                            SerializerFeature.WriteDateUseDateFormat);
                    i.setPaymentDetailsStr(s);
                }
            });

            xqOrderRawVOS.parallelStream().forEach(i -> {
                List<XqCutAmountDetailVO> cutAmountDetailVOS = this.baseMapper.listCutAmountDetails(i.getId());
                BigDecimal total = cutAmountDetailVOS != null && !cutAmountDetailVOS.isEmpty()
                        ? cutAmountDetailVOS.parallelStream()
                        .map(XqCutAmountDetailVO::getCutAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        : BigDecimal.ZERO;
                i.setCutAmount(total);
                i.setCutDetails(cutAmountDetailVOS);
                if (cutAmountDetailVOS != null && !cutAmountDetailVOS.isEmpty()) {
                    String s = JSON.toJSONStringWithDateFormat(cutAmountDetailVOS, "yyyy-MM-dd",
                            SerializerFeature.WriteDateUseDateFormat);
                    i.setCutDetailsStr(s);
                }

                // 预扣款信息
                List<XqWithholdDetail> list = xqWithholdDetailService
                        .lambdaQuery()
                        .eq(XqWithholdDetail::getSourceId, i.getId())
                        .orderByAsc(XqWithholdDetail::getCreateBy)
                        .list();

                List<AddWithholdDTO> withholdDTOS = new ArrayList<>();
                BigDecimal withholdTotal = BigDecimal.ZERO;

                if (list != null && list.size() > 0) {
                    for (XqWithholdDetail xqWithholdDetail : list) {
                        withholdTotal = withholdTotal.add(xqWithholdDetail.getMoney());
                        AddWithholdDTO addWithholdDTO = new AddWithholdDTO();
                        BeanUtils.copyProperties(xqWithholdDetail, addWithholdDTO);
                        withholdDTOS.add(addWithholdDTO);
                    }
                }

                i.setWithholdAmount(withholdTotal);
                i.setWithholdDTOS(withholdDTOS);
                if (withholdDTOS != null && !withholdDTOS.isEmpty()) {
                    String s = JSON.toJSONStringWithDateFormat(withholdDTOS, "yyyy-MM-dd",
                            SerializerFeature.WriteDateUseDateFormat);
                    i.setWithholdStr(s);
                }
            });


            vo.setOrderRawVOS(xqOrderRawVOS);
        } else {
            /* 查询默认记录(跟随已填入的产品信息) 默认出来 */
            List<XqOrderProdVO> orderProdVOS = vo.getOrderProdVOS();
            if (orderProdVOS != null && !orderProdVOS.isEmpty()) {
                xqOrderRawVOS = orderProdVOS.parallelStream()
                        .map(orderProdVO -> {
                            XqOrderRawVO xqOrderRawVO = new XqOrderRawVO();
                            xqOrderRawVO.setProductName(orderProdVO.getProductName());
                            xqOrderRawVO.setProductId(orderProdVO.getProductId());
                            xqOrderRawVO.setUnitPrice(BigDecimal.ZERO);
                            xqOrderRawVO.setLayoutRequirements(orderProdVO.getLayoutRequirements());
                            return xqOrderRawVO;
                        })
                        .collect(Collectors.toList());
                vo.setOrderRawVOS(xqOrderRawVOS);
                // xqOrderRawFinanceVOS = orderProdVOS.parallelStream()
//                        .map(orderProdVO -> {
//                            XqOrderRawFinanceVO xqOrderRawVO = new XqOrderRawFinanceVO();
//                            xqOrderRawVO.setProductName(orderProdVO.getProductName());
//                            return xqOrderRawVO;
//                        })
//                        .collect(Collectors.toList());
//                vo.setOrderRawFinanceVOS(xqOrderRawFinanceVOS);
            } else {
                vo.setOrderRawVOS(Collections.emptyList());
                //          vo.setOrderRawFinanceVOS(Collections.emptyList());
            }
        }
        //辅料采购
        CompletableFuture<List<XqOrderAcsrVO>> xqOrderAcsrVOSFuture = CompletableFuture.supplyAsync(() -> this.baseMapper.listOrderAcsr(id));
        CompletableFuture<List<XqOrderRawVO>> xqOrderRawVOSFuture = CompletableFuture.supplyAsync(() -> this.baseMapper.listOrderRaw(id));
        CompletableFuture.allOf(xqOrderAcsrVOSFuture, xqOrderRawVOSFuture).join();
        List<XqOrderAcsrVO> xqOrderAcsrVOS = xqOrderAcsrVOSFuture.join();
        List<XqOrderRawVO> xqOrderRawVOS1 = xqOrderRawVOSFuture.join();

        if (xqOrderAcsrVOS != null && !xqOrderAcsrVOS.isEmpty()) {
            Map<String, XqOrderRawVO> map = xqOrderRawVOS1.stream().collect(Collectors.toMap(XqOrderRawVO::getProductId,
                    Function.identity(), (v1, v2) -> v1));

            xqOrderAcsrVOS.parallelStream().forEach(i -> {
                XqOrderRawVO xqOrderRawVO = map.get(i.getProductId());
                XqOrderDetail xqOrderDetail = xqOrderDetailService.getOne(new LambdaQueryWrapper<XqOrderDetail>()
                        .eq(XqOrderDetail::getProductId, i.getProductId())
                        .eq(XqOrderDetail::getOrderId, i.getOrderId())
                        .eq(XqOrderDetail::getLayoutRequirements, i.getLayoutRequirements())
                        .last("limit 1"));
                if (xqOrderRawVO != null) {
                    i.setCategoryId(xqOrderRawVO.getCategoryId());
                }
                i.setTotalBoxes(xqOrderDetail != null ? xqOrderDetail.getTotalBoxes() : 0);

                // --用料
                List<AccInventoryDetailsVO> useInventoryDetails = this.baseMapper.listUseOrderAcsrDetails(i.getId());
                useInventoryDetails.parallelStream().forEach(o -> {
                    // 设置库存数
                    if (StringUtils.isNotBlank(i.getAccessoryId())) {
                        Integer inventoryNum = xqInventoryService.getInventoryNum(o.getWarehouseId(),
                                i.getAccessoryId(), null, null);
                        o.setInventoryNum(inventoryNum);
                        // i.setSkuNum(inventoryNum);
                    }
                    // 全部转为正数
                    if (o.getNum() < 0) {
                        o.setNum(-o.getNum());
                    }
                });
                // to json
                String s1 = JSONObject.toJSONStringWithDateFormat(useInventoryDetails, "yyyy-MM-dd HH:mm:ss",
                        SerializerFeature.WriteDateUseDateFormat);
                i.setUseInventoryDetailsStr(s1);
                // 统计用料数量
                i.setUseNum(useInventoryDetails.stream().map(AccInventoryDetailsVO::getNum)
                        .reduce(0, Integer::sum));
                if (i.getUseNum() < 0) {
                    i.setUseNum(-i.getUseNum());
                }
                // --退料
                List<AccInventoryDetailsVO> backInventoryDetails = this.baseMapper.listBackOrderAcsrDetails(i.getId());
                // to json
                String s2 = JSONObject.toJSONStringWithDateFormat(backInventoryDetails, "yyyy-MM-dd HH:mm:ss",
                        SerializerFeature.WriteDateUseDateFormat);
                i.setBackInventoryDetailsStr(s2);
                // 统计退料数量
                i.setBackNum(backInventoryDetails.stream().map(AccInventoryDetailsVO::getNum)
                        .reduce(0, Integer::sum));

                // 设置库存数
                if (StringUtils.isNotBlank(i.getAccessoryId())) {
                    BigDecimal inventoryNum = xqInventoryService.getSkuNum(i.getAccessoryId());
                    i.setSkuNum(inventoryNum);
                }
            });
            vo.setOrderAcsrVOS(xqOrderAcsrVOS);
        } else {
            // 查询默认记录(跟随已填入的产品信息) 默认各个产品两条
            List<XqOrderProdVO> orderProdVOS = vo.getOrderProdVOS();
            List<XqOrderProdVO> newOrderProdVOS = new ArrayList<>();

            for (XqOrderProdVO xqOrderProdVO : orderProdVOS) {
                String[] packaging = xqOrderProdVO.getPackaging().split("\\|");
                if (packaging.length > 1) {
                    for (String str : packaging) {
                        XqOrderProdVO xqOrderProdVO1 = new XqOrderProdVO();
                        BeanUtils.copyProperties(xqOrderProdVO, xqOrderProdVO1);
                        xqOrderProdVO1.setPackaging(str);
                        newOrderProdVOS.add(xqOrderProdVO1);
                    }
                } else {
                    newOrderProdVOS.add(xqOrderProdVO);
                }
            }
            if (newOrderProdVOS != null && !newOrderProdVOS.isEmpty()) {
                xqOrderAcsrVOS = newOrderProdVOS.parallelStream().flatMap(orderProdVO -> {
                    XqOrderDetail xqOrderDetail = xqOrderDetailService.getOne(new LambdaQueryWrapper<XqOrderDetail>()
                            .eq(XqOrderDetail::getProductId, orderProdVO.getProductId())
                            .eq(XqOrderDetail::getLayoutRequirements, orderProdVO.getLayoutRequirements())
                            .eq(XqOrderDetail::getOrderId, orderProdVO.getOrderId())
                            .last("limit 1"));

                    XqOrderAcsrVO xqOrderAcsrVO = new XqOrderAcsrVO();
                    XqOrderAcsrVO xqOrderAcsrVO2 = new XqOrderAcsrVO();
                    if (xqOrderDetail != null) {
                        xqOrderAcsrVO.setTotalBoxes(xqOrderDetail.getTotalBoxes());
                    } else {
                        xqOrderAcsrVO.setTotalBoxes(0);
                    }
                    xqOrderAcsrVO.setProductId(orderProdVO.getProductId());
                    xqOrderAcsrVO.setProductName(orderProdVO.getProductName());
                    xqOrderAcsrVO.setPackaging(orderProdVO.getPackaging());
                    xqOrderAcsrVO.setCurrency("CNY");
                    xqOrderAcsrVO.setTaxRate(BigDecimal.ZERO);
                    xqOrderAcsrVO.setPackagingUnit(orderProdVO.getPackagingUnit());
                    xqOrderAcsrVO.setLayoutRequirements(orderProdVO.getLayoutRequirements());
                    xqOrderAcsrVO.setUnitPrice(BigDecimal.ZERO);
                    // 根据已填写的原料记录，set相应的categoryId
                    List<XqRawMaterialPurchase> list22 = xqRawMaterialPurchaseService.lambdaQuery()
                            .eq(XqRawMaterialPurchase::getOrderId, id).list();
                    Map<String, XqRawMaterialPurchase> collect1 = list22.parallelStream()
                            .collect(Collectors.toMap(XqRawMaterialPurchase::getProductId, Function.identity(),
                                    (k1, k2) -> k1));
                    XqRawMaterialPurchase xqRawMaterialPurchase = collect1.get(xqOrderAcsrVO.getProductId());
                    if (xqRawMaterialPurchase != null) {
                        xqOrderAcsrVO.setCategoryId(xqRawMaterialPurchase.getCategoryId());
                    }
                    BeanUtils.copyProperties(xqOrderAcsrVO, xqOrderAcsrVO2);
                    return Stream.of(xqOrderAcsrVO, xqOrderAcsrVO2);
                }).collect(Collectors.toList());

                vo.setOrderAcsrVOS(xqOrderAcsrVOS);
            } else {
                vo.setOrderAcsrVOS(Collections.emptyList());
            }
        }
//        /* 货运信息 */
        CompletableFuture<List<XqOrderFretVO>> xqOrderFretVOSFuture = CompletableFuture.supplyAsync(() -> this.baseMapper.listOrderFret(id));
        xqOrderFretVOSFuture.thenAccept(xqOrderFretVOS -> {
            if (xqOrderFretVOS != null && !xqOrderFretVOS.isEmpty()) {
                List<XqOrderRawVO> orderRawVOS1 = vo.getOrderRawVOS();
                if (orderRawVOS1 != null && !orderRawVOS1.isEmpty()) {
                    BigDecimal totalWeight = BigDecimal.ZERO;
                    for (XqOrderRawVO orderRawVO : orderRawVOS1) {
                        BigDecimal weight = orderRawVO.getWeight();
                        if (weight != null) {
                            totalWeight = totalWeight.add(weight);
                        }
                    }
                    xqOrderFretVOS.get(0).setNetWeight(totalWeight);
                    xqOrderFretVOS.get(0).setContainerTemperature("-20℃/关闭");
                }
            }
            vo.setOrderFretVOS(xqOrderFretVOS);
        });

        xqOrderFretVOSFuture.join();
        //货运费用 0919---zqy修改
        List<XqOrderRawVO> orderRawVOS = vo.getOrderRawVOS();
        List<XqOrderFretCostVO> xqOrderFretCostVOS = this.baseMapper.listOrderFretCostWithType(id, 1);
        if (xqOrderFretCostVOS != null && xqOrderFretCostVOS.size() > 0) {
            ArrayList<XqOrderFretCostVO> xqOrderInsuranceVOS1 = initFretCostVOs();
            if (xqOrderFretCostVOS.size() > 0) {
                for (XqOrderFretCostVO xqOrderFretCostVO : xqOrderFretCostVOS) {
                    String feeType = xqOrderFretCostVO.getFeeType();
                    for (XqOrderFretCostVO xqOrderFretCostVO99 : xqOrderInsuranceVOS1) {
                        String feeType1 = xqOrderFretCostVO99.getFeeType();
                        if (feeType.equals(feeType1)) {
                            BeanUtils.copyProperties(xqOrderFretCostVO, xqOrderFretCostVO99);
                            if (xqOrderFretCostVO99.getPrice() != null && xqOrderFretCostVO99.getPrice().compareTo(BigDecimal.ZERO) == 0) {
                                if (feeType.equals(CommonConstant.DOMESTIC_SHIPPING_FEE)) {
                                    xqOrderFretCostVO99.setCurrency("USD");
                                } else {
                                    xqOrderFretCostVO99.setCurrency("CNY");
                                }
                            }
                            if (xqOrderFretCostVO99.getFeeType().equals(CommonConstant.EXTRA_FEE_1)) {
                                xqOrderFretCostVO99.setFeeTypeTxt("国内额外费用1");
                            } else if (xqOrderFretCostVO99.getFeeType().equals(CommonConstant.EXTRA_FEE_2)) {
                                xqOrderFretCostVO99.setFeeTypeTxt("国内额外费用2");
                            } else if (xqOrderFretCostVO99.getFeeType().equals(CommonConstant.EXTRA_FEE_3)) {
                                xqOrderFretCostVO99.setFeeTypeTxt("国内额外费用3");
                            } else if (xqOrderFretCostVO99.getFeeType().equals(CommonConstant.EXTRA_FEE_4)) {
                                xqOrderFretCostVO99.setFeeTypeTxt("国内额外费用4");
                            }
                        }
                    }
                }
            }
//            List<XqOrderFretCostVO> supplierFretCostList = Collections.emptyList();
//            XqOrderRawVO xqOrderRawVO = orderRawVOS.get(0);
//            String supplierId = xqOrderRawVO.getSupplierId();
//            supplierFretCostList = baseMapper.listSupplierFretCost(supplierId);
//            if (supplierFretCostList.size() > 0) {
//                for (XqOrderFretCostVO xqOrderFretCostVO : supplierFretCostList) {
//                    String feeType = xqOrderFretCostVO.getFeeType();
//                    for (XqOrderFretCostVO xqOrderFretCostVO99 : xqOrderInsuranceVOS1) {
//                        String feeType1 = xqOrderFretCostVO99.getFeeType();
//                        if (feeType.equals(feeType1)&&xqOrderFretCostVO99.getPrice().compareTo(BigDecimal.ZERO)==0) {
//                            BeanUtils.copyProperties(xqOrderFretCostVO, xqOrderFretCostVO99);
//                        }
//                    }
//                }
//            }
            xqOrderInsuranceVOS1.forEach(i -> i.setIzDomestic(1));
            vo.setOrderFretCostVOS(xqOrderInsuranceVOS1);
        } else {
            boolean ewx = false;
            if (xqOrderRawVOS.size() > 0) {
                for (XqOrderRawVO vo1 : xqOrderRawVOS) {
                    if (StringUtils.isNotBlank(vo1.getPurchaseType())) {
                        if (vo1.getPurchaseType().equals("1")) {
                            ewx = true;
                        }
                    }
                }
            }
            ArrayList<XqOrderFretCostVO> xqOrderInsuranceVOS1 = initFretCostVOs();
            xqOrderInsuranceVOS1.forEach(i -> i.setIzDomestic(1));
            if (ewx) {
                // 根据供应商id查询默认国内货运信息
                List<XqOrderFretCostVO> supplierFretCostList = Collections.emptyList();
                if (orderRawVOS != null && orderRawVOS.size() > 0 && orderRawVOS.get(0) != null) {
                    XqOrderRawVO xqOrderRawVO = orderRawVOS.get(0);
                    String supplierId = xqOrderRawVO.getSupplierId();
                    supplierFretCostList = baseMapper.listSupplierFretCost(supplierId);
                    if (supplierFretCostList.size() > 0) {
                        for (XqOrderFretCostVO xqOrderFretCostVO : supplierFretCostList) {
                            String feeType = xqOrderFretCostVO.getFeeType();
                            for (XqOrderFretCostVO xqOrderFretCostVO99 : xqOrderInsuranceVOS1) {
                                String feeType1 = xqOrderFretCostVO99.getFeeType();
                                if (feeType.equals(feeType1)) {
                                    BeanUtils.copyProperties(xqOrderFretCostVO, xqOrderFretCostVO99);
                                    if (xqOrderFretCostVO99.getFeeType().equals(CommonConstant.EXTRA_FEE_1)) {
                                        xqOrderFretCostVO99.setFeeTypeTxt("国内额外费用1");
                                    } else if (xqOrderFretCostVO99.getFeeType().equals(CommonConstant.EXTRA_FEE_2)) {
                                        xqOrderFretCostVO99.setFeeTypeTxt("国内额外费用2");
                                    } else if (xqOrderFretCostVO99.getFeeType().equals(CommonConstant.EXTRA_FEE_3)) {
                                        xqOrderFretCostVO99.setFeeTypeTxt("国内额外费用3");
                                    } else if (xqOrderFretCostVO99.getFeeType().equals(CommonConstant.EXTRA_FEE_4)) {
                                        xqOrderFretCostVO99.setFeeTypeTxt("国内额外费用4");
                                    }
                                }
                            }
                        }
                    }
                }
            }
            vo.setOrderFretCostVOS(xqOrderInsuranceVOS1);
        }

        List<XqOrderFretCostVO> xqOrderFretCostVOS2 = this.baseMapper.listOrderFretCostWithType(id, 0);
        if (xqOrderFretCostVOS2 != null && xqOrderFretCostVOS2.size() > 0) {
            ArrayList<XqOrderFretCostVO> xqOrderInsuranceVOS1 = initFretCostForeignFee();

//            if (xqOrderFretCostVOS2.size() > 0) {
//                for (XqOrderFretCostVO xqOrderFretCostVO : xqOrderFretCostVOS2) {
//                    String feeType = xqOrderFretCostVO.getFeeType();
//                    for (XqOrderFretCostVO xqOrderFretCostVO99 : xqOrderInsuranceVOS1) {
//                        String feeType1 = xqOrderFretCostVO99.getFeeType();
//                        if (feeType.equals(feeType1)) {
//                            BeanUtils.copyProperties(xqOrderFretCostVO, xqOrderFretCostVO99);
//                        }
//                    }
//                }
//            }

            if (xqOrderFretCostVOS2.size() > 0) {
                for (XqOrderFretCostVO xqOrderFretCostVO : xqOrderFretCostVOS2) {
                    String feeType = xqOrderFretCostVO.getFeeType();
                    for (XqOrderFretCostVO xqOrderFretCostVO99 : xqOrderInsuranceVOS1) {
                        String feeType1 = xqOrderFretCostVO99.getFeeType();
                        if (feeType.equals(feeType1)) {
                            BeanUtils.copyProperties(xqOrderFretCostVO, xqOrderFretCostVO99);
                        } else if (CommonConstant.FOREIGN_TRUCK_FEE.equals(feeType1) && feeType.contains(feeType1)) {
                            String agent = xqOrderFretCostVO.getAgent();
                            String txt = xqOrderFretCostVO99.getFeeTypeTxt();
                            BeanUtils.copyProperties(xqOrderFretCostVO, xqOrderFretCostVO99);
                            xqOrderFretCostVO99.setAgent(agent);
                            xqOrderFretCostVO99.setFeeTypeTxt(txt);
                        }
                    }
                }
            }
            xqOrderInsuranceVOS1.forEach(i -> i.setIzDomestic(0));
            vo.setOrderFretCostVOS2(xqOrderInsuranceVOS1);
        } else {
            ArrayList<XqOrderFretCostVO> xqOrderInsuranceVOS1 = initFretCostForeignFee();
            xqOrderInsuranceVOS1.forEach(i -> i.setIzDomestic(0));
            // 根据客户id查询默认国外货运费用
            List<XqOrderFretCostVO> customerFretCost = Collections.emptyList();
            String customerId = vo.getCustomerId();
            if (StringUtils.isNotBlank(customerId)) {
                customerFretCost = baseMapper.listCustomerFretCost(customerId, null);
                if (customerFretCost.size() > 0) {
                    for (XqOrderFretCostVO xqOrderFretCostVO : customerFretCost) {
                        String feeType = xqOrderFretCostVO.getFeeType();
                        for (XqOrderFretCostVO xqOrderFretCostVO99 : xqOrderInsuranceVOS1) {
                            String feeType1 = xqOrderFretCostVO99.getFeeType();
                            if (feeType.equals(feeType1)) {
                                BeanUtils.copyProperties(xqOrderFretCostVO, xqOrderFretCostVO99);
                            }
                        }
                    }
                }
            }

            vo.setOrderFretCostVOS2(xqOrderInsuranceVOS1);
        }
        /* 退运费用 */
        CompletableFuture<List<XqOrderFretCostVO>> returnFeesFuture = CompletableFuture.supplyAsync(() -> this.baseMapper.listFretCostReturnFee(id));
        returnFeesFuture.thenAccept(returnFees -> {
            if (CollectionUtil.isNotEmpty(returnFees)) {
                vo.setOrderFretCostReturnFeeVOS(returnFees);
            } else {
                vo.setOrderFretCostReturnFeeVOS(Collections.emptyList());
            }
        });
        returnFeesFuture.join();
        CompletableFuture<List<XqOrderInsuranceVO>> xqOrderInsuranceVOSFuture = CompletableFuture.supplyAsync(() -> this.baseMapper.listOrderInsurance(id));
        xqOrderInsuranceVOSFuture.thenAccept(xqOrderInsuranceVOS -> {
            if (xqOrderInsuranceVOS != null && !xqOrderInsuranceVOS.isEmpty()) {
                vo.setOrderInsuranceVOS(xqOrderInsuranceVOS);
            } else {
                XqOrderInsuranceVO xqOrderInsuranceVO = new XqOrderInsuranceVO();
                ArrayList<XqOrderInsuranceVO> xqOrderInsuranceVOS1 = new ArrayList<>(1);
                xqOrderInsuranceVOS1.add(xqOrderInsuranceVO);
                vo.setOrderInsuranceVOS(xqOrderInsuranceVOS1);
            }
        });
        xqOrderInsuranceVOSFuture.join();

        vo.setOrderFreightNoteVOS(this.baseMapper.listOrderFreightNoteVOS(id));
        /* 问题说明查询 */
        List<XqProblemNoteVO> xqProblemNoteList = this.baseMapper.listProblemNotes(id);
        // group by module_type
        Map<String, List<XqProblemNoteVO>> collect1 =
                xqProblemNoteList.stream().collect(Collectors.groupingBy(XqProblemNoteVO::getModuleType));
        vo.setOrderNotes(collect1.get(CommonConstant.ORDER_MODULE) == null ?
                Collections.emptyList() : collect1.get(CommonConstant.ORDER_MODULE));
        vo.setRemiNotes(collect1.get(CommonConstant.REMITTANCE_MODULE) == null ?
                Collections.emptyList() : collect1.get(CommonConstant.REMITTANCE_MODULE));
        vo.setRawNotes(collect1.get(CommonConstant.RAW_MODULE) == null ?
                Collections.emptyList() : collect1.get(CommonConstant.RAW_MODULE));

        CompletableFuture<List<XqTruckInfoVO>> truckInfoListFuture = CompletableFuture.supplyAsync(() -> this.baseMapper.listTruckInfo(id));

        truckInfoListFuture.thenAccept(truckInfoList -> {
            if (!truckInfoList.isEmpty()) {
                vo.setTruckInfos(truckInfoList);
            } else {
                vo.setTruckInfos(Collections.emptyList());
            }
        });

        truckInfoListFuture.join();


        if (vo.getOrderType().equals("1")) {
            //去找国外出库费
            List<InstUptExitStorageDetailItemDTO> voList = xqOverseasExitHeadService.exitStorageDetailList(vo.getOrderNum());
            vo.setOverseasExitDTOS(voList);
        }
//        CompletableFuture<XqOrderAllVO> completableFuture = CompletableFuture.allOf(future, xqOrderAcsrVOSFuture, xqOrderRawVOSFuture, xqOrderFretVOSFuture, returnFeesFuture, truckInfoListFuture)
//                .thenApply((Void) -> {
//                    // 在这里将多个CompletableFuture的结果合并到vo对象中
//                    vo.setOrderRemiVOS(future.join());
//                    vo.setOrderAcsrVOS(xqOrderAcsrVOSFuture.join());
//                    vo.setOrderRawVOS(xqOrderRawVOSFuture.join());
//                    vo.setOrderFretVOS(xqOrderFretVOSFuture.join());
//                    vo.setOrderFretCostReturnFeeVOS(returnFeesFuture.join());
//                    vo.setTruckInfos(truckInfoListFuture.join());
//                    return vo;
//                });
        return vo;
    }

    private ArrayList<XqOrderFretCostVO> initFretCostForeignFee() {
        ArrayList<XqOrderFretCostVO> xqOrderInsuranceVOS1 = new ArrayList<>(10);
        XqOrderFretCostVO xqOrderFretCostVO1 = new XqOrderFretCostVO();
        xqOrderFretCostVO1.setFeeType(CommonConstant.FOREIGN_CUSTOMS_CLEARANCE_TAX_FEE);
        xqOrderFretCostVO1.setFeeTypeTxt("清关关税");
        xqOrderFretCostVO1.setCurrency("USD");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO1);
        XqOrderFretCostVO xqOrderFretCostVO2 = new XqOrderFretCostVO();
        xqOrderFretCostVO2.setFeeType(CommonConstant.FOREIGN_CUSTOMS_CLEARANCE_FEE);
        xqOrderFretCostVO2.setFeeTypeTxt("国外清关费");
        xqOrderFretCostVO2.setCurrency("USD");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO2);
        XqOrderFretCostVO xqOrderFretCostVO3 = new XqOrderFretCostVO();
        xqOrderFretCostVO3.setFeeType(CommonConstant.FOREIGN_TRUCK_FEE);
        xqOrderFretCostVO3.setFeeTypeTxt("国外卡车费");
        xqOrderFretCostVO3.setCurrency("USD");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO3);
//        XqOrderFretCostVO xqOrderFretCostVO4 = new XqOrderFretCostVO();
//        xqOrderFretCostVO4.setFeeType(CommonConstant.FOREIGN_CHECK_FEE);
//        xqOrderFretCostVO4.setFeeTypeTxt("国外查验费");
//        xqOrderFretCostVO4.setCurrency("USD");
//        xqOrderInsuranceVOS1.add(xqOrderFretCostVO4);
        XqOrderFretCostVO xqOrderFretCostVO5 = new XqOrderFretCostVO();
        xqOrderFretCostVO5.setFeeType(CommonConstant.EXTRA_FEE_1);
        xqOrderFretCostVO5.setFeeTypeTxt("额外费用1");
        xqOrderFretCostVO5.setCurrency("USD");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO5);
        XqOrderFretCostVO xqOrderFretCostVO6 = new XqOrderFretCostVO();
        xqOrderFretCostVO6.setFeeType(CommonConstant.EXTRA_FEE_2);
        xqOrderFretCostVO6.setFeeTypeTxt("额外费用2");
        xqOrderFretCostVO6.setCurrency("USD");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO6);
        XqOrderFretCostVO xqOrderFretCostVO7 = new XqOrderFretCostVO();
        xqOrderFretCostVO7.setFeeType(CommonConstant.EXTRA_FEE_3);
        xqOrderFretCostVO7.setFeeTypeTxt("额外费用3");
        xqOrderFretCostVO7.setCurrency("USD");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO7);
        XqOrderFretCostVO xqOrderFretCostVO8 = new XqOrderFretCostVO();
        xqOrderFretCostVO8.setFeeType(CommonConstant.EXTRA_FEE_4);
        xqOrderFretCostVO8.setFeeTypeTxt("额外费用4");
        xqOrderFretCostVO8.setCurrency("USD");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO8);
        XqOrderFretCostVO xqOrderFretCostVO9 = new XqOrderFretCostVO();
        xqOrderFretCostVO9.setFeeType(CommonConstant.EXTRA_FEE_5);
        xqOrderFretCostVO9.setFeeTypeTxt("额外费用5");
        xqOrderFretCostVO9.setCurrency("USD");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO9);
        XqOrderFretCostVO xqOrderFretCostVO10 = new XqOrderFretCostVO();
        xqOrderFretCostVO10.setFeeType(CommonConstant.EXTRA_FEE_6);
        xqOrderFretCostVO10.setFeeTypeTxt("额外费用6");
        xqOrderFretCostVO10.setCurrency("USD");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO10);

        return xqOrderInsuranceVOS1;
    }

    public ArrayList<XqOrderFretCostVO> initFretCostVOs() {
        ArrayList<XqOrderFretCostVO> xqOrderInsuranceVOS1 = new ArrayList<>(10);
        XqOrderFretCostVO xqOrderFretCostVO1 = new XqOrderFretCostVO();
        xqOrderFretCostVO1.setFeeType(CommonConstant.DOMESTIC_SHIPPING_FEE);
        xqOrderFretCostVO1.setFeeTypeTxt("国内海运费");
        xqOrderFretCostVO1.setCurrency("USD");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO1);
        XqOrderFretCostVO xqOrderFretCostVO2 = new XqOrderFretCostVO();
        xqOrderFretCostVO2.setFeeType(CommonConstant.DOMESTIC_INLAND_FEE);
        xqOrderFretCostVO2.setFeeTypeTxt("国内陆运费");
        xqOrderFretCostVO2.setCurrency("CNY");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO2);
        XqOrderFretCostVO xqOrderFretCostVO3 = new XqOrderFretCostVO();
        xqOrderFretCostVO3.setFeeType(CommonConstant.DOMESTIC_CUSTOMS_CLEARANCE_FEE);
        xqOrderFretCostVO3.setFeeTypeTxt("国内报关费");
        xqOrderFretCostVO3.setCurrency("CNY");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO3);
        XqOrderFretCostVO xqOrderFretCostVO4 = new XqOrderFretCostVO();
        xqOrderFretCostVO4.setFeeType(CommonConstant.DOMESTIC_MANIFEST_FEE);
        xqOrderFretCostVO4.setFeeTypeTxt("国内舱单费");
        xqOrderFretCostVO4.setCurrency("CNY");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO4);
        XqOrderFretCostVO xqOrderFretCostVO5 = new XqOrderFretCostVO();
        xqOrderFretCostVO5.setFeeType(CommonConstant.DOMESTIC_PORT_FEE);
        xqOrderFretCostVO5.setFeeTypeTxt("国内港杂费");
        xqOrderFretCostVO5.setCurrency("CNY");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO5);
        XqOrderFretCostVO xqOrderFretCostVO6 = new XqOrderFretCostVO();
        xqOrderFretCostVO6.setFeeType(CommonConstant.DOMESTIC_DELIVER_FEE);
        xqOrderFretCostVO6.setFeeTypeTxt("送货费");
        xqOrderFretCostVO6.setCurrency("CNY");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO6);
        XqOrderFretCostVO xqOrderFretCostVO7 = new XqOrderFretCostVO();
        xqOrderFretCostVO7.setFeeType(CommonConstant.EXTRA_FEE_1);
        xqOrderFretCostVO7.setFeeTypeTxt("国内额外费用1");
        xqOrderFretCostVO7.setCurrency("CNY");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO7);
        XqOrderFretCostVO xqOrderFretCostVO8 = new XqOrderFretCostVO();
        xqOrderFretCostVO8.setFeeType(CommonConstant.EXTRA_FEE_2);
        xqOrderFretCostVO8.setFeeTypeTxt("国内额外费用2");
        xqOrderFretCostVO8.setCurrency("CNY");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO8);
        XqOrderFretCostVO xqOrderFretCostVO9 = new XqOrderFretCostVO();
        xqOrderFretCostVO9.setFeeType(CommonConstant.EXTRA_FEE_3);
        xqOrderFretCostVO9.setFeeTypeTxt("国内额外费用3");
        xqOrderFretCostVO9.setCurrency("CNY");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO9);
        XqOrderFretCostVO xqOrderFretCostVO10 = new XqOrderFretCostVO();
        xqOrderFretCostVO10.setFeeType(CommonConstant.EXTRA_FEE_4);
        xqOrderFretCostVO10.setFeeTypeTxt("国内额外费用4");
        xqOrderFretCostVO10.setCurrency("CNY");
        xqOrderInsuranceVOS1.add(xqOrderFretCostVO10);
        return xqOrderInsuranceVOS1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addAll(AddXqOrderAllDTO dto) {
        /* 订单主表 */
        XqOrder xqOrder = new XqOrder();
        BeanUtils.copyProperties(dto, xqOrder);
        // 校验订单号
        if (this.lambdaQuery().eq(XqOrder::getOrderNum, dto.getOrderNum()).count() > 0) {
            throw new InterestingBootException("订单号已存在");
        }
        // 将当前登陆人id放入transfer_by
        String userId = FilterContextHandler.getUserId();
        xqOrder.setTransferBy(userId);

        this.save(xqOrder);

        List<SysRole> roleByUserId = sysUserRoleService.getRoleByUserId(userId);
        List<Integer> roleCodes = roleByUserId.stream().map(SysRole::getRoleCode).collect(Collectors.toList());


        // 获取订单新增后返回的id
        String orderId = xqOrder.getId();

        // 根据订单类型
        // 反刷仓库地址
        if (dto.getOrderType().equals("1")) {
            String warehouseId = dto.getWarehouseId();
            if (StringUtils.isBlank(warehouseId)) throw new InterestingBootException("新增失败，仓库不可为空");
            XqWarehouse xqWarehouse = xqWarehouseService.getById(warehouseId);
            if (xqWarehouse == null) throw new InterestingBootException("新增失败，仓库不存在");
            xqWarehouse.setAddress(dto.getDeliveryAddress());
            xqWarehouseService.updateById(xqWarehouse);

            // 根据客户id查询货运信息
            List<XqOrderFretCostVO> customerFretCostList = Collections.emptyList();
            customerFretCostList = baseMapper.listWarehouseFretCost(xqWarehouse.getId(), dto.getDeliveryAddressId());
            List<XqFreightCostInfo> infoList = new ArrayList<>();

            if (customerFretCostList != null && customerFretCostList.size() > 0) {
                for (XqOrderFretCostVO xqOrderFretCostVO : customerFretCostList) {
                    XqFreightCostInfo info = new XqFreightCostInfo();
                    BeanUtils.copyProperties(xqOrderFretCostVO, info);
                    if (StringUtils.isNotBlank(dto.getDeliveryAddressId())) {
//                            if (info.getId().equals(dto.getDeliveryAddressId())) {
//                            info.setPrice(dto.getGwkcf());
//                            }
                        if (info.getPrice() == null || info.getPrice().compareTo(BigDecimal.ZERO) == 0) {
                            info.setIzDefaultColor(2);
                        } else {
                            info.setIzDefaultColor(0);
                        }
                        info.setOrderId(orderId);
                        info.setId(null);
                        infoList.add(info);
                    }
                }
                xqFreightCostInfoService.saveBatch(infoList);
            }

        } else {
            // 客户名称
            String customerName = dto.getCustomer();
            // 客户信息
            XqCustomer ctm = xqCustomerService.lambdaQuery()
                    .eq(XqCustomer::getName, customerName)
                    .last("LIMIT 1").one();
            // 客户存在
            if (ctm != null) {
                xqOrder.setCustomerId(ctm.getId());

                // 客户存在，则反刷客户地址
                ctm.setAddress(dto.getDeliveryAddress());
                xqCustomerService.updateById(ctm);

                // 根据客户id查询货运信息
                List<XqOrderFretCostVO> customerFretCostList = Collections.emptyList();
                customerFretCostList = baseMapper.listCustomerFretCost(ctm.getId(), dto.getDeliveryAddressId());
                List<XqFreightCostInfo> infoList = new ArrayList<>();

                if (customerFretCostList != null && customerFretCostList.size() > 0) {
                    for (XqOrderFretCostVO xqOrderFretCostVO : customerFretCostList) {
                        XqFreightCostInfo info = new XqFreightCostInfo();
                        BeanUtils.copyProperties(xqOrderFretCostVO, info);
                        if (StringUtils.isNotBlank(dto.getDeliveryAddressId())) {
//                            if (info.getId().equals(dto.getDeliveryAddressId())) {
//                            info.setPrice(dto.getGwkcf());
//                            }
                            if (info.getPrice() == null || info.getPrice().compareTo(BigDecimal.ZERO) == 0) {
                                info.setIzDefaultColor(2);
                            } else {
                                info.setIzDefaultColor(0);
                            }
                            info.setOrderId(orderId);
                            info.setId(null);
                            infoList.add(info);
                        }
                    }
                    xqFreightCostInfoService.saveBatch(infoList);
                }


                // 客户不存在，则新增客户，并把地址放入
            } else {

                XqCustomer xqCustomer = new XqCustomer();
                xqCustomer.setName(customerName);
                xqCustomer.setAddress(dto.getDeliveryAddress());
                xqCustomerService.save(xqCustomer);
                // 在新增的订单的客户id中放入id,并更新订单
                xqOrder.setCustomerId(xqCustomer.getId());
                this.updateById(xqOrder);

                // 设置默认的货运费用
                ArrayList<XqFreightCostInfo> xqFreightCostInfos = new ArrayList<>(3);
                XqFreightCostInfo xqFreightCostInfo = new XqFreightCostInfo();
                xqFreightCostInfo.setFeeType(CommonConstant.EXTRA_FEE_1);
                xqFreightCostInfos.add(xqFreightCostInfo);
                XqFreightCostInfo xqFreightCostInfo2 = new XqFreightCostInfo();
                xqFreightCostInfo2.setFeeType(CommonConstant.FOREIGN_CUSTOMS_CLEARANCE_FEE);
                xqFreightCostInfos.add(xqFreightCostInfo2);
                XqFreightCostInfo xqFreightCostInfo3 = new XqFreightCostInfo();
                xqFreightCostInfo3.setFeeType(CommonConstant.FOREIGN_TRUCK_FEE);
                xqFreightCostInfos.add(xqFreightCostInfo3);

                xqFreightCostInfos.forEach(i -> {
                    i.setOrderId(orderId);
                    i.setIzDomestic(0);
                    i.setCustomerId(xqCustomer.getId());
                    i.setPrice(BigDecimal.ZERO);
                    i.setIzDefaultColor(2);
                });
                xqFreightCostInfoService.saveBatch(xqFreightCostInfos);

            }

        }
        //如果是客户订单，默认新增一条收汇情况和中保信投保
        if ("2".equals(dto.getOrderType())) {
            XqOrderConfig xqOrderConfig = xqOrderConfigService.getOne(new LambdaQueryWrapper<XqOrderConfig>().last("limit 1"));
            //销售总额
            BigDecimal salesAmount = dto.getOrderProdVOS().stream().map(AddOrderProdDTO::getSalesAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            //额外费用
            BigDecimal extraPrice = dto.getXqOrderExtraCostDTOS().stream().map(XqOrderExtraCostDTO::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            //佣金
            BigDecimal amount = dto.getOrderComsVOS().stream()
                    .filter(addOrderComsDTO -> "ddkc".equals(addOrderComsDTO.getCommissionType()))
                    .map(AddOrderComsDTO::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            //总的未收款
            BigDecimal totalOutstandingReceivables = salesAmount.add(extraPrice).subtract(amount);
            //保理利息
            BigDecimal factoringInterest = salesAmount
                    .multiply(new BigDecimal(xqOrderConfig.getDays()))
                    .multiply(xqOrderConfig.getFactoringInterestRate())
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            //未收款
            BigDecimal outstandingReceivables = totalOutstandingReceivables.subtract(factoringInterest);
            //未收款比例
            BigDecimal outstandingRemittanceRatio = outstandingReceivables.divide(totalOutstandingReceivables, 2, BigDecimal.ROUND_HALF_UP);

            XqRemittanceDetail xqRemittanceDetail = new XqRemittanceDetail();
            xqRemittanceDetail.setOrderId(orderId);
            xqRemittanceDetail.setFactoringInterest(factoringInterest);
            xqRemittanceDetail.setOutstandingReceivables(outstandingReceivables);
            xqRemittanceDetail.setOutstandingRemittanceRatio(outstandingRemittanceRatio);
            if (!CollectionUtils.isEmpty(dto.getOrderProdVOS())) {
                xqRemittanceDetail.setCurrency(dto.getOrderProdVOS().get(0).getCurrency());
            }
            xqRemittanceDetailService.save(xqRemittanceDetail);

            if (!CollectionUtils.isEmpty(dto.getOrderProdVOS()) && "USD".equals(dto.getOrderProdVOS().get(0).getCurrency())) {
                XqCreditsInsurance xqCreditsInsurance = new XqCreditsInsurance();
                xqCreditsInsurance.setOrderId(orderId);
                BigDecimal creditInsurancePremiumConvert = salesAmount.multiply(xqOrderConfig.getCreditInsuranceRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
                xqCreditsInsurance.setCreditInsurancePremiumConvert(creditInsurancePremiumConvert);
                xqCreditsInsuranceService.save(xqCreditsInsurance);
            }
        }


        // 额外收费表
        xqOrderExtraCostService.insert(dto.getXqOrderExtraCostDTOS(), orderId);

        // 判断是否最终确认
        if (dto.getCompleteFlag() != null && dto.getCompleteFlag() == 1) {
            // 判断当前用户的角色中有无需要最终确认的角色
            List<Integer> confirmList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
            // 几个确认的角色，几条记录
            ArrayList<XqOrderFinallyConfirm> xqOrderFinallyConfirms = new ArrayList<>();

            for (Integer role : roleCodes) {
                if (confirmList.contains(role)) {
                    XqOrderFinallyConfirm xqOrderFinallyConfirm = new XqOrderFinallyConfirm();
                    xqOrderFinallyConfirm.setOrderId(orderId);
                    xqOrderFinallyConfirm.setUserId(userId);
                    xqOrderFinallyConfirm.setRoleId(role.toString());
                    xqOrderFinallyConfirms.add(xqOrderFinallyConfirm);
                }
            }
            xqOrderFinallyConfirmService.saveBatch(xqOrderFinallyConfirms);

            List<String> collect = xqOrderFinallyConfirms.stream().map(XqOrderFinallyConfirm::getRoleId).collect(Collectors.toList());

            if (CollectionUtil.isNotEmpty(collect)) {
                StringBuilder completeState = new StringBuilder(xqOrder.getCompleteState() == null ? "" : xqOrder.getCompleteState());
                boolean change = false;
                for (String roleCode : collect) {
                    if (!completeState.toString().contains(roleCode)) {
                        completeState.append(roleCode).append(",");
                        change = true;
                    }
                }
                if (change) {
                    this.lambdaUpdate().set(XqOrder::getCompleteState, completeState.toString()).eq(XqOrder::getId, orderId).update();
                }

            }

        }

        /* 收汇到期日 */
        List<XqRemittanceExpire> addRemittanceExpires = new ArrayList<>();
//        List<AddOrderRemiExpDateDTO> remiExpDateVOS = dto.getRemiExpDateVOS();
//        if (CollectionUtil.isNotEmpty(remiExpDateVOS)) {
//            for (AddOrderRemiExpDateDTO remiExpDateVO : remiExpDateVOS) {
//                XqRemittanceExpire newCom = new XqRemittanceExpire();
//                BeanUtils.copyProperties(remiExpDateVO, newCom);
//                newCom.setId(null);
//                newCom.setOrderId(orderId);
//                addRemittanceExpires.add(newCom);
//            }
//        }

        // 默认生成
        Date actualDeliveryDate = dto.getActualDeliveryDate();
        String paymentMethod = dto.getPaymentMethod();
        if (actualDeliveryDate != null && StringUtils.isNotBlank(paymentMethod)) {
            XqRemittanceExpire newCom = new XqRemittanceExpire();
            newCom.setId(null);
            newCom.setOrderId(orderId);

            newCom.setIzDefault(1);
            BigDecimal salesAmount = BigDecimal.ZERO;
            //--销售金额-额外费用-佣金里面的订单扣除类型的金额
            if (dto.getOrderProdVOS().size() > 0) {
                for (AddOrderProdDTO addOrderRawDTO : dto.getOrderProdVOS()) {
                    salesAmount = salesAmount.add(addOrderRawDTO.getSalesAmount());
                }
            }
            //额外费用
            if (dto.getXqOrderExtraCostDTOS().size() > 0) {
                BigDecimal extraCost = BigDecimal.ZERO;
                for (XqOrderExtraCostDTO xqOrderExtraCostDTO : dto.getXqOrderExtraCostDTOS()) {
                    extraCost = extraCost.add(xqOrderExtraCostDTO.getPrice());
                }
                salesAmount = salesAmount.add(extraCost);
            }
            //佣金信息
            if (dto.getOrderComsVOS().size() > 0) {
                BigDecimal ordercoms = BigDecimal.ZERO;
                for (AddOrderComsDTO addOrderComsDTO : dto.getOrderComsVOS()) {
                    if (StringUtils.isBlank(addOrderComsDTO.getCommissionType())) {
                        throw new InterestingBootException("佣金类型不能为空");
                    }
                    if (addOrderComsDTO.getCommissionType().equals("ddkc")) {
                        ordercoms = ordercoms.add(addOrderComsDTO.getAmount());
                    }
                }
                salesAmount = salesAmount.subtract(ordercoms);
            }
            newCom.setPrice(salesAmount);


            XqOrderPaymentMethod xqOrderPaymentMethod = xqOrderPaymentMethodService.getById(paymentMethod);
            if (xqOrderPaymentMethod == null) throw new InterestingBootException("新增失败，付款方式不存在");
            Integer value = xqOrderPaymentMethod.getValue();
            long l = actualDeliveryDate.getTime() + TimeUnit.DAYS.toMillis(value.longValue());
            Date date = new Date(l);
            newCom.setRemittanceExpireDate(date);
            addRemittanceExpires.add(newCom);
        }

        if (CollectionUtil.isNotEmpty(addRemittanceExpires)) xqRemittanceExpireService.saveBatch(addRemittanceExpires);


        /* 订单产品子表 */
        List<XqOrderDetail> toAddComs = new ArrayList<>();
        List<AddOrderProdDTO> orderProdVOS = dto.getOrderProdVOS();
        for (AddOrderProdDTO addCom : orderProdVOS) {
            if (StringUtils.isBlank(addCom.getCurrency())) {
                throw new InterestingBootException("产品信息里面的币种不能为空");
            }
            XqOrderDetail newCom = new XqOrderDetail();
            BeanUtils.copyProperties(addCom, newCom);

            /* 校验计算逻辑 */
            /* 计算总重 */
            // totalWeight = totalBoxes * weightPerBox
//            BigDecimal weightPerBox = addCom.getWeightPerBox();
//            Integer totalBoxes = addCom.getTotalBoxes();
//            BigDecimal totalWeight = addCom.getTotalWeight();
//            if (weightPerBox != null && totalBoxes != null && totalWeight != null) {
//                BigDecimal multiply = weightPerBox.multiply(new BigDecimal(totalBoxes)).setScale(2, BigDecimal.ROUND_HALF_UP);
//                if (multiply.compareTo(totalWeight) != 0) {
//                    throw new InterestingBootException("产品重量计算错误");
//                }
//            }
//
//            /* 销售金额 */
//            // pricePerLb * totalWeight + pricePerBox * totalBoxes = salesAmount
//            BigDecimal pricePerLb = addCom.getPricePerLb();
//            BigDecimal pricePerBox = addCom.getPricePerBox();
//            BigDecimal salesAmount = addCom.getSalesAmount();
//            if (pricePerLb != null && pricePerBox != null && salesAmount != null) {
//                BigDecimal multiply = pricePerLb.multiply(totalWeight).setScale(2, BigDecimal.ROUND_HALF_UP);
//                BigDecimal multiply1 = pricePerBox.multiply(new BigDecimal(totalBoxes)).setScale(2, BigDecimal.ROUND_HALF_UP);
//                if (multiply.add(multiply1).compareTo(salesAmount) != 0) {
//                    throw new InterestingBootException("产品销售金额计算错误");
//                }
//            }

            /* 根据产品名，新增产品信息记录，有则直接插入id，没有则新增产品信息再插入id */
            String productName = addCom.getProductName();
            String productCategory = addCom.getProductCategoryName();
            String packing = addCom.getPackaging();
            // 定义要匹配的正则表达式
            String regex = "^[-/+x\\u4e00-\\u9fa5\\w\\d]+(\\|[-/+x\\u4e00-\\u9fa5\\w\\d]+)*$"; // 包装方式的正则表达式

            // 判断是否满足验证规则
            if (packing.indexOf('|') > 0) {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(packing);

                if (!matcher.matches()) {
                    throw new InterestingBootException("包装方式必须以横杠隔开");
                }
            }
//            XqProductInfo one = xqProductInfoService.lambdaQuery()
//                    .eq(XqProductInfo::getProductName, productName)
//                    .eq(XqProductInfo::getPackaging, packing)
//                    .last("LIMIT 1").one();

//            XqProductInfo one = xqProductInfoService.lambdaQuery()
//                    .eq(XqProductInfo::getProductName, productName)
//                    .eq(XqProductInfo::getPackaging, packing)
//                    .eq(XqProductInfo::getForeignTariff, addCom.getForeignTariff())
//                    .eq(XqProductInfo::getHsCodeDomestic, addCom.getHsCodeDomestic())
//                    .eq(XqProductInfo::getHsCodeForeign, addCom.getHsCodeForeign())
//                    .eq(XqProductInfo::getPackagingUnit, addCom.getPackagingUnit())
//                    .eq(XqProductInfo::getProductSpecs, addCom.getProductSpecs())
//                    .eq(XqProductInfo::getProductNameEn, addCom.getProductNameEn())
//                    .eq(XqProductInfo::getProductGrade, addCom.getProductGrade())
//                    .last("LIMIT 1").one();
//            if (one == null) {
////                XqProductInfo xqProductInfo = new XqProductInfo();
////                BeanUtils.copyProperties(addCom, xqProductInfo);
////                xqProductInfoService.save(xqProductInfo);
////                newCom.setProductId(xqProductInfo.getId());
//                throw new InterestingBootException("未找到产品：" + productName + "     " + addCom.getProductNameEn() + "     " + addCom.getProductSpecs() + "     " + addCom.getProductGrade() + "     " + addCom.getPackaging() + "     " + addCom.getHsCodeDomestic() + "     " + addCom.getHsCodeForeign() + "     " + addCom.getForeignTariff() + ",产品信息必须从产品管理新增");
//            } else {
            newCom.setProductId(addCom.getProductId());
//            }

            if (StringUtils.isNotBlank(productCategory)) {
                XqProductCategory one1 = xqProductCategoryService.lambdaQuery()
                        .eq(XqProductCategory::getId, productCategory)
                        .last("LIMIT 1").one();
                if (one1 == null) {
//                    XqProductCategory xqProductCategory = new XqProductCategory();
//                    xqProductCategory.setCategory(productCategory);
//                    xqProductCategoryService.save(xqProductCategory);
//                    newCom.setProductCategory(xqProductCategory.getId());
                    throw new InterestingBootException("产品品类必须从产品品类管理新增");
                } else {
                    newCom.setProductCategory(one1.getId());
                }
            }

            newCom.setId(null);
            newCom.setOrderId(orderId);
            toAddComs.add(newCom);
        }
        boolean addResult = xqOrderDetailService.saveBatch(toAddComs);

        /* 文件保存 */
//        List<InstOrUpdtXqFilesDTO> orderFiles1 = dto.getOrderFiles1();
//        if (orderFiles1 != null && orderFiles1.size() > 0) {
//            for (InstOrUpdtXqFilesDTO instOrUpdtXqFilesDTO : orderFiles1) {
//                XqFiles xqFiles = new XqFiles();
//                BeanUtils.copyProperties(instOrUpdtXqFilesDTO, xqFiles);
//                xqFiles.setOrderId(xqOrder.getId());
//                xqFiles.setModuleType("1");
//                xqFilesService.save(xqFiles);
//            }
//        }


        /* 佣金表 */
        List<XqOrderCommission> toAddComs2 = new ArrayList<>();
        List<AddOrderComsDTO> orderComsVOS = dto.getOrderComsVOS();

        // 查询已有的模板记录,比较已有的佣金模板，不完全相等的就新增
        // 佣金公司，佣金条件，佣金比例
        if (CollectionUtil.isNotEmpty(orderComsVOS)) {

            List<XqCommissionHistory> list = xqCommissionHistoryService.lambdaQuery()
                    .eq(XqCommissionHistory::getCustomerId, xqOrder.getCustomerId()).list();
            if (CollectionUtil.isNotEmpty(list)) {
                List<String> collect = list.stream().map(XqCommissionHistory::getId).collect(Collectors.toList());
                boolean insertFlag = true;
                for (String s : collect) {

                    List<XqCommissionCompany> list1 = xqCommissionCompanyService.lambdaQuery()
                            .eq(XqCommissionCompany::getTemplateId, s).list();

                    int validSize = 0;
                    if (list1.size() - orderComsVOS.size() == 0) {
                        for (XqCommissionCompany xqCommissionCompany : list1) {
                            for (AddOrderComsDTO orderComsVO : orderComsVOS) {
                                if (xqCommissionCompany.getName().equals(orderComsVO.getCompanyName())
                                        //         && xqCommissionCompany.getRequirements().equals(orderComsVO.getRequirements())
                                        && xqCommissionCompany.getPercentage().compareTo(orderComsVO.getPercentage()) == 0) {
                                    validSize += 1;
                                    break;
                                }
                            }
                        }

                        if (validSize == list1.size()) {
                            insertFlag = false;
                            break;
                        }

                    }
                }

                if (insertFlag) {
                    // 插入模板记录
                    XqCommissionHistory xqCommissionHistory = new XqCommissionHistory();
                    Date date = new Date();
                    SimpleDateFormat sf = new SimpleDateFormat("MMddHHmm");
                    String format = sf.format(date);
                    xqCommissionHistory.setName(format + "模板");
                    xqCommissionHistory.setCustomerId(xqOrder.getCustomerId());
                    xqCommissionHistoryService.save(xqCommissionHistory);
                    // 插入模板下的佣金公司记录
                    List<XqCommissionCompany> collect1 = orderComsVOS.stream().map(i -> {
                        XqCommissionCompany xqCommissionCompany = new XqCommissionCompany();
                        BeanUtils.copyProperties(i, xqCommissionCompany);
                        xqCommissionCompany.setName(i.getCompanyName());
                        xqCommissionCompany.setTemplateId(xqCommissionHistory.getId());
                        xqCommissionCompany.setId(null);
                        return xqCommissionCompany;
                    }).collect(Collectors.toList());
                    xqCommissionCompanyService.saveBatch(collect1);
                }

            } else {
                // 插入模板记录
                XqCommissionHistory xqCommissionHistory = new XqCommissionHistory();
                Date date = new Date();
                SimpleDateFormat sf = new SimpleDateFormat("MMddHHmm");
                String format = sf.format(date);
                xqCommissionHistory.setName(format + "模板");
                xqCommissionHistory.setCustomerId(xqOrder.getCustomerId());
                xqCommissionHistoryService.save(xqCommissionHistory);
                // 插入模板下的佣金公司记录
                List<XqCommissionCompany> collect1 = orderComsVOS.stream().map(i -> {
                    XqCommissionCompany xqCommissionCompany = new XqCommissionCompany();
                    BeanUtils.copyProperties(i, xqCommissionCompany);
                    xqCommissionCompany.setName(i.getCompanyName());
                    xqCommissionCompany.setTemplateId(xqCommissionHistory.getId());
                    xqCommissionCompany.setId(null);
                    return xqCommissionCompany;
                }).collect(Collectors.toList());
                xqCommissionCompanyService.saveBatch(collect1);
            }
        }

        for (AddOrderComsDTO addCom : orderComsVOS) {
            XqOrderCommission newCom = new XqOrderCommission();
            BeanUtils.copyProperties(addCom, newCom);

//            // 查询默认佣金公司，有则直接插入，无则新增
//            XqCommissionCompany one = xqCommissionCompanyService.lambdaQuery()
//                    .eq(XqCommissionCompany::getName, addCom.getCompanyName())
//                    .eq(XqCommissionCompany::getCustomerId, xqOrder.getCustomerId())
//                    .last("LIMIT 1").one();
//            if (one == null) {
//                XqCommissionCompany xqCommissionCompany = new XqCommissionCompany();
//                BeanUtils.copyProperties(addCom, xqCommissionCompany);
//                xqCommissionCompany.setName(addCom.getCompanyName());
//                xqCommissionCompany.setId(null);
//                xqCommissionCompany.setCustomerId(xqOrder.getCustomerId());
//                xqCommissionCompanyService.save(xqCommissionCompany);
//            }
            newCom.setId(null);
            newCom.setOrderId(orderId);
            toAddComs2.add(newCom);
        }
        boolean addResult2 = xqOrderCommissionService.saveBatch(toAddComs2);

        /* 订单质量问题说明 */
        List<InstOrUpdtNotesDTO> orderNotes = dto.getOrderNotes();
        List<XqProblemNote> toAddNotes = new ArrayList<>();
        for (InstOrUpdtNotesDTO addCom : orderNotes) {
            XqProblemNote newCom = new XqProblemNote();
            BeanUtils.copyProperties(addCom, newCom);
            newCom.setModuleType(CommonConstant.ORDER_MODULE);
            newCom.setId(null);
            newCom.setOrderId(orderId);
            toAddNotes.add(newCom);
        }
        xqProblemNoteService.saveBatch(toAddNotes);


        // 货运信息，保险费用初始化
        XqFreightInfo xqFreightInfo = new XqFreightInfo();
        xqFreightInfo.setOrderId(orderId);
        xqFreightInfo.setContainerTemperature("-20℃/关闭");
        xqFreightInfo.setVolume(BigDecimal.valueOf(50));
        xqFreightInfoService.save(xqFreightInfo);
        if (StringUtils.isNotBlank(xqOrder.getCustomerId()) && StringUtils.isNotBlank(xqOrder.getDeliveryAddress())) {

        }

        XqInsuranceExpenses xqInsuranceExpenses = new XqInsuranceExpenses();
        xqInsuranceExpenses.setOrderId(orderId);
        xqInsuranceExpensesService.save(xqInsuranceExpenses);

//        ArrayList<SysCategory> sysCategories = new ArrayList<>();
//        String[] sysOrgCodes = {"1", "2", "3", "4", "5"};
//        for (String sysOrgCode : sysOrgCodes) {
//            SysCategory sysCategory = new SysCategory();
//            sysCategory.setOrderId(xqOrder.getId());
//            sysCategory.setName("根目录");
//            sysCategory.setPid("0");
//            sysCategory.setSysOrgCode(sysOrgCode);
//            sysCategories.add(sysCategory);
//        }
//        sysCategoryService.saveBatch(sysCategories);
        /* 附件根节点初始化 */
        SysCategory sysCategory = new SysCategory();
        sysCategory.setOrderId(orderId);
        sysCategory.setName("根目录");
        sysCategory.setPid("0");
        sysCategoryService.save(sysCategory);
        //跟单
        SysCategory gendanCategory = new SysCategory();
        gendanCategory.setOrderId(orderId);
        gendanCategory.setName("销售");
        gendanCategory.setPid(sysCategory.getId());
        sysCategoryService.save(gendanCategory);
        //跟单D
        SysCategory gendanCategoryD = new SysCategory();
        gendanCategoryD.setOrderId(orderId);
        gendanCategoryD.setName("D");
        gendanCategoryD.setPid(sysCategory.getId());
        sysCategoryService.save(gendanCategoryD);

        SysCategory gendanCategoryqg = new SysCategory();
        gendanCategoryqg.setOrderId(orderId);
        gendanCategoryqg.setName("清关");
        gendanCategoryqg.setPid(gendanCategoryD.getId());
        sysCategoryService.save(gendanCategoryqg);

        SysCategory gendanCategoryfakehu = new SysCategory();
        gendanCategoryfakehu.setOrderId(orderId);
        gendanCategoryfakehu.setName("发客户");
        gendanCategoryfakehu.setPid(gendanCategoryD.getId());
        sysCategoryService.save(gendanCategoryfakehu);

        //跟单P
        SysCategory gendanCategoryP = new SysCategory();
        gendanCategoryP.setOrderId(orderId);
        gendanCategoryP.setName("P");
        gendanCategoryP.setPid(sysCategory.getId());
        sysCategoryService.save(gendanCategoryP);

        SysCategory gendanCategorycpzp = new SysCategory();
        gendanCategorycpzp.setOrderId(orderId);
        gendanCategorycpzp.setName("产品照片");
        gendanCategorycpzp.setPid(gendanCategoryP.getId());
        sysCategoryService.save(gendanCategorycpzp);

        SysCategory gendanCategoryzgzp = new SysCategory();
        gendanCategoryzgzp.setOrderId(orderId);
        gendanCategoryzgzp.setName("装柜照片");
        gendanCategoryzgzp.setPid(gendanCategoryP.getId());
        sysCategoryService.save(gendanCategoryzgzp);
        //采购
        SysCategory yuanliaocaigouCategory = new SysCategory();
        yuanliaocaigouCategory.setOrderId(orderId);
        yuanliaocaigouCategory.setName("采购");
        yuanliaocaigouCategory.setPid(sysCategory.getId());
        sysCategoryService.save(yuanliaocaigouCategory);

        //原料采购（合同）
        SysCategory hetongCategory = new SysCategory();
        hetongCategory.setOrderId(orderId);
        hetongCategory.setName("合同");
        hetongCategory.setPid(yuanliaocaigouCategory.getId());
        sysCategoryService.save(hetongCategory);

        //原料采购（财务数据）
        SysCategory caiwushujuCategory = new SysCategory();
        caiwushujuCategory.setOrderId(orderId);
        caiwushujuCategory.setName("财务数据");
        caiwushujuCategory.setPid(yuanliaocaigouCategory.getId());
        sysCategoryService.save(caiwushujuCategory);

        //原料采购（微生物）最新（工厂检测报告）
        SysCategory weishengwuCategory = new SysCategory();
        weishengwuCategory.setOrderId(orderId);
        weishengwuCategory.setName("工厂检测报告");
        weishengwuCategory.setPid(yuanliaocaigouCategory.getId());
        sysCategoryService.save(weishengwuCategory);

//        //原料采购（农残报告）
//        SysCategory nongcanbaogaoCategory = new SysCategory();
//        nongcanbaogaoCategory.setOrderId(orderId);
//        nongcanbaogaoCategory.setName("农残报告");
//        nongcanbaogaoCategory.setPid(yuanliaocaigouCategory.getId());
//        sysCategoryService.save(nongcanbaogaoCategory);

        //货运
        SysCategory huoyunCategory = new SysCategory();
        huoyunCategory.setOrderId(orderId);
        huoyunCategory.setName("货运");
        huoyunCategory.setPid(sysCategory.getId());
        sysCategoryService.save(huoyunCategory);

        //货运（出运信息）
        SysCategory chuyunxinxiCategory = new SysCategory();
        chuyunxinxiCategory.setOrderId(orderId);
        chuyunxinxiCategory.setName("出运信息");
        chuyunxinxiCategory.setPid(huoyunCategory.getId());
        sysCategoryService.save(chuyunxinxiCategory);

        //货运（账单）
        SysCategory zhangdanCategory = new SysCategory();
        zhangdanCategory.setOrderId(orderId);
        zhangdanCategory.setName("账单");
        zhangdanCategory.setPid(huoyunCategory.getId());
        sysCategoryService.save(zhangdanCategory);

        //货运（报检资料）
        SysCategory baojianziliaoCategory = new SysCategory();
        baojianziliaoCategory.setOrderId(orderId);
        baojianziliaoCategory.setName("报检资料");
        baojianziliaoCategory.setPid(huoyunCategory.getId());
        sysCategoryService.save(baojianziliaoCategory);

        //辅料 包材包装
        SysCategory baocaibaozhuangCategory = new SysCategory();
        baocaibaozhuangCategory.setOrderId(orderId);
        baocaibaozhuangCategory.setName("包材包装");
        baocaibaozhuangCategory.setPid(sysCategory.getId());
        sysCategoryService.save(baocaibaozhuangCategory);

        //辅料 收汇
        SysCategory shouhuiCategory = new SysCategory();
        shouhuiCategory.setOrderId(orderId);
        shouhuiCategory.setName("收汇");
        shouhuiCategory.setPid(sysCategory.getId());
        sysCategoryService.save(shouhuiCategory);

        //辅料特殊情况
        SysCategory teshuCategory = new SysCategory();
        teshuCategory.setOrderId(orderId);
        teshuCategory.setName("特殊情况");
        teshuCategory.setPid(sysCategory.getId());
        sysCategoryService.save(teshuCategory);

        return true;
//
//        /* 原料采购 */
//        List<XqRawMaterialPurchase> toAddComs3 = new ArrayList<>();
//        List<AddOrderRawDTO> orderRawVOS = dto.getOrderRawVOS();
//        for (AddOrderRawDTO addCom : orderRawVOS) {
        //         XqRawMaterialPurchase newCom = new XqRawMaterialPurchase();
//            BeanUtils.copyProperties(addCom, newCom);
//            newCom.setId(null);
//            newCom.setOrderId(xqOrder.getId());
//            toAddComs3.add(newCom);
//        }
//        boolean addResult3 = xqRawMaterialPurchaseService.saveBatch(toAddComs3);
//
//
//        /* 辅料采购 */
//        List<AddOrderAcsrDTO> orderAcsrVOS = dto.getOrderAcsrVOS();
//        List<XqAccessoriesPurchase> toAddComs4 = new ArrayList<>();
//        for (AddOrderAcsrDTO addCom : orderAcsrVOS) {
//            XqAccessoriesPurchase newCom = new XqAccessoriesPurchase();
//            BeanUtils.copyProperties(addCom, newCom);
//
//            /* 新增辅料信息，有则直接插入id，没有则新增辅料信息再插入id */
//            String accessoryName = addCom.getAccessoryName();
//            String size = addCom.getSize();
//            XqAccessoryInfo one = xqAccessoryInfoService.lambdaQuery()
//                    .eq(XqAccessoryInfo::getAccessoryName, accessoryName)
//                    .eq(XqAccessoryInfo::getSize, size).last("LIMIT 1").one();
//            if (one == null) {
//                XqAccessoryInfo xqAccessoryInfo = new XqAccessoryInfo();
//                xqAccessoryInfo.setAccessoryName(accessoryName);
//                xqAccessoryInfo.setSize(size);
//                xqAccessoryInfoService.save(xqAccessoryInfo);
//                newCom.setAccessoryId(xqAccessoryInfo.getId());
//            } else {
//                newCom.setAccessoryId(one.getId());
//            }
//
//            newCom.setId(null);
//            newCom.setOrderId(xqOrder.getId());
//            toAddComs4.add(newCom);
//        }
//        boolean addResult4 = xqAccessoriesPurchaseService.saveBatch(toAddComs4);
//
//        /* 货运信息 */
//        List<AddOrderFretDTO> orderFretVOS = dto.getOrderFretVOS();
//        List<XqFreightInfo> toAddComs5 = new ArrayList<>();
//        for (AddOrderFretDTO addCom : orderFretVOS) {
//            XqFreightInfo newCom = new XqFreightInfo();
//            BeanUtils.copyProperties(addCom, newCom);
//            newCom.setId(null);
//            newCom.setOrderId(xqOrder.getId());
//            toAddComs5.add(newCom);
//        }
//        boolean addResult5 = xqFreightInfoService.saveBatch(toAddComs5);
//
//        /* 货运费用（国内） */
//        List<AddOrderFretCostDTO> orderFretCostVOS = dto.getOrderFretCostVOS();
//        List<XqFreightCostInfo> toAddComs6 = new ArrayList<>();
//        for (AddOrderFretCostDTO addCom : orderFretCostVOS) {
//            XqFreightCostInfo newCom = new XqFreightCostInfo();
//            BeanUtils.copyProperties(addCom, newCom);
//            newCom.setId(null);
//            newCom.setOrderId(xqOrder.getId());
//            toAddComs6.add(newCom);
//        }
//        boolean addResult6 = xqFreightCostInfoService.saveBatch(toAddComs6);
//
//        /* 货运费用（国外） */
//        List<XqFreightCostInfo> toAddComs7 = new ArrayList<>();
//        List<AddOrderFretCostDTO> orderFretCostVOS2 = dto.getOrderFretCostVOS2();
//        for (AddOrderFretCostDTO addCom : orderFretCostVOS2) {
//            XqFreightCostInfo newCom = new XqFreightCostInfo();
//            BeanUtils.copyProperties(addCom, newCom);
//            newCom.setId(null);
//            newCom.setOrderId(xqOrder.getId());
//            toAddComs7.add(newCom);
//        }
//        boolean addResult7 = xqFreightCostInfoService.saveBatch(toAddComs7);
//
//        /* 保险费用 */
//        List<XqInsuranceExpenses> toAddComs8 = new ArrayList<>();
//        List<AddOrderInsuranceDTO> orderInsuranceVOS = dto.getOrderInsuranceVOS();
//        for (AddOrderInsuranceDTO addCom : orderInsuranceVOS) {
//            XqInsuranceExpenses newCom = new XqInsuranceExpenses();
//            BeanUtils.copyProperties(addCom, newCom);
//            newCom.setId(null);
//            newCom.setOrderId(xqOrder.getId());
//            toAddComs8.add(newCom);
//        }
//        boolean addResult8 = xqInsuranceExpensesService.saveBatch(toAddComs8);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editAll(EditXqOrderAllDTO dto) {
        /* 订单主表 */
        String orderId = dto.getId();
        XqOrder xqOrder = this.getById(orderId);
        if (xqOrder == null) {
            throw new InterestingBootException("订单不存在");
        }
        if ("2".equals(dto.getOrderType())) {
            //当前销售总额
            BigDecimal salesAmount = dto.getOrderProdVOS().stream().map(AddOrderProdDTO::getSalesAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            //额外费用
            BigDecimal extraPrice = dto.getXqOrderExtraCostDTOS().stream().map(XqOrderExtraCostDTO::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            //佣金
            BigDecimal amount = dto.getOrderComsVOS().stream().map(AddOrderComsDTO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            //总的未收款
            BigDecimal totalOutstandingReceivables = salesAmount.add(extraPrice).subtract(amount);
            //原销售总额
            List<XqOrderDetail> originalOrderDetails = xqOrderDetailService.list(new LambdaQueryWrapper<XqOrderDetail>().eq(XqOrderDetail::getOrderId, orderId));
            BigDecimal originalSalesAmount = originalOrderDetails.stream().map(XqOrderDetail::getSalesAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            //如果有修改
            if (salesAmount.compareTo(originalSalesAmount) != 0) {
                XqOrderConfig xqOrderConfig = xqOrderConfigService.getOne(new LambdaQueryWrapper<XqOrderConfig>().last("limit 1"));
                //保理利息
                BigDecimal factoringInterest = salesAmount.multiply(new BigDecimal(xqOrderConfig.getDays())).multiply(xqOrderConfig.getFactoringInterestRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
                //未收款
                BigDecimal outstandingReceivables = totalOutstandingReceivables.subtract(factoringInterest);
                //未收汇比例
                BigDecimal outstandingRemittanceRatios = outstandingReceivables.divide(totalOutstandingReceivables, 2, BigDecimal.ROUND_HALF_UP);
                if (!CollectionUtils.isEmpty(dto.getOrderRemiVOS())) {
                    AddOrderRemiDTO addOrderRemiDTO = dto.getOrderRemiVOS().get(0);
                    addOrderRemiDTO.setFactoringInterest(factoringInterest);
                    outstandingReceivables = outstandingReceivables.subtract(addOrderRemiDTO.getRemittanceAmount() == null ? BigDecimal.ZERO : addOrderRemiDTO.getRemittanceAmount())
                            .subtract(addOrderRemiDTO.getServiceCharge() == null ? BigDecimal.ZERO : addOrderRemiDTO.getServiceCharge())
                            .subtract(addOrderRemiDTO.getFactoringMoney() == null ? BigDecimal.ZERO : addOrderRemiDTO.getFactoringMoney())
                            .subtract(addOrderRemiDTO.getDiscount() == null ? BigDecimal.ZERO : addOrderRemiDTO.getDiscount())
                            .subtract(addOrderRemiDTO.getQuality() == null ? BigDecimal.ZERO : addOrderRemiDTO.getQuality());
                    addOrderRemiDTO.setOutstandingReceivables(outstandingReceivables);
                    addOrderRemiDTO.setOutstandingRemittanceRatio(outstandingRemittanceRatios);
                    dto.getOrderRemiVOS().set(0, addOrderRemiDTO);
                } else {
                    AddOrderRemiDTO addOrderRemiDTO = new AddOrderRemiDTO();
                    addOrderRemiDTO.setFactoringInterest(factoringInterest);
                    addOrderRemiDTO.setOutstandingReceivables(outstandingReceivables);
                    addOrderRemiDTO.setOutstandingRemittanceRatio(outstandingRemittanceRatios);
                    if (!CollectionUtils.isEmpty(dto.getOrderProdVOS())) {
                        addOrderRemiDTO.setCurrency(dto.getOrderProdVOS().get(0).getCurrency());
                    }

                    List<AddOrderRemiDTO> orderRemiVOS = new ArrayList<>();
                    orderRemiVOS.add(addOrderRemiDTO);
                    dto.setOrderRemiVOS(orderRemiVOS);
                }
                //
                //信保保险费
                BigDecimal creditInsurancePremiumConvert = salesAmount.multiply(xqOrderConfig.getCreditInsuranceRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
                //未收汇比例
                if (!CollectionUtils.isEmpty(dto.getOrderCreditsInsuranceVOS())) {
                    AddOrderCreditsInsuranceDTO addOrderCreditsInsuranceDTO = dto.getOrderCreditsInsuranceVOS().get(0);
                    addOrderCreditsInsuranceDTO.setOrderId(dto.getId());
                    addOrderCreditsInsuranceDTO.setCreditInsurancePremiumConvert(creditInsurancePremiumConvert);
                    dto.getOrderCreditsInsuranceVOS().set(0, addOrderCreditsInsuranceDTO);
                } else {
                    AddOrderCreditsInsuranceDTO addOrderCreditsInsuranceDTO = new AddOrderCreditsInsuranceDTO();
                    addOrderCreditsInsuranceDTO.setOrderId(dto.getId());
                    addOrderCreditsInsuranceDTO.setCreditInsurancePremiumConvert(creditInsurancePremiumConvert);

                    List<AddOrderCreditsInsuranceDTO> orderCreditsInsuranceVOS = new ArrayList<>();
                    orderCreditsInsuranceVOS.add(addOrderCreditsInsuranceDTO);
                    dto.setOrderCreditsInsuranceVOS(orderCreditsInsuranceVOS);
                }
            }
        }

        // wn 20240412
        // 如果收汇情况为空 删除
        if (dto.getOrderRemiVOS().isEmpty()) {
            xqRemittanceDetailService.lambdaUpdate().set(XqRemittanceDetail::getIzDelete, 1)
                    .eq(XqRemittanceDetail::getOrderId, orderId)
                    .update();
        }
        // wn 20240412

        // 拿出当前用户的userId和角色列表
        String userId = FilterContextHandler.getUserId();
        List<SysRole> roleByUserId = sysUserRoleService.getRoleByUserId(userId);
        List<Integer> roleCodes = roleByUserId.stream().map(SysRole::getRoleCode).collect(Collectors.toList());

        if (roleCodes.contains(0)) {
            roleCodes.add(1);
            roleCodes.add(2);
            roleCodes.add(3);
            roleCodes.add(4);
            roleCodes.add(5);
            roleCodes.add(6);
            roleCodes.add(7);
            roleCodes.add(8);
            roleCodes.add(9);
        }

        List<AddOrderFretDTO> orderFretVOS = dto.getOrderFretVOS();

        AddOrderInsuranceDTO addOrderInsuranceDTO = dto.getOrderInsuranceVOS().get(0);

        List<AddOrderComsDTO> orderComsVOS = dto.getOrderComsVOS();

        if (roleCodes.size() == 0) throw new InterestingBootException("当前用户无角色权限");


        // 判断是否最终确认
        if (dto.getCompleteFlag() != null && dto.getCompleteFlag() == 1) {

            List<Integer> confirmList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
            // 几个确认的角色，几条记录
            ArrayList<XqOrderFinallyConfirm> xqOrderFinallyConfirms = new ArrayList<>();

            // 2024-04-23 anian 查询当前用户订单是否最终确认
            List<XqOrderFinallyConfirm> list = xqOrderFinallyConfirmService
                    .lambdaQuery()
                    .eq(XqOrderFinallyConfirm::getOrderId, orderId)
                    .in(XqOrderFinallyConfirm::getRoleId, roleCodes)
                    .eq(XqOrderFinallyConfirm::getUserId, userId)
                    .groupBy(XqOrderFinallyConfirm::getRoleId)
                    .list();


            if (!CollectionUtils.isEmpty(list)) {
                xqOrderFinallyConfirmService
                        .lambdaUpdate()
                        .eq(XqOrderFinallyConfirm::getOrderId, orderId)
                        .eq(XqOrderFinallyConfirm::getUserId, userId)
                        .remove();
            }

            for (Integer role : roleCodes) {
                if (confirmList.contains(role)) {
                    XqOrderFinallyConfirm xqOrderFinallyConfirm = new XqOrderFinallyConfirm();
                    xqOrderFinallyConfirm.setOrderId(orderId);
                    xqOrderFinallyConfirm.setUserId(userId);
                    xqOrderFinallyConfirm.setRoleId(role.toString());
                    xqOrderFinallyConfirms.add(xqOrderFinallyConfirm);
                }
            }

            // 2024-04-23 anian
            if (xqOrderFinallyConfirms.size() > 0) {
                xqOrderFinallyConfirmService.saveBatch(xqOrderFinallyConfirms);

                List<String> collect = xqOrderFinallyConfirms.stream().map(XqOrderFinallyConfirm::getRoleId).collect(Collectors.toList());

                if (CollectionUtil.isNotEmpty(collect)) {
                    StringBuilder completeState = new StringBuilder(xqOrder.getCompleteState() == null ? "" : xqOrder.getCompleteState());
                    boolean change = false;
                    for (String roleCode : collect) {
                        if (!completeState.toString().contains(roleCode)) {
                            completeState.append(roleCode).append(",");
                            change = true;
                        }
                    }
                    if (change) {
                        this.lambdaUpdate().set(XqOrder::getCompleteState, completeState.toString()).eq(XqOrder::getId, orderId).update();
                    }


                }
            }


        }

        // 跟单员可修改的部分
        if (roleCodes.contains(1)) {
            // 判断订单号重复
            String orderNo = dto.getOrderNum();
            if (this.lambdaQuery()
                    .eq(XqOrder::getOrderNum, orderNo)
                    .ne(XqOrder::getId, orderId)
                    .count() > 0) {
                throw new InterestingBootException("订单号重复");
            }
            // 根据订单类型反刷地址


            // 客户信息
            String customerId = dto.getCustomerId();
            //XqCustomer ctm = xqCustomerService.getById(customerId);
            // 反刷仓库地址
            if (dto.getOrderType().equals("1")) {
                String warehouseId = dto.getWarehouseId();
                if (StringUtils.isBlank(warehouseId)) throw new InterestingBootException("编辑失败，仓库不可为空");
                XqWarehouse xqWarehouse = xqWarehouseService.getById(warehouseId);
                if (xqWarehouse == null) throw new InterestingBootException("编辑失败，仓库不存在");
                xqWarehouse.setAddress(dto.getDeliveryAddress());
                xqWarehouseService.updateById(xqWarehouse);

            } else {
                if (StringUtils.isBlank(customerId)) throw new InterestingBootException("编辑失败，客户不可为空");
                XqCustomer ctm = xqCustomerService.getById(customerId);
                if (ctm == null) throw new InterestingBootException("编辑失败，客户不存在");
                ctm.setAddress(dto.getDeliveryAddress());
                xqCustomerService.updateById(ctm);
                //客户不为空
                if (ctm != null) {
                    xqOrder.setCustomerId(ctm.getId());


//zkk0403
                    List<AddOrderFretCostDTO> orderFretCostVOS2 = dto.getOrderFretCostVOS2();
                    for (AddOrderFretCostDTO costDTO : orderFretCostVOS2) {
                        XqFreightCostInfo costInfo = null;
                        // 国外卡车费等于0时，重新附上客户维护的默认值
                        if (costDTO.getIzDefaultColor() == null) {
                            costDTO.setIzDefaultColor(0);
                        }
                        if (costDTO.getFeeType().contains("gwkcf") && costDTO.getPrice().compareTo(BigDecimal.ZERO) == 0 && costDTO.getIzDefaultColor() == 0) {
                            // 根据客户id和送货地址查询默认值
                            costInfo = baseMapper.getFreightCostInfoByAddress(customerId, dto.getDeliveryAddress());

                        } else if (costDTO.getPrice().compareTo(BigDecimal.ZERO) == 0 && costDTO.getIzDefaultColor() == 0) {
                            // 等于0时，重新附上客户维护的默认值
                            costInfo = xqFreightCostInfoService.lambdaQuery()
                                    .eq(XqFreightCostInfo::getCustomerId, customerId)
                                    .isNull(XqFreightCostInfo::getOrderId)
                                    .likeRight(XqFreightCostInfo::getFeeType, costDTO.getFeeType())
                                    .one();
                        }

                        if (costInfo != null) {
                            costDTO.setFeeType(costInfo.getFeeType());
                            costDTO.setPrice(costInfo.getPrice());
                            costDTO.setIzDefaultColor(0);
//                            costDTO.setAgent(costInfo.getAgent());
                            costDTO.setCurrency(costInfo.getCurrency());
                        }

                    }
                    dto.setOrderFretCostVOS2(orderFretCostVOS2);
//zkk0403


//                    // 根据客户id查询货运信息
//                    List<XqOrderFretCostVO> customerFretCostList = Collections.emptyList();
//                    customerFretCostList = baseMapper.listCustomerFretCost(ctm.getId(), dto.getDeliveryAddressId());
//                    List<XqFreightCostInfo> infoList = new ArrayList<>();

//                    if (customerFretCostList != null && customerFretCostList.size() > 0) {
//                        for (XqOrderFretCostVO xqOrderFretCostVO : customerFretCostList) {
//                            XqFreightCostInfo info = new XqFreightCostInfo();
//                            BeanUtils.copyProperties(xqOrderFretCostVO, info);
//                            if (StringUtils.isNotBlank(dto.getDeliveryAddressId())&&info.getIzDefaultColor()==0) {
//                                if (info.getId().equals(dto.getDeliveryAddressId())) {
//                                    info.setPrice(dto.getGwkcf());
//                                }
//                                if (info.getPrice() == null || info.getPrice().compareTo(BigDecimal.ZERO) == 0) {
//                                    info.setIzDefaultColor(2);
//                                } else {
//                                    info.setIzDefaultColor(0);
//                                }
//                                info.setOrderId(orderId);
//                                info.setId("");
//                                infoList.add(info);
//                            }
//                            if (StringUtils.isNotBlank(info.getId())){
//                                xqFreightCostInfoService.removeById(info.getId());
//                            }
//                        }
//                        xqFreightCostInfoService.saveBatch(infoList);
//                    }
//                    // 根据客户id查询货运信息
//                    List<XqOrderFretCostVO> customerFretCostList = Collections.emptyList();
//                    customerFretCostList = baseMapper.listCustomerFretOrderCost(ctm.getId(), dto.getId());
//                    List<XqFreightCostInfo> infoList = new ArrayList<>();
//
//                    if (customerFretCostList != null && customerFretCostList.size() > 0) {
//                        for (XqOrderFretCostVO xqOrderFretCostVO : customerFretCostList) {
//                            if (xqOrderFretCostVO.getId().equals(dto.getDeliveryAddressId())) {
//                                XqFreightCostInfo info = new XqFreightCostInfo();
//                                BeanUtils.copyProperties(xqOrderFretCostVO, info);
//                                info.setPrice(dto.getGwkcf());
//                                infoList.add(info);
//                            } else {
//                                continue;
//                            }
//                        }
//                        xqFreightCostInfoService.updateBatchById(infoList);
//                    }

                } else {
                    XqCustomer xqCustomer = new XqCustomer();
                    xqCustomer.setName(dto.getCustomer());
                    xqCustomerService.save(xqCustomer);

                    // 设置默认的货运费用
                    ArrayList<XqFreightCostInfo> xqFreightCostInfos = new ArrayList<>(3);
                    XqFreightCostInfo xqFreightCostInfo = new XqFreightCostInfo();
                    xqFreightCostInfo.setFeeType(CommonConstant.EXTRA_FEE_1);
                    xqFreightCostInfos.add(xqFreightCostInfo);
                    XqFreightCostInfo xqFreightCostInfo2 = new XqFreightCostInfo();
                    xqFreightCostInfo2.setFeeType(CommonConstant.FOREIGN_CUSTOMS_CLEARANCE_FEE);
                    xqFreightCostInfos.add(xqFreightCostInfo2);
                    XqFreightCostInfo xqFreightCostInfo3 = new XqFreightCostInfo();
                    xqFreightCostInfo3.setFeeType(CommonConstant.FOREIGN_TRUCK_FEE);
                    xqFreightCostInfos.add(xqFreightCostInfo3);
                    xqFreightCostInfos.forEach(i -> {
                        i.setIzDomestic(0);
                        i.setCustomerId(xqCustomer.getId());
                        i.setIzDefaultColor(0);
                    });
                    xqFreightCostInfoService.saveBatch(xqFreightCostInfos);
                    xqOrder.setCustomerId(xqCustomer.getId());
                    customerId = xqCustomer.getId();
                }


            }
            if (dto.getOrderType().equals("1")) {
                if (StringUtils.isBlank(dto.getCustomer())) {
                    customerId = "";
                }
            }

            // 修改订单表
            this.lambdaUpdate()
                    .set(XqOrder::getId, orderId)
                    .set(XqOrder::getShippingNum, dto.getShippingNum())
                    .set(XqOrder::getOrderType, dto.getOrderType())
                    .set(XqOrder::getCustomerId, customerId)
                    .set(XqOrder::getExchangeRate, dto.getExchangeRate())
                    .set(XqOrder::getOrderNum, dto.getOrderNum())
                    .set(XqOrder::getOriginOrderNum, dto.getOriginOrderNum())
                    .set(XqOrder::getPaymentMethod, dto.getPaymentMethod())
                    .set(XqOrder::getShippingDateRequired, dto.getShippingDateRequired())
                    .set(XqOrder::getDeliveryDateRequired, dto.getDeliveryDateRequired())
                    .set(XqOrder::getDeliveryBookingNum, dto.getDeliveryBookingNum())
                    .set(XqOrder::getQualityControlPerson, dto.getQualityControlPerson())
                    .set(XqOrder::getOrderFollowUpPerson, dto.getOrderFollowUpPerson())
                    .set(XqOrder::getActualDeliveryDate, dto.getActualDeliveryDate())
                    .set(XqOrder::getSpecialRequirements, dto.getSpecialRequirements())
                    .set(XqOrder::getDeliveryAddress, dto.getDeliveryAddress())
                    .set(XqOrder::getWarehouseId, dto.getWarehouseId())
                    .set(XqOrder::getTravelFee, dto.getTravelFee())
                    .eq(XqOrder::getId, orderId)
                    .update();


            // 默认生成收汇到期日
            Date actualDeliveryDate = dto.getActualDeliveryDate();
            String paymentMethod = dto.getPaymentMethod();

            boolean hasDefault = false;

            List<XqRemittanceExpire> xqRemittanceExpires = xqRemittanceExpireService.lambdaQuery().eq(XqRemittanceExpire::getOrderId, orderId).list();
            for (XqRemittanceExpire xqRemittanceExpire : xqRemittanceExpires) {
                if (xqRemittanceExpire.getIzDefault() == 1) {
                    hasDefault = true;
                    break;
                }
            }

            if (!hasDefault && actualDeliveryDate != null && StringUtils.isNotBlank(paymentMethod)) {
                XqRemittanceExpire xqRemittanceExpire = new XqRemittanceExpire();
                xqRemittanceExpire.setOrderId(orderId);
                xqRemittanceExpire.setIzDefault(1);
                BigDecimal salesAmount = BigDecimal.ZERO;
                // --销售金额-额外费用-佣金里面的订单扣除类型的金额
                if (dto.getOrderProdVOS().size() > 0) {
                    for (AddOrderProdDTO addOrderRawDTO : dto.getOrderProdVOS()) {
                        salesAmount = salesAmount.add(addOrderRawDTO.getSalesAmount());
                    }
                }
                //额外费用
                if (dto.getXqOrderExtraCostDTOS().size() > 0) {
                    BigDecimal extraCost = BigDecimal.ZERO;
                    for (XqOrderExtraCostDTO xqOrderExtraCostDTO : dto.getXqOrderExtraCostDTOS()) {
                        extraCost = extraCost.add(xqOrderExtraCostDTO.getPrice());
                    }
                    salesAmount = salesAmount.add(extraCost);
                }
                //佣金信息
                if (orderComsVOS.size() > 0) {
                    BigDecimal ordercoms = BigDecimal.ZERO;
                    for (AddOrderComsDTO addOrderComsDTO : orderComsVOS) {
                        if (StringUtils.isBlank(addOrderComsDTO.getCommissionType())) {
                            throw new InterestingBootException("佣金类型不能为空");
                        }
                        if (addOrderComsDTO.getCommissionType().equals("ddkc")) {
                            ordercoms = ordercoms.add(addOrderComsDTO.getAmount());
                        }
                    }
                    salesAmount = salesAmount.subtract(ordercoms);
                }


                xqRemittanceExpire.setPrice(salesAmount);
                XqOrderPaymentMethod xqOrderPaymentMethod = xqOrderPaymentMethodService.getById(paymentMethod);
                if (xqOrderPaymentMethod == null) throw new InterestingBootException("新增失败，付款方式不存在");
                Integer value = xqOrderPaymentMethod.getValue();
                long l = actualDeliveryDate.getTime() + TimeUnit.DAYS.toMillis(value.longValue());
                Date date = new Date(l);
                xqRemittanceExpire.setRemittanceExpireDate(date);

                xqRemittanceExpireService.save(xqRemittanceExpire);
            }

//            if (actualDeliveryDate == null || StringUtils.isBlank(paymentMethod)) {
//                for (XqRemittanceExpire xqRemittanceExpire : xqRemittanceExpires) {
//                    if (xqRemittanceExpire.getIzDefault() == 1) {
//                        xqRemittanceExpireService.removeById(xqRemittanceExpire);
//                    }
//                }
//            }
            if (hasDefault) {
                XqRemittanceExpire xqRemittanceExpire = xqRemittanceExpireService.getOne(new LambdaQueryWrapper<XqRemittanceExpire>()
                        .eq(XqRemittanceExpire::getOrderId, dto.getId()).eq(XqRemittanceExpire::getIzDefault, 1).last("limit 1"));
                if (xqRemittanceExpire != null) {
                    BigDecimal salesAmount = BigDecimal.ZERO;
                    // --销售金额-额外费用-佣金里面的订单扣除类型的金额
                    if (dto.getOrderProdVOS().size() > 0) {
                        for (AddOrderProdDTO addOrderRawDTO : dto.getOrderProdVOS()) {
                            salesAmount = salesAmount.add(addOrderRawDTO.getSalesAmount());
                        }
                    }
                    //额外费用
                    if (dto.getXqOrderExtraCostDTOS().size() > 0) {
                        BigDecimal extraCost = BigDecimal.ZERO;
                        for (XqOrderExtraCostDTO xqOrderExtraCostDTO : dto.getXqOrderExtraCostDTOS()) {
                            extraCost = extraCost.add(xqOrderExtraCostDTO.getPrice());
                        }
                        salesAmount = salesAmount.add(extraCost);
                    }
                    //佣金信息
                    if (orderComsVOS.size() > 0) {
                        BigDecimal ordercoms = BigDecimal.ZERO;
                        for (AddOrderComsDTO addOrderComsDTO : orderComsVOS) {
                            if (addOrderComsDTO.getCommissionType().equals("ddkc")) {
                                ordercoms = ordercoms.add(addOrderComsDTO.getAmount());
                            }
                        }
                        salesAmount = salesAmount.subtract(ordercoms);
                    }
                    xqRemittanceExpire.setPrice(salesAmount);
                    xqRemittanceExpireService.updateById(xqRemittanceExpire);
                }
            }
            // 额外费用子表
            xqOrderExtraCostService.update(dto.getXqOrderExtraCostDTOS(), orderId);
            if (dto.getXqOrderExtraCostDTOS().size() > 0) {
                xqRemittanceDetailService.updateRemittance(orderId, dto.getOrderRemiVOS());

            }
            //收汇情况信保费用
            xqCreditsInsuranceService.updateCreditsInsurance(xqOrder.getId(), dto.getOrderCreditsInsuranceVOS());
            // 产品信息
            /* 订单产品子表 */
            xqProductInfoService.updateOrderProd(xqOrder.getId(), dto.getOrderProdVOS());

            // 特殊情况说明
//            xqProblemNoteService.updateNotes(xqOrder.getId(), CommonConstant.ORDER_MODULE, dto.getOrderNotes());


            // 佣金信息
            /* 佣金表 */
            orderComsVOS.forEach(i -> {
                if (StringUtils.isBlank(i.getId())) {
                    i.setId(UUID.randomUUID().toString());
                }
            });
            // 查询已有的模板记录,比较已有的佣金模板，不完全相等的就新增
            // 佣金公司，佣金条件，佣金比例
            if (CollectionUtil.isNotEmpty(orderComsVOS)) {
                List<XqCommissionHistory> list = xqCommissionHistoryService.lambdaQuery()
                        .eq(XqCommissionHistory::getCustomerId, xqOrder.getCustomerId()).list();
                if (CollectionUtil.isNotEmpty(list)) {
                    List<String> collect = list.stream().map(XqCommissionHistory::getId).collect(Collectors.toList());
                    boolean insertFlag = true;
                    for (String s : collect) {
                        List<XqCommissionCompany> list1 = xqCommissionCompanyService.lambdaQuery()
                                .eq(XqCommissionCompany::getTemplateId, s).list();

                        int validSize = 0;
                        if (list1.size() - orderComsVOS.size() == 0) {
                            for (XqCommissionCompany xqCommissionCompany : list1) {
                                for (AddOrderComsDTO orderComsVO : orderComsVOS) {
                                    if (xqCommissionCompany.getName().equals(orderComsVO.getCompanyName())
                                            // && xqCommissionCompany.getRequirements().equals(orderComsVO.getRequirements())
                                            && xqCommissionCompany.getPercentage().compareTo(orderComsVO.getPercentage()) == 0) {
                                        validSize += 1;
                                        break;
                                    }
                                }
                            }

                            if (validSize == list1.size()) {
                                insertFlag = false;
                                break;
                            }

                        }
                    }

                    if (insertFlag) {
                        // 插入模板记录
                        XqCommissionHistory xqCommissionHistory = new XqCommissionHistory();
                        Date date = new Date();
                        SimpleDateFormat sf = new SimpleDateFormat("MMddHHmm");
                        String format = sf.format(date);
                        xqCommissionHistory.setName(format + "模板");
                        xqCommissionHistory.setCustomerId(xqOrder.getCustomerId());
                        xqCommissionHistoryService.save(xqCommissionHistory);
                        // 插入模板下的佣金公司记录
                        List<XqCommissionCompany> collect1 = orderComsVOS.stream().map(i -> {
                            XqCommissionCompany xqCommissionCompany = new XqCommissionCompany();
                            BeanUtils.copyProperties(i, xqCommissionCompany);
                            xqCommissionCompany.setName(i.getCompanyName());
                            xqCommissionCompany.setTemplateId(xqCommissionHistory.getId());
                            xqCommissionCompany.setId(null);
                            return xqCommissionCompany;
                        }).collect(Collectors.toList());
                        xqCommissionCompanyService.saveBatch(collect1);
                    }

                } else {
                    // 插入模板记录
                    XqCommissionHistory xqCommissionHistory = new XqCommissionHistory();
                    Date date = new Date();
                    SimpleDateFormat sf = new SimpleDateFormat("MMddHHmm");
                    String format = sf.format(date);
                    xqCommissionHistory.setName(format + "模板");
                    xqCommissionHistory.setCustomerId(xqOrder.getCustomerId());
                    xqCommissionHistoryService.save(xqCommissionHistory);
                    // 插入模板下的佣金公司记录
                    List<XqCommissionCompany> collect1 = orderComsVOS.stream().map(i -> {
                        XqCommissionCompany xqCommissionCompany = new XqCommissionCompany();
                        BeanUtils.copyProperties(i, xqCommissionCompany);
                        xqCommissionCompany.setName(i.getCompanyName());
                        xqCommissionCompany.setTemplateId(xqCommissionHistory.getId());
                        return xqCommissionCompany;
                    }).collect(Collectors.toList());
                    xqCommissionCompanyService.saveBatch(collect1);
                }
            }
            xqOrderCommissionService.updateOrderCommissions(xqOrder.getId(), orderComsVOS, xqOrder.getCustomerId());

            // 货运信息，目的港
            xqFreightInfoService.updateOrderFretInfo(orderId, dto.getOrderFretVOS(), 1);

        }

        // 国内财务
        if (roleCodes.contains(2)) {
            // 佣金表
            for (AddOrderComsDTO orderComsVO : orderComsVOS) {
                xqOrderCommissionService.lambdaUpdate()
                        .set(XqOrderCommission::getFinanceConfirmedAmount, orderComsVO.getFinanceConfirmedAmount())
                        .set(XqOrderCommission::getFinanceAuditDate, orderComsVO.getFinanceAuditDate())
                        .eq(XqOrderCommission::getId, orderComsVO.getId())
                        .update();

            }


            // 辅料采购信息
            xqAccessoriesPurchaseService.updateAccessories(orderId, dto.getOrderAcsrVOS(), xqOrder.getOrderNum(), 2);
            // 货运信息 ，出口报关单
            xqFreightInfoService.updateOrderFretInfo(orderId, dto.getOrderFretVOS(), 2);
            /* 货运费用 */
            // 国内
            xqFreightCostInfoService.updateOrderFretCost(orderId, dto.getOrderFretCostVOS(), 2);
            // 国外
            xqFreightCostInfoService.updateOrderFretCost2(orderId, dto.getOrderFretCostVOS2(), 2);
            /* 退运费用 */
            xqFreightCostInfoService.updateOrderFretCostReturnFee(orderId, dto.getOrderFretCostReturnFeeVOS(), 2);
            // 保险费用信息 财务确认金额，财务审核日期
            xqInsuranceExpensesService.lambdaUpdate()
                    .set(XqInsuranceExpenses::getFinanceConfirmAmount, addOrderInsuranceDTO.getFinanceConfirmAmount())
                    .set(XqInsuranceExpenses::getFinanceAuditDate, addOrderInsuranceDTO.getFinanceAuditDate())
                    .eq(XqInsuranceExpenses::getId, addOrderInsuranceDTO.getId())
                    .update();

            /* 卡车信息 */
            xqTruckInfoService.updateTruckInfo(xqOrder.getId(), dto.getTruckInfos(), 2);

        }

        // 国外财务
        if (roleCodes.contains(3)) {
            /* 收汇情况 */
            xqRemittanceDetailService.updateRemittance(orderId, dto.getOrderRemiVOS());

            /* 收汇到期日 */
            // 收汇到期日不为空才能修改
            if (!dto.getRemiExpDateVOS().isEmpty()) {
                xqRemittanceExpireService.updateRemiExpDate(xqOrder.getId(), dto.getRemiExpDateVOS());
            } else {
                xqRemittanceExpireService.remove(new LambdaUpdateWrapper<XqRemittanceExpire>().eq(XqRemittanceExpire::getOrderId, xqOrder.getId()));
            }

            // 收汇情况说明
            xqProblemNoteService.updateNotes(xqOrder.getId(), CommonConstant.REMITTANCE_MODULE, dto.getRemiNotes());
            //收汇情况信保费用
            xqCreditsInsuranceService.updateCreditsInsurance(xqOrder.getId(), dto.getOrderCreditsInsuranceVOS());
            //海外出库信保费用
            xqOverseasExitDetailService.updateOverseaCreditsInsurance(xqOrder.getId(), dto.getOverseasExitDTOS());


        }


        // 出纳
        if (roleCodes.contains(4)) {
            // 质量问题说明
            xqProblemNoteService.updateNotes(orderId, CommonConstant.RAW_MODULE, dto.getRawNotes());
            // 原料采购，包含了出纳的金额详情表
            xqRawMaterialPurchaseService.updateOrderRaw(orderId, dto.getOrderRawVOS(), 4);

        }


        // 原料采购
        if (roleCodes.contains(5)) {
            // 出口客户订单信息 发票号，境内发货人，境外收货人
            this.lambdaUpdate()
                    .set(XqOrder::getInvoiceNum, dto.getInvoiceNum())
                    .set(XqOrder::getOverseasReceiver, dto.getOverseasReceiver())
                    .set(XqOrder::getDomesticSender, dto.getDomesticSender())
                    .eq(XqOrder::getId, dto.getId())
                    .update();

            /* 原料采购 */
            xqRawMaterialPurchaseService.updateOrderRaw(orderId, dto.getOrderRawVOS(), 5);

            // 质量问题说明
            xqProblemNoteService.updateNotes(orderId, CommonConstant.RAW_MODULE, dto.getRawNotes());

            // 货运信息
            xqFreightInfoService.updateOrderFretInfo(orderId, dto.getOrderFretVOS(), 5);


        }


        // 辅料采购
        if (roleCodes.contains(6)) {
            // 生产信息
            this.lambdaUpdate()
                    .set(XqOrder::getProductionDate, dto.getProductionDate())
                    .set(XqOrder::getMaskedContent, dto.getMaskedContent())
                    .eq(XqOrder::getId, orderId)
                    .update();

            /* 辅料采购 */
            xqAccessoriesPurchaseService.updateAccessories(orderId, dto.getOrderAcsrVOS(), xqOrder.getOrderNum(), 6);

            // 货运信息
            xqFreightInfoService.updateOrderFretInfo(orderId, dto.getOrderFretVOS(), 6);

        }

        // 货运
        if (roleCodes.contains(7) || roleCodes.contains(1)) {

            /* 货运信息 */
            xqFreightInfoService.updateOrderFretInfo(orderId, dto.getOrderFretVOS(), 7);

            /* 货运费用 */
            // 国内
            xqFreightCostInfoService.updateOrderFretCost(orderId, dto.getOrderFretCostVOS(), 7);
            // 国外
            xqFreightCostInfoService.updateOrderFretCost2(orderId, dto.getOrderFretCostVOS2(), 7);

            /* 退运费用 */
            xqFreightCostInfoService.updateOrderFretCostReturnFee(orderId, dto.getOrderFretCostReturnFeeVOS(), 7);

            /* 保险费用 */
            xqInsuranceExpensesService.updateOrderInsurance(orderId, dto.getOrderInsuranceVOS());

            /* 卡车信息 */
            xqTruckInfoService.updateTruckInfo(xqOrder.getId(), dto.getTruckInfos(), 7);

            /*货运备注信息*/
            xqFreightNoteService.updateOrderFretNote(xqOrder.getId(), dto.getOrderFreightNoteVOS());

            if (roleCodes.contains(7)) {
                /* 原料采购 */
                xqRawMaterialPurchaseService.updateOrderRaw(orderId, dto.getOrderRawVOS(), 7);
            }
        }


        /* 文件 */
//        List<InstOrUpdtXqFilesDTO> orderFiles1 = dto.getOrderFiles1();
//        for (InstOrUpdtXqFilesDTO instOrUpdtXqFilesDTO : orderFiles1) {
//            if (StringUtils.isBlank(instOrUpdtXqFilesDTO.getId())) {
//                instOrUpdtXqFilesDTO.setId(UUID.randomUUID().toString());
//            }
//        }
//        List<InstOrUpdtXqFilesDTO> orderFiles2 = dto.getOrderFiles2();
//        for (InstOrUpdtXqFilesDTO instOrUpdtXqFilesDTO : orderFiles2) {
//            if (StringUtils.isBlank(instOrUpdtXqFilesDTO.getId())) {
//                instOrUpdtXqFilesDTO.setId(UUID.randomUUID().toString());
//            }
//        }
//        List<InstOrUpdtXqFilesDTO> orderFiles3 = dto.getOrderFiles3();
//        for (InstOrUpdtXqFilesDTO instOrUpdtXqFilesDTO : orderFiles3) {
//            if (StringUtils.isBlank(instOrUpdtXqFilesDTO.getId())) {
//                instOrUpdtXqFilesDTO.setId(UUID.randomUUID().toString());
//            }
//        }
//        List<InstOrUpdtXqFilesDTO> orderFiles4 = dto.getOrderFiles4();
//        for (InstOrUpdtXqFilesDTO instOrUpdtXqFilesDTO : orderFiles4) {
//            if (StringUtils.isBlank(instOrUpdtXqFilesDTO.getId())) {
//                instOrUpdtXqFilesDTO.setId(UUID.randomUUID().toString());
//            }
//        }
//        List<InstOrUpdtXqFilesDTO> orderFiles5 = dto.getOrderFiles5();
//        for (InstOrUpdtXqFilesDTO instOrUpdtXqFilesDTO : orderFiles5) {
//            if (StringUtils.isBlank(instOrUpdtXqFilesDTO.getId())) {
//                instOrUpdtXqFilesDTO.setId(UUID.randomUUID().toString());
//            }
//        }
//
//        updateOrderFiles1(xqOrder.getId(), dto.getOrderFiles1());
//        updateOrderFiles2(xqOrder.getId(), dto.getOrderFiles2());
//        updateOrderFiles3(xqOrder.getId(), dto.getOrderFiles3());
//        updateOrderFiles4(xqOrder.getId(), dto.getOrderFiles4());
//        updateOrderFiles5(xqOrder.getId(), dto.getOrderFiles5());


        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean auditFollowerById(AuditDTO dto) {
        LoginUser userInfo = FilterContextHandler.getUserInfo();
        List<Integer> roleCodes = userInfo.getRoleCodes();
        if (!roleCodes.contains(CommonConstant.ORDER_FOLLOWER_MANAGER)) {
            throw new InterestingBootException("只有跟单经理才能审核");
        }

        String ids = dto.getIds();
        String[] splits = ids.split(",");

        if (dto.getAuditStatus().equals(CommonConstant.AUDIT_ORIGIN)) {
            for (String id : splits) {
                XqOrder byId = this.getById(id);
                if (byId.getFollowerAudit().equals(CommonConstant.AUDIT_ORIGIN)) {
                    throw new InterestingBootException("只有已审核的订单才能撤销审核");
                }

                byId.setFollowerAudit(dto.getAuditStatus());
                byId.setAuditTime(new Date());
                byId.setAuditBy(userInfo.getId());
                byId.setCompleteState("");
                this.updateById(byId);

                // 删除跟单已最终确认的订单
                xqOrderFinallyConfirmService.lambdaUpdate()
                        .set(XqOrderFinallyConfirm::getIzDelete, 1)
                        .eq(XqOrderFinallyConfirm::getOrderId, byId.getId())
                        .eq(XqOrderFinallyConfirm::getRoleId, CommonConstant.ORDER_FOLLOWER)
                        .update();
            }
        } else if (dto.getAuditStatus().equals(CommonConstant.AUDIT_PASS)) {
            for (String id : splits) {
                XqOrder byId = this.getById(id);
                if (!byId.getFollowerAudit().equals(CommonConstant.AUDIT_ORIGIN)) {
                    throw new InterestingBootException("只有未审核的订单才能审核通过");
                }

                // 查询跟单是否最终确认
                List<XqOrderFinallyConfirm> confirmList = xqOrderFinallyConfirmService.lambdaQuery()
                        .eq(XqOrderFinallyConfirm::getOrderId, byId.getId())
                        .eq(XqOrderFinallyConfirm::getRoleId, CommonConstant.ORDER_FOLLOWER)
                        .list();

                if (confirmList.isEmpty()) {
                    throw new InterestingBootException("跟单未最终确认，无法审核通过!");
                }

                byId.setFollowerAudit(dto.getAuditStatus());
                byId.setAuditTime(new Date());
                byId.setAuditBy(userInfo.getId());
                this.updateById(byId);
            }
        } else {
            throw new InterestingBootException("非法参数");
        }

        return true;
    }

//    @Override
//    public boolean auditFinanceById(AuditDTO dto) {
//        LoginUser userInfo = FilterContextHandler.getUserInfo();
//        List<Integer> roleCodes = userInfo.getRoleCodes();
//        if (!roleCodes.contains(CommonConstant.DOMESTIC_FINANCE)) {
//            throw new InterestingBootException("只有国内财务才能审核");
//        }
//
//        String ids = dto.getIds();
//        String[] splits = ids.split(",");
//
//        if (dto.getAuditStatus().equals(CommonConstant.AUDIT_ORIGIN)) {
//            for (String id : splits) {
//                XqOrder byId = this.getById(id);
//                byId.setFinanceAudit(dto.getAuditStatus());
//                this.updateById(byId);
//            }
//        } else if (dto.getAuditStatus().equals(CommonConstant.AUDIT_PASS)) {
//            for (String id : splits) {
//                XqOrder byId = this.getById(id);
//                if (!byId.getFollowerAudit().equals(CommonConstant.AUDIT_ORIGIN)) {
//                    throw new InterestingBootException("只有未审核的订单才能审核通过");
//                }
//
//                byId.setFinanceAudit(dto.getAuditStatus());
//                this.updateById(byId);
//            }
//        } else {
//            throw new InterestingBootException("非法参数");
//        }
//
//        return true;
//    }

    /**
     * 根据id复制面单
     *
     * @param id
     * @return
     */
    @Override
    public boolean copyOrder(String id) {
        CopyAddXqOrderAllDTO copyAddXqOrderAllDTO = new CopyAddXqOrderAllDTO();

        /* 订单主表 */
        XqOrderAllVO xqOrderAllVO = this.queryAllById(id);
        BeanUtils.copyProperties(xqOrderAllVO, copyAddXqOrderAllDTO);
        String orderNum = copyAddXqOrderAllDTO.getOrderNum();
        // 获取长度为3的随机数 以英文与数字生成
        String s = oConvertUtils.randomGen(3);
        copyAddXqOrderAllDTO.setOrderNum(orderNum + "_Copy" + s);
        copyAddXqOrderAllDTO.setInvoiceNum("");
        copyAddXqOrderAllDTO.setOverseasReceiver("");
        copyAddXqOrderAllDTO.setDomesticSender("");
        copyAddXqOrderAllDTO.setInvoiceDate(null);
        /* 订单产品子表 */
        List<XqOrderProdVO> orderProdVOS = xqOrderAllVO.getOrderProdVOS();
        List<AddOrderProdDTO> collect = orderProdVOS.stream().map(i -> {
            AddOrderProdDTO addOrderProdDTO = new AddOrderProdDTO();
            BeanUtils.copyProperties(i, addOrderProdDTO);
            addOrderProdDTO.setId(null);
            return addOrderProdDTO;
        }).collect(Collectors.toList());
        copyAddXqOrderAllDTO.setOrderProdVOS(collect);

        /* 佣金表 */
        List<XqOrderComsVO> orderComsVOS = xqOrderAllVO.getOrderComsVOS();
        List<AddOrderComsDTO> collect1 = orderComsVOS.stream().map(i -> {
            AddOrderComsDTO addOrderComsDTO = new AddOrderComsDTO();
            BeanUtils.copyProperties(i, addOrderComsDTO);
            addOrderComsDTO.setFinanceConfirmedAmount(BigDecimal.ZERO);
            addOrderComsDTO.setFinanceAuditDate(null);
            return addOrderComsDTO;
        }).collect(Collectors.toList());
        copyAddXqOrderAllDTO.setOrderComsVOS(collect1);

        boolean b = copyAddAll(copyAddXqOrderAllDTO);

        return true;
    }


    /**
     * 差异化处理编辑 文件
     *
     * @param orderId
     * @param xqFilesDTOS
     * @return
     */
    public boolean updateOrderFiles(String orderId, List<InstOrUpdtXqFilesDTO> xqFilesDTOS) {
        List<XqFiles> originalComs =
                xqFilesService.list(new LambdaQueryWrapper<XqFiles>().eq(XqFiles::getOrderId, orderId));
        // 插入参数的id分组
        Map<String, InstOrUpdtXqFilesDTO> addComsMap = xqFilesDTOS.stream().collect(Collectors.toMap(InstOrUpdtXqFilesDTO::getId, Function.identity()));

        List<String> toDeleteIds = new ArrayList<>();
        List<XqFiles> toAddComs = new ArrayList<>();

        for (XqFiles originalCom : originalComs) {
            InstOrUpdtXqFilesDTO addCom = addComsMap.get(originalCom.getId());
            if (addCom != null) {
//                BeanUtils.copyProperties(addCom, originalCom);
                addComsMap.remove(originalCom.getId());
            } else {
                // 删除记录
                toDeleteIds.add(originalCom.getId());
            }
        }

        boolean deleteResult = xqFilesService.removeByIds(toDeleteIds);

        for (InstOrUpdtXqFilesDTO addCom : addComsMap.values()) {
            XqFiles newCom = new XqFiles();
            BeanUtils.copyProperties(addCom, newCom);

            newCom.setId(null);
            newCom.setOrderId(orderId);
            toAddComs.add(newCom);
        }

        boolean addResult = xqFilesService.saveBatch(toAddComs);

        return deleteResult && addResult;
    }


    /**
     * 差异化处理编辑 文件
     *
     * @param orderId
     * @param xqFilesDTOS
     * @return
     */
    public boolean updateOrderFiles1(String orderId, List<InstOrUpdtXqFilesDTO> xqFilesDTOS) {
        List<XqFiles> originalComs =
                xqFilesService.list(new LambdaQueryWrapper<XqFiles>().eq(XqFiles::getOrderId, orderId)
                        .eq(XqFiles::getModuleType, "1"));
        // 插入参数的id分组
        Map<String, InstOrUpdtXqFilesDTO> addComsMap = xqFilesDTOS.stream().collect(Collectors.toMap(InstOrUpdtXqFilesDTO::getId, Function.identity()));

        List<String> toDeleteIds = new ArrayList<>();
        List<XqFiles> toAddComs = new ArrayList<>();

        for (XqFiles originalCom : originalComs) {
            InstOrUpdtXqFilesDTO addCom = addComsMap.get(originalCom.getId());
            if (addCom != null) {
//                BeanUtils.copyProperties(addCom, originalCom);
                addComsMap.remove(originalCom.getId());
            } else {
                // 删除记录
                toDeleteIds.add(originalCom.getId());
            }
        }

        boolean deleteResult = xqFilesService.removeByIds(toDeleteIds);

        for (InstOrUpdtXqFilesDTO addCom : addComsMap.values()) {
            XqFiles newCom = new XqFiles();
            BeanUtils.copyProperties(addCom, newCom);

            newCom.setId(null);
            newCom.setOrderId(orderId);
            newCom.setModuleType("1");
            toAddComs.add(newCom);
        }

        boolean addResult = xqFilesService.saveBatch(toAddComs);

        return deleteResult && addResult;
    }

    /**
     * 差异化处理编辑 文件
     *
     * @param orderId
     * @param xqFilesDTOS
     * @return
     */
    public boolean updateOrderFiles2(String orderId, List<InstOrUpdtXqFilesDTO> xqFilesDTOS) {
        List<XqFiles> originalComs =
                xqFilesService.list(new LambdaQueryWrapper<XqFiles>().eq(XqFiles::getOrderId, orderId)
                        .eq(XqFiles::getModuleType, "2"));
        // 插入参数的id分组
        Map<String, InstOrUpdtXqFilesDTO> addComsMap = xqFilesDTOS.stream().collect(Collectors.toMap(InstOrUpdtXqFilesDTO::getId, Function.identity()));

        List<String> toDeleteIds = new ArrayList<>();
        List<XqFiles> toAddComs = new ArrayList<>();

        for (XqFiles originalCom : originalComs) {
            InstOrUpdtXqFilesDTO addCom = addComsMap.get(originalCom.getId());
            if (addCom != null) {
//                BeanUtils.copyProperties(addCom, originalCom);
                addComsMap.remove(originalCom.getId());
            } else {
                // 删除记录
                toDeleteIds.add(originalCom.getId());
            }
        }

        boolean deleteResult = xqFilesService.removeByIds(toDeleteIds);

        for (InstOrUpdtXqFilesDTO addCom : addComsMap.values()) {
            XqFiles newCom = new XqFiles();
            BeanUtils.copyProperties(addCom, newCom);

            newCom.setId(null);
            newCom.setOrderId(orderId);
            newCom.setModuleType("2");
            toAddComs.add(newCom);
        }

        boolean addResult = xqFilesService.saveBatch(toAddComs);

        return deleteResult && addResult;
    }

    /**
     * 差异化处理编辑 文件
     *
     * @param orderId
     * @param xqFilesDTOS
     * @return
     */
    public boolean updateOrderFiles3(String orderId, List<InstOrUpdtXqFilesDTO> xqFilesDTOS) {
        List<XqFiles> originalComs =
                xqFilesService.list(new LambdaQueryWrapper<XqFiles>().eq(XqFiles::getOrderId, orderId)
                        .eq(XqFiles::getModuleType, "3"));
        // 插入参数的id分组
        Map<String, InstOrUpdtXqFilesDTO> addComsMap = xqFilesDTOS.stream().collect(Collectors.toMap(InstOrUpdtXqFilesDTO::getId, Function.identity()));

        List<String> toDeleteIds = new ArrayList<>();
        List<XqFiles> toAddComs = new ArrayList<>();

        for (XqFiles originalCom : originalComs) {
            InstOrUpdtXqFilesDTO addCom = addComsMap.get(originalCom.getId());
            if (addCom != null) {
//                BeanUtils.copyProperties(addCom, originalCom);
                addComsMap.remove(originalCom.getId());
            } else {
                // 删除记录
                toDeleteIds.add(originalCom.getId());
            }
        }

        boolean deleteResult = xqFilesService.removeByIds(toDeleteIds);

        for (InstOrUpdtXqFilesDTO addCom : addComsMap.values()) {
            XqFiles newCom = new XqFiles();
            BeanUtils.copyProperties(addCom, newCom);

            newCom.setId(null);
            newCom.setOrderId(orderId);
            newCom.setModuleType("3");
            toAddComs.add(newCom);
        }

        boolean addResult = xqFilesService.saveBatch(toAddComs);

        return deleteResult && addResult;
    }

    /**
     * 差异化处理编辑 文件
     *
     * @param orderId
     * @param xqFilesDTOS
     * @return
     */
    public boolean updateOrderFiles4(String orderId, List<InstOrUpdtXqFilesDTO> xqFilesDTOS) {
        List<XqFiles> originalComs =
                xqFilesService.list(new LambdaQueryWrapper<XqFiles>().eq(XqFiles::getOrderId, orderId)
                        .eq(XqFiles::getModuleType, "4"));
        // 插入参数的id分组
        Map<String, InstOrUpdtXqFilesDTO> addComsMap = xqFilesDTOS.stream().collect(Collectors.toMap(InstOrUpdtXqFilesDTO::getId, Function.identity()));

        List<String> toDeleteIds = new ArrayList<>();
        List<XqFiles> toAddComs = new ArrayList<>();

        for (XqFiles originalCom : originalComs) {
            InstOrUpdtXqFilesDTO addCom = addComsMap.get(originalCom.getId());
            if (addCom != null) {
//                BeanUtils.copyProperties(addCom, originalCom);
                addComsMap.remove(originalCom.getId());
            } else {
                // 删除记录
                toDeleteIds.add(originalCom.getId());
            }
        }

        boolean deleteResult = xqFilesService.removeByIds(toDeleteIds);

        for (InstOrUpdtXqFilesDTO addCom : addComsMap.values()) {
            XqFiles newCom = new XqFiles();
            BeanUtils.copyProperties(addCom, newCom);

            newCom.setId(null);
            newCom.setOrderId(orderId);
            newCom.setModuleType("4");
            toAddComs.add(newCom);
        }

        boolean addResult = xqFilesService.saveBatch(toAddComs);

        return deleteResult && addResult;
    }

//    /**
//     * 差异化处理编辑 文件
//     *
//     * @param orderId
//     * @param addOrderFretDTOS
//     * @return
//     */
//    public boolean updateOrderFiles5(String orderId, List<InstOrUpdtXqFilesDTO> xqFilesDTOS) {
//        List<XqFiles> originalComs =
//                xqFilesService.list(new LambdaQueryWrapper<XqFiles>().eq(XqFiles::getOrderId, orderId)
//                        .eq(XqFiles::getModuleType, "5"));
//        // 插入参数的id分组
//        Map<String, InstOrUpdtXqFilesDTO> addComsMap = xqFilesDTOS.stream().collect(Collectors.toMap(InstOrUpdtXqFilesDTO::getId, Function.identity()));
//
//        List<String> toDeleteIds = new ArrayList<>();
//        List<XqFiles> toAddComs = new ArrayList<>();
//
//        for (XqFiles originalCom : originalComs) {
//            InstOrUpdtXqFilesDTO addCom = addComsMap.get(originalCom.getId());
//            if (addCom != null) {
////                BeanUtils.copyProperties(addCom, originalCom);
//                addComsMap.remove(originalCom.getId());
//            } else {
//                // 删除记录
//                toDeleteIds.add(originalCom.getId());
//            }
//        }
//
//        boolean deleteResult = xqFilesService.removeByIds(toDeleteIds);
//
//        for (InstOrUpdtXqFilesDTO addCom : addComsMap.values()) {
//            XqFiles newCom = new XqFiles();
//            BeanUtils.copyProperties(addCom, newCom);
//
//            newCom.setId(null);
//            newCom.setOrderId(orderId);
//            newCom.setModuleType("5");
//            toAddComs.add(newCom);
//        }
//
//        boolean addResult = xqFilesService.saveBatch(toAddComs);
//
//        return deleteResult && addResult;
//    }

    /**
     * 根据 dto 新增面单
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean copyAddAll(CopyAddXqOrderAllDTO dto) {
        /* 订单主表 */
        XqOrder xqOrder = new XqOrder();
        BeanUtils.copyProperties(dto, xqOrder);
        // 客户信息
        String customer = dto.getCustomer();
        XqCustomer ctm = xqCustomerService.lambdaQuery()
                .eq(XqCustomer::getName, customer)
                .last("LIMIT 1").one();
        if (ctm != null) {
            xqOrder.setCustomerId(ctm.getId());
        } else {
            XqCustomer xqCustomer = new XqCustomer();
            xqCustomer.setName(customer);
            xqCustomerService.save(xqCustomer);

            // 设置默认的货运费用
            ArrayList<XqFreightCostInfo> xqFreightCostInfos = new ArrayList<>(3);
            XqFreightCostInfo xqFreightCostInfo = new XqFreightCostInfo();
            xqFreightCostInfo.setFeeType(CommonConstant.EXTRA_FEE_1);
            xqFreightCostInfos.add(xqFreightCostInfo);
            XqFreightCostInfo xqFreightCostInfo2 = new XqFreightCostInfo();
            xqFreightCostInfo2.setFeeType(CommonConstant.FOREIGN_CUSTOMS_CLEARANCE_FEE);
            xqFreightCostInfos.add(xqFreightCostInfo2);
            XqFreightCostInfo xqFreightCostInfo3 = new XqFreightCostInfo();
            xqFreightCostInfo3.setFeeType(CommonConstant.FOREIGN_TRUCK_FEE);
            xqFreightCostInfos.add(xqFreightCostInfo3);
            xqFreightCostInfos.forEach(i -> {
                i.setIzDomestic(0);
                i.setCustomerId(xqCustomer.getId());
            });
            xqFreightCostInfoService.saveBatch(xqFreightCostInfos);

            xqOrder.setCustomerId(xqCustomer.getId());
        }
        this.updateById(xqOrder);

        this.save(xqOrder);


        /* 订单产品子表 */
        List<XqOrderDetail> toAddComs = new ArrayList<>();
        List<AddOrderProdDTO> orderProdVOS = dto.getOrderProdVOS();
        for (AddOrderProdDTO addCom : orderProdVOS) {
            XqOrderDetail newCom = new XqOrderDetail();
            BeanUtils.copyProperties(addCom, newCom);

            /* 校验计算逻辑 */
            /* 计算总重 */
//            // totalWeight = totalBoxes * weightPerBox
//            BigDecimal weightPerBox = addCom.getWeightPerBox();
//            Integer totalBoxes = addCom.getTotalBoxes();
//            BigDecimal totalWeight = addCom.getTotalWeight();
//            if (weightPerBox != null && totalBoxes != null && totalWeight != null) {
//                BigDecimal multiply = weightPerBox.multiply(new BigDecimal(totalBoxes)).setScale(2, BigDecimal.ROUND_HALF_UP);
//                if (multiply.compareTo(totalWeight) != 0) {
//                    throw new InterestingBootException("产品重量计算错误");
//                }
//            }
//
//            /* 销售金额 */
//            // pricePerLb * totalWeight + pricePerBox * totalBoxes = salesAmount
//            BigDecimal pricePerLb = addCom.getPricePerLb();
//            BigDecimal pricePerBox = addCom.getPricePerBox();
//            BigDecimal salesAmount = addCom.getSalesAmount();
//            if (pricePerLb != null && pricePerBox != null && salesAmount != null) {
//                BigDecimal multiply = pricePerLb.multiply(totalWeight).setScale(2, BigDecimal.ROUND_HALF_UP);
//                BigDecimal multiply1 = pricePerBox.multiply(new BigDecimal(totalBoxes)).setScale(2, BigDecimal.ROUND_HALF_UP);
//                if (multiply.add(multiply1).compareTo(salesAmount) != 0) {
//                    throw new InterestingBootException("产品销售金额计算错误");
//                }
//            }

            /* 根据产品名，新增产品信息记录，有则直接插入id，没有则新增产品信息再插入id */
            String productName = addCom.getProductName();
            String packing = addCom.getPackaging();
            XqProductInfo one = xqProductInfoService.lambdaQuery()
                    .eq(XqProductInfo::getProductName, productName)
                    .eq(XqProductInfo::getPackaging, packing)
                    .last("LIMIT 1").one();
            if (one == null) {
                XqProductInfo xqProductInfo = new XqProductInfo();
                BeanUtils.copyProperties(addCom, xqProductInfo);
                xqProductInfoService.save(xqProductInfo);
                newCom.setProductId(xqProductInfo.getId());
            } else {
                newCom.setProductId(one.getId());
            }

            newCom.setId(null);
            newCom.setOrderId(xqOrder.getId());
            toAddComs.add(newCom);
        }
        boolean addResult = xqOrderDetailService.saveBatch(toAddComs);




        /* 佣金表 */
        List<XqOrderCommission> toAddComs2 = new ArrayList<>();
        List<AddOrderComsDTO> orderComsVOS = dto.getOrderComsVOS();


        for (AddOrderComsDTO addCom : orderComsVOS) {
            XqOrderCommission newCom = new XqOrderCommission();
            BeanUtils.copyProperties(addCom, newCom);

//            // 查询默认佣金公司，有则直接插入，无则新增
//            XqCommissionCompany one = xqCommissionCompanyService.lambdaQuery()
//                    .eq(XqCommissionCompany::getName, addCom.getCompanyName())
//                    .eq(XqCommissionCompany::getCustomerId, xqOrder.getCustomerId())
//                    .last("LIMIT 1").one();
//            if (one == null) {
//                XqCommissionCompany xqCommissionCompany = new XqCommissionCompany();
//                BeanUtils.copyProperties(addCom, xqCommissionCompany);
//                xqCommissionCompany.setName(addCom.getCompanyName());
//                xqCommissionCompany.setId(null);
//                xqCommissionCompany.setCustomerId(xqOrder.getCustomerId());
//                xqCommissionCompanyService.save(xqCommissionCompany);
//            }

            newCom.setId(null);
            newCom.setOrderId(xqOrder.getId());
            toAddComs2.add(newCom);
        }
        boolean addResult2 = xqOrderCommissionService.saveBatch(toAddComs2);

//        /* 原料采购 */
//        List<XqRawMaterialPurchase> toAddComs3 = new ArrayList<>();
//        List<AddOrderRawDTO> orderRawVOS = dto.getOrderRawVOS();
//        for (AddOrderRawDTO addCom : orderRawVOS) {
//            XqRawMaterialPurchase newCom = new XqRawMaterialPurchase();
//            BeanUtils.copyProperties(addCom, newCom);
//            newCom.setId(null);
//            newCom.setOrderId(xqOrder.getId());
//            toAddComs3.add(newCom);
//        }
//        boolean addResult3 = xqRawMaterialPurchaseService.saveBatch(toAddComs3);
//
//
//        /* 辅料采购 */
//        List<AddOrderAcsrDTO> orderAcsrVOS = dto.getOrderAcsrVOS();
//        List<XqAccessoriesPurchase> toAddComs4 = new ArrayList<>();
//        for (AddOrderAcsrDTO addCom : orderAcsrVOS) {
//            XqAccessoriesPurchase newCom = new XqAccessoriesPurchase();
//            BeanUtils.copyProperties(addCom, newCom);
//
//            /* 新增辅料信息，有则直接插入id，没有则新增辅料信息再插入id */
//            String accessoryName = addCom.getAccessoryName();
//            String size = addCom.getSize();
//            XqAccessoryInfo one = xqAccessoryInfoService.lambdaQuery()
//                    .eq(XqAccessoryInfo::getAccessoryName, accessoryName)
//                    .eq(XqAccessoryInfo::getSize, size).last("LIMIT 1").one();
//            if (one == null) {
//                XqAccessoryInfo xqAccessoryInfo = new XqAccessoryInfo();
//                xqAccessoryInfo.setAccessoryName(accessoryName);
//                xqAccessoryInfo.setSize(size);
//                xqAccessoryInfoService.save(xqAccessoryInfo);
//                newCom.setAccessoryId(xqAccessoryInfo.getId());
//            } else {
//                newCom.setAccessoryId(one.getId());
//            }
//
//            newCom.setId(null);
//            newCom.setOrderId(xqOrder.getId());
//            toAddComs4.add(newCom);
//        }
//        boolean addResult4 = xqAccessoriesPurchaseService.saveBatch(toAddComs4);
//
//        /* 货运信息 */
//        List<AddOrderFretDTO> orderFretVOS = dto.getOrderFretVOS();
//        List<XqFreightInfo> toAddComs5 = new ArrayList<>();
//        for (AddOrderFretDTO addCom : orderFretVOS) {
//            XqFreightInfo newCom = new XqFreightInfo();
//            BeanUtils.copyProperties(addCom, newCom);
//
//            XqOrder byId = this.getById(xqOrder.getId());
//            // 插入出货序号
//            if (addCom.getCustomsClearanceTime() != null) {
//                String codeNumByType2 = sysCodeRoleService.getCodeNumByType2(1, 1);
//                byId.setShippingNum(codeNumByType2);
//                this.updateById(byId);
//            }
//
//            // 插入发票日期
//            Date invoiceDate = byId.getInvoiceDate();
//            Date loadingDate = addCom.getLoadingDate();
//            if (invoiceDate == null && loadingDate != null) {
//                byId.setInvoiceDate(loadingDate);
//                this.updateById(byId);
//            }
//
//            newCom.setId(null);
//            newCom.setOrderId(xqOrder.getId());
//            toAddComs5.add(newCom);
//        }
//        boolean addResult5 = xqFreightInfoService.saveBatch(toAddComs5);
//
//        /* 货运费用（国内） */
//        List<AddOrderFretCostDTO> orderFretCostVOS = dto.getOrderFretCostVOS();
//        List<XqFreightCostInfo> toAddComs6 = new ArrayList<>();
//        for (AddOrderFretCostDTO addCom : orderFretCostVOS) {
//            XqFreightCostInfo newCom = new XqFreightCostInfo();
//            BeanUtils.copyProperties(addCom, newCom);
//            newCom.setId(null);
//            newCom.setOrderId(xqOrder.getId());
//            newCom.setIzDomestic(1);
//            toAddComs6.add(newCom);
//        }
//        boolean addResult6 = xqFreightCostInfoService.saveBatch(toAddComs6);
//
//        /* 货运费用（国外） */
//        List<XqFreightCostInfo> toAddComs7 = new ArrayList<>();
//        List<AddOrderFretCostDTO> orderFretCostVOS2 = dto.getOrderFretCostVOS2();
//        for (AddOrderFretCostDTO addCom : orderFretCostVOS2) {
//            XqFreightCostInfo newCom = new XqFreightCostInfo();
//            BeanUtils.copyProperties(addCom, newCom);
//            newCom.setId(null);
//            newCom.setOrderId(xqOrder.getId());
//            newCom.setIzDomestic(0);
//            toAddComs7.add(newCom);
//        }
//        boolean addResult7 = xqFreightCostInfoService.saveBatch(toAddComs7);
//
//        /* 保险费用 */
//        List<XqInsuranceExpenses> toAddComs8 = new ArrayList<>();
//        List<AddOrderInsuranceDTO> orderInsuranceVOS = dto.getOrderInsuranceVOS();
//        for (AddOrderInsuranceDTO addCom : orderInsuranceVOS) {
//            XqInsuranceExpenses newCom = new XqInsuranceExpenses();
//            BeanUtils.copyProperties(addCom, newCom);
//            newCom.setId(null);
//            newCom.setOrderId(xqOrder.getId());
//            toAddComs8.add(newCom);
//        }
//        boolean addResult8 = xqInsuranceExpensesService.saveBatch(toAddComs8);
//
//        /* 文件信息 */
//
//
//        /* 问题说明 */
//        // 产品质量问题说明
//        List<InstOrUpdtNotesDTO> orderNotes = dto.getOrderNotes();
//        List<XqProblemNote> toAddNotes = new ArrayList<>();
//        for (InstOrUpdtNotesDTO addCom : orderNotes) {
//            XqProblemNote newCom = new XqProblemNote();
//            BeanUtils.copyProperties(addCom, newCom);
//            newCom.setModuleType(CommonConstant.ORDER_MODULE);
//            newCom.setId(null);
//            newCom.setOrderId(xqOrder.getId());
//            toAddNotes.add(newCom);
//        }
//        xqProblemNoteService.saveBatch(toAddNotes);
//        // 原料质量问题说明
//        List<InstOrUpdtNotesDTO> rawNotes = dto.getRawNotes();
//        List<XqProblemNote> toAddNotes2 = new ArrayList<>();
//        for (InstOrUpdtNotesDTO addCom : rawNotes) {
//            XqProblemNote newCom = new XqProblemNote();
//            BeanUtils.copyProperties(addCom, newCom);
//            newCom.setModuleType(CommonConstant.RAW_MODULE);
//            newCom.setId(null);
//            newCom.setOrderId(xqOrder.getId());
//            toAddNotes2.add(newCom);
//        }
//        xqProblemNoteService.saveBatch(toAddNotes2);
//        // 辅料质量问题说明
//        List<InstOrUpdtNotesDTO> remiNotes = dto.getRemiNotes();
//        List<XqProblemNote> toAddNotes3 = new ArrayList<>();
//        for (InstOrUpdtNotesDTO addCom : remiNotes) {
//            XqProblemNote newCom = new XqProblemNote();
//            BeanUtils.copyProperties(addCom, newCom);
//            newCom.setModuleType(CommonConstant.REMITTANCE_MODULE);
//            newCom.setId(null);
//            newCom.setOrderId(xqOrder.getId());
//            toAddNotes3.add(newCom);
//        }
//        xqProblemNoteService.saveBatch(toAddNotes3);


        return true;
    }

    @Override
    public XqOrderCompleteMsgVO queryCompleteState() {
        List<Integer> roleCodes = FilterContextHandler.getUserInfo().getRoleCodes();
        String userId = FilterContextHandler.getUserInfo().getId();
        if (CollectionUtil.isEmpty(roleCodes)) {
            return null;
        }
        return getMsgByRole(roleCodes, userId);
    }


    /**
     * 面单小红点提醒流程 (旧版):
     * 1、跟单最终确认后通知 -> 跟单经理审核
     * 2、跟单经理审核完成后通知 -> 国外财务(收汇)、原料采购、货运
     * 3、原料采购最终确认后通知 -> 辅料采购员，出纳
     * 4、辅料采购，货运都最终确认后通知 -> 国内财务
     * <p>
     * <p>
     * 面单小红点提醒流程（新版）:
     * 1、跟单最终确认后通知 -> 跟单经理审核
     * 2、跟单经理审核完成后通知 -> 国外财务(收汇)、原料采购、货运
     * 3、原料采购最终确认后通知 —> 辅料采购员
     * <p>
     * 4、副总角色可看到实际交货日后的订单
     *
     * @param roleCodes
     * @return
     */
    public XqOrderCompleteMsgVO getMsgByRole(List<Integer> roleCodes, String userId) {
        if (roleCodes.isEmpty()) {
            return null;
        }
        String followerAudit = "1";
        // 角色是 1 跟单 9 跟单经理
        if (roleCodes.contains(CommonConstant.ORDER_FOLLOWER) ||
                roleCodes.contains(CommonConstant.ORDER_FOLLOWER_MANAGER)
        ) {
            followerAudit = "0";
        }

        // 查询当前角色已最终确认的订单id
        Set<String> unCompleteOrderNums = new HashSet<>();


        XqOrderCompleteMsgVO xqOrderCompleteMsgVO = new XqOrderCompleteMsgVO();
        xqOrderCompleteMsgVO.setType(1);

        // 排查 admmin角色
        if (!roleCodes.contains(0)) {
            // 1 跟单角色
            if (roleCodes.contains(CommonConstant.ORDER_FOLLOWER)) {
                unCompleteOrderNums = xqOrderFinallyConfirmService.selectOrderNumByRoleCodes(CommonConstant.ORDER_FOLLOWER, followerAudit, null, userId);
            }
            // 9 跟单经理
            if (roleCodes.contains(CommonConstant.ORDER_FOLLOWER_MANAGER)) {
                Set<String> managers = xqOrderFinallyConfirmService.selectOrderNumByRoleCodes(CommonConstant.ORDER_FOLLOWER_MANAGER, followerAudit, CommonConstant.ORDER_FOLLOWER, userId);
                if (!managers.isEmpty()) {
                    unCompleteOrderNums.addAll(managers);
                }

            }

            // 2 国内财务
//            if (roleCodes.contains(CommonConstant.DOMESTIC_FINANCE)) {
//                Set<String> domesticFinances = xqOrderFinallyConfirmService.selectOrderNumByRoles(CommonConstant.DOMESTIC_FINANCE, followerAudit, CommonConstant.ACCESSORY_PURCHASE, CommonConstant.SHIPPING);
//                if (!domesticFinances.isEmpty()) {
//                    unCompleteOrderNums.addAll(domesticFinances);
//                }
//
//            }
            // 3 国外财务
            if (roleCodes.contains(CommonConstant.FOREIGN_FINANCE)) {
                Set<String> foreignFinances = xqOrderFinallyConfirmService.selectOrderNumByRoleCodes(CommonConstant.FOREIGN_FINANCE, followerAudit, null, userId);
                if (!foreignFinances.isEmpty()) {
                    unCompleteOrderNums.addAll(foreignFinances);
                }

            }
            // 4 出纳
//            if (roleCodes.contains(CommonConstant.CASHIER)) {
//                Set<String> cashiers = xqOrderFinallyConfirmService.selectOrderNumByRoleCodes(CommonConstant.CASHIER, followerAudit, CommonConstant.MATERIAL_PURCHASE);
//                if (!cashiers.isEmpty()) {
//                    unCompleteOrderNums.addAll(cashiers);
//                }
//
//            }
            // 5 原料采购
            if (roleCodes.contains(CommonConstant.MATERIAL_PURCHASE)) {
                Set<String> materialPurchases = xqOrderFinallyConfirmService.selectOrderNumByRoleCodes(CommonConstant.MATERIAL_PURCHASE, followerAudit, null, userId);
                if (!materialPurchases.isEmpty()) {
                    unCompleteOrderNums.addAll(materialPurchases);
                }

            }

            // 6 辅料采购
            if (roleCodes.contains(CommonConstant.ACCESSORY_PURCHASE)) {
                Set<String> accessoryPurchase = xqOrderFinallyConfirmService.selectOrderNumByRoleCodes(CommonConstant.ACCESSORY_PURCHASE, followerAudit, CommonConstant.MATERIAL_PURCHASE, userId);
                if (!accessoryPurchase.isEmpty()) {
                    unCompleteOrderNums.addAll(accessoryPurchase);
                }

            }
            // 7 货运
            if (roleCodes.contains(CommonConstant.SHIPPING)) {
                Set<String> shippings = xqOrderFinallyConfirmService.selectOrderNumByRoleCodes(CommonConstant.SHIPPING, followerAudit, null, userId);
                if (!shippings.isEmpty()) {
                    unCompleteOrderNums.addAll(shippings);
                }
            }

            // 10 副总
            if (roleCodes.contains(CommonConstant.DEPUTY_GENERAL_MANAGER)) {
                Set<String> shippings = xqOrderFinallyConfirmService.selectOrderNumByRoleCodes(CommonConstant.DEPUTY_GENERAL_MANAGER, followerAudit, null, userId);
                if (!shippings.isEmpty()) {
                    unCompleteOrderNums = shippings;
                } else {
                    unCompleteOrderNums = new HashSet<>();
                }
            }

        }

//        Integer unCompleteNum = 0;
//        List<String> unCompleteOrderNums = new ArrayList<>();
//        if (roleCodes.contains(CommonConstant.ORDER_FOLLOWER.toString())) {
//            for (XqOrder xqOrder : list) {
//                String completeState = xqOrder.getCompleteState();
//                if (!completeState.contains(CommonConstant.ORDER_FOLLOWER.toString())
//                        && xqOrder.getFollowerAudit().equals("0")) {
//                    unCompleteNum += 1;
//                    unCompleteOrderNums.add(xqOrder.getOrderNum());
//                }
//            }
//
//        } else if (roleCode.equals(CommonConstant.ORDER_FOLLOWER_MANAGER)) {
//            for (XqOrder xqOrder : list) {
//                String completeState = xqOrder.getCompleteState();
//                if (completeState.contains(CommonConstant.ORDER_FOLLOWER.toString())
//                        && xqOrder.getFollowerAudit().equals("0")) {
//                    unCompleteNum += 1;
//                    unCompleteOrderNums.add(xqOrder.getOrderNum());
//                }
//            }
//
//        } else if (roleCode.equals(CommonConstant.DOMESTIC_FINANCE)) {
//            for (XqOrder xqOrder : list) {
//                String completeState = xqOrder.getCompleteState();
//                if ((completeState.contains(CommonConstant.ORDER_FOLLOWER.toString())
//                        || completeState.contains(CommonConstant.ACCESSORY_PURCHASE.toString())
//                        || completeState.contains(CommonConstant.SHIPPING.toString()))
//                        && !completeState.contains(CommonConstant.DOMESTIC_FINANCE.toString())
//                        && xqOrder.getFollowerAudit().equals("1")) {
//                    unCompleteNum += 1;
//                    unCompleteOrderNums.add(xqOrder.getOrderNum());
//                }
//            }
//
//        } else if (roleCode.equals(CommonConstant.FOREIGN_FINANCE)) {
//            for (XqOrder xqOrder : list) {
//                String completeState = xqOrder.getCompleteState();
//                if (completeState.contains(CommonConstant.ORDER_FOLLOWER.toString())
//                        && !completeState.contains(CommonConstant.FOREIGN_FINANCE.toString())
//                        && xqOrder.getFollowerAudit().equals("1")) {
//                    unCompleteNum += 1;
//                    unCompleteOrderNums.add(xqOrder.getOrderNum());
//                }
//            }
//        } else if (roleCode.equals(CommonConstant.CASHIER)) {
//            for (XqOrder xqOrder : list) {
//                String completeState = xqOrder.getCompleteState();
//                if (completeState.contains(CommonConstant.MATERIAL_PURCHASE.toString())
//                        && !completeState.contains(CommonConstant.CASHIER.toString())
//                        && xqOrder.getFollowerAudit().equals("1")) {
//                    unCompleteNum += 1;
//                    unCompleteOrderNums.add(xqOrder.getOrderNum());
//                }
//            }
//
//        } else if (roleCode.equals(CommonConstant.MATERIAL_PURCHASE)) {
//            for (XqOrder xqOrder : list) {
//                String completeState = xqOrder.getCompleteState();
//                if (completeState.contains(CommonConstant.ORDER_FOLLOWER.toString())//跟单员
//                        && !completeState.contains(CommonConstant.MATERIAL_PURCHASE.toString())
//                        && xqOrder.getFollowerAudit().equals("1")) {
//                    unCompleteNum += 1;
//                    unCompleteOrderNums.add(xqOrder.getOrderNum());
//                }
//            }
//
//        } else if (roleCode.equals(CommonConstant.ACCESSORY_PURCHASE)) {
//            for (XqOrder xqOrder : list) {
//                String completeState = xqOrder.getCompleteState();
//                if (completeState.contains(CommonConstant.MATERIAL_PURCHASE.toString())
//                        && !completeState.contains(CommonConstant.ACCESSORY_PURCHASE.toString())
//                        && xqOrder.getFollowerAudit().equals("1")) {
//                    unCompleteNum += 1;
//                    unCompleteOrderNums.add(xqOrder.getOrderNum());
//                }
//            }
//
//        } else if (roleCode.equals(CommonConstant.SHIPPING)) {
//            for (XqOrder xqOrder : list) {
//                String completeState = xqOrder.getCompleteState();
//                if (completeState.contains(CommonConstant.ORDER_FOLLOWER.toString())
//                        && !completeState.contains(CommonConstant.SHIPPING.toString())
//                        && xqOrder.getFollowerAudit().equals("1")) {
//                    unCompleteNum += 1;
//                    unCompleteOrderNums.add(xqOrder.getOrderNum());
//                }
//            }
//        }

        xqOrderCompleteMsgVO.setUnCompleteNum(unCompleteOrderNums.size());
        xqOrderCompleteMsgVO.setUnCompleteOrderNums(unCompleteOrderNums);
        return xqOrderCompleteMsgVO;
    }

    @Override
    public boolean removeDetail(String id) {
        XqOrder byId = this.getById(id);

        Set<String> collect = xqAccessoriesPurchaseService.lambdaQuery()
                .eq(XqAccessoriesPurchase::getOrderId, byId.getId())
                .list().stream().map(XqAccessoriesPurchase::getId).collect(Collectors.toSet());

        if (CollectionUtil.isNotEmpty(collect)) {
            /* 删除库存子表 */
            xqInventoryService.lambdaUpdate()
                    .in(XqInventory::getSourceId, collect)
                    .eq(XqInventory::getSourceType, CommonConstant.INVENTORY_FLCG)
                    .remove();
        }


        return true;
    }

    @Override
    public XqOrderAllVO copyOrdersViews(String id) {
        /* 订单主表 */
        XqOrderAllVO vo = this.baseMapper.getOrderHead(id);
        String orderFollowUpPerson = vo.getOrderFollowUpPerson();
        if (StringUtils.isBlank(orderFollowUpPerson)) {
            vo.setOrderFollowUpPerson(FilterContextHandler.getUserInfo().getRealname());
        }


        /* 订单产品子表 */
        List<XqOrderProdVO> xqOrderProdVOS = this.baseMapper.listOrderProds(id);
        xqOrderProdVOS.forEach(xqOrderProdVO -> xqOrderProdVO.setId(null));
        vo.setOrderProdVOS(xqOrderProdVOS);

        /* 佣金表 */
        List<XqOrderComsVO> xqOrderComsVOS = this.baseMapper.listOrderComs(id);
        if (xqOrderComsVOS != null && xqOrderComsVOS.size() > 0) {
            /* 查询已插入的记录 */
            for (XqOrderComsVO vo1 : xqOrderComsVOS) {
                vo1.setFinanceAuditDate(null);
                vo1.setFinanceConfirmedAmount(BigDecimal.ZERO);
            }
            vo.setOrderComsVOS(xqOrderComsVOS);
        } else {
            vo.setOrderComsVOS(Collections.emptyList());
        }

        vo.setId(null);
        vo.setOrderNum(null);
        vo.setInvoiceNum(null);
        vo.setOverseasReceiver(null);
        vo.setDomesticSender(null);
        vo.setInvoiceDate(null);
        vo.setProductionDate(null);
        vo.setMaskedContent(null);

        return vo;
    }

    @Override
    public IPage<SellProfitPageVO> pageSellProfit(QueryPageSellProfitDTO dto) {
        Page<SellProfitPageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        // 多客户筛选处理
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        IPage<SellProfitPageVO> sellProfitPageVOIPage = this.baseMapper.pageSellProfit(page, dto, customerIds);
        // 序号
        List<SellProfitPageVO> records = sellProfitPageVOIPage.getRecords();
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        int recordCount = 1;
        // 差旅费用
        BigDecimal travelFee = BigDecimal.ZERO;
        // 货物保险费
        BigDecimal insuranceFee = BigDecimal.ZERO;
        // 信保保险费
        BigDecimal creditInsurancePremium = BigDecimal.ZERO;
        // 总中间商佣金
        BigDecimal middleManCommission = BigDecimal.ZERO;
        // 总第三方进口佣金
        BigDecimal thirdPartImportCommission = BigDecimal.ZERO;
        // 客户佣金
        BigDecimal customerCommission = BigDecimal.ZERO;
        // 质量扣款
        BigDecimal qualityDeduction = BigDecimal.ZERO;
        // 折扣
        BigDecimal discount = BigDecimal.ZERO;
        // 保理利息
        BigDecimal factoringInterest = BigDecimal.ZERO;
        // 国内海运费
        BigDecimal domesticShippingFee = BigDecimal.ZERO;
        // 总出港前总费用
        BigDecimal beforeDepartureTotal = BigDecimal.ZERO;
        // 总服务费
        BigDecimal serviceCharge = BigDecimal.ZERO;
        // 国外卡车费
        BigDecimal foreignTruckFee = BigDecimal.ZERO;
        // 清关关税
        BigDecimal customsClearanceTaxFee = BigDecimal.ZERO;
        // 国外清关费
        BigDecimal foreignCustomsClearanceFee = BigDecimal.ZERO;
        // 额外费用
        BigDecimal extraFee = BigDecimal.ZERO;
        // 总收汇额外费用
        BigDecimal collectionAdditionalFees = BigDecimal.ZERO;
        //
        BigDecimal salesProfit = BigDecimal.ZERO;
        // 辅料采购金额（辅料合计->除以汇率前）
        BigDecimal allCost = BigDecimal.ZERO;
        //
        BigDecimal totalExtraCosts = BigDecimal.ZERO;

        // 辅料采购金额（辅料合计2）
        BigDecimal accessoryPurchaseTotal1 = BigDecimal.ZERO;
        //BigDecimal accessoryPurchaseTotal2 = BigDecimal.ZERO;
        for (SellProfitPageVO record : records) {
            int currentCount1 = record.getRecordCount();
            String orderNum = record.getOrderNum();
            int totalCount = (int) records.stream()
                    .filter(sp -> sp.getOrderNum().equals(orderNum))
                    .count();

            XqCommissionDistribution xqCommissionDistribution = xqCommissionDistributionService.getOne(new LambdaQueryWrapper<XqCommissionDistribution>()
                    .eq(XqCommissionDistribution::getProductId, record.getProductId())
                    .eq(XqCommissionDistribution::getOrderDetailId, record.getOrderDetailId()).last("limit 1"));
            if (xqCommissionDistribution != null) {
                record.setMiddleManCommission(xqCommissionDistribution.getCommissionPrice());
            }


            // 判断当前订单号是否与上一个一致
            // String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                    recordCount = 1;
                    salesProfit = BigDecimal.ZERO;
                }
                if (currentCount1 != totalCount) {
                    if (recordCount == totalCount) {
                        //这样的话取的就是相同订单里面最后一个。重新重头计算
                        record = singleProfit(record);
                        continue;
                    }
                }
                currentSerialNumber++;
            } else {//跨页后相同订单里面拼柜不显示
                if (currentCount1 != totalCount) {
                    if (recordCount == totalCount) {
                        //这样的话取的就是相同订单里面最后一个。重新重头计算
                        record = singleProfit(record);
                        continue;
                    }
                }
            }
            record.setSerialNumber(currentSerialNumber);
            if (orderNum.equals(currentOrderNumber)) {
                if ("USD".equals(record.getRawCurrency())) {
                    record.setBeforeDepartureTotal(BigDecimal.ZERO);
                }
                if (recordCount == 1) {
                    // wn 20240408
//                    XqOrder xqOrder = this.getOne(new LambdaQueryWrapper<XqOrder>().eq(XqOrder::getOrderNum, record.getOrderNum()));
//                    List<XqOrderExtraCost> extraCosts = xqOrderExtraCostService.list(new LambdaQueryWrapper<XqOrderExtraCost>().eq(XqOrderExtraCost::getOrderId, xqOrder.getId()));
//                    for (XqOrderExtraCost cost : extraCosts) {
//                        if (cost.getCurrency() != "USD") {
//                            XqOrderDetail xqOrderDetail = xqOrderDetailService.getById(record.getOrderDetailId());
//                            if (xqOrderDetail != null && cost.getPrice() != null) {
//                                if (xqOrderDetail.getCurrency().equals(cost.getCurrency())) {
//                                    totalExtraCosts = totalExtraCosts.add(cost.getPrice().multiply(xqOrderDetail.getDetailExchangeRate() == null ? new BigDecimal(6.5) : xqOrderDetail.getDetailExchangeRate()));
//                                }
//                            }
//                        } else {
//                            totalExtraCosts = totalExtraCosts.add(cost.getPrice());
//                        }
//
//                    }
                    // wn 20240408
                    record.setSalesAmount(record.getSalesAmount().add(totalExtraCosts));
                    totalExtraCosts = BigDecimal.ZERO;
                }
                if (record.getRecordCount() > 1) {
                    if (record.getRecordCount() != recordCount) {
                        travelFee = travelFee.add(record.getTravelFee());
                        insuranceFee = insuranceFee.add(record.getInsuranceFee());
                        creditInsurancePremium = creditInsurancePremium.add(record.getCreditInsurancePremium());
                        middleManCommission = middleManCommission.add(record.getMiddleManCommission());
                        thirdPartImportCommission = thirdPartImportCommission.add(record.getThirdPartImportCommission());
                        customerCommission = customerCommission.add(record.getCustomerCommission());
                        qualityDeduction = qualityDeduction.add(record.getQualityDeduction());
                        discount = discount.add(record.getDiscount());
                        factoringInterest = factoringInterest.add(record.getFactoringInterest());
                        domesticShippingFee = domesticShippingFee.add(record.getDomesticShippingFee());
                        foreignTruckFee = foreignTruckFee.add(record.getForeignTruckFee());
//                        if (record.getTaxRefundAmount().compareTo(BigDecimal.ZERO) == 0) {
//                            record.setBeforeDepartureTotal(beforeDepartureTotal);
//                        } else {
//                            beforeDepartureTotal = beforeDepartureTotal.add(record.getBeforeDepartureTotal());
//                        }


                        beforeDepartureTotal = beforeDepartureTotal.add(record.getBeforeDepartureTotal());

                        serviceCharge = serviceCharge.add(record.getServiceCharge());
                        customsClearanceTaxFee = customsClearanceTaxFee.add(record.getCustomsClearanceTaxFee());
                        foreignCustomsClearanceFee = foreignCustomsClearanceFee.add(record.getForeignCustomsClearanceFee());
                        extraFee = extraFee.add(record.getExtraFee());
                        collectionAdditionalFees = collectionAdditionalFees.add(record.getCollectionAdditionalFees());
                        accessoryPurchaseTotal1 = accessoryPurchaseTotal1.add(record.getAccessoryPurchaseTotal1());
                        // accessoryPurchaseTotal2 = accessoryPurchaseTotal2.add(record.getAccessoryPurchaseTotal2());

                        //重新计算每磅和销售利润
                        allCost = record.getPurchaseAmount()
                                .add(record.getCustomerCommission())
                                .add(record.getThirdPartImportCommission())
                                .add(record.getMiddleManCommission())
                                .add(record.getServiceCharge())
                                .add(record.getQualityDeduction())
                                .add(record.getDiscount())
                                .add(record.getCreditInsurancePremium())
                                .add(record.getReturnFee())
                                .add(record.getFactoringInterest())
                                .add(record.getAccessoryPurchaseTotal())
                                .add(record.getAccessoryPurchaseTotal1())
                                //.add(record.getAccessoryPurchaseTotal2())
                                .add(record.getBeforeDepartureTotal())
                                .add(record.getInsuranceFee())
                                .add(record.getDomesticShippingFee())
                                .add(record.getCustomsClearanceTaxFee())
                                .add(record.getForeignCustomsClearanceFee())
                                .add(record.getForeignTruckFee())
                                .subtract(record.getTaxRefundAmount())
                                .add(record.getTravelFee())
                                .add(record.getExtraFee());
                        //重新计算最后一个的销售利润
                        // TODO 利润再加上收汇额外费用
                        record.setSellProfit(record.getSalesAmount().add(record.getCollectionAdditionalFees()).add(record.getCutAmount()).subtract(allCost));
                        record.setCostPerLb(allCost.divide(record.getTotalWeight(), 3, RoundingMode.HALF_UP));
                        record.setLclSellProfit(BigDecimal.ZERO);
                        salesProfit = salesProfit.add(record.getSellProfit());
                        allCost = BigDecimal.ZERO;
                        //销售利润、每磅成本、拼柜总成本重新算
                    } else {
                        record.setTravelFee(record.getTotalTravelFee().subtract(travelFee));
                        record.setInsuranceFee(record.getTotalInsuranceFee().subtract(insuranceFee));
                        record.setCreditInsurancePremium(record.getTotalCreditInsurancePremium().subtract(creditInsurancePremium));
                        record.setMiddleManCommission(record.getTotalMiddleManCommission().subtract(middleManCommission));
                        record.setThirdPartImportCommission(record.getTotalThirdPartImportCommission().subtract(thirdPartImportCommission));
                        record.setCustomerCommission(record.getTotalCustomerCommission().subtract(customerCommission));
                        record.setQualityDeduction(record.getTotalQualityDeduction().subtract(qualityDeduction));
                        record.setDiscount(record.getTotalDiscount().subtract(discount));
                        record.setFactoringInterest(record.getTotalFactoringInterest().subtract(factoringInterest));
                        record.setDomesticShippingFee(record.getTotalDomesticShippingFee().subtract(domesticShippingFee));
//                        if (record.getTaxRefundAmount().compareTo(BigDecimal.ZERO) == 0) {
//                            record.setBeforeDepartureTotal(record.getTotalBeforeDepartureTotal());
//                        } else {
//                            record.setBeforeDepartureTotal(record.getTotalBeforeDepartureTotal().subtract(beforeDepartureTotal));
//                        }


                        record.setBeforeDepartureTotal(record.getTotalBeforeDepartureTotal().subtract(beforeDepartureTotal));

                        record.setServiceCharge(record.getTotalServiceCharge().subtract(serviceCharge));
                        record.setForeignTruckFee(record.getTotalForeignTruckFee().subtract(foreignTruckFee));
                        record.setCustomsClearanceTaxFee(record.getTotalCustomsClearanceTaxFee().subtract(customsClearanceTaxFee));
                        record.setForeignCustomsClearanceFee(record.getTotalForeignCustomsClearanceFee().subtract(foreignCustomsClearanceFee));
                        record.setExtraFee(record.getTotalExtraFee().subtract(extraFee));
                        record.setCollectionAdditionalFees(record.getTotalCollectionAdditionalFees().subtract(collectionAdditionalFees));
                        record.setAccessoryPurchaseTotal1(record.getTotalAccessoryPurchaseTotal1().subtract(accessoryPurchaseTotal1));
                        // record.setAccessoryPurchaseTotal2(record.getTotalAccessoryPurchaseTotal2().subtract(accessoryPurchaseTotal2));
                        allCost = record.getPurchaseAmount()
                                .add(record.getCustomerCommission())
                                .add(record.getThirdPartImportCommission())
                                .add(record.getBeforeDepartureTotal())
                                .add(record.getAccessoryPurchaseTotal())
                                .add(record.getMiddleManCommission())
                                .add(record.getQualityDeduction())
                                .add(record.getDiscount())
                                .add(record.getCreditInsurancePremium())
                                .add(record.getReturnFee())
                                .add(record.getFactoringInterest())
                                .add(record.getAccessoryPurchaseTotal1())
                                //.add(record.getAccessoryPurchaseTotal2())
                                .add(record.getInsuranceFee())
                                .add(record.getDomesticShippingFee())
                                .add(record.getCustomsClearanceTaxFee())
                                .add(record.getForeignCustomsClearanceFee())
                                .add(record.getForeignTruckFee())
                                .add(record.getServiceCharge())
                                .subtract(record.getTaxRefundAmount())
                                .add(record.getTravelFee())
                                .add(record.getExtraFee());
                        //重新计算最后一个的销售利润
                        record.setSellProfit(record.getSalesAmount().add(record.getCollectionAdditionalFees()).add(record.getCutAmount()).subtract(allCost));
                        record.setCostPerLb(allCost.divide(record.getTotalWeight(), 3, RoundingMode.HALF_UP));

                        salesProfit = salesProfit.add(record.getSellProfit());
                        record.setLclSellProfit(salesProfit);
                        travelFee = BigDecimal.ZERO;
                        insuranceFee = BigDecimal.ZERO;
                        creditInsurancePremium = BigDecimal.ZERO;
                        middleManCommission = BigDecimal.ZERO;
                        thirdPartImportCommission = BigDecimal.ZERO;
                        customerCommission = BigDecimal.ZERO;
                        qualityDeduction = BigDecimal.ZERO;
                        discount = BigDecimal.ZERO;
                        factoringInterest = BigDecimal.ZERO;
                        domesticShippingFee = BigDecimal.ZERO;
                        beforeDepartureTotal = BigDecimal.ZERO;
                        serviceCharge = BigDecimal.ZERO;
                        foreignTruckFee = BigDecimal.ZERO;
                        customsClearanceTaxFee = BigDecimal.ZERO;
                        foreignCustomsClearanceFee = BigDecimal.ZERO;
                        extraFee = BigDecimal.ZERO;
                        collectionAdditionalFees = BigDecimal.ZERO;
                        salesProfit = BigDecimal.ZERO;
                        allCost = BigDecimal.ZERO;
                        totalExtraCosts = BigDecimal.ZERO;

                        accessoryPurchaseTotal1 = BigDecimal.ZERO;
                        //accessoryPurchaseTotal2 = BigDecimal.ZERO;
                    }
                    //   if (record.getRecordCount()!=recordCount)

                } else {
                    record.setBeforeDepartureTotal(record.getTotalBeforeDepartureTotal());
                    //重新计算每磅和销售利润
                    allCost = record.getPurchaseAmount()
                            .add(record.getCustomerCommission())
                            .add(record.getThirdPartImportCommission())
                            .add(record.getBeforeDepartureTotal())
                            .add(record.getAccessoryPurchaseTotal())
                            .add(record.getMiddleManCommission())
                            .add(record.getQualityDeduction())
                            .add(record.getDiscount())
                            .add(record.getCreditInsurancePremium())
                            .add(record.getFactoringInterest())
                            .add(record.getAccessoryPurchaseTotal1())
                            //.add(record.getAccessoryPurchaseTotal2())
                            .add(record.getInsuranceFee())
                            .add(record.getDomesticShippingFee())
                            .add(record.getReturnFee())
                            .add(record.getCustomsClearanceTaxFee())
                            .add(record.getForeignCustomsClearanceFee())
                            .add(record.getForeignTruckFee())
                            //.add(record.getCollectionAdditionalFees())
                            .add(record.getServiceCharge())
                            .subtract(record.getTaxRefundAmount())
                            .add(record.getTravelFee())
                            .add(record.getExtraFee());
                    //重新计算最后一个的销售利润
                    record.setSellProfit(record.getSalesAmount().add(record.getCollectionAdditionalFees()).add(record.getCutAmount()).subtract(allCost));
                    System.out.println("allCost: " + allCost + ",totalWeight: " + record.getTotalWeight() + record.getOrderDetailId());
                    record.setCostPerLb(allCost.divide(record.getTotalWeight(), 3, RoundingMode.HALF_UP));
                    salesProfit = salesProfit.add(record.getSellProfit());
                    record.setLclSellProfit(BigDecimal.ZERO);
                }
            }

            recordCount++;
        }


        return sellProfitPageVOIPage;
    }


    @Autowired
    private XqSellingProfitMapper xqSellingProfitMapper;

    @Override
    public IPage<SellProfitPageVO> pageSellProfitNew(QueryPageSellProfitDTO dto) {
        Page<SellProfitPageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        // 多客户筛选处理
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        IPage<SellProfitPageVO> sellProfitPageVOIPage = xqSellingProfitMapper.pageSellProfitNew(page, dto, customerIds);
        if (sellProfitPageVOIPage != null && sellProfitPageVOIPage.getRecords().size() > 0) {
            List<SellProfitPageVO> records = sellProfitPageVOIPage.getRecords();

            // 序号

            String currentOrderNumber = UUID.randomUUID().toString();
            int currentSerialNumber = 0;

            for (SellProfitPageVO record : records) {
                // 判断当前订单号是否与上一个一致
                String orderNum = record.getOrderNum();
                if (!currentOrderNumber.equals(orderNum)) {
                    if (orderNum == null) {
                        currentOrderNumber = "";
                    } else {
                        currentOrderNumber = orderNum;
                    }
                    currentSerialNumber++;
                }
                record.setSerialNumber(currentSerialNumber);

            }
        }

        return sellProfitPageVOIPage;
    }


    @Override
    public List<SellProfitTotalVO> totalSellProfitNew(QueryPageSellProfitDTO dto) {
        // 多客户筛选处理
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        List<SellProfitTotalVO> list = new ArrayList<>();
        SellProfitTotalVO vo = xqSellingProfitMapper.getTotal(dto, customerIds);
        if (vo == null) {
            vo = new SellProfitTotalVO();
        }
        list.add(vo);
        return list;
    }

    @Override
    public boolean cancelOrderStatus(String orderNum) {

        LoginUser userInfo = FilterContextHandler.getUserInfo();

        // 如果是副总角色
        if (userInfo.getRoleCodes().contains(10)) {

            // 查下订单是否存在
            XqOrder xqOrder = this.lambdaQuery().eq(XqOrder::getOrderNum, orderNum).last("limit 1").one();
            if (xqOrder == null) {
                throw new InterestingBootException("订单不存在！");
            }
            List<XqOrderFinallyConfirm> list = xqOrderFinallyConfirmService
                    .lambdaQuery()
                    .eq(XqOrderFinallyConfirm::getOrderId, xqOrder.getId())
                    .in(XqOrderFinallyConfirm::getRoleId, 10)
                    .eq(XqOrderFinallyConfirm::getUserId, FilterContextHandler.getUserId())
                    .list();


            // 没有确认过进插入新的确认信息

            if (list == null || list.size() == 0) {
                XqOrderFinallyConfirm xqOrderFinallyConfirm = new XqOrderFinallyConfirm();
                xqOrderFinallyConfirm.setOrderId(xqOrder.getId());
                xqOrderFinallyConfirm.setUserId(userInfo.getId());
                xqOrderFinallyConfirm.setRoleId("10");
                return xqOrderFinallyConfirmService.save(xqOrderFinallyConfirm);
            }

        } else if (userInfo.getRoleCodes().contains(3)) {
            // 查下订单是否存在
            XqOrder xqOrder = this.lambdaQuery().eq(XqOrder::getOrderNum, orderNum).last("limit 1").one();
            if (xqOrder == null) {
                throw new InterestingBootException("订单不存在！");
            }
            List<XqOrderFinallyConfirm> list = xqOrderFinallyConfirmService
                    .lambdaQuery()
                    .eq(XqOrderFinallyConfirm::getOrderId, xqOrder.getId())
                    .in(XqOrderFinallyConfirm::getRoleId, 3)
                    .eq(XqOrderFinallyConfirm::getUserId, FilterContextHandler.getUserId())
                    .list();


            // 没有确认过进插入新的确认信息

            if (list == null || list.size() == 0) {
                XqOrderFinallyConfirm xqOrderFinallyConfirm = new XqOrderFinallyConfirm();
                xqOrderFinallyConfirm.setOrderId(xqOrder.getId());
                xqOrderFinallyConfirm.setUserId(userInfo.getId());
                xqOrderFinallyConfirm.setRoleId("3");
                return xqOrderFinallyConfirmService.save(xqOrderFinallyConfirm);
            }
        }

        return true;
    }

    @Override
    public String getShippingNum(Date etd) {
        //获取年
        SimpleDateFormat sf = new SimpleDateFormat("yyyy");
        String year = sf.format(etd);
        //获取数据库里最新编号
        XqOrder xqOrder = this.lambdaQuery().apply("shipping_num regexp {0}", "^" + year + "[0-9]{4}$")
                .last("order by right(shipping_num, 4) desc limit 1").one();
        //生成最新的编号
        String shipping_num = year;
        if (!ObjectUtils.isEmpty(xqOrder)) {
            if (StringUtils.isNotEmpty(xqOrder.getShippingNum())) {
                String lastNum = xqOrder.getShippingNum().substring(xqOrder.getShippingNum().length() - 4);
                String newNum = String.format("%04d", Integer.parseInt(lastNum) + 1);
                shipping_num = shipping_num + newNum;
            } else {
                String newNum = String.format("%04d", 1);
                shipping_num = shipping_num + newNum;
            }
        } else {
            String newNum = String.format("%04d", 1);
            shipping_num = shipping_num + newNum;
        }
        return shipping_num;
    }


    private SellProfitPageVO singleProfit(SellProfitPageVO record1) {
        int recordCount = 1;
        BigDecimal travelFee = BigDecimal.ZERO;
        BigDecimal insuranceFee = BigDecimal.ZERO;
        BigDecimal creditInsurancePremium = BigDecimal.ZERO;
        BigDecimal middleManCommission = BigDecimal.ZERO;
        BigDecimal thirdPartImportCommission = BigDecimal.ZERO;
        BigDecimal customerCommission = BigDecimal.ZERO;
        BigDecimal qualityDeduction = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;
        BigDecimal factoringInterest = BigDecimal.ZERO;
        BigDecimal domesticShippingFee = BigDecimal.ZERO;
        BigDecimal beforeDepartureTotal = BigDecimal.ZERO;
        BigDecimal serviceCharge = BigDecimal.ZERO;
        BigDecimal foreignTruckFee = BigDecimal.ZERO;
        BigDecimal customsClearanceTaxFee = BigDecimal.ZERO;
        BigDecimal foreignCustomsClearanceFee = BigDecimal.ZERO;
        BigDecimal extraFee = BigDecimal.ZERO;
        BigDecimal collectionAdditionalFees = BigDecimal.ZERO;
        BigDecimal salesProfit = BigDecimal.ZERO;
        BigDecimal allCost = BigDecimal.ZERO;
        BigDecimal totalExtraCosts = BigDecimal.ZERO;

        BigDecimal accessoryPurchaseTotal1 = BigDecimal.ZERO;
        //BigDecimal accessoryPurchaseTotal2 = BigDecimal.ZERO;

        if (record1.getRecordCount() > 1) {
            QueryPageSellProfitDTO dto = new QueryPageSellProfitDTO();
            dto.setOrderNum(record1.getOrderNum());
            Page<SellProfitPageVO> page = new Page<>(1, 99);
            IPage<SellProfitPageVO> sellProfitPageVOIPage = this.baseMapper.pageSellProfit(page, dto, null);
            for (SellProfitPageVO record : sellProfitPageVOIPage.getRecords()) {
                if (record.getRecordCount() != recordCount) {
                    travelFee = travelFee.add(record.getTravelFee());
                    insuranceFee = insuranceFee.add(record.getInsuranceFee());
                    creditInsurancePremium = creditInsurancePremium.add(record.getCreditInsurancePremium());
                    middleManCommission = middleManCommission.add(record.getMiddleManCommission());
                    thirdPartImportCommission = thirdPartImportCommission.add(record.getThirdPartImportCommission());
                    customerCommission = customerCommission.add(record.getCustomerCommission());
                    qualityDeduction = qualityDeduction.add(record.getQualityDeduction());
                    discount = discount.add(record.getDiscount());
                    factoringInterest = factoringInterest.add(record.getFactoringInterest());
                    domesticShippingFee = domesticShippingFee.add(record.getDomesticShippingFee());
                    foreignTruckFee = foreignTruckFee.add(record.getForeignTruckFee());
//                    if (record.getTaxRefundAmount().compareTo(BigDecimal.ZERO) == 0) {
//                        record.setBeforeDepartureTotal(BigDecimal.ZERO);
//                    } else {
//                        beforeDepartureTotal = beforeDepartureTotal.add(record.getBeforeDepartureTotal());
//                    }


                    beforeDepartureTotal = beforeDepartureTotal.add(record.getBeforeDepartureTotal());

                    serviceCharge = serviceCharge.add(record.getServiceCharge());
                    customsClearanceTaxFee = customsClearanceTaxFee.add(record.getCustomsClearanceTaxFee());
                    foreignCustomsClearanceFee = foreignCustomsClearanceFee.add(record.getForeignCustomsClearanceFee());
                    extraFee = extraFee.add(record.getExtraFee());
                    collectionAdditionalFees = collectionAdditionalFees.add(record.getCollectionAdditionalFees());
                    accessoryPurchaseTotal1 = accessoryPurchaseTotal1.add(record.getAccessoryPurchaseTotal1());
                    //accessoryPurchaseTotal2 = accessoryPurchaseTotal2.add(record.getAccessoryPurchaseTotal2());

                    //重新计算每磅和销售利润
                    allCost = record.getPurchaseAmount()
                            .add(record.getCustomerCommission())
                            .add(record.getThirdPartImportCommission())
                            .add(record.getMiddleManCommission())
                            .add(record.getServiceCharge())
                            .add(record.getQualityDeduction())
                            .add(record.getDiscount())
                            .add(record.getReturnFee())
                            .add(record.getCreditInsurancePremium())
                            .add(record.getFactoringInterest())
                            .add(record.getAccessoryPurchaseTotal())
                            .add(record.getAccessoryPurchaseTotal1())
                            // .add(record.getAccessoryPurchaseTotal2())
                            .add(record.getBeforeDepartureTotal())
                            .add(record.getInsuranceFee())
                            .add(record.getDomesticShippingFee())
                            .add(record.getCustomsClearanceTaxFee())
                            .add(record.getForeignCustomsClearanceFee())
                            .add(record.getForeignTruckFee())
                            .subtract(record.getTaxRefundAmount())
                            .add(record.getTravelFee())
                            .add(record.getExtraFee());
                    //重新计算最后一个的销售利润
                    salesProfit = salesProfit.add(record.getSalesAmount().add(record.getCollectionAdditionalFees()).subtract(allCost));
                    allCost = BigDecimal.ZERO;
                    //销售利润、每磅成本、拼柜总成本重新算
                } else {
                    record.setTravelFee(record.getTotalTravelFee().subtract(travelFee));
                    record.setInsuranceFee(record.getTotalInsuranceFee().subtract(insuranceFee));
                    record.setCreditInsurancePremium(record.getTotalCreditInsurancePremium().subtract(creditInsurancePremium));
                    record.setMiddleManCommission(record.getTotalMiddleManCommission().subtract(middleManCommission));
                    record.setCustomerCommission(record.getTotalCustomerCommission().subtract(customerCommission));
                    record.setThirdPartImportCommission(record.getTotalThirdPartImportCommission().subtract(thirdPartImportCommission));
                    record.setQualityDeduction(record.getTotalQualityDeduction().subtract(qualityDeduction));
                    record.setDiscount(record.getTotalDiscount().subtract(discount));
                    record.setFactoringInterest(record.getTotalFactoringInterest().subtract(factoringInterest));
                    record.setDomesticShippingFee(record.getTotalDomesticShippingFee().subtract(domesticShippingFee));
//                    if (record.getTaxRefundAmount().compareTo(BigDecimal.ZERO) == 0) {
//                        record.setBeforeDepartureTotal(BigDecimal.ZERO);
//                    } else {
//                        record.setBeforeDepartureTotal(record.getTotalBeforeDepartureTotal().subtract(beforeDepartureTotal));
//                    }


                    record.setBeforeDepartureTotal(record.getTotalBeforeDepartureTotal().subtract(beforeDepartureTotal));

                    record.setServiceCharge(record.getTotalServiceCharge().subtract(serviceCharge));
                    record.setForeignTruckFee(record.getTotalForeignTruckFee().subtract(foreignTruckFee));
                    record.setCustomsClearanceTaxFee(record.getTotalCustomsClearanceTaxFee().subtract(customsClearanceTaxFee));
                    record.setForeignCustomsClearanceFee(record.getTotalForeignCustomsClearanceFee().subtract(foreignCustomsClearanceFee));
                    record.setExtraFee(record.getTotalExtraFee().subtract(extraFee));
                    record.setCollectionAdditionalFees(record.getTotalCollectionAdditionalFees().subtract(collectionAdditionalFees));
                    record.setAccessoryPurchaseTotal1(record.getTotalAccessoryPurchaseTotal1().subtract(accessoryPurchaseTotal1));
                    //  record.setAccessoryPurchaseTotal2(record.getTotalAccessoryPurchaseTotal2().subtract(accessoryPurchaseTotal2));
                    allCost = record.getPurchaseAmount()
                            .add(record.getCustomerCommission())
                            .add(record.getThirdPartImportCommission())
                            .add(record.getBeforeDepartureTotal())
                            .add(record.getAccessoryPurchaseTotal())
                            .add(record.getMiddleManCommission())
                            .add(record.getQualityDeduction())
                            .add(record.getReturnFee())
                            .add(record.getDiscount())
                            .add(record.getCreditInsurancePremium())
                            .add(record.getFactoringInterest())
                            .add(record.getAccessoryPurchaseTotal1())
                            //.add(record.getAccessoryPurchaseTotal2())
                            .add(record.getInsuranceFee())
                            .add(record.getDomesticShippingFee())
                            .add(record.getCustomsClearanceTaxFee())
                            .add(record.getForeignCustomsClearanceFee())
                            .add(record.getForeignTruckFee())
                            .add(record.getServiceCharge())
                            .subtract(record.getTaxRefundAmount())
                            .add(record.getTravelFee())
                            .add(record.getExtraFee());
                    //重新计算最后一个的销售利润
                    record1.setSellProfit(record.getSalesAmount().add(record.getCollectionAdditionalFees()).subtract(allCost));
                    record1.setCostPerLb(allCost.divide(record.getTotalWeight(), 2, RoundingMode.HALF_UP));
                    salesProfit = salesProfit.add(record1.getSellProfit());
                    record1.setLclSellProfit(salesProfit);
                    travelFee = BigDecimal.ZERO;
                    insuranceFee = BigDecimal.ZERO;
                    creditInsurancePremium = BigDecimal.ZERO;
                    middleManCommission = BigDecimal.ZERO;
                    customerCommission = BigDecimal.ZERO;
                    qualityDeduction = BigDecimal.ZERO;
                    discount = BigDecimal.ZERO;
                    factoringInterest = BigDecimal.ZERO;
                    thirdPartImportCommission = BigDecimal.ZERO;
                    domesticShippingFee = BigDecimal.ZERO;
                    beforeDepartureTotal = BigDecimal.ZERO;
                    serviceCharge = BigDecimal.ZERO;
                    foreignTruckFee = BigDecimal.ZERO;
                    customsClearanceTaxFee = BigDecimal.ZERO;
                    foreignCustomsClearanceFee = BigDecimal.ZERO;
                    extraFee = BigDecimal.ZERO;
                    collectionAdditionalFees = BigDecimal.ZERO;
                    salesProfit = BigDecimal.ZERO;
                    allCost = BigDecimal.ZERO;
                    totalExtraCosts = BigDecimal.ZERO;

                    accessoryPurchaseTotal1 = BigDecimal.ZERO;
                    //accessoryPurchaseTotal2 = BigDecimal.ZERO;
                }
                recordCount++;
            }

        }
        return record1;
    }

    //    @Override
//    @SneakyThrows
//    public void pageSellProfitExport(QueryPageSellProfitDTO dto, HttpServletResponse response) {
//        Page<SellProfitPageVO> page = new Page<>(1, 9999);
//        // 多客户筛选处理
//        String customerName = dto.getCustomerName();
//        List<String> customerIds = null;
//        if (StringUtils.isNotBlank(customerName)) {
//            String[] split = customerName.split(",");
//            customerIds = ListUtil.toList(split);
//            customerIds.remove("");
//        }
//        IPage<SellProfitPageVO> sellProfitPageVOIPage = this.baseMapper.pageSellProfit(page, dto, customerIds);
//        // 序号
//        List<SellProfitPageVO> records = sellProfitPageVOIPage.getRecords();
//        String currentOrderNumber = UUID.randomUUID().toString();
//        int currentSerialNumber = 0;
//        int recordCount = 1;
//        BigDecimal travelFee = BigDecimal.ZERO;
//        BigDecimal insuranceFee = BigDecimal.ZERO;
//        BigDecimal creditInsurancePremium = BigDecimal.ZERO;
//        BigDecimal middleManCommission = BigDecimal.ZERO;
//        BigDecimal thirdPartImportCommission = BigDecimal.ZERO;
//        BigDecimal customerCommission = BigDecimal.ZERO;
//        BigDecimal qualityDeduction = BigDecimal.ZERO;
//        BigDecimal discount = BigDecimal.ZERO;
//        BigDecimal factoringInterest = BigDecimal.ZERO;
//        BigDecimal domesticShippingFee = BigDecimal.ZERO;
//        BigDecimal beforeDepartureTotal = BigDecimal.ZERO;
//        BigDecimal serviceCharge = BigDecimal.ZERO;
//        BigDecimal foreignTruckFee = BigDecimal.ZERO;
//        BigDecimal customsClearanceTaxFee = BigDecimal.ZERO;
//        BigDecimal foreignCustomsClearanceFee = BigDecimal.ZERO;
//        BigDecimal extraFee = BigDecimal.ZERO;
//        BigDecimal collectionAdditionalFees = BigDecimal.ZERO;
//        BigDecimal salesProfit = BigDecimal.ZERO;
//        BigDecimal allCost = BigDecimal.ZERO;
//        BigDecimal totalExtraCosts = BigDecimal.ZERO;
//
//        BigDecimal accessoryPurchaseTotal1 = BigDecimal.ZERO;
//        //  BigDecimal accessoryPurchaseTotal2 = BigDecimal.ZERO;
//        for (SellProfitPageVO record : records) {
//            String orderNum = record.getOrderNum();
//            XqCommissionDistribution xqCommissionDistribution = xqCommissionDistributionService.getOne(new LambdaQueryWrapper<XqCommissionDistribution>()
//                    .eq(XqCommissionDistribution::getProductId, record.getProductId())
//                    .eq(XqCommissionDistribution::getOrderDetailId, record.getOrderDetailId()).last("limit 1"));
//            if (xqCommissionDistribution != null) {
//                record.setMiddleManCommission(xqCommissionDistribution.getCommissionPrice());
//            }
//
//
//            // 判断当前订单号是否与上一个一致
//            // String orderNum = record.getOrderNum();
//            if (!currentOrderNumber.equals(orderNum)) {
//                if (orderNum == null) {
//                    currentOrderNumber = "";
//                } else {
//                    currentOrderNumber = orderNum;
//                    recordCount = 1;
//                    travelFee = BigDecimal.ZERO;
//                    insuranceFee = BigDecimal.ZERO;
//                    creditInsurancePremium = BigDecimal.ZERO;
//                    middleManCommission = BigDecimal.ZERO;
//                    customerCommission = BigDecimal.ZERO;
//                    qualityDeduction = BigDecimal.ZERO;
//                    discount = BigDecimal.ZERO;
//                    factoringInterest = BigDecimal.ZERO;
//                    domesticShippingFee = BigDecimal.ZERO;
//                    beforeDepartureTotal = BigDecimal.ZERO;
//                    serviceCharge = BigDecimal.ZERO;
//                    foreignTruckFee = BigDecimal.ZERO;
//                    customsClearanceTaxFee = BigDecimal.ZERO;
//                    foreignCustomsClearanceFee = BigDecimal.ZERO;
//                    extraFee = BigDecimal.ZERO;
//                    collectionAdditionalFees = BigDecimal.ZERO;
//                    salesProfit = BigDecimal.ZERO;
//                    allCost = BigDecimal.ZERO;
//                    totalExtraCosts = BigDecimal.ZERO;
//                    accessoryPurchaseTotal1 = BigDecimal.ZERO;
//                    //accessoryPurchaseTotal2 = BigDecimal.ZERO;
//                }
//                currentSerialNumber++;
//            }
//            record.setSerialNumber(currentSerialNumber);
//            if (orderNum.equals(currentOrderNumber)) {
//                if ("USD".equals(record.getRawCurrency())) {
//                    record.setBeforeDepartureTotal(BigDecimal.ZERO);
//                }
//                if (recordCount == 1) {
//                    XqOrder xqOrder = this.getOne(new LambdaQueryWrapper<XqOrder>().eq(XqOrder::getOrderNum, record.getOrderNum()));
//                    List<XqOrderExtraCost> extraCosts = xqOrderExtraCostService.list(new LambdaQueryWrapper<XqOrderExtraCost>().eq(XqOrderExtraCost::getOrderId, xqOrder.getId()));
//                    for (XqOrderExtraCost cost : extraCosts) {
//                        totalExtraCosts = totalExtraCosts.add(cost.getPrice());
//                    }
//                    record.setSalesAmount(record.getSalesAmount().add(totalExtraCosts));
//                    totalExtraCosts = BigDecimal.ZERO;
//                }
//                if (record.getRecordCount() > 1) {
//                    if (record.getRecordCount() != recordCount) {
//                        travelFee = travelFee.add(record.getTravelFee());
//                        insuranceFee = insuranceFee.add(record.getInsuranceFee());
//                        creditInsurancePremium = creditInsurancePremium.add(record.getCreditInsurancePremium());
//                        middleManCommission = middleManCommission.add(record.getMiddleManCommission());
//                        customerCommission = customerCommission.add(record.getCustomerCommission());
//                        qualityDeduction = qualityDeduction.add(record.getQualityDeduction());
//                        thirdPartImportCommission = thirdPartImportCommission.add(record.getThirdPartImportCommission());
//                        discount = discount.add(record.getDiscount());
//                        factoringInterest = factoringInterest.add(record.getFactoringInterest());
//                        domesticShippingFee = domesticShippingFee.add(record.getDomesticShippingFee());
//                        foreignTruckFee = foreignTruckFee.add(record.getForeignTruckFee());
//                        beforeDepartureTotal = beforeDepartureTotal.add(record.getBeforeDepartureTotal());
//                        serviceCharge = serviceCharge.add(record.getServiceCharge());
//                        customsClearanceTaxFee = customsClearanceTaxFee.add(record.getCustomsClearanceTaxFee());
//                        foreignCustomsClearanceFee = foreignCustomsClearanceFee.add(record.getForeignCustomsClearanceFee());
//                        extraFee = extraFee.add(record.getExtraFee());
//                        collectionAdditionalFees = collectionAdditionalFees.add(record.getCollectionAdditionalFees());
//                        accessoryPurchaseTotal1 = accessoryPurchaseTotal1.add(record.getAccessoryPurchaseTotal1());
//                        //  accessoryPurchaseTotal2 = accessoryPurchaseTotal2.add(record.getAccessoryPurchaseTotal2());
//
//                        //重新计算每磅和销售利润
//                        allCost = record.getPurchaseAmount()
//                                .add(record.getCustomerCommission())
//                                .add(record.getThirdPartImportCommission())
//                                .add(record.getMiddleManCommission())
//                                .add(record.getServiceCharge())
//                                .add(record.getQualityDeduction())
//                                .add(record.getDiscount())
//                                .add(record.getCreditInsurancePremium())
//                                .add(record.getFactoringInterest())
//                                .add(record.getAccessoryPurchaseTotal())
//                                .add(record.getReturnFee())
//                                .add(record.getAccessoryPurchaseTotal1())
//                                // .add(record.getAccessoryPurchaseTotal2())
//                                .add(record.getBeforeDepartureTotal())
//                                .add(record.getInsuranceFee())
//                                .add(record.getDomesticShippingFee())
//                                .add(record.getCustomsClearanceTaxFee())
//                                .add(record.getForeignCustomsClearanceFee())
//                                .add(record.getForeignTruckFee())
//                                .subtract(record.getTaxRefundAmount())
//                                .add(record.getTravelFee())
//                                .add(record.getExtraFee());
//                        //重新计算最后一个的销售利润
//                        record.setSellProfit(record.getSalesAmount().subtract(allCost));
//                        record.setCostPerLb(allCost.divide(record.getTotalWeight(), 2, RoundingMode.HALF_UP));
//                        record.setLclSellProfit(BigDecimal.ZERO);
//                        salesProfit = salesProfit.add(record.getSellProfit());
//                        allCost = BigDecimal.ZERO;
//                        //销售利润、每磅成本、拼柜总成本重新算
//                    } else {
//                        record.setTravelFee(record.getTotalTravelFee().subtract(travelFee));
//                        record.setInsuranceFee(record.getTotalInsuranceFee().subtract(insuranceFee));
//                        record.setCreditInsurancePremium(record.getTotalCreditInsurancePremium().subtract(creditInsurancePremium));
//                        record.setMiddleManCommission(record.getTotalMiddleManCommission().subtract(middleManCommission));
//                        record.setCustomerCommission(record.getTotalCustomerCommission().subtract(customerCommission));
//                        record.setThirdPartImportCommission(record.getTotalThirdPartImportCommission().subtract(thirdPartImportCommission));
//                        record.setQualityDeduction(record.getTotalQualityDeduction().subtract(qualityDeduction));
//                        record.setDiscount(record.getTotalDiscount().subtract(discount));
//                        record.setFactoringInterest(record.getTotalFactoringInterest().subtract(factoringInterest));
//                        record.setDomesticShippingFee(record.getTotalDomesticShippingFee().subtract(domesticShippingFee));
//                        record.setBeforeDepartureTotal(record.getTotalBeforeDepartureTotal().subtract(beforeDepartureTotal));
//                        record.setServiceCharge(record.getTotalServiceCharge().subtract(serviceCharge));
//                        record.setForeignTruckFee(record.getTotalForeignTruckFee().subtract(foreignTruckFee));
//                        record.setCustomsClearanceTaxFee(record.getTotalCustomsClearanceTaxFee().subtract(customsClearanceTaxFee));
//                        record.setForeignCustomsClearanceFee(record.getTotalForeignCustomsClearanceFee().subtract(foreignCustomsClearanceFee));
//                        record.setExtraFee(record.getTotalExtraFee().subtract(extraFee));
//                        record.setCollectionAdditionalFees(record.getTotalCollectionAdditionalFees().subtract(collectionAdditionalFees));
//                        record.setAccessoryPurchaseTotal1(record.getTotalAccessoryPurchaseTotal1().subtract(accessoryPurchaseTotal1));
//                        //  record.setAccessoryPurchaseTotal2(record.getTotalAccessoryPurchaseTotal2().subtract(accessoryPurchaseTotal2));
//                        allCost = record.getPurchaseAmount()
//                                .add(record.getCustomerCommission())
//                                .add(record.getThirdPartImportCommission())
//                                .add(record.getBeforeDepartureTotal())
//                                .add(record.getAccessoryPurchaseTotal())
//                                .add(record.getMiddleManCommission())
//                                .add(record.getQualityDeduction())
//                                .add(record.getDiscount())
//                                .add(record.getCreditInsurancePremium())
//                                .add(record.getFactoringInterest())
//                                .add(record.getAccessoryPurchaseTotal1())
//                                //.add(record.getAccessoryPurchaseTotal2())
//                                .add(record.getInsuranceFee())
//                                .add(record.getReturnFee())
//                                .add(record.getDomesticShippingFee())
//                                .add(record.getCustomsClearanceTaxFee())
//                                .add(record.getForeignCustomsClearanceFee())
//                                .add(record.getForeignTruckFee())
//                                .add(record.getServiceCharge())
//                                .subtract(record.getTaxRefundAmount())
//                                .add(record.getTravelFee())
//                                .add(record.getExtraFee());
//                        //重新计算最后一个的销售利润
//                        record.setSellProfit(record.getSalesAmount().subtract(allCost));
//                        record.setCostPerLb(allCost.divide(record.getTotalWeight(), 2, RoundingMode.HALF_UP));
//
//                        salesProfit = salesProfit.add(record.getSellProfit());
//                        record.setLclSellProfit(salesProfit);
//                        insuranceFee = BigDecimal.ZERO;
//                        creditInsurancePremium = BigDecimal.ZERO;
//                        middleManCommission = BigDecimal.ZERO;
//                        customerCommission = BigDecimal.ZERO;
//                        qualityDeduction = BigDecimal.ZERO;
//                        discount = BigDecimal.ZERO;
//                        factoringInterest = BigDecimal.ZERO;
//                        domesticShippingFee = BigDecimal.ZERO;
//                        beforeDepartureTotal = BigDecimal.ZERO;
//                        thirdPartImportCommission = BigDecimal.ZERO;
//                        serviceCharge = BigDecimal.ZERO;
//                        foreignTruckFee = BigDecimal.ZERO;
//                        customsClearanceTaxFee = BigDecimal.ZERO;
//                        foreignCustomsClearanceFee = BigDecimal.ZERO;
//                        extraFee = BigDecimal.ZERO;
//                        collectionAdditionalFees = BigDecimal.ZERO;
//                        salesProfit = BigDecimal.ZERO;
//                        allCost = BigDecimal.ZERO;
//                        totalExtraCosts = BigDecimal.ZERO;
//                        accessoryPurchaseTotal1 = BigDecimal.ZERO;
//                        // accessoryPurchaseTotal2 = BigDecimal.ZERO;
//                    }
//
//                } else {
//                    //重新计算每磅和销售利润
//                    allCost = record.getPurchaseAmount()
//                            .add(record.getCustomerCommission())
//                            .add(record.getThirdPartImportCommission())
//                            .add(record.getBeforeDepartureTotal())
//                            .add(record.getAccessoryPurchaseTotal())
//                            .add(record.getMiddleManCommission())
//                            .add(record.getQualityDeduction())
//                            .add(record.getDiscount())
//                            .add(record.getCreditInsurancePremium())
//                            .add(record.getFactoringInterest())
//                            .add(record.getReturnFee())
//                            .add(record.getAccessoryPurchaseTotal1())
//                            // .add(record.getAccessoryPurchaseTotal2())
//                            .add(record.getInsuranceFee())
//                            .add(record.getDomesticShippingFee())
//                            .add(record.getCustomsClearanceTaxFee())
//                            .add(record.getForeignCustomsClearanceFee())
//                            .add(record.getForeignTruckFee())
//                            // .add(record.getCollectionAdditionalFees())
//                            .add(record.getServiceCharge())
//                            .subtract(record.getTaxRefundAmount())
//                            .add(record.getTravelFee())
//                            .add(record.getExtraFee());
//                    //重新计算最后一个的销售利润
//                    record.setSellProfit(record.getSalesAmount().subtract(allCost));
//                    record.setCostPerLb(allCost.divide(record.getTotalWeight(), 2, RoundingMode.HALF_UP));
//                    record.setLclSellProfit(BigDecimal.ZERO);
//                    allCost = BigDecimal.ZERO;
//                }
//            }
//            recordCount++;
//        }
//
//
//        String exportColumn = dto.getExportColumn();
//
//        CommonUtils.export(exportColumn, records, response, SellProfitPageVO.class, null, null, false);
//
//
//    }
    @Override
    @SneakyThrows
    public void pageSellProfitExport(QueryPageSellProfitDTO dto, HttpServletResponse response) {
        dto.setPageSize(99999);
        IPage<SellProfitPageVO> sellProfitPageVOIPage = pageSellProfitNew(dto);
        // 序号
        List<SellProfitPageVO> records = sellProfitPageVOIPage.getRecords();
//        if (records.size() > 0) {
//            int num = 1;
//            for (SellProfitPageVO record : records) {
//                record.setSerialNumber(num);
//                num++;
//            }
//        }

        // 查询销售利润总额
        List<SellProfitTotalVO> profitTotalVOList = totalSellProfitNew(dto);
        SellProfitTotalVO totalVO = profitTotalVOList.get(0);

        SellProfitPageVO vo = new SellProfitPageVO();
        vo.setProductName("合计：");
        vo.setSellProfit(totalVO.getTotalSellProfit());
        vo.setSalesAmount(totalVO.getTotalSalesAmount());
        vo.setPurchaseAmount(totalVO.getTotalPurchaseAmount());
        vo.setReturnFee(totalVO.getTotalReturnFee());
        vo.setExtraFee(totalVO.getTotalExtraFee());
        vo.setCustomerCommission(totalVO.getTotalcCstomerCommission());
        vo.setThirdPartImportCommission(totalVO.getTotalThirdPartImportCommission());
        vo.setMiddleManCommission(totalVO.getTotalMiddleManCommission());
        vo.setQualityDeduction(totalVO.getTotalQualityDeduction());
        vo.setDiscount(totalVO.getTotalDiscount());
        vo.setServiceCharge(totalVO.getTotalServiceCharge());
        vo.setCreditInsurancePremium(totalVO.getTotalCreditInsurancePremium());
        vo.setFactoringInterest(totalVO.getTotalFactoringInterest());
        vo.setAccessoryPurchaseTotal(totalVO.getTotalAccessoryPurchaseTotal());
        vo.setAccessoryPurchaseTotal1(null);
        vo.setBeforeDepartureTotal(totalVO.getTotalBeforeDepartureTotal());
        vo.setInsuranceFee(totalVO.getTotalInsuranceFee());
        vo.setDomesticShippingFee(totalVO.getTotalDomesticShippingFee());
        vo.setCustomsClearanceTaxFee(totalVO.getTotalCustomsClearanceTaxFee());
        vo.setForeignCustomsClearanceFee(totalVO.getTotalForeignCustomsClearanceFee());
        vo.setForeignTruckFee(totalVO.getTotalForeignTruckFee());
        vo.setTravelFee(totalVO.getTotalTravelFee());
        vo.setTaxRefundAmount(totalVO.getTotalTaxRefundAmount());
        vo.setLclSellProfit(totalVO.getTotalLclSellProfit());
        vo.setCollectionAdditionalFees(totalVO.getTotalCollectionAdditionalFees());
        vo.setTotalWeight(null);
        vo.setPricePerBox(null);
        vo.setPricePerLb(null);
        vo.setUnitPrice(null);
        vo.setWeight(totalVO.getTotalWeight());
        vo.setExchangeRate(null);
        vo.setCostPerLb(null);
        vo.setCutAmount(totalVO.getCutAmount());
        records.add(vo);
        String exportColumn = dto.getExportColumn();

        CommonUtils.export2(exportColumn, records, response, SellProfitPageVO.class, SellProfitTotalVO.class, profitTotalVOList, false);


    }

    @Override
    public boolean auditRepeatById(AuditDTO dto) {
        String ids = dto.getIds();
        String[] splits = ids.split(",");

        if (dto.getAuditStatus().equals(CommonConstant.AUDIT_ORIGIN)) {
            for (String id : splits) {
                XqOrder byId = this.getById(id);
                byId.setRepeatAudit(dto.getAuditStatus());
                this.updateById(byId);
            }
        } else if (dto.getAuditStatus().equals(CommonConstant.AUDIT_PASS)) {
            for (String id : splits) {
                XqOrder byId = this.getById(id);
                if (!byId.getRepeatAudit().equals(CommonConstant.AUDIT_ORIGIN)) {
                    throw new InterestingBootException("只有未审核的订单才能审核通过");
                }

                byId.setRepeatAudit(dto.getAuditStatus());
                this.updateById(byId);
            }
        } else {
            throw new InterestingBootException("非法参数");
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transferOrder(TransferDTO dto) {

        String orderId = dto.getOrderId();
        XqOrder xqOrder = this.getById(orderId);
        if (xqOrder == null) throw new InterestingBootException("转让失败，该订单未找到");
        // 判断当前登录人是否为所有者
        String owner = xqOrder.getTransferBy();
        String userId = FilterContextHandler.getUserId();

        List<SysUserRole> sysUserRoles = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
        boolean hasAdminRole = false;
        for (SysUserRole sysUserRole : sysUserRoles) {
            if ("f6817f48af4fb3af11b9e8bf182f618b".equals(sysUserRole.getRoleId())) {
                hasAdminRole = true;
                break;
            }
        }

        if (!owner.equals(userId) && !hasAdminRole)
            throw new InterestingBootException("转让失败，当前登录用户没有转让权限");

        // 将 transferBy 修改为转让目标
        xqOrder.setTransferBy(dto.getTransferTarget());
        this.updateById(xqOrder);

        XqOrderTransferRecord xqOrderTransferRecord = new XqOrderTransferRecord();
        xqOrderTransferRecord.setOrderId(orderId);
        xqOrderTransferRecord.setTransferFrom(userId);
        xqOrderTransferRecord.setTransferTo(dto.getTransferTarget());

        xqOrderTransferRecordService.save(xqOrderTransferRecord);

        return true;

    }

    @Override
    public Result<?> listDeliveryAddress(String customerId) {
//
//        List<String> feeTypeList = Arrays.asList("gwkcf1", "gwkcf2", "gwkcf3", "gwkcf4", "gwkcf5", "gwkcf6", "gwkcf7", "gwkcf8");
        List<XqFreightCostInfo> list = xqFreightCostInfoService.lambdaQuery()
                .eq(XqFreightCostInfo::getCustomerId, customerId)
                .isNull(XqFreightCostInfo::getOrderId)
                .likeRight(XqFreightCostInfo::getFeeType, "gwkcf")
                .orderByDesc(XqFreightCostInfo::getCreateTime)
                .list();

        List<JSONObject> resultList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list)) {
            resultList = list.stream().map(xqFreightCostInfo -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", xqFreightCostInfo.getId());
                jsonObject.put("address", xqFreightCostInfo.getAddress());
                jsonObject.put("price", xqFreightCostInfo.getPrice());
                return jsonObject;
            }).collect(Collectors.toList());
        }

        return Result.OK(resultList);

    }

    @Override
    public Result<?> listDeliveryAddressByWarehouseId(String warehouseId) {
        List<XqFreightCostInfo> list = xqFreightCostInfoService.lambdaQuery()
                .eq(XqFreightCostInfo::getWarehouseId, warehouseId)
                .isNull(XqFreightCostInfo::getOrderId)
                .likeRight(XqFreightCostInfo::getFeeType, "gwkcf")
                .orderByDesc(XqFreightCostInfo::getCreateTime)
                .list();

        List<JSONObject> resultList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list)) {
            resultList = list.stream().map(xqFreightCostInfo -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", xqFreightCostInfo.getId());
                jsonObject.put("address", xqFreightCostInfo.getAddress());
                jsonObject.put("price", xqFreightCostInfo.getPrice());
                return jsonObject;
            }).collect(Collectors.toList());
        }

        return Result.OK(resultList);
    }

    @Override
    public void doNotRemind(String orderId) {
        this.lambdaUpdate().set(XqOrder::getIzRemind, 0).eq(XqOrder::getId, orderId).update();
    }

    @Override
    public void doNotRemindPayment(String orderId) {
        this.lambdaUpdate().set(XqOrder::getIzRemindPayment, 0).eq(XqOrder::getId, orderId).update();
    }

    @Override
    public ShowOrderVO showOrder(String id) {

        XqOrderAllVO xqOrderAllVO = this.baseMapper.getOrderHead(id);
        if (xqOrderAllVO == null) throw new InterestingBootException("查询失败，该记录不存在");

        ShowOrderVO vo = new ShowOrderVO();
        BeanUtils.copyProperties(xqOrderAllVO, vo);

        String orderFollowUpPerson = vo.getOrderFollowUpPerson();
        if (StringUtils.isBlank(orderFollowUpPerson)) {
            vo.setOrderFollowUpPerson(FilterContextHandler.getUserInfo().getRealname());
        }
        String warehouseId = vo.getWarehouseId();
        // 并行执行接口调用
        XqWarehouse xqWarehouse = CompletableFuture.supplyAsync(() -> xqWarehouseService.getById(warehouseId)).join();
        if (xqWarehouse != null) {
            vo.setWarehouseName(xqWarehouse.getName());
        }
        List<XqOrderRemiExpDateVO> remiExpDateVOS = CompletableFuture.supplyAsync(() -> this.baseMapper.listRemiExpDateVOS(id)).join();
        vo.setRemiExpDateVOS(CollectionUtil.isNotEmpty(remiExpDateVOS) ? remiExpDateVOS : Collections.emptyList());

        List<XqOrderProdVO> xqOrderProdVOS = this.baseMapper.listOrderProds(id);
        if (CollectionUtil.isNotEmpty(xqOrderProdVOS)) {
            XqOrderProdVO xqOrderProdVO = xqOrderProdVOS.get(0);
            String weightPerBoxUnitTxt = xqOrderProdVO.getWeightPerBoxUnitTxt();
            if (StringUtils.isNotBlank(weightPerBoxUnitTxt)) {
                switch (weightPerBoxUnitTxt) {
                    case "kg":
                        vo.setProductUnitName("千克");
                        break;
                    case "lbs":
                        vo.setProductUnitName("磅");
                        break;
                    case "oz":
                        vo.setProductUnitName("盎司");
                        break;
                }
            } else {
                vo.setProductUnitName("千克");
            }

        }
        vo.setOrderProdVOS(xqOrderProdVOS);


        vo.setXqOrderExtraCostVOS(this.baseMapper.listOrderExtraCosts(id));
        List<XqOrderComsVO> xqOrderComsVOS = CompletableFuture.supplyAsync(() -> this.baseMapper.listOrderComs(id)).join();
        vo.setOrderComsVOS(xqOrderComsVOS != null && !xqOrderComsVOS.isEmpty() ? xqOrderComsVOS : Collections.emptyList());
        /* 收汇情况 */
        CompletableFuture<List<XqOrderRemiVO>> future = CompletableFuture.supplyAsync(() -> this.baseMapper.listOrderRemi(id));
        future.thenAccept(xqOrderRemiVOS -> {
            //去查下额外费用有没有
            //  List<XqOrderExtraCost> list=xqOrderExtraCostService.list().
            if (CollectionUtil.isNotEmpty(xqOrderRemiVOS)) {
                for (XqOrderRemiVO xqOrderRemiVO : xqOrderRemiVOS) {
                    xqOrderRemiVO.setRemittanceAmount(xqOrderRemiVO.getRemittanceAmount() == null ? new BigDecimal("0.00") : xqOrderRemiVO.getRemittanceAmount());
                    xqOrderRemiVO.setServiceCharge(xqOrderRemiVO.getServiceCharge() == null ? new BigDecimal("0.00") : xqOrderRemiVO.getServiceCharge());
                    xqOrderRemiVO.setFactoringInterest(xqOrderRemiVO.getFactoringInterest() == null ? new BigDecimal("0.00") : xqOrderRemiVO.getFactoringInterest());
                    xqOrderRemiVO.setFactoringMoney(xqOrderRemiVO.getFactoringMoney() == null ? new BigDecimal("0.00") : xqOrderRemiVO.getFactoringMoney());
                }
            }
            vo.setOrderRemiVOS(xqOrderRemiVOS);

        });
        //信保费用
        vo.setOrderCreditsInsuranceVOS(this.baseMapper.listOrderCreditsInsuranceVOS(id));

        //原料采购
        List<XqOrderRawVO> xqOrderRawVOS = this.baseMapper.listOrderRaw(id);
        //原料采购财务模块
        // List<XqOrderRawFinanceVO> xqOrderRawFinanceVOS = this.baseMapper.listOrderRawFinance(id);

        if (xqOrderRawVOS != null && !xqOrderRawVOS.isEmpty()) {
            xqOrderRawVOS.parallelStream().forEach(i -> {
                List<XqPaymentDetailVO> paymentDetailVOS = this.baseMapper.listPaymentDetails(i.getId());
                BigDecimal total = paymentDetailVOS != null && !paymentDetailVOS.isEmpty()
                        ? paymentDetailVOS.parallelStream()
                        .map(XqPaymentDetailVO::getPayMoney)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        : BigDecimal.ZERO;
                i.setPaymentAmount(total);
                i.setPaymentDetails(paymentDetailVOS);
                if (paymentDetailVOS != null && !paymentDetailVOS.isEmpty()) {
                    String s = JSON.toJSONStringWithDateFormat(paymentDetailVOS, "yyyy-MM-dd",
                            SerializerFeature.WriteDateUseDateFormat);
                    i.setPaymentDetailsStr(s);
                }
            });

            xqOrderRawVOS.parallelStream().forEach(i -> {
                List<XqCutAmountDetailVO> cutAmountDetailVOS = this.baseMapper.listCutAmountDetails(i.getId());
                BigDecimal total = cutAmountDetailVOS != null && !cutAmountDetailVOS.isEmpty()
                        ? cutAmountDetailVOS.parallelStream()
                        .map(XqCutAmountDetailVO::getCutAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        : BigDecimal.ZERO;
                i.setCutAmount(total);
                i.setCutDetails(cutAmountDetailVOS);
                if (cutAmountDetailVOS != null && !cutAmountDetailVOS.isEmpty()) {
                    String s = JSON.toJSONStringWithDateFormat(cutAmountDetailVOS, "yyyy-MM-dd",
                            SerializerFeature.WriteDateUseDateFormat);
                    i.setCutDetailsStr(s);
                }
            });


            vo.setOrderRawVOS(xqOrderRawVOS);
        } else {
            /* 查询默认记录(跟随已填入的产品信息) 默认出来 */
            List<XqOrderProdVO> orderProdVOS = vo.getOrderProdVOS();
            if (orderProdVOS != null && !orderProdVOS.isEmpty()) {
                xqOrderRawVOS = orderProdVOS.parallelStream()
                        .map(orderProdVO -> {
                            XqOrderRawVO xqOrderRawVO = new XqOrderRawVO();
                            xqOrderRawVO.setProductName(orderProdVO.getProductName());
                            xqOrderRawVO.setProductId(orderProdVO.getProductId());
                            xqOrderRawVO.setUnitPrice(BigDecimal.ZERO);
                            return xqOrderRawVO;
                        })
                        .collect(Collectors.toList());
                vo.setOrderRawVOS(xqOrderRawVOS);

            } else {
                vo.setOrderRawVOS(Collections.emptyList());
            }
        }
        List<XqOrderRawVO> orderRawVOS2 = vo.getOrderRawVOS();

        if (CollectionUtil.isNotEmpty(orderRawVOS2)) {
            for (XqOrderRawVO xqOrderRawVO : orderRawVOS2) {
                if (xqOrderRawVO.getFinanceCompleteState() != null) {
                    if (xqOrderRawVO.getFinanceCompleteState() == 0) {
                        xqOrderRawVO.setFinanceCompleteStateName("否");
                    } else {
                        xqOrderRawVO.setFinanceCompleteStateName("是");
                    }
                }
            }

            vo.setOrderRawVOS(orderRawVOS2);
        }

        //辅料采购
        CompletableFuture<List<XqOrderAcsrVO>> xqOrderAcsrVOSFuture = CompletableFuture.supplyAsync(() -> this.baseMapper.listOrderAcsr(id));
        CompletableFuture<List<XqOrderRawVO>> xqOrderRawVOSFuture = CompletableFuture.supplyAsync(() -> this.baseMapper.listOrderRaw(id));
        CompletableFuture.allOf(xqOrderAcsrVOSFuture, xqOrderRawVOSFuture).join();

        List<XqOrderAcsrVO> xqOrderAcsrVOS = xqOrderAcsrVOSFuture.join();
        List<XqOrderRawVO> xqOrderRawVOS1 = xqOrderRawVOSFuture.join();

        if (xqOrderAcsrVOS != null && !xqOrderAcsrVOS.isEmpty()) {
            Map<String, XqOrderRawVO> map = xqOrderRawVOS1.stream().collect(Collectors.toMap(XqOrderRawVO::getProductId,
                    Function.identity(), (v1, v2) -> v1));

            xqOrderAcsrVOS.parallelStream().forEach(i -> {
                XqOrderRawVO xqOrderRawVO = map.get(i.getProductId());
                XqOrderDetail xqOrderDetail = xqOrderDetailService.getOne(new LambdaQueryWrapper<XqOrderDetail>()
                        .eq(XqOrderDetail::getProductId, i.getProductId())
                        .eq(XqOrderDetail::getOrderId, i.getOrderId())
                        .eq(XqOrderDetail::getLayoutRequirements, i.getLayoutRequirements())
                        .last("limit 1"));
                if (xqOrderRawVO != null) {
                    i.setCategoryId(xqOrderRawVO.getCategoryId());
                }
                i.setTotalBoxes(xqOrderDetail != null ? xqOrderDetail.getTotalBoxes() : 0);

                // --用料
                List<AccInventoryDetailsVO> useInventoryDetails = this.baseMapper.listUseOrderAcsrDetails(i.getId());
                useInventoryDetails.parallelStream().forEach(o -> {
                    // 设置库存数
                    if (StringUtils.isNotBlank(i.getAccessoryId())) {
                        Integer inventoryNum = xqInventoryService.getInventoryNum(o.getWarehouseId(),
                                i.getAccessoryId(), o.getSourceRepNum(), i.getUnitPrice());
                        o.setInventoryNum(inventoryNum);
                        // i.setSkuNum(inventoryNum);
                    }
                    // 全部转为正数
                    if (o.getNum() < 0) {
                        o.setNum(-o.getNum());
                    }
                });
                // to json
                String s1 = JSONObject.toJSONStringWithDateFormat(useInventoryDetails, "yyyy-MM-dd HH:mm:ss",
                        SerializerFeature.WriteDateUseDateFormat);
                i.setUseInventoryDetailsStr(s1);
                // 统计用料数量
                i.setUseNum(useInventoryDetails.stream().map(AccInventoryDetailsVO::getNum)
                        .reduce(0, Integer::sum));
                if (i.getUseNum() < 0) {
                    i.setUseNum(-i.getUseNum());
                }
                // --退料
                List<AccInventoryDetailsVO> backInventoryDetails = this.baseMapper.listBackOrderAcsrDetails(i.getId());
                // to json
                String s2 = JSONObject.toJSONStringWithDateFormat(backInventoryDetails, "yyyy-MM-dd HH:mm:ss",
                        SerializerFeature.WriteDateUseDateFormat);
                i.setBackInventoryDetailsStr(s2);
                // 统计退料数量
                i.setBackNum(backInventoryDetails.stream().map(AccInventoryDetailsVO::getNum)
                        .reduce(0, Integer::sum));

                // 设置库存数
                if (StringUtils.isNotBlank(i.getAccessoryId())) {
                    BigDecimal inventoryNum = xqInventoryService.getSkuNum(i.getAccessoryId());
                    i.setSkuNum(inventoryNum == null ? new BigDecimal("0.00") : inventoryNum);
                }
            });
            vo.setOrderAcsrVOS(xqOrderAcsrVOS);
        } else {
            // 查询默认记录(跟随已填入的产品信息) 默认各个产品两条
            List<XqOrderProdVO> orderProdVOS = vo.getOrderProdVOS();
            List<XqOrderProdVO> newOrderProdVOS = new ArrayList<>();

            for (XqOrderProdVO xqOrderProdVO : orderProdVOS) {
                String[] packaging = xqOrderProdVO.getPackaging().split("\\|");
                if (packaging.length > 1) {
                    for (String str : packaging) {
                        XqOrderProdVO xqOrderProdVO1 = new XqOrderProdVO();
                        BeanUtils.copyProperties(xqOrderProdVO, xqOrderProdVO1);
                        xqOrderProdVO1.setPackaging(str);
                        newOrderProdVOS.add(xqOrderProdVO1);
                    }
                } else {
                    newOrderProdVOS.add(xqOrderProdVO);
                }
            }
            if (newOrderProdVOS != null && !newOrderProdVOS.isEmpty()) {
                xqOrderAcsrVOS = newOrderProdVOS.parallelStream().flatMap(orderProdVO -> {
                    XqOrderDetail xqOrderDetail = xqOrderDetailService.getOne(new LambdaQueryWrapper<XqOrderDetail>()
                            .eq(XqOrderDetail::getProductId, orderProdVO.getProductId())
                            .eq(XqOrderDetail::getLayoutRequirements, orderProdVO.getLayoutRequirements())
                            .eq(XqOrderDetail::getOrderId, orderProdVO.getOrderId())
                            .last("limit 1"));

                    XqOrderAcsrVO xqOrderAcsrVO = new XqOrderAcsrVO();
                    XqOrderAcsrVO xqOrderAcsrVO2 = new XqOrderAcsrVO();
                    if (xqOrderDetail != null) {
                        xqOrderAcsrVO.setTotalBoxes(xqOrderDetail.getTotalBoxes());
                    } else {
                        xqOrderAcsrVO.setTotalBoxes(0);
                    }
                    xqOrderAcsrVO.setProductId(orderProdVO.getProductId());
                    xqOrderAcsrVO.setProductName(orderProdVO.getProductName());
                    xqOrderAcsrVO.setPackaging(orderProdVO.getPackaging());
                    xqOrderAcsrVO.setCurrency("CNY");
                    xqOrderAcsrVO.setTaxRate(BigDecimal.ZERO);
                    xqOrderAcsrVO.setPackagingUnit(orderProdVO.getPackagingUnit());
                    xqOrderAcsrVO.setLayoutRequirements(orderProdVO.getLayoutRequirements());
                    xqOrderAcsrVO.setUnitPrice(BigDecimal.ZERO);
                    // 根据已填写的原料记录，set相应的categoryId
                    List<XqRawMaterialPurchase> list22 = xqRawMaterialPurchaseService.lambdaQuery()
                            .eq(XqRawMaterialPurchase::getOrderId, id).list();
                    Map<String, XqRawMaterialPurchase> collect1 = list22.parallelStream()
                            .collect(Collectors.toMap(XqRawMaterialPurchase::getProductId, Function.identity(),
                                    (k1, k2) -> k1));
                    XqRawMaterialPurchase xqRawMaterialPurchase = collect1.get(xqOrderAcsrVO.getProductId());
                    if (xqRawMaterialPurchase != null) {
                        xqOrderAcsrVO.setCategoryId(xqRawMaterialPurchase.getCategoryId());
                    }
                    BeanUtils.copyProperties(xqOrderAcsrVO, xqOrderAcsrVO2);
                    return Stream.of(xqOrderAcsrVO, xqOrderAcsrVO2);
                }).collect(Collectors.toList());

                vo.setOrderAcsrVOS(xqOrderAcsrVOS);
            } else {
                vo.setOrderAcsrVOS(Collections.emptyList());
            }
        }

        //        /* 货运信息 */
        CompletableFuture<List<XqOrderFretVO>> xqOrderFretVOSFuture = CompletableFuture.supplyAsync(() -> this.baseMapper.listOrderFret(id));
        xqOrderFretVOSFuture.thenAccept(xqOrderFretVOS ->

        {
            if (xqOrderFretVOS != null && !xqOrderFretVOS.isEmpty()) {
                List<XqOrderRawVO> orderRawVOS1 = vo.getOrderRawVOS();
                if (orderRawVOS1 != null && !orderRawVOS1.isEmpty()) {
                    BigDecimal totalWeight = BigDecimal.ZERO;
                    for (XqOrderRawVO orderRawVO : orderRawVOS1) {
                        BigDecimal weight = orderRawVO.getWeight();
                        if (weight != null) {
                            totalWeight = totalWeight.add(weight);
                        }
                    }

                    xqOrderFretVOS.get(0).setNetWeight(totalWeight);
                    xqOrderFretVOS.get(0).setContainerTemperature("-20℃/关闭");
                }
            }
            if (CollectionUtil.isNotEmpty(xqOrderFretVOS)) {
                vo.setXqOrderFretVO(xqOrderFretVOS.get(0));
            }
        });

        xqOrderFretVOSFuture.join();
        //货运费用 0919---zqy修改
        List<XqOrderRawVO> orderRawVOS = vo.getOrderRawVOS();
        List<XqOrderFretCostVO> xqOrderFretCostVOS = this.baseMapper.listOrderFretCostWithType(id, 1);
        if (xqOrderFretCostVOS != null && xqOrderFretCostVOS.size() > 0) {
            ArrayList<XqOrderFretCostVO> xqOrderInsuranceVOS1 = initFretCostVOs();
            if (xqOrderFretCostVOS.size() > 0) {
                for (XqOrderFretCostVO xqOrderFretCostVO : xqOrderFretCostVOS) {
                    String feeType = xqOrderFretCostVO.getFeeType();
                    for (XqOrderFretCostVO xqOrderFretCostVO99 : xqOrderInsuranceVOS1) {
                        String feeType1 = xqOrderFretCostVO99.getFeeType();
                        if (feeType.equals(feeType1)) {
                            BeanUtils.copyProperties(xqOrderFretCostVO, xqOrderFretCostVO99);
                            if (xqOrderFretCostVO99.getPrice() != null && xqOrderFretCostVO99.getPrice().compareTo(BigDecimal.ZERO) == 0) {
                                if (feeType.equals(CommonConstant.DOMESTIC_SHIPPING_FEE)) {
                                    xqOrderFretCostVO99.setCurrency("USD");
                                } else {
                                    xqOrderFretCostVO99.setCurrency("CNY");
                                }
                            }

                        }

                    }
                }
            }
            xqOrderInsuranceVOS1.forEach(i -> {
                i.setIzDomestic(1);
                String filesUrl = i.getFilesUrl();
                if (StringUtils.isNotBlank(filesUrl)) {
                    String[] split = filesUrl.split(",");
                    ArrayList<String> newPaths = new ArrayList<>(split.length);
                    for (String s : split) {
                        s = ossPath + "/" + s;
                        newPaths.add(s);
                    }
                    String string = newPaths.toString();
                    filesUrl = string.substring(1, string.length() - 1);
                }
                i.setFilesUrl(filesUrl);

            });
            vo.setOrderFretCostVOS(xqOrderInsuranceVOS1);
        } else {
            // 根据供应商id查询默认国内货运信息
            List<XqOrderFretCostVO> supplierFretCostList = Collections.emptyList();
            if (orderRawVOS != null && orderRawVOS.size() > 0 && orderRawVOS.get(0) != null) {
                XqOrderRawVO xqOrderRawVO = orderRawVOS.get(0);
                String supplierId = xqOrderRawVO.getSupplierId();
                supplierFretCostList = baseMapper.listSupplierFretCost(supplierId);
            }
            ArrayList<XqOrderFretCostVO> xqOrderInsuranceVOS1 = initFretCostVOs();
            xqOrderInsuranceVOS1.forEach(i -> {
                i.setIzDomestic(1);
                String filesUrl = i.getFilesUrl();
                if (StringUtils.isNotBlank(filesUrl)) {
                    String[] split = filesUrl.split(",");
                    ArrayList<String> newPaths = new ArrayList<>(split.length);
                    for (String s : split) {
                        s = ossPath + "/" + s;
                        newPaths.add(s);
                    }
                    String string = newPaths.toString();
                    filesUrl = string.substring(1, string.length() - 1);
                }
                i.setFilesUrl(filesUrl);
            });
            vo.setOrderFretCostVOS(xqOrderInsuranceVOS1);
        }


        List<XqOrderFretCostVO> xqOrderFretCostVOS2 = this.baseMapper.listOrderFretCostWithType(id, 0);
        if (xqOrderFretCostVOS2 != null && xqOrderFretCostVOS2.size() > 0) {
            ArrayList<XqOrderFretCostVO> xqOrderInsuranceVOS1 = initFretCostForeignFee();

            if (xqOrderFretCostVOS2.size() > 0) {
                for (XqOrderFretCostVO xqOrderFretCostVO : xqOrderFretCostVOS2) {
                    String feeType = xqOrderFretCostVO.getFeeType();
                    for (XqOrderFretCostVO xqOrderFretCostVO99 : xqOrderInsuranceVOS1) {
                        String feeType1 = xqOrderFretCostVO99.getFeeType();
                        if (feeType.equals(feeType1)) {
                            BeanUtils.copyProperties(xqOrderFretCostVO, xqOrderFretCostVO99);
                        }
                    }
                }
            }
            xqOrderInsuranceVOS1.forEach(i -> {
                i.setIzDomestic(0);
                String filesUrl = i.getFilesUrl();
                if (StringUtils.isNotBlank(filesUrl)) {
                    String[] split = filesUrl.split(",");
                    ArrayList<String> newPaths = new ArrayList<>(split.length);
                    for (String s : split) {
                        s = ossPath + "/" + s;
                        newPaths.add(s);
                    }
                    String string = newPaths.toString();
                    filesUrl = string.substring(1, string.length() - 1);
                }
                i.setFilesUrl(filesUrl);
            });
            vo.setOrderFretCostVOS2(xqOrderInsuranceVOS1);
        } else {
            // 根据客户id查询默认国外货运费用
            List<XqOrderFretCostVO> customerFretCost = Collections.emptyList();
            String customerId = vo.getCustomerId();
            if (StringUtils.isNotBlank(customerId)) {
                customerFretCost = baseMapper.listCustomerFretCost(customerId, null);
            }
            ArrayList<XqOrderFretCostVO> xqOrderInsuranceVOS1 = initFretCostForeignFee();
            xqOrderInsuranceVOS1.forEach(i -> {
                i.setIzDomestic(0);
                String filesUrl = i.getFilesUrl();
                if (StringUtils.isNotBlank(filesUrl)) {
                    String[] split = filesUrl.split(",");
                    ArrayList<String> newPaths = new ArrayList<>(split.length);
                    for (String s : split) {
                        s = ossPath + "/" + s;
                        newPaths.add(s);
                    }
                    String string = newPaths.toString();
                    filesUrl = string.substring(1, string.length() - 1);
                }
                i.setFilesUrl(filesUrl);

            });
            vo.setOrderFretCostVOS2(xqOrderInsuranceVOS1);
        }

        /* 退运费用 */
        CompletableFuture<List<XqOrderFretCostVO>> returnFeesFuture = CompletableFuture.supplyAsync(() -> this.baseMapper.listFretCostReturnFee(id));
        returnFeesFuture.thenAccept(returnFees ->

        {
            if (CollectionUtil.isNotEmpty(returnFees)) {
                vo.setOrderFretCostReturnFeeVOS(returnFees);
            } else {
                vo.setOrderFretCostReturnFeeVOS(Collections.emptyList());
            }
        });
        returnFeesFuture.join();

        CompletableFuture<List<XqOrderInsuranceVO>> xqOrderInsuranceVOSFuture = CompletableFuture.supplyAsync(() -> this.baseMapper.listOrderInsurance(id));
        xqOrderInsuranceVOSFuture.thenAccept(xqOrderInsuranceVOS ->

        {
            if (xqOrderInsuranceVOS != null && !xqOrderInsuranceVOS.isEmpty()) {
                vo.setXqOrderInsuranceVO(xqOrderInsuranceVOS.get(0));
            } else {
                XqOrderInsuranceVO xqOrderInsuranceVO = new XqOrderInsuranceVO();
                ArrayList<XqOrderInsuranceVO> xqOrderInsuranceVOS1 = new ArrayList<>(1);
                xqOrderInsuranceVOS1.add(xqOrderInsuranceVO);
                vo.setXqOrderInsuranceVO(xqOrderInsuranceVOS1.get(0));
            }
        });
        xqOrderInsuranceVOSFuture.join();

        // 货运备注
        List<XqOrderFreightNoteVO> xqOrderFreightNoteVOS = this.baseMapper.listOrderFreightNoteVOS(id);
        if (CollectionUtil.isNotEmpty(xqOrderFreightNoteVOS)) {
            for (XqOrderFreightNoteVO xqOrderFreightNoteVO : xqOrderFreightNoteVOS) {
                String filesUrl = xqOrderFreightNoteVO.getPhotos();
                if (StringUtils.isNotBlank(filesUrl)) {
                    String[] split = filesUrl.split(",");
                    ArrayList<String> newPaths = new ArrayList<>(split.length);
                    for (String s : split) {
                        s = ossPath + "/" + s;
                        newPaths.add(s);
                    }
                    String string = newPaths.toString();
                    filesUrl = string.substring(1, string.length() - 1);
                }
                xqOrderFreightNoteVO.setPhotos(filesUrl);
            }
        }
        vo.setOrderFreightNoteVOS(xqOrderFreightNoteVOS);

        /* 问题说明查询 */
        List<XqProblemNoteVO> xqProblemNoteList = this.baseMapper.listProblemNotes(id);
        // group by module_type
        Map<String, List<XqProblemNoteVO>> collect1 =
                xqProblemNoteList.stream().collect(Collectors.groupingBy(XqProblemNoteVO::getModuleType));
        vo.setOrderNotes(collect1.get(CommonConstant.ORDER_MODULE) == null ?
                Collections.emptyList() : collect1.get(CommonConstant.ORDER_MODULE));
        vo.setRemiNotes(collect1.get(CommonConstant.REMITTANCE_MODULE) == null ?
                Collections.emptyList() : collect1.get(CommonConstant.REMITTANCE_MODULE));
        vo.setRawNotes(collect1.get(CommonConstant.RAW_MODULE) == null ?
                Collections.emptyList() : collect1.get(CommonConstant.RAW_MODULE));


        if (xqOrderAllVO.getOrderType().equals("1")) {
            // 卡车
            CompletableFuture<List<XqTruckInfoVO>> truckInfoListFuture = CompletableFuture.supplyAsync(() -> this.baseMapper.listTruckInfo(id));

            truckInfoListFuture.thenAccept(truckInfoList -> {
                if (!truckInfoList.isEmpty()) {
                    vo.setTruckInfos(truckInfoList);
                } else {
                    vo.setTruckInfos(Collections.emptyList());
                }
            });

            truckInfoListFuture.join();

            // 海外仓入库
            CompletableFuture<IPage<EnterStorageDetailPageVO>> enterStorageFuture = CompletableFuture.supplyAsync(() -> xqOverseasEnterHeadMapper.pageEnterStorageDetail(new Page<>(1, 9999), new QueryPageEnterStorageDetailDTO().setOrderNum(xqOrderAllVO.getOrderNum())));

            enterStorageFuture.thenAccept(enterStorageDetailPageVOIPage -> {
                List<EnterStorageDetailPageVO> records = enterStorageDetailPageVOIPage.getRecords();
                vo.setEnterStorageDetailPageVOS(CollectionUtil.isNotEmpty(records) ? records : Collections.emptyList());
            });

            enterStorageFuture.join();

            // 海外仓出库
            CompletableFuture<IPage<ExitStorageDetailPageVO>> exitStorageFuture = CompletableFuture.supplyAsync(() -> xqOverseasExitHeadMapper.pageExitStorageDetail(new Page<>(1, 9999), new QueryPageExitStorageDetailDTO().setSourceRepNum(xqOrderAllVO.getOrderNum())));

            exitStorageFuture.thenAccept(exitStorageDetailPageVOIPage -> {
                List<ExitStorageDetailPageVO> records = exitStorageDetailPageVOIPage.getRecords();
                vo.setExitStorageDetailPageVOS(CollectionUtil.isNotEmpty(records) ? records : Collections.emptyList());
            });

            exitStorageFuture.join();
        }


        // 原料采购
        List<XqOrderRawVO> orderRawVOS1 = vo.getOrderRawVOS();
        List<XqOrderRawVO> collect = orderRawVOS1.stream().filter(i -> StringUtils.isNotBlank(i.getCurrency())).collect(Collectors.toList());
        List<SumShowOrderVO> a = sum(collect, XqOrderRawVO.class, "currency", "weight,purchaseAmount,firstPayment,taxRefundAmount,paymentAmount,cutAmount,unPaymentAmount");
        a.forEach(i ->

        {
            XqOrderRawVO xqOrderRawVO = new XqOrderRawVO();
            String currency = i.getCurrency() + "合计";
            Map<String, BigDecimal> map = i.getMap();
            xqOrderRawVO.setCurrency(currency);

            map.forEach((x, y) -> {
                String methodName = "set" + x.substring(0, 1).toUpperCase() + x.substring(1);
                try {
                    Field declaredField = xqOrderRawVO.getClass().getDeclaredField(x);
                    xqOrderRawVO.getClass().getDeclaredMethod(methodName, declaredField.getType()).invoke(xqOrderRawVO, y);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            orderRawVOS1.add(xqOrderRawVO);
        });

        vo.setOrderRawVOS(orderRawVOS1);


        // 辅料采购
        List<XqOrderAcsrVO> orderAcsrVOS = vo.getOrderAcsrVOS();
        List<XqOrderAcsrVO> collect2 = orderAcsrVOS.stream().filter(i -> StringUtils.isNotBlank(i.getCurrency())).collect(Collectors.toList());
        List<SumShowOrderVO> b = sum(collect2, XqOrderAcsrVO.class, "currency", "purchaseAmount,taxIncludedAmount");

        b.forEach(i ->

        {
            XqOrderAcsrVO vo1 = new XqOrderAcsrVO();
            String currency = i.getCurrency() + "合计";
            Map<String, BigDecimal> map = i.getMap();
            vo1.setCurrency(currency);

            map.forEach((x, y) -> {
                String methodName = "set" + x.substring(0, 1).toUpperCase() + x.substring(1);
                try {
                    Field declaredField = vo1.getClass().getDeclaredField(x);
                    vo1.getClass().getDeclaredMethod(methodName, declaredField.getType()).invoke(vo1, y);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            orderAcsrVOS.add(vo1);
        });

        vo.setOrderAcsrVOS(orderAcsrVOS);

        // 国内
        List<XqOrderFretCostVO> orderFretCostVOS = vo.getOrderFretCostVOS();
        List<XqOrderFretCostVO> collect3 = orderFretCostVOS.stream().filter(i -> StringUtils.isNotBlank(i.getCurrency())).collect(Collectors.toList());
        List<SumShowOrderVO> c = sum(collect3, XqOrderFretCostVO.class, "currency", "price,financeAmount");

        c.forEach(i ->

        {
            XqOrderFretCostVO vo1 = new XqOrderFretCostVO();
            String currency = i.getCurrency() + "合计";
            Map<String, BigDecimal> map = i.getMap();
            vo1.setCurrency(currency);

            map.forEach((x, y) -> {
                String methodName = "set" + x.substring(0, 1).toUpperCase() + x.substring(1);
                try {
                    Field declaredField = vo1.getClass().getDeclaredField(x);
                    vo1.getClass().getDeclaredMethod(methodName, declaredField.getType()).invoke(vo1, y);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            orderFretCostVOS.add(vo1);
        });
        vo.setOrderFretCostVOS(orderFretCostVOS);
        // 国外
        List<XqOrderFretCostVO> orderFretCostVOS2 = vo.getOrderFretCostVOS2();
        List<XqOrderFretCostVO> collect4 = orderFretCostVOS2.stream().filter(i -> StringUtils.isNotBlank(i.getCurrency())).collect(Collectors.toList());
        List<SumShowOrderVO> d = sum(collect4, XqOrderFretCostVO.class, "currency", "price,financeAmount");

        d.forEach(i ->

        {
            XqOrderFretCostVO vo1 = new XqOrderFretCostVO();
            String currency = i.getCurrency() + "合计";
            Map<String, BigDecimal> map = i.getMap();
            vo1.setCurrency(currency);

            map.forEach((x, y) -> {
                String methodName = "set" + x.substring(0, 1).toUpperCase() + x.substring(1);
                try {
                    Field declaredField = vo1.getClass().getDeclaredField(x);
                    vo1.getClass().getDeclaredMethod(methodName, declaredField.getType()).invoke(vo1, y);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            orderFretCostVOS2.add(vo1);
        });
        vo.setOrderFretCostVOS2(orderFretCostVOS2);
        // 退运
        List<XqOrderFretCostVO> orderFretCostReturnFeeVOS = vo.getOrderFretCostReturnFeeVOS();
        List<XqOrderFretCostVO> collect5 = orderFretCostReturnFeeVOS.stream().filter(i -> StringUtils.isNotBlank(i.getCurrency())).collect(Collectors.toList());
        List<SumShowOrderVO> f = sum(collect5, XqOrderFretCostVO.class, "currency", "price,financeAmount");

        f.forEach(i ->

        {
            XqOrderFretCostVO vo1 = new XqOrderFretCostVO();
            String currency = i.getCurrency() + "合计";
            Map<String, BigDecimal> map = i.getMap();
            vo1.setCurrency(currency);

            map.forEach((x, y) -> {
                String methodName = "set" + x.substring(0, 1).toUpperCase() + x.substring(1);
                try {
                    Field declaredField = vo1.getClass().getDeclaredField(x);
                    vo1.getClass().getDeclaredMethod(methodName, declaredField.getType()).invoke(vo1, y);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            orderFretCostReturnFeeVOS.add(vo1);
        });
        vo.setOrderFretCostReturnFeeVOS(orderFretCostReturnFeeVOS);

        return vo;

    }

    @Override
    public List<SellProfitPageVO1> pageSellProfit1(QueryPageSellProfitDTO dto) {
        return baseMapper.pageSellProfit1(dto);
    }

    @Override
    public List<SellProfitTotalVO> totalSellProfit(QueryPageSellProfitDTO dto) {
        List<SellProfitTotalVO> list = new ArrayList<>();
        BigDecimal total = new BigDecimal(0);
        dto.setPageSize(99999);
        IPage<SellProfitPageVO> page = pageSellProfit(dto);

        if (page != null && page.getRecords().size() > 0) {
            for (SellProfitPageVO vo : page.getRecords()) {
                if (vo.getSellProfit() != null) {
                    total = total.add(vo.getSellProfit());
                }
            }
        }
        SellProfitTotalVO vo = new SellProfitTotalVO();
        vo.setTotalSellProfit(total);
        list.add(vo);
        return list;
    }


    @Override
    public void orderExportVO(String orderId, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> rmap = null;
        Map<String, Object> cmap = null;
        Map<String, Object> data = new HashMap<>();
        // 配置模板属性
        Template t = null;
        Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding("UTF-8");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        configuration.setClassLoaderForTemplateLoading(classLoader, "/templates/");
        //导出的文件名
        Writer out = null;
        try {
            request.setCharacterEncoding("UTF-8");
//            configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates/"));
//            //获取模板文件
//            t = configuration.getTemplate("2.ftl");
            t = configuration.getTemplate("2.ftl");
            // 查询数据
            XqOrderExportVO xqOrderExportVO = this.baseMapper.orderExportVOByGzy(orderId);


            if (xqOrderExportVO != null) {
                data.put("orderNum", xqOrderExportVO.getOrderNum());
                data.put("customer", xqOrderExportVO.getCustomer());
                data.put("deliveryAddress", xqOrderExportVO.getDeliveryAddress());
                data.put("invoiceDate", xqOrderExportVO.getInvoiceDate());
                data.put("invoiceNo", xqOrderExportVO.getInvoiceNo());
                data.put("agentName", xqOrderExportVO.getAgentName());
                data.put("etd", xqOrderExportVO.getEtd());
                data.put("eta", xqOrderExportVO.getEta());
                data.put("containerNo", xqOrderExportVO.getContainerNo());
                data.put("billOfLading", xqOrderExportVO.getBillOfLading());
                data.put("transportDetails", xqOrderExportVO.getTransportDetails());
                data.put("voyageNumber", xqOrderExportVO.getVoyageNumber());
                data.put("productionDate", xqOrderExportVO.getProductionDate());

                data.put("termsOfPayment", xqOrderExportVO.getTermsOfPayment());
//                data.put("netWeight", xqOrderExportVO.getNetWeight());
//                data.put("grossWeight", xqOrderExportVO.getGrossWeight());
                data.put("sumVolume", xqOrderExportVO.getSumVolume());
            }
            List<XqOrderExportProductVO> xqOrderExportProductVOS = xqOrderExportVO.getXqOrderExportProductVOS();

            BigDecimal lastGrossWeight = xqOrderExportVO.getAllGrossWeight();
            BigDecimal lastVolume = new BigDecimal(xqOrderExportVO.getSumVolume());
            for (int i = 0; i < xqOrderExportProductVOS.size() - 1; i++) {
                lastGrossWeight = lastGrossWeight.subtract(xqOrderExportProductVOS.get(i).getFtWeightKgs());
                lastVolume = lastVolume.subtract(new BigDecimal(xqOrderExportProductVOS.get(i).getVolume()));
            }
            xqOrderExportProductVOS.get(xqOrderExportProductVOS.size() - 1).setFtWeightKgs(lastGrossWeight.setScale(0, BigDecimal.ROUND_HALF_UP));
            BigDecimal decimal = lastGrossWeight.divide(new BigDecimal("0.454"), BigDecimal.ROUND_HALF_UP);
            xqOrderExportProductVOS.get(xqOrderExportProductVOS.size() - 1).setFtWeightLbs(decimal.setScale(0, BigDecimal.ROUND_HALF_UP));
            xqOrderExportProductVOS.get(xqOrderExportProductVOS.size() - 1).setVolume(lastVolume.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
            // 货币单位
            String currency = xqOrderExportProductVOS.get(0).getCurrency().toUpperCase();
            if (StringUtils.isBlank(currency)) currency = "USD";
            data.put("currency", currency);

            BigDecimal sumWeight = xqOrderExportProductVOS.stream().map(XqOrderExportProductVO::getCgWeightLbs).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            BigDecimal grossWeightByTransferUnit = xqOrderExportProductVOS.stream().map(XqOrderExportProductVO::getFtWeightLbs).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

            BigDecimal netWeight = xqOrderExportProductVOS.stream().map(XqOrderExportProductVO::getCgWeightKgs).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            BigDecimal grossWeight = xqOrderExportProductVOS.stream().map(XqOrderExportProductVO::getFtWeightKgs).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

            BigDecimal sumSalesAmount = xqOrderExportProductVOS.stream().map(XqOrderExportProductVO::getSingleAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            BigDecimal sumBoxes = xqOrderExportProductVOS.stream().map(XqOrderExportProductVO::getTotalBoxes).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            data.put("sumWeight", sumWeight);
            data.put("sumSalesAmount", sumSalesAmount);
            data.put("sumBoxes", sumBoxes);
            data.put("netWeight", netWeight);
            data.put("grossWeight", grossWeight);
            String s = MoneyUtils.convertEnglishAmount(sumSalesAmount.doubleValue(), "USD");
            s = "TOTAL " + currency + " " + s.toUpperCase() + ".";
            data.put("totalAmount", s);

            data.put("weightUnitTxt", xqOrderExportProductVOS.get(0).getWeightUnitTxt() == null ? "LBS" : xqOrderExportProductVOS.get(0).getWeightUnitTxt().toUpperCase());

            BigDecimal unitTransfer = xqOrderExportVO.getUnitTransfer();
            switch ((String) data.get("weightUnitTxt")) {
                case "KG":
                    unitTransfer = BigDecimal.ONE;
                    break;
                case "LBS":
                    unitTransfer = new BigDecimal("2.20463");
                    break;
                case "OZ":
                    unitTransfer = new BigDecimal("35.27397");
                    break;
            }

            data.put("unitTransfer", unitTransfer);
            data.put("netWeightByTransferUnit", data.get("netWeight") == null ? null : unitTransfer.multiply((BigDecimal) data.get("netWeight")));
//            data.put("grossWeightByTransferUnit", data.get("grossWeight") == null ? null : unitTransfer.multiply((BigDecimal) data.get("grossWeight")));
            data.put("grossWeightByTransferUnit", grossWeightByTransferUnit);

            data.put("packagingUnitTxt", xqOrderExportProductVOS.get(0).getPackagingUnitTxt().toUpperCase());


            // N.W.   G.W. 相关数据
            data.put("productInfo", xqOrderExportProductVOS);

            String excelName = xqOrderExportVO.getOrderNum() + ".xls";

            try {
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(excelName, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new InterestingBootException(e.getMessage());
            }

            ServletOutputStream sos = null;
            try {
                // 下载文件能正常显示中文
                sos = response.getOutputStream();
                out = new BufferedWriter(new OutputStreamWriter(sos, "utf-8"));
                // 将填充数据填入模板文件并输出到目标文件
                t.process(data, out);
                out.flush();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @SneakyThrows
    private List<SumShowOrderVO> sum(List<?> dataList, Class<?> dataClass, String groupingBy, String countFeilds) {

        String[] countFeildsList = countFeilds.split(",");
        if (CollectionUtil.isEmpty(dataList)) return new ArrayList<>();

        // 定义返回值
        List<SumShowOrderVO> sumShowOrderVOS = new ArrayList<>();
        // 根据币种分开，key为币种，value为结果集
        Map<String, ? extends List<?>> map = dataList.stream().collect(Collectors.groupingBy(a -> {
            String invoke;
            try {
                invoke = (String) a.getClass().getDeclaredMethod("get" + groupingBy.substring(0, 1).toUpperCase() + groupingBy.substring(1)).invoke(a);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return invoke;
        }));


        map.forEach((i, j) -> {
            // 币种
            SumShowOrderVO sumShowOrderVO = new SumShowOrderVO();
            sumShowOrderVO.setCurrency(i);
            // 这个币种的map对象
            Map<String, BigDecimal> map1 = new HashMap<>();
            // 根据字段循环
            for (String s : countFeildsList) {
                // 相加集合中指定字段的值
                BigDecimal result = j.stream().map(o -> {
                    BigDecimal invoke;
                    try {
                        invoke = (BigDecimal) dataClass.getMethod("get" + s.substring(0, 1).toUpperCase() + s.substring(1)).invoke(o);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                    return invoke;
                }).filter(Objects::nonNull).reduce(BigDecimal::add).orElse(new BigDecimal("0.00"));
                // 每次循环代表一个字段的和，放进map
                map1.put(s, result);

            }
            sumShowOrderVO.setMap(map1);
            sumShowOrderVOS.add(sumShowOrderVO);

        });

        return sumShowOrderVOS;

    }


}
