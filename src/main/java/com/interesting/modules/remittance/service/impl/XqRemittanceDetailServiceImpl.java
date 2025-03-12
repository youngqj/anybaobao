package com.interesting.modules.remittance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.order.dto.sub.AddOrderComsDTO;
import com.interesting.modules.order.dto.sub.AddOrderProdDTO;
import com.interesting.modules.order.dto.sub.AddOrderRemiDTO;
import com.interesting.modules.order.dto.sub.XqOrderExtraCostDTO;
import com.interesting.modules.order.entity.XqOrder;
import com.interesting.modules.order.service.IXqOrderService;
import com.interesting.modules.orderconfig.entity.XqOrderConfig;
import com.interesting.modules.orderconfig.service.IXqOrderConfigService;
import com.interesting.modules.orderdetail.entity.XqOrderDetail;
import com.interesting.modules.orderdetail.service.IXqOrderDetailService;
import com.interesting.modules.remittance.entity.XqRemittanceDetail;
import com.interesting.modules.remittance.mapper.XqRemittanceDetailMapper;
import com.interesting.modules.remittance.service.IXqRemittanceDetailService;
import com.interesting.modules.remittance.vo.XqRemittanceDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.remittance.dto.QueryXqRemittanceDetailDTO;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Description: 收汇情况表
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Service
public class XqRemittanceDetailServiceImpl extends ServiceImpl<XqRemittanceDetailMapper, XqRemittanceDetail> implements IXqRemittanceDetailService {
    @Override
    public IPage<XqRemittanceDetailVO> pageList(Page<XqRemittanceDetailVO> page, QueryXqRemittanceDetailDTO dto) {
        return this.baseMapper.pageList(page, dto);
    }


    /**
     * 差异化处理编辑 收汇情况
     *
     * @param orderId
     * @param addOrderRemiDTOS
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRemittance(String orderId, List<AddOrderRemiDTO> addOrderRemiDTOS) {

        addOrderRemiDTOS.forEach(i -> {
            if (StringUtils.isBlank(i.getId())) {
                i.setId(UUID.randomUUID().toString());
            }
        });


        List<XqRemittanceDetail> originalComs = this.list(new LambdaQueryWrapper<XqRemittanceDetail>().eq(XqRemittanceDetail::getOrderId, orderId));

        Map<String, AddOrderRemiDTO> addComsMap = new LinkedHashMap<>();
        for (AddOrderRemiDTO addOrderRawDTO : addOrderRemiDTOS) {
            addComsMap.put(addOrderRawDTO.getId(), addOrderRawDTO);
        }

        List<XqRemittanceDetail> toUpdateComs = new ArrayList<>();
        List<String> toDeleteIds = new ArrayList<>();
        List<XqRemittanceDetail> toAddComs = new ArrayList<>();

        for (XqRemittanceDetail originalCom : originalComs) {
            AddOrderRemiDTO addCom = addComsMap.get(originalCom.getId());
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

        for (AddOrderRemiDTO addCom : addComsMap.values()) {
            XqRemittanceDetail newCom = new XqRemittanceDetail();
            BeanUtils.copyProperties(addCom, newCom);
            newCom.setId(null);
            newCom.setOrderId(orderId);
            toAddComs.add(newCom);
        }

        this.saveBatch(toAddComs);

    }


}
