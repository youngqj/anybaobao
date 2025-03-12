package com.interesting.modules.rawmaterial.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interesting.common.constant.CommonConstant;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.freightcost.entity.XqFreightCostInfo;
import com.interesting.modules.freightcost.service.IXqFreightCostInfoService;
import com.interesting.modules.order.dto.sub.AddOrderFretCostDTO;
import com.interesting.modules.order.dto.sub.AddOrderRawDTO;
import com.interesting.modules.order.mapper.XqOrderMapper;
import com.interesting.modules.order.service.IXqOrderService;
import com.interesting.modules.order.vo.sub.XqOrderFretCostVO;
import com.interesting.modules.rawmaterial.dto.AddWithholdDTO;
import com.interesting.modules.rawmaterial.dto.InstOrUpdtCutAmountDetailDTO;
import com.interesting.modules.rawmaterial.dto.InstOrUpdtPaymentDetailDTO;
import com.interesting.modules.rawmaterial.dto.QueryXqRawMaterialPurchaseDTO;
import com.interesting.modules.rawmaterial.entity.XqRawMaterialPurchase;
import com.interesting.modules.rawmaterial.mapper.XqRawMaterialPurchaseMapper;
import com.interesting.modules.rawmaterial.service.IXqRawMaterialPurchaseService;
import com.interesting.modules.rawmaterial.service.XqPaymentDetailService;
import com.interesting.modules.rawmaterial.vo.XqRawMaterialPurchaseVO;
import com.interesting.modules.remittance.vo.QueryDataNotInDetailVO;
import com.interesting.modules.supplier.entity.XqSupplier;
import com.interesting.modules.supplier.service.IXqSupplierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: 原料采购表
 * @Author: interesting-boot
 * @Date: 2023-06-02
 * @Version: V1.0
 */
@Service
public class XqRawMaterialPurchaseServiceImpl extends ServiceImpl<XqRawMaterialPurchaseMapper, XqRawMaterialPurchase> implements IXqRawMaterialPurchaseService {

    @Resource
    private IXqSupplierService xqSupplierService;
    @Resource
    private IXqFreightCostInfoService xqFreightCostInfoService;
    @Resource
    private XqPaymentDetailService xqPaymentDetailService;
    @Resource
    private XqOrderMapper xqOrderMapper;
    @Resource
    private IXqOrderService xqOrderService;

    @Override
    public IPage<XqRawMaterialPurchaseVO> pageList(Page<XqRawMaterialPurchaseVO> page, QueryXqRawMaterialPurchaseDTO dto) {
        return this.baseMapper.pageList(page, dto);
    }


    /**
     * 差异化处理编辑 原料采购
     *
     * @param orderId
     * @param addOrderRawDTOS
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderRaw(String orderId, List<AddOrderRawDTO> addOrderRawDTOS, Integer roleCode) {

        addOrderRawDTOS.forEach(i -> {
            if (StringUtils.isBlank(i.getId())) {
                i.setId(UUID.randomUUID().toString());
            }
            if (StringUtils.isBlank(i.getCurrency())) {
                throw new InterestingBootException("未填写币种");
            }
        });

        for (int i = 0; i < addOrderRawDTOS.size(); i++) {
            AddOrderRawDTO addOrderRawDTO = addOrderRawDTOS.get(i);
            addOrderRawDTO.setSortNum(i);
        }


        List<XqRawMaterialPurchase> originalComs = this.list(new LambdaQueryWrapper<XqRawMaterialPurchase>().eq(XqRawMaterialPurchase::getOrderId, orderId));

        // 原料采购
        if (roleCode == 5) {

            // 插入参数的id分组
            Map<String, AddOrderRawDTO> addComsMap = new LinkedHashMap<>();
            for (AddOrderRawDTO addOrderRawDTO : addOrderRawDTOS) {
                //供应商是否存在
                XqSupplier supplier = xqSupplierService.getById(addOrderRawDTO.getSupplierId());
                if (StringUtils.isNotBlank(addOrderRawDTO.getSupplierId())) {
                    if (supplier == null) {
                        throw new InterestingBootException("未找到供应商，请重新选择!");
                    } else {

                    }

                }
                addComsMap.put(addOrderRawDTO.getId(), addOrderRawDTO);
            }


            // 查询是否有默认数据
            List<XqFreightCostInfo> gnCostInfoList =
                    xqFreightCostInfoService.list(new LambdaQueryWrapper<XqFreightCostInfo>()
                            .eq(XqFreightCostInfo::getOrderId, orderId)
                            .eq(XqFreightCostInfo::getIzDomestic, 1)
                            .eq(XqFreightCostInfo::getIzReturnFee, 0));

            if (gnCostInfoList.size() == 0) {

                Map<String, XqOrderFretCostVO> fretCostDTOMap = xqOrderService.initFretCostVOs().stream().collect(Collectors.toMap(XqOrderFretCostVO::getFeeType, Function.identity()));

                List<XqFreightCostInfo> toAddComs = new ArrayList<>();

                // 根据供应商id 插入默认数据
                if (addOrderRawDTOS.size() != 0) {

                    // 取第一个供应商信息
                    AddOrderRawDTO orderRawDTO = addOrderRawDTOS.get(0);

                    if (orderRawDTO != null
                            && StringUtils.isNotBlank(orderRawDTO.getSupplierId())
                            && "1".equals(orderRawDTO.getPurchaseType())) {

                        // 国内默认货运信息
                        List<XqOrderFretCostVO> supplierList = xqOrderMapper.listSupplierFretCost(orderRawDTO.getSupplierId());

                        if (supplierList != null && supplierList.size() > 0) {
                            for (XqOrderFretCostVO vo : supplierList) {

                                XqOrderFretCostVO fretCostDTO = fretCostDTOMap.get(vo.getFeeType());
                                if (fretCostDTO != null) {
                                    XqFreightCostInfo info = new XqFreightCostInfo();
                                    BeanUtils.copyProperties(vo, info);
                                    info.setIzDefaultColor(0);
                                    info.setOrderId(orderId);
                                    info.setId(null);
                                    info.setIzDomestic(1);
                                    toAddComs.add(info);
                                    //
                                    fretCostDTOMap.remove(vo.getFeeType());
                                }
                            }

                            for (XqOrderFretCostVO addCom : fretCostDTOMap.values()) {
                                XqFreightCostInfo newCom = new XqFreightCostInfo();
                                BeanUtils.copyProperties(addCom, newCom);
                                newCom.setId(null);
                                newCom.setOrderId(orderId);
                                newCom.setIzDomestic(1);
                                newCom.setIzReturnFee(0);
                                newCom.setIzDefaultColor(0);
                                toAddComs.add(newCom);

                            }

                            if (toAddComs.size() > 0) {
                                xqFreightCostInfoService.saveBatch(toAddComs);
                            }

                        }
                    }
                }
            }


            List<String> toDeleteIds = new ArrayList<>();
            List<XqRawMaterialPurchase> toAddComs = new ArrayList<>();

            for (XqRawMaterialPurchase originalCom : originalComs) {
                AddOrderRawDTO addCom = addComsMap.get(originalCom.getId());
                if (addCom != null) {
                    // 更新记录

                    this.lambdaUpdate()
                            .set(XqRawMaterialPurchase::getSortNum, addCom.getSortNum())
                            .set(XqRawMaterialPurchase::getSupplierId, addCom.getSupplierId())
                            .set(XqRawMaterialPurchase::getPurchaseContract, addCom.getPurchaseContract())
                            .set(XqRawMaterialPurchase::getAgreementNo, addCom.getAgreementNo())
                            .set(XqRawMaterialPurchase::getCategoryId, addCom.getCategoryId())
                            .set(XqRawMaterialPurchase::getPurchaseType, addCom.getPurchaseType())
                            .set(XqRawMaterialPurchase::getProductId, addCom.getProductId())
                            .set(XqRawMaterialPurchase::getWeight, addCom.getWeight())
                            .set(XqRawMaterialPurchase::getWeightUnit, addCom.getWeightUnit())
                            .set(XqRawMaterialPurchase::getCurrency, addCom.getCurrency())
                            .set(XqRawMaterialPurchase::getUnitPrice, addCom.getUnitPrice())
                            .set(XqRawMaterialPurchase::getPurchaseAmount, addCom.getPurchaseAmount())
                            .set(XqRawMaterialPurchase::getFirstPaymentExpireDate, addCom.getFirstPaymentExpireDate())
                            .set(XqRawMaterialPurchase::getPaymentExpireDate, addCom.getPaymentExpireDate())
                            .set(XqRawMaterialPurchase::getTaxRefundRate, addCom.getTaxRefundRate())
                            .set(XqRawMaterialPurchase::getTaxRefundAmount, addCom.getTaxRefundAmount())
                            .set(XqRawMaterialPurchase::getPurchaseNote, addCom.getPurchaseNote())
                            .set(XqRawMaterialPurchase::getUnPaymentAmount, addCom.getUnPaymentAmount())
                            .set(XqRawMaterialPurchase::getFirstPayment, addCom.getFirstPayment())
                            .set(XqRawMaterialPurchase::getLastPayment, addCom.getLastPayment())
                            .set(XqRawMaterialPurchase::getFirstPaymentRate, addCom.getFirstPaymentRate())
                            .set(XqRawMaterialPurchase::getRealUnitPrice, addCom.getRealUnitPrice())
                            .set(XqRawMaterialPurchase::getLayoutRequirements, addCom.getLayoutRequirements())
                            .set(XqRawMaterialPurchase::getPurchaseAmountCny, addCom.getPurchaseAmountCny())
                            .eq(XqRawMaterialPurchase::getId, originalCom.getId())
                            .update();


                    // 从传入的佣金记录列表中移除已经更新的记录
                    addComsMap.remove(originalCom.getId());
                } else {
                    // 删除记录
                    toDeleteIds.add(originalCom.getId());
                }
            }

            this.removeByIds(toDeleteIds);

            for (AddOrderRawDTO addCom : addComsMap.values()) {
                XqRawMaterialPurchase newCom = new XqRawMaterialPurchase();
                BeanUtils.copyProperties(addCom, newCom);
                newCom.setId(null);
                newCom.setOrderId(orderId);
                toAddComs.add(newCom);
            }

            this.saveBatch(toAddComs);


        } else if (roleCode == 4) {
            // 出纳人员


            for (AddOrderRawDTO addOrderRawDTO : addOrderRawDTOS) {

                this.lambdaUpdate()
                        .set(XqRawMaterialPurchase::getPaymentAmount, addOrderRawDTO.getPaymentAmount())
                        .set(XqRawMaterialPurchase::getFinanceCompleteState, addOrderRawDTO.getFinanceCompleteState())
                        .set(XqRawMaterialPurchase::getPaymentStatus, addOrderRawDTO.getPaymentStatus())
                        .set(XqRawMaterialPurchase::getFinancialNote, addOrderRawDTO.getFinancialNote())
                        .set(XqRawMaterialPurchase::getUnPaymentAmount, addOrderRawDTO.getUnPaymentAmount())
                        .set(XqRawMaterialPurchase::getCutAmount, addOrderRawDTO.getCutAmount())
                        .set(XqRawMaterialPurchase::getWithholdAmount, addOrderRawDTO.getWithholdAmount())
                        .set(XqRawMaterialPurchase::getPurchaseAmountCny, addOrderRawDTO.getPurchaseAmountCny())
                        .eq(XqRawMaterialPurchase::getId, addOrderRawDTO.getId())
                        .update();

                // 预扣金额信息
                String withholdStr = addOrderRawDTO.getWithholdStr();
                if (StringUtils.isNotBlank(withholdStr)) {

                    ObjectMapper objectMapper = new ObjectMapper();
                    List<AddWithholdDTO> paymentDetails = null;
                    try {
                        paymentDetails = objectMapper.readValue(withholdStr,
                                new TypeReference<List<AddWithholdDTO>>() {
                                });
                    } catch (JsonProcessingException e) {
                        throw new InterestingBootException(e.getMessage());
                    }
                    paymentDetails.forEach(i -> {
                        if (StringUtils.isBlank(i.getId())) {
                            i.setId(UUID.randomUUID().toString());
                        }
                    });
                    if (paymentDetails != null && paymentDetails.size() > 0) {
                        xqPaymentDetailService.updateWithholdDetails(addOrderRawDTO.getId(), paymentDetails);
                    }

                }

                // 更新付款详情
                String paymentDetailsStr = addOrderRawDTO.getPaymentDetailsStr();
                List<InstOrUpdtPaymentDetailDTO> finalPaymentDetails = new ArrayList<>();
                if (StringUtils.isNotBlank(paymentDetailsStr)) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<InstOrUpdtPaymentDetailDTO> paymentDetails = null;
                    try {
                        paymentDetails = objectMapper.readValue(paymentDetailsStr,
                                new TypeReference<List<InstOrUpdtPaymentDetailDTO>>() {
                                });
                    } catch (JsonProcessingException e) {
                        throw new InterestingBootException(e.getMessage());
                    }
                    paymentDetails.forEach(i -> {
                        if (StringUtils.isBlank(i.getId())) {
                            i.setId(UUID.randomUUID().toString());
                        }
                    });
                    finalPaymentDetails = paymentDetails;
                }
                // 更新扣款详情
                String cutAmountDetailsStr = addOrderRawDTO.getCutDetailsStr();
                if (StringUtils.isNotBlank(cutAmountDetailsStr)) {
                    ObjectMapper objectMapper1 = new ObjectMapper();
                    List<InstOrUpdtCutAmountDetailDTO> cutAmountDetails = null;
                    try {
                        cutAmountDetails = objectMapper1.readValue(cutAmountDetailsStr,
                                new TypeReference<List<InstOrUpdtCutAmountDetailDTO>>() {
                                });
                    } catch (JsonProcessingException e) {
                        throw new InterestingBootException(e.getMessage());
                    }
                    for (InstOrUpdtCutAmountDetailDTO dto : cutAmountDetails) {

                        if (StringUtils.isBlank(dto.getId())) {
                            dto.setId(UUID.randomUUID().toString());
                        } else {
                            InstOrUpdtPaymentDetailDTO instOrUpdtPaymentDetailDTO = new InstOrUpdtPaymentDetailDTO();
                            instOrUpdtPaymentDetailDTO.setId(dto.getId());
                            instOrUpdtPaymentDetailDTO.setPayMoney(dto.getCutAmount());
                            instOrUpdtPaymentDetailDTO.setPayRemark(dto.getPayRemark());
                            instOrUpdtPaymentDetailDTO.setType(dto.getType());
                            instOrUpdtPaymentDetailDTO.setPayTime(dto.getPayTime());
                            finalPaymentDetails.add(instOrUpdtPaymentDetailDTO);
                        }
                    }
                }

                xqPaymentDetailService.updatePaymentDetails(addOrderRawDTO.getId(), finalPaymentDetails);
            }

        } else if (roleCode == 7) {
            for (AddOrderRawDTO addOrderRawDTO : addOrderRawDTOS) {
                this.lambdaUpdate()
                        .set(XqRawMaterialPurchase::getInspectionNote, addOrderRawDTO.getInspectionNote())
                        .eq(XqRawMaterialPurchase::getId, addOrderRawDTO.getId())
                        .update();
            }
        }
    }

    @Override
    public List<QueryDataNotInDetailVO> queryNotPayOrder() {
        return this.baseMapper.queryNotPayOrder();
    }
}
