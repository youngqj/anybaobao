package com.interesting.modules.truck.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.truck.dto.InstUptTruckInfoDTO;
import com.interesting.modules.truck.entity.XqTruckInfo;
import com.interesting.modules.truck.service.XqTruckInfoService;
import com.interesting.modules.truck.mapper.XqTruckInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 26773
 * @description 针对表【xq_truck_info(卡车信息表)】的数据库操作Service实现
 * @createDate 2023-08-01 17:00:10
 */
@Service
public class XqTruckInfoServiceImpl extends ServiceImpl<XqTruckInfoMapper, XqTruckInfo>
        implements XqTruckInfoService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTruckInfo(String id, List<InstUptTruckInfoDTO> truckInfos, Integer roleCode) {

        if (roleCode == 7) {
            truckInfos.forEach(i -> {
                if (StringUtils.isBlank(i.getId())) {
                    i.setId(UUID.randomUUID().toString());
                }
            });

            List<XqTruckInfo> list = this.lambdaQuery().eq(XqTruckInfo::getOrderId, id)
                    .list();

            Map<String, InstUptTruckInfoDTO> addComsMap = new LinkedHashMap<>();
            for (InstUptTruckInfoDTO truckInfo : truckInfos) {
                addComsMap.put(truckInfo.getId(), truckInfo);
            }

            List<String> toDeleteIds = new ArrayList<>();
            List<XqTruckInfo> toAddComs = new ArrayList<>();

            for (XqTruckInfo xqTruckInfo : list) {
                InstUptTruckInfoDTO instUptTruckInfoDTO = addComsMap.get(xqTruckInfo.getId());
                if (instUptTruckInfoDTO == null) {
                    toDeleteIds.add(xqTruckInfo.getId());
                } else {
                    this.lambdaUpdate()
                            .set(XqTruckInfo::getCustomerOrderNum,instUptTruckInfoDTO.getCustomerOrderNum())
                            .set(XqTruckInfo::getTruckCompany,instUptTruckInfoDTO.getTruckCompany())
                            .set(XqTruckInfo::getTruckFee,instUptTruckInfoDTO.getTruckFee())
                            .set(XqTruckInfo::getRemark,instUptTruckInfoDTO.getRemark())
                            .set(XqTruckInfo::getDeliveryWarehouse, instUptTruckInfoDTO.getDeliveryWarehouse())
                            .set(XqTruckInfo::getDeliveryTime, instUptTruckInfoDTO.getDeliveryTime())
                            .set(XqTruckInfo::getApplyDate,instUptTruckInfoDTO.getApplyDate())
                            .set(XqTruckInfo::getPayDate,instUptTruckInfoDTO.getPayDate())
                            .set(XqTruckInfo::getArrivalTime, instUptTruckInfoDTO.getArrivalTime())
                            .set(XqTruckInfo::getArrivalWarehouse, instUptTruckInfoDTO.getArrivalWarehouse())
                            .set(XqTruckInfo::getCustomerName, instUptTruckInfoDTO.getCustomerName())
                            .eq(XqTruckInfo::getId, instUptTruckInfoDTO.getId())
                            .update();

                    addComsMap.remove(xqTruckInfo.getId());
                }
            }

            addComsMap.forEach((k, v) -> {
                XqTruckInfo xqTruckInfo = new XqTruckInfo();
                BeanUtils.copyProperties(v, xqTruckInfo);
                xqTruckInfo.setOrderId(id);
                xqTruckInfo.setId(null);
                toAddComs.add(xqTruckInfo);
            });

            if (toDeleteIds.size() > 0) {
                this.removeByIds(toDeleteIds);
            }

            this.saveBatch(toAddComs);
        } else if (roleCode == 2) {
            for (InstUptTruckInfoDTO truckInfo : truckInfos) {
                this.lambdaUpdate()
                        .set(XqTruckInfo::getFinanceAmount,truckInfo.getFinanceAmount())
                        .set(XqTruckInfo::getFinanceAuditTime,truckInfo.getFinanceAuditTime())
                        .eq(XqTruckInfo::getId, truckInfo.getId())
                        .update();
            }

        } else {
            return;
        }
    }

}





