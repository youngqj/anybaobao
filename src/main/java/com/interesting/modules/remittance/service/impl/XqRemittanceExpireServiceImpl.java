package com.interesting.modules.remittance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.order.dto.sub.AddOrderRemiExpDateDTO;
import com.interesting.modules.remittance.entity.XqRemittanceExpire;
import com.interesting.modules.remittance.service.XqRemittanceExpireService;
import com.interesting.modules.remittance.mapper.XqRemittanceExpireMapper;
import com.interesting.modules.remittance.vo.QueryDataNotInDetailVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
* @author 26773
* @description 针对表【xq_remittance_expire】的数据库操作Service实现
* @createDate 2023-08-15 15:37:10
*/
@Service
public class XqRemittanceExpireServiceImpl extends ServiceImpl<XqRemittanceExpireMapper, XqRemittanceExpire>
    implements XqRemittanceExpireService{



    @Override
    @Transactional(rollbackFor = Exception.class)
    public  void updateRemiExpDate(String id, List<AddOrderRemiExpDateDTO> remiExpDateVOS) {

        remiExpDateVOS.forEach(i -> {
            if (StringUtils.isBlank(i.getId())) {
                i.setId(UUID.randomUUID().toString());
            }
        });

        List<XqRemittanceExpire> originalComs =
                this.list(new LambdaQueryWrapper<XqRemittanceExpire>()
                        .eq(XqRemittanceExpire::getOrderId, id));

        Map<String, AddOrderRemiExpDateDTO> addComsMap = new LinkedHashMap<>();
        for (AddOrderRemiExpDateDTO truckInfo : remiExpDateVOS) {
            addComsMap.put(truckInfo.getId(), truckInfo);
        }

        List<XqRemittanceExpire> toUpdateComs = new ArrayList<>();
        List<String> toDeleteIds = new ArrayList<>();
        List<XqRemittanceExpire> toAddComs = new ArrayList<>();

        for (XqRemittanceExpire originalCom : originalComs) {
            AddOrderRemiExpDateDTO addCom = addComsMap.get(originalCom.getId());
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

        for (AddOrderRemiExpDateDTO addCom : addComsMap.values()) {
            XqRemittanceExpire newCom = new XqRemittanceExpire();
            BeanUtils.copyProperties(addCom, newCom);
            newCom.setId(null);
            newCom.setOrderId(id);
            toAddComs.add(newCom);
        }

        this.saveBatch(toAddComs);

    }

    @Override
    public List<QueryDataNotInDetailVO>  queryDataNotInDetail() {
        return this.baseMapper.queryDataNotInDetail();
    }


}




