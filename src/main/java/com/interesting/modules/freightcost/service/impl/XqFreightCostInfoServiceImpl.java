package com.interesting.modules.freightcost.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.freightcost.dto.QueryXqFreightCostInfoDTO;
import com.interesting.modules.freightcost.entity.XqFreightCostInfo;
import com.interesting.modules.freightcost.mapper.XqFreightCostInfoMapper;
import com.interesting.modules.freightcost.service.IXqFreightCostInfoService;
import com.interesting.modules.freightcost.vo.XqFreightCostInfoVO;
import com.interesting.modules.order.dto.sub.AddOrderFretCostDTO;
import com.interesting.modules.supplier.entity.XqSupplier;
import com.interesting.modules.supplier.service.IXqSupplierService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: 货运费用信息
 * @Author: interesting-boot
 * @Date: 2023-06-02
 * @Version: V1.0
 */
@Service
public class XqFreightCostInfoServiceImpl extends ServiceImpl<XqFreightCostInfoMapper, XqFreightCostInfo> implements IXqFreightCostInfoService {

    @Autowired
    private IXqSupplierService xqSupplierService;


    @Override
    public IPage<XqFreightCostInfoVO> pageList(Page<XqFreightCostInfoVO> page, QueryXqFreightCostInfoDTO dto) {
        return this.baseMapper.pageList(page, dto);
    }


    /**
     * 差异化处理编辑 货运费用（国内）
     *
     * @param orderId
     * @param addOrderFretCostDTOS
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderFretCost(String orderId, List<AddOrderFretCostDTO> addOrderFretCostDTOS, Integer roleCode) {

        // 货运人员
        if (roleCode == 7) {

//            List<XqFreightCostInfo> updateList = new ArrayList<>();
//
//            for (AddOrderFretCostDTO addOrderFretCostDTO : addOrderFretCostDTOS) {
//                if(addOrderFretCostDTO.getId() != null){
//                    XqFreightCostInfo newCom = new XqFreightCostInfo();
//                    BeanUtils.copyProperties(addOrderFretCostDTO, newCom);
//                    newCom.setOrderId(orderId);
////                    updateList.add(newCom);
//                    saveOrUpdate(newCom);
//                }
//            }


            // 处理历史数据

            List<XqFreightCostInfo> toAddComs = new ArrayList<>();
            for (AddOrderFretCostDTO addOrderFretCostDTO : addOrderFretCostDTOS) {
                if (addOrderFretCostDTO.getId() != null && addOrderFretCostDTO.getId().length() == 20 && addOrderFretCostDTO.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                    XqFreightCostInfo newCom = new XqFreightCostInfo();
                    BeanUtils.copyProperties(addOrderFretCostDTO, newCom);
                    newCom.setId(null);
                    newCom.setOrderId(orderId);
                    newCom.setIzDomestic(1);
                    newCom.setIzReturnFee(0);
                    toAddComs.add(newCom);
                } else if (addOrderFretCostDTO.getId() == null) {
                    addOrderFretCostDTO.setId(UUID.randomUUID().toString());
                }
            }

//
//            addOrderFretCostDTOS.forEach(i -> {
//                if (StringUtils.isBlank(i.getId())) {
//                    i.setId(UUID.randomUUID().toString());
//                }
//            });

            // 查询国内货运信息
            List<XqFreightCostInfo> originalComs =
                    this.list(new LambdaQueryWrapper<XqFreightCostInfo>()
                            .eq(XqFreightCostInfo::getOrderId, orderId)
                            .eq(XqFreightCostInfo::getIzDomestic, 1)
                            .eq(XqFreightCostInfo::getIzReturnFee, 0));


            // 插入参数的id分组
            Map<String, AddOrderFretCostDTO> addComsMap = addOrderFretCostDTOS.stream()
                    .collect(Collectors.toMap(AddOrderFretCostDTO::getId, Function.identity()));

//            List<String> toDeleteIds = new ArrayList<>();


            for (XqFreightCostInfo originalCom : originalComs) {
                AddOrderFretCostDTO addCom = addComsMap.get(originalCom.getId());
                if (addCom != null) {

                    if (null == addCom.getPrice()) {
                        addCom.setPrice(BigDecimal.ZERO);
                    }

                    this.lambdaUpdate()
                            .set(XqFreightCostInfo::getAgent, addCom.getAgent())
                            .set(XqFreightCostInfo::getRemark, addCom.getRemark())
                            .set(XqFreightCostInfo::getCurrency, addCom.getCurrency())
                            .set(XqFreightCostInfo::getPrice, addCom.getPrice())
                            .set(XqFreightCostInfo::getFilesUrl, addCom.getFilesUrl())
                            .set(XqFreightCostInfo::getIzDomestic, 1)
                            .set(XqFreightCostInfo::getIzReturnFee, 0)
                            .set(XqFreightCostInfo::getIzDefaultColor, addCom.getIzDefaultColor())
                            .eq(XqFreightCostInfo::getId, addCom.getId())
                            .update();
                }
//                    // 从传入的列表中移除已经更新的记录
//                    addComsMap.remove(originalCom.getId());
//                } else {
//                    // 删除记录
//                    toDeleteIds.add(originalCom.getId());
//                }
            }

//            this.removeByIds(toDeleteIds);

//            for (AddOrderFretCostDTO addCom : addComsMap.values()) {
//                XqFreightCostInfo newCom = new XqFreightCostInfo();
//                BeanUtils.copyProperties(addCom, newCom);
//                newCom.setId(null);
//                newCom.setOrderId(orderId);
//                newCom.setIzDomestic(1);
//                newCom.setIzReturnFee(0);
//                newCom.setIzDefaultColor(0);
//                toAddComs.add(newCom);
//
//            }
//
            if (toAddComs.size() > 0) {

                this.saveBatch(toAddComs);
            }
        } else if (roleCode == 2 && CollectionUtil.isNotEmpty(addOrderFretCostDTOS)) {
            // 国内财务

            for (AddOrderFretCostDTO addOrderFretCostDTO : addOrderFretCostDTOS) {
                this.lambdaUpdate()
                        .set(XqFreightCostInfo::getFinanceAmount, addOrderFretCostDTO.getFinanceAmount())
                        .set(XqFreightCostInfo::getIzDefaultColor, addOrderFretCostDTO.getIzDefaultColor())
                        .set(XqFreightCostInfo::getFinanceAuditTime, addOrderFretCostDTO.getFinanceAuditTime())
                        .eq(XqFreightCostInfo::getId, addOrderFretCostDTO.getId())
                        .update();

            }

        } else {
            return;
        }

    }


    /**
     * 差异化处理编辑 货运费用（国外）
     *
     * @param orderId
     * @param addOrderFretCostDTOS
     * @param roleCode
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderFretCost2(String orderId, List<AddOrderFretCostDTO> addOrderFretCostDTOS, Integer roleCode) {

        if (roleCode == 7) {
            addOrderFretCostDTOS.forEach(i -> {
                if (StringUtils.isBlank(i.getId())) {
                    i.setId(UUID.randomUUID().toString());
                }
            });

            List<XqFreightCostInfo> originalComs =
                    this.list(new LambdaQueryWrapper<XqFreightCostInfo>()
                            .eq(XqFreightCostInfo::getOrderId, orderId)
                            .eq(XqFreightCostInfo::getIzDomestic, 0)
                            .eq(XqFreightCostInfo::getIzReturnFee, 0));
            // 插入参数的id分组
            Map<String, AddOrderFretCostDTO> addComsMap = addOrderFretCostDTOS.stream().collect(Collectors.toMap(AddOrderFretCostDTO::getId, Function.identity()));
            List<String> toDeleteIds = new ArrayList<>();
            List<XqFreightCostInfo> toAddComs = new ArrayList<>();

            for (XqFreightCostInfo originalCom : originalComs) {
                AddOrderFretCostDTO addCom = addComsMap.get(originalCom.getId());
                if (addCom != null) {

                    if (null == addCom.getPrice()) {
                        originalCom.setPrice(BigDecimal.ZERO);
                    }

                    this.lambdaUpdate()
                            .set(XqFreightCostInfo::getAgent, addCom.getAgent())
                            .set(XqFreightCostInfo::getRemark, addCom.getRemark())
                            .set(XqFreightCostInfo::getCurrency, addCom.getCurrency())
                            .set(XqFreightCostInfo::getPrice, addCom.getPrice())
                            .set(XqFreightCostInfo::getFilesUrl, addCom.getFilesUrl())
                            .set(XqFreightCostInfo::getIzDomestic, 0)
                            .set(XqFreightCostInfo::getIzReturnFee, 0)
                            .set(XqFreightCostInfo::getIzDefaultColor, addCom.getIzDefaultColor())
                            .eq(XqFreightCostInfo::getId, addCom.getId())
                            .update();

                    // 从传入的佣金记录列表中移除已经更新的记录
                    addComsMap.remove(originalCom.getId());
                } else {
                    // 删除记录
                    toDeleteIds.add(originalCom.getId());
                }
            }

            this.removeByIds(toDeleteIds);

            for (AddOrderFretCostDTO addCom : addComsMap.values()) {
                XqFreightCostInfo newCom = new XqFreightCostInfo();
                BeanUtils.copyProperties(addCom, newCom);
                newCom.setId(null);
                newCom.setIzDomestic(0);
                newCom.setIzReturnFee(0);
                newCom.setIzDefaultColor(0);
                newCom.setOrderId(orderId);
                toAddComs.add(newCom);
            }

            this.saveBatch(toAddComs);

        } else if (roleCode == 2 && CollectionUtil.isNotEmpty(addOrderFretCostDTOS)) {
            for (AddOrderFretCostDTO addOrderFretCostDTO : addOrderFretCostDTOS) {
                this.lambdaUpdate()
                        .set(XqFreightCostInfo::getFinanceAmount, addOrderFretCostDTO.getFinanceAmount())
                        .set(XqFreightCostInfo::getFinanceAuditTime, addOrderFretCostDTO.getFinanceAuditTime())
                        .set(XqFreightCostInfo::getIzDefaultColor, addOrderFretCostDTO.getIzDefaultColor())
                        .eq(XqFreightCostInfo::getId, addOrderFretCostDTO.getId())
                        .update();

            }

        } else {
            return;
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderFretCostReturnFee(String id, List<AddOrderFretCostDTO> orderFretCostReturnFeeVOS, Integer roleCode) {

        if (roleCode == 7) {
            orderFretCostReturnFeeVOS.forEach(i -> {
                if (StringUtils.isBlank(i.getId())) {
                    i.setId(UUID.randomUUID().toString());
                }
            });

            List<XqFreightCostInfo> originalComs =
                    this.list(new LambdaQueryWrapper<XqFreightCostInfo>()
                            .eq(XqFreightCostInfo::getOrderId, id)
                            .eq(XqFreightCostInfo::getIzReturnFee, 1));

            Map<String, AddOrderFretCostDTO> addComsMap = new LinkedHashMap<>();
            for (AddOrderFretCostDTO addOrderFretCostDTO : orderFretCostReturnFeeVOS) {
                addComsMap.put(addOrderFretCostDTO.getId(), addOrderFretCostDTO);
            }

            List<String> toDeleteIds = new ArrayList<>();
            List<XqFreightCostInfo> toAddComs = new ArrayList<>();

            for (XqFreightCostInfo originalCom : originalComs) {
                AddOrderFretCostDTO addCom = addComsMap.get(originalCom.getId());
                if (addCom != null) {
                    if (addCom.getPayTime() != null) {
                        originalCom.setPayMoney(addCom.getFinanceAmount());
                    } else {
                        originalCom.setPayMoney(BigDecimal.ZERO);
                    }
                    // 更新记录
                    this.lambdaUpdate()
                            .set(XqFreightCostInfo::getAgent, addCom.getAgent())
                            .set(XqFreightCostInfo::getRemark, addCom.getRemark())
                            .set(XqFreightCostInfo::getCurrency, addCom.getCurrency())
                            .set(XqFreightCostInfo::getPrice, addCom.getPrice())
                            .set(XqFreightCostInfo::getApplicationTime, addCom.getApplicationTime())
                            .set(XqFreightCostInfo::getPayTime, addCom.getPayTime())
                            .set(XqFreightCostInfo::getFilesUrl, addCom.getFilesUrl())
                            .set(XqFreightCostInfo::getIzReturnFee, 1)
                            .set(XqFreightCostInfo::getProductId, addCom.getProductId())
                            .set(XqFreightCostInfo::getLayoutRequirements, addCom.getLayoutRequirements())
                            .set(XqFreightCostInfo::getPayMoney, originalCom.getPayMoney())
                            .set(XqFreightCostInfo::getIzDomestic, null)
                            .set(XqFreightCostInfo::getFeeType, addCom.getFeeType())
                            .eq(XqFreightCostInfo::getId, originalCom.getId())
                            .update();

                    // 从传入的列表中移除已经更新的记录
                    addComsMap.remove(originalCom.getId());
                } else {
                    // 删除记录
                    toDeleteIds.add(originalCom.getId());
                }
            }

            this.removeByIds(toDeleteIds);

            for (AddOrderFretCostDTO addCom : addComsMap.values()) {
                XqFreightCostInfo newCom = new XqFreightCostInfo();
                BeanUtils.copyProperties(addCom, newCom);
                newCom.setId(null);
                newCom.setOrderId(id);
                newCom.setIzDomestic(null);
                newCom.setIzReturnFee(1);
                newCom.setFeeType(addCom.getFeeType());
                toAddComs.add(newCom);
            }

            this.saveBatch(toAddComs);
        } else if (roleCode == 2 && CollectionUtil.isNotEmpty(orderFretCostReturnFeeVOS)) {
            for (AddOrderFretCostDTO addOrderFretCostDTO : orderFretCostReturnFeeVOS) {
                this.lambdaUpdate()
                        .set(XqFreightCostInfo::getFinanceAmount, addOrderFretCostDTO.getFinanceAmount())
                        .set(XqFreightCostInfo::getFinanceAuditTime, addOrderFretCostDTO.getFinanceAuditTime())
                        .eq(XqFreightCostInfo::getId, addOrderFretCostDTO.getId())
                        .update();

            }

        } else {
            return;
        }
    }
}
