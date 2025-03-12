package com.interesting.modules.remittance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.order.dto.sub.*;
import com.interesting.modules.order.entity.XqOrder;
import com.interesting.modules.order.service.IXqOrderService;
import com.interesting.modules.orderconfig.entity.XqOrderConfig;
import com.interesting.modules.orderconfig.service.IXqOrderConfigService;
import com.interesting.modules.orderdetail.entity.XqOrderDetail;
import com.interesting.modules.orderdetail.service.IXqOrderDetailService;
import com.interesting.modules.remittance.entity.XqCreditsInsurance;
import com.interesting.modules.remittance.entity.XqRemittanceExpire;
import com.interesting.modules.remittance.mapper.XqCreditsInsuranceMapper;
import com.interesting.modules.remittance.service.XqCreditsInsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
public class XqCreditsInsuranceServiceImpl extends ServiceImpl<XqCreditsInsuranceMapper, XqCreditsInsurance> implements XqCreditsInsuranceService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCreditsInsurance(String id, List<AddOrderCreditsInsuranceDTO> creditsInsuranceDTOS) {
        creditsInsuranceDTOS.forEach(i -> {
            if (StringUtils.isBlank(i.getId())) {
                i.setId(UUID.randomUUID().toString());
            }
        });

        List<XqCreditsInsurance> originalComs =
                this.list(new LambdaQueryWrapper<XqCreditsInsurance>()
                        .eq(XqCreditsInsurance::getOrderId, id));

        Map<String, AddOrderCreditsInsuranceDTO> addComsMap = new LinkedHashMap<>();
        for (AddOrderCreditsInsuranceDTO creditsInsuranceDTO : creditsInsuranceDTOS) {
            addComsMap.put(creditsInsuranceDTO.getId(), creditsInsuranceDTO);
        }

        List<XqCreditsInsurance> toUpdateComs = new ArrayList<>();
        List<String> toDeleteIds = new ArrayList<>();
        List<XqCreditsInsurance> toAddComs = new ArrayList<>();

        for (XqCreditsInsurance originalCom : originalComs) {
            AddOrderCreditsInsuranceDTO addCom = addComsMap.get(originalCom.getId());
            if (addCom != null) {
                // 更新记录
                BeanUtils.copyProperties(addCom, originalCom);

                toUpdateComs.add(originalCom);
                // 从传入的佣金记录列表中移除已经更新的记录
                addComsMap.remove(originalCom.getId());
            } else {
                // 删除记录
                toDeleteIds.add(originalCom.getId());
            }
        }

        this.updateBatchById(toUpdateComs);
        this.removeByIds(toDeleteIds);

        for (AddOrderCreditsInsuranceDTO addCom : addComsMap.values()) {
            XqCreditsInsurance newCom = new XqCreditsInsurance();
            BeanUtils.copyProperties(addCom, newCom);
            newCom.setId(null);
            newCom.setOrderId(id);
            toAddComs.add(newCom);
        }

        this.saveBatch(toAddComs);

    }

}

