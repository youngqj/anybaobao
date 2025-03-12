package com.interesting.modules.accmaterial.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.accmaterial.dto.InstOrUpdtAccInventoryDTO;
import com.interesting.modules.accmaterial.dto.QueryXqAccessoriesPurchaseDTO;
import com.interesting.modules.accmaterial.entity.XqAccessoriesPurchase;
import com.interesting.modules.accmaterial.entity.XqAccessoryInfo;
import com.interesting.modules.accmaterial.entity.XqInventory;
import com.interesting.modules.accmaterial.mapper.XqAccessoriesPurchaseMapper;
import com.interesting.modules.accmaterial.service.IXqAccessoriesPurchaseService;
import com.interesting.modules.accmaterial.service.XqAccessoryInfoService;
import com.interesting.modules.accmaterial.service.XqInventoryService;
import com.interesting.modules.accmaterial.vo.XqAccessoriesExportVO;
import com.interesting.modules.accmaterial.vo.XqAccessoriesPurchaseVO;
import com.interesting.modules.order.dto.sub.AddOrderAcsrDTO;
import com.interesting.modules.order.service.IXqOrderService;
import com.interesting.modules.supplier.entity.XqSupplier;
import com.interesting.modules.supplier.service.IXqSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description: 辅料采购表
 * @Author: interesting-boot
 * @Date: 2023-06-02
 * @Version: V1.0
 */
@Service
public class XqAccessoriesPurchaseServiceImpl extends ServiceImpl<XqAccessoriesPurchaseMapper, XqAccessoriesPurchase> implements IXqAccessoriesPurchaseService {


    @Resource
    private IXqOrderService xqOrderService;
    @Resource
    private IXqSupplierService xqSupplierService;
    @Resource
    private XqAccessoryInfoService xqAccessoryInfoService;
    @Autowired
    private XqInventoryService xqInventoryService;


    @Override
    public IPage<XqAccessoriesPurchaseVO> pageList(Page<XqAccessoriesPurchaseVO> page, QueryXqAccessoriesPurchaseDTO dto) {
        return this.baseMapper.pageList(page, dto);
    }


    /**
     * 差异化处理编辑 辅料采购
     *
     * @param orderId
     * @param addOrderAcsrDTOS
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAccessories(String orderId, List<AddOrderAcsrDTO> addOrderAcsrDTOS, String orderNum, Integer roleCode) {

        if (roleCode == 6) {
            addOrderAcsrDTOS.forEach(i -> {
                if (StringUtils.isBlank(i.getId())) {
                    i.setId(UUID.randomUUID().toString());
                }
                if (StringUtils.isBlank(i.getCurrency())) {
                    throw new InterestingBootException("辅料信息未填写币种");
                }
            });
            for (int i = 0; i < addOrderAcsrDTOS.size(); i++) {
                AddOrderAcsrDTO addOrderAcsrDTO = addOrderAcsrDTOS.get(i);
                addOrderAcsrDTO.setSortNum(i);
            }

            List<XqAccessoriesPurchase> originalComs = this.list(new LambdaQueryWrapper<XqAccessoriesPurchase>().eq(XqAccessoriesPurchase::getOrderId, orderId));

            Map<String, AddOrderAcsrDTO> addComsMap = new LinkedHashMap<>();
            for (AddOrderAcsrDTO addOrderRawDTO : addOrderAcsrDTOS) {
                //供应商是否存在
                if (StringUtils.isNotBlank(addOrderRawDTO.getSupplierId())) {
                    XqSupplier supplier = xqSupplierService.getById(addOrderRawDTO.getSupplierId());
                    if (supplier == null) {
                        //新增
                        XqSupplier xqSupplier = new XqSupplier();
                        xqSupplier.setName(addOrderRawDTO.getSupplierId());
                        xqSupplier.setType("2");
                        xqSupplierService.save(xqSupplier);
                        addOrderRawDTO.setSupplierId(xqSupplier.getId());
                    }
                }
                addComsMap.put(addOrderRawDTO.getId(), addOrderRawDTO);
            }

            List<XqAccessoriesPurchase> toUpdateComs = new ArrayList<>();
            List<String> toDeleteIds = new ArrayList<>();
//        List<XqAccessoriesPurchase> toAddComs = new ArrayList<>();

            for (XqAccessoriesPurchase originalCom : originalComs) {
                AddOrderAcsrDTO addCom = addComsMap.get(originalCom.getId());
                if (addCom != null) {
                    // 更新记录
                    BeanUtils.copyProperties(addCom, originalCom);

                    /* 新增辅料信息，有则直接插入id，没有则新增辅料信息再插入id */
                    String accessoryName = addCom.getAccessoryName().trim();
                    String size = addCom.getSize().trim();
                    String msf = addCom.getMaterialSpecification().trim();
                    XqAccessoryInfo one = xqAccessoryInfoService.getByCondition(accessoryName, size, msf);
                    if (one == null) {
                        throw new InterestingBootException("未找到辅料-" + accessoryName + "|" + size + "|" + msf + ",请先去录入");
                    } else {
                        originalCom.setAccessoryId(one.getId());
                    }

                    /* 更新用料or退料详情记录 */
                    // 用料
                    // parse json
                    if (StringUtils.isNotBlank(addCom.getUseInventoryDetailsStr())) {
                        JSONArray objects = JSON.parseArray(addCom.getUseInventoryDetailsStr());
                        ArrayList<InstOrUpdtAccInventoryDTO> list1 = new ArrayList<>();
                        for (int i = 0; i < objects.size(); i++) {
                            JSONObject jsonObject = objects.getJSONObject(i);
                            InstOrUpdtAccInventoryDTO paymentDetail =
                                    jsonObject.toJavaObject(InstOrUpdtAccInventoryDTO.class);
                            list1.add(paymentDetail);
                            XqInventory xqInventory = xqInventoryService.getOne(new LambdaQueryWrapper<XqInventory>().eq(XqInventory::getId, paymentDetail.getId()));
                            if (xqInventory == null) {
                                //库存判断
                                Integer skuNum = xqInventoryService.getInventoryNum(paymentDetail.getWarehouseId(), originalCom.getAccessoryId(), paymentDetail.getSourceRepNum(), paymentDetail.getUnitPrice());
                                if (skuNum.compareTo(paymentDetail.getNum()) < 0) {

                                    throw new InterestingBootException("辅料 - " + accessoryName + " | " + size + " | " + msf + "用料后库存不足！不可以用料");
                                }
                            }
                        }
                        addCom.setUseInventoryDetails(list1);
                        List<InstOrUpdtAccInventoryDTO> useInventoryDetails = addCom.getUseInventoryDetails();
                        useInventoryDetails.forEach(i -> {
                            if (StringUtils.isBlank(i.getId())) {
                                i.setId(UUID.randomUUID().toString());
                            }
                            // 设置都为负数
                            if (i.getNum() > 0) {
                                i.setNum(-i.getNum());
                            }


                        });

                        xqInventoryService.updateUseInventoryDetails(originalCom.getId(), useInventoryDetails, originalCom.getAccessoryId());
                    } else {
                        // 清除用料信息
                        xqInventoryService.lambdaUpdate()
                                .set(XqInventory::getIzDelete, 1)
                                .eq(XqInventory::getSourceId, originalCom.getId())
                                .eq(XqInventory::getDirection, 2)
                                .update();
                    }

                    // 退料
                    // parse json
                    if (StringUtils.isNotBlank(addCom.getBackInventoryDetailsStr())) {
                        JSONArray objects2 = JSON.parseArray(addCom.getBackInventoryDetailsStr());
                        ArrayList<InstOrUpdtAccInventoryDTO> list2 = new ArrayList<>();
                        for (int i = 0; i < objects2.size(); i++) {
                            JSONObject jsonObject = objects2.getJSONObject(i);
                            InstOrUpdtAccInventoryDTO paymentDetail =
                                    jsonObject.toJavaObject(InstOrUpdtAccInventoryDTO.class);
                            // todo 设置单价
                            paymentDetail.setUnitPrice(addCom.getUnitPrice());
                            paymentDetail.setCurrency(addCom.getCurrency());
                            list2.add(paymentDetail);
                        }
                        addCom.setBackInventoryDetails(list2);

                        List<InstOrUpdtAccInventoryDTO> backInventoryDetails = addCom.getBackInventoryDetails();
                        backInventoryDetails.forEach(i -> {
                            if (StringUtils.isBlank(i.getId())) {
                                i.setId(UUID.randomUUID().toString());
                            }
                            // 设置都为正数
                            if (i.getNum() < 0) {
                                i.setNum(-i.getNum());
                            }
                        });
                        xqInventoryService.updateBackInventoryDetails(originalCom.getId(),
                                backInventoryDetails,
                                originalCom.getAccessoryId(),
                                orderNum);
                    }else {
                        // 清除退料信息
                        xqInventoryService.lambdaUpdate()
                                .set(XqInventory::getIzDelete, 1)
                                .eq(XqInventory::getSourceId, originalCom.getId())
                                .eq(XqInventory::getDirection, 1)
                                .update();
                    }

                    toUpdateComs.add(originalCom);
                    this.lambdaUpdate()
                            .set(XqAccessoriesPurchase::getOrderId, originalCom.getOrderId())
                            .set(XqAccessoriesPurchase::getSortNum, originalCom.getSortNum())
                            .set(XqAccessoriesPurchase::getProductId, originalCom.getProductId())
                            .set(XqAccessoriesPurchase::getPackaging, originalCom.getPackaging())
                            .set(XqAccessoriesPurchase::getPackagingUnit, originalCom.getPackagingUnit())
                            .set(XqAccessoriesPurchase::getLayoutRequirements, originalCom.getLayoutRequirements())
                            .set(XqAccessoriesPurchase::getSupplierId, originalCom.getSupplierId())
                            .set(XqAccessoriesPurchase::getPurchaseContractNo, originalCom.getPurchaseContractNo())
                            .set(XqAccessoriesPurchase::getAccessoryId, originalCom.getAccessoryId())
                            .set(XqAccessoriesPurchase::getOrderQuantity, originalCom.getOrderQuantity())
                            .set(XqAccessoriesPurchase::getCurrency, originalCom.getCurrency())
                            .set(XqAccessoriesPurchase::getUnitPrice, originalCom.getUnitPrice())
                            .set(XqAccessoriesPurchase::getPurchaseAmount, originalCom.getPurchaseAmount())
                            .set(XqAccessoriesPurchase::getTaxRate, originalCom.getTaxRate())
                            .set(XqAccessoriesPurchase::getTaxIncludedAmount, originalCom.getTaxIncludedAmount())
                            .set(XqAccessoriesPurchase::getPurchaseNote, originalCom.getPurchaseNote())
                            .eq(XqAccessoriesPurchase::getId, originalCom.getId())
                            .update();


                    // 从传入的佣金记录列表中移除已经更新的记录
                    addComsMap.remove(originalCom.getId());
                } else {
                    // 删除记录
                    toDeleteIds.add(originalCom.getId());
                }
            }

            this.removeByIds(toDeleteIds);
            if (!CollectionUtils.isEmpty(toDeleteIds)) {
                // 清除退料信息
                xqInventoryService.lambdaUpdate()
                        .set(XqInventory::getIzDelete, 1)
                        .in(XqInventory::getSourceId, toDeleteIds)
                        .eq(XqInventory::getDirection, 1)
                        .remove();
                // 清除用料信息
                xqInventoryService.lambdaUpdate()
                        .set(XqInventory::getIzDelete, 1)
                        .in(XqInventory::getSourceId, toDeleteIds)
                        .eq(XqInventory::getDirection, 2)
                        .update();
            }

            for (AddOrderAcsrDTO addCom : addComsMap.values()) {
                XqAccessoriesPurchase newCom = new XqAccessoriesPurchase();
                BeanUtils.copyProperties(addCom, newCom);

                /* 新增辅料信息，有则直接插入id，没有则新增辅料信息再插入id */
                if (StringUtils.isBlank(addCom.getAccessoryName()) && StringUtils.isBlank(addCom.getSize()) && StringUtils.isBlank(addCom.getMaterialSpecification())) {
                    continue;
                }
                String accessoryName = addCom.getAccessoryName().trim();
                String size = addCom.getSize().trim();
                String msf = addCom.getMaterialSpecification().trim();
                XqAccessoryInfo one = xqAccessoryInfoService.getByCondition(accessoryName, size, msf);
                if (one == null) {
                    throw new InterestingBootException("未找到辅料- " + accessoryName + " | " + size + " | " + msf + ",请先去录入");
                } else {
                    newCom.setAccessoryId(one.getId());
                }

                newCom.setId(null);
                newCom.setOrderId(orderId);

                this.save(newCom);
                /* 更新用料or退料详情记录 */
                // 用料
                // parse json
                if (StringUtils.isNotBlank(addCom.getUseInventoryDetailsStr())) {
                    JSONArray objects = JSON.parseArray(addCom.getUseInventoryDetailsStr());
                    ArrayList<InstOrUpdtAccInventoryDTO> list1 = new ArrayList<>();
                    for (int i = 0; i < objects.size(); i++) {
                        JSONObject jsonObject = objects.getJSONObject(i);
                        InstOrUpdtAccInventoryDTO paymentDetail =
                                jsonObject.toJavaObject(InstOrUpdtAccInventoryDTO.class);
                        list1.add(paymentDetail);
                        XqInventory xqInventory = xqInventoryService.getOne(new LambdaQueryWrapper<XqInventory>().eq(XqInventory::getId, paymentDetail.getId()));
                        if (xqInventory == null) {
                            //库存判断
                            Integer skuNum = xqInventoryService.getInventoryNum(paymentDetail.getWarehouseId(), newCom.getAccessoryId(), paymentDetail.getSourceRepNum(), paymentDetail.getUnitPrice());
                            if (skuNum.compareTo(paymentDetail.getNum()) < 0) {

                                throw new InterestingBootException("辅料 - " + accessoryName + " | " + size + " | " + msf + "用料后库存不足！不可以用料");
                            }

                        }
                    }

                    addCom.setUseInventoryDetails(list1);
                    List<InstOrUpdtAccInventoryDTO> useInventoryDetails = addCom.getUseInventoryDetails();
                    useInventoryDetails.forEach(i -> {
                        if (StringUtils.isBlank(i.getId())) {
                            i.setId(UUID.randomUUID().toString());
                        }
                        // 设置都为负数
                        if (i.getNum() > 0) {
                            i.setNum(-i.getNum());
                        }
                    });
                    xqInventoryService.updateUseInventoryDetails(newCom.getId(), useInventoryDetails, newCom.getAccessoryId());
                }

                // 退料
                // parse json
                if (StringUtils.isNotBlank(addCom.getBackInventoryDetailsStr())) {
                    JSONArray objects2 = JSON.parseArray(addCom.getBackInventoryDetailsStr());
                    ArrayList<InstOrUpdtAccInventoryDTO> list2 = new ArrayList<>();
                    for (int i = 0; i < objects2.size(); i++) {
                        JSONObject jsonObject = objects2.getJSONObject(i);
                        InstOrUpdtAccInventoryDTO paymentDetail =
                                jsonObject.toJavaObject(InstOrUpdtAccInventoryDTO.class);

                        // todo 设置单价
                        paymentDetail.setUnitPrice(addCom.getUnitPrice());
                        paymentDetail.setCurrency(addCom.getCurrency());

                        list2.add(paymentDetail);
                    }
                    addCom.setBackInventoryDetails(list2);

                    List<InstOrUpdtAccInventoryDTO> backInventoryDetails = addCom.getBackInventoryDetails();
                    backInventoryDetails.forEach(i -> {
                        if (StringUtils.isBlank(i.getId())) {
                            i.setId(UUID.randomUUID().toString());
                        }
                        // 设置都为正数
                        if (i.getNum() < 0) {
                            i.setNum(-i.getNum());
                        }
                    });
                    xqInventoryService.updateBackInventoryDetails(newCom.getId(),
                            backInventoryDetails, newCom.getAccessoryId(), orderNum);
                }
            }

            // 国外财务
        } else if (roleCode == 2) {
            if (CollectionUtil.isNotEmpty(addOrderAcsrDTOS)) {
                for (AddOrderAcsrDTO addOrderAcsrDTO : addOrderAcsrDTOS) {
                    this.lambdaUpdate()
                            .set(XqAccessoriesPurchase::getFinanceAuditDate, addOrderAcsrDTO.getFinanceAuditDate())
                            .set(XqAccessoriesPurchase::getFinanceAuditAmount, addOrderAcsrDTO.getFinanceAuditAmount())
                            .eq(XqAccessoriesPurchase::getId, addOrderAcsrDTO.getId())
                            .update();
                }
            }

        } else {
            return;
        }
    }

    @Override
    public List<XqAccessoriesExportVO> listByExport(String orderNum) {
        return baseMapper.listByExport(orderNum);
    }

}


