package com.interesting.modules.commission.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.api.vo.Result;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.commission.dto.AddCommissionDistributionDTO;
import com.interesting.modules.commission.dto.QueryCommissionTempDTO;
import com.interesting.modules.commission.dto.QueryCommissionTempDetailDTO;
import com.interesting.modules.commission.entity.XqCommissionDistribution;
import com.interesting.modules.commission.entity.XqOrderCommission;
import com.interesting.modules.commission.mapper.XqOrderCommissionMapper;
import com.interesting.modules.commission.service.IXqOrderCommissionService;
import com.interesting.modules.commission.service.XqCommissionDistributionService;
import com.interesting.modules.commission.vo.CommissionHistoryTmpDetailsVO;
import com.interesting.modules.commission.vo.CommissionHistoryTmpVO;
import com.interesting.modules.order.dto.sub.AddOrderComsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 佣金表
 * @Author: interesting-boot
 * @Date: 2023-06-02
 * @Version: V1.0
 */
@Service
public class XqOrderCommissionServiceImpl extends ServiceImpl<XqOrderCommissionMapper, XqOrderCommission> implements IXqOrderCommissionService {
    @Autowired
    private XqCommissionDistributionService xqCommissionDistributionService;
//    @Override
//    public IPage<XqOrderCommissionVO> pageList(Page<XqOrderCommissionVO> page, QueryXqOrderCommissionDTO dto) {
//        return this.baseMapper.pageList(page, dto);
//    }

    @Override
    public IPage<CommissionHistoryTmpVO> pageCommissionTemp(String customerId, QueryCommissionTempDTO dto) {
        Page<CommissionHistoryTmpVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());

        return this.baseMapper.pageCommissionTemp(page, customerId, dto);
    }

    @Override
    public IPage<CommissionHistoryTmpDetailsVO> pageCommissionTempDetails(String templateId, QueryCommissionTempDetailDTO dto) {
        Page<CommissionHistoryTmpDetailsVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        return this.baseMapper.pageCommissionTempDetails(page, templateId, dto);
    }


    /**
     * 差异化处理编辑 佣金表
     *
     * @param orderId
     * @param addOrderComsDTOS
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderCommissions(String orderId, List<AddOrderComsDTO> addOrderComsDTOS, String customerId) {
        // 查询原有的佣金记录
        List<XqOrderCommission> originalComs = this.list(new LambdaQueryWrapper<XqOrderCommission>().eq(XqOrderCommission::getOrderId, orderId));

        Map<String, AddOrderComsDTO> addComsMap = new LinkedHashMap<>();
        for (AddOrderComsDTO addOrderRawDTO : addOrderComsDTOS) {
            addComsMap.put(addOrderRawDTO.getId(), addOrderRawDTO);
        }

        List<String> toDeleteIds = new ArrayList<>();
        List<XqOrderCommission> toAddComs = new ArrayList<>();

        // 查询默认佣金公司，有记录则不插入，无则新增
//        Integer count = xqCommissionCompanyService.lambdaQuery()
//                .eq(XqCommissionCompany::getCustomerId, customerId)
//                .count();

        for (XqOrderCommission originalCom : originalComs) {
            AddOrderComsDTO addCom = addComsMap.get(originalCom.getId());
            if (addCom != null) {
                // 更新记录
                if (addCom.getFinanceConfirmedAmount() != null && addCom.getFinanceConfirmedAmount().compareTo(BigDecimal.ZERO) > 0) {
                    addCom.setAmount(addCom.getFinanceConfirmedAmount());
                }

//                // 根据名称查询是否存在，不存在则插入
//                XqCommissionCompany one = xqCommissionCompanyService.lambdaQuery()
//                        .eq(XqCommissionCompany::getName, addCom.getCompanyName())
//                        .eq(XqCommissionCompany::getCustomerId, customerId)
//                        .last("LIMIT 1").one();
//                if (one == null) {
//                    XqCommissionCompany xqCommissionCompany = new XqCommissionCompany();
//                    BeanUtils.copyProperties(addCom, xqCommissionCompany);
//                    xqCommissionCompany.setName(addCom.getCompanyName());
//                    xqCommissionCompany.setId(null);
//                    xqCommissionCompany.setCustomerId(customerId);
//                    xqCommissionCompanyService.save(xqCommissionCompany);
//                }

                this.lambdaUpdate()
                        .set(XqOrderCommission::getCompanyName, addCom.getCompanyName())
                        .set(XqOrderCommission::getRequirements, addCom.getRequirements())
                        .set(XqOrderCommission::getParam, addCom.getParam())
                        .set(XqOrderCommission::getPercentage, addCom.getPercentage())
                        .set(XqOrderCommission::getAmount, addCom.getAmount())
                        .set(XqOrderCommission::getCommissionType, addCom.getCommissionType())
                        .set(XqOrderCommission::getApplicationDate, addCom.getApplicationDate())
                        .set(XqOrderCommission::getNotes, addCom.getNotes())
                        .set(XqOrderCommission::getCustomerOrderNum,addCom.getCustomerOrderNum())
                        .eq(XqOrderCommission::getId, addCom.getId())
                        .update();


                addComsMap.remove(originalCom.getId());
                // 从传入的佣金记录列表中移除已经更新的记录
                if (StringUtils.isBlank(addCom.getCommissionDetailsStr())) {
                    xqCommissionDistributionService.remove(new LambdaQueryWrapper<XqCommissionDistribution>()
                            .eq(XqCommissionDistribution::getCommissionId, addCom.getId()));
                    continue;
                }
                JSONArray objects2 = JSON.parseArray(addCom.getCommissionDetailsStr());
                ArrayList<XqCommissionDistribution> list1 = new ArrayList<>();
                ArrayList<XqCommissionDistribution> list2 = new ArrayList<>();
                for (int i = 0; i < objects2.size(); i++) {
                    JSONObject jsonObject = objects2.getJSONObject(i);
                    AddCommissionDistributionDTO addCommissionDistributionDTO =
                            jsonObject.toJavaObject(AddCommissionDistributionDTO.class);
                    XqCommissionDistribution xqCommissionDistribution = xqCommissionDistributionService.getOne(new LambdaQueryWrapper<XqCommissionDistribution>()
                            .eq(XqCommissionDistribution::getId, addCommissionDistributionDTO.getId()));
                    if (xqCommissionDistribution == null) {
                        xqCommissionDistribution = new XqCommissionDistribution();
                        org.springframework.beans.BeanUtils.copyProperties(addCommissionDistributionDTO, xqCommissionDistribution);
                        xqCommissionDistribution.setCommissionId(addCom.getId());
                        list1.add(xqCommissionDistribution);
                    } else {
                        org.springframework.beans.BeanUtils.copyProperties(addCommissionDistributionDTO, xqCommissionDistribution);
                        xqCommissionDistribution.setCommissionId(addCom.getId());
                        list2.add(xqCommissionDistribution);
                    }

                }
                xqCommissionDistributionService.saveBatch(list1);
                xqCommissionDistributionService.updateBatchById(list2);

            } else {
                // 删除记录
                toDeleteIds.add(originalCom.getId());
            }
        }

        this.removeByIds(toDeleteIds);

        for (AddOrderComsDTO addCom : addComsMap.values()) {
            XqOrderCommission newCom = new XqOrderCommission();
            BeanUtils.copyProperties(addCom, newCom);

//            // 根据名称查询是否存在，不存在则插入
//            XqCommissionCompany one = xqCommissionCompanyService.lambdaQuery()
//                    .eq(XqCommissionCompany::getName, addCom.getCompanyName())
//                    .eq(XqCommissionCompany::getCustomerId, customerId)
//                    .last("LIMIT 1").one();
//            if (one == null) {
//                XqCommissionCompany xqCommissionCompany = new XqCommissionCompany();
//                BeanUtils.copyProperties(addCom, xqCommissionCompany);
//                xqCommissionCompany.setName(addCom.getCompanyName());
//                xqCommissionCompany.setId(null);
//                xqCommissionCompany.setCustomerId(customerId);
//                xqCommissionCompanyService.save(xqCommissionCompany);
//            }

            newCom.setId(null);
            newCom.setOrderId(orderId);
            this.save(newCom);

            if (StringUtils.isBlank(addCom.getCommissionDetailsStr())) {
                xqCommissionDistributionService.remove(new LambdaQueryWrapper<XqCommissionDistribution>()
                        .eq(XqCommissionDistribution::getCommissionId, addCom.getId()));
                continue;
            }
            JSONArray objects2 = JSON.parseArray(addCom.getCommissionDetailsStr());
            ArrayList<XqCommissionDistribution> list1 = new ArrayList<>();
            ArrayList<XqCommissionDistribution> list2 = new ArrayList<>();
            for (int i = 0; i < objects2.size(); i++) {
                JSONObject jsonObject = objects2.getJSONObject(i);
                AddCommissionDistributionDTO addCommissionDistributionDTO =
                        jsonObject.toJavaObject(AddCommissionDistributionDTO.class);
                XqCommissionDistribution xqCommissionDistribution = xqCommissionDistributionService.getOne(new LambdaQueryWrapper<XqCommissionDistribution>()
                        .eq(XqCommissionDistribution::getId, addCommissionDistributionDTO.getId()));
                if (xqCommissionDistribution == null) {
                    xqCommissionDistribution = new XqCommissionDistribution();
                    org.springframework.beans.BeanUtils.copyProperties(addCommissionDistributionDTO, xqCommissionDistribution);
                    xqCommissionDistribution.setCommissionId(newCom.getId());
                    list1.add(xqCommissionDistribution);
                } else {
                    org.springframework.beans.BeanUtils.copyProperties(addCommissionDistributionDTO, xqCommissionDistribution);
                    xqCommissionDistribution.setCommissionId(newCom.getId());
                    list2.add(xqCommissionDistribution);
                }

            }
            xqCommissionDistributionService.saveBatch(list1);
            xqCommissionDistributionService.updateBatchById(list2);
        }



    }


}
