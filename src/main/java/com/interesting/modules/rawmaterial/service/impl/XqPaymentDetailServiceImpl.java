package com.interesting.modules.rawmaterial.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.prepayments.entity.XqPrepayments;
import com.interesting.modules.prepayments.service.IXqPrepaymentsService;
import com.interesting.modules.rawmaterial.dto.AddWithholdDTO;
import com.interesting.modules.rawmaterial.dto.InstOrUpdtPaymentDetailDTO;
import com.interesting.modules.rawmaterial.entity.XqPaymentDetail;
import com.interesting.modules.rawmaterial.entity.XqWithholdDetail;
import com.interesting.modules.rawmaterial.service.XqPaymentDetailService;
import com.interesting.modules.rawmaterial.mapper.XqPaymentDetailMapper;
import com.interesting.modules.rawmaterial.service.XqWithholdDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author 26773
* @description 针对表【xq_payment_detail】的数据库操作Service实现
* @createDate 2023-06-29 14:14:22
*/
@Service
public class XqPaymentDetailServiceImpl extends ServiceImpl<XqPaymentDetailMapper, XqPaymentDetail>
    implements XqPaymentDetailService{

    @Autowired
    private IXqPrepaymentsService xqPrepaymentsService;

    /**
     * 差异化处理编辑 原料采购
     *
     * @param sourceId
     * @param instOrUpdtPaymentDetailDTOS
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePaymentDetails(String sourceId, List<InstOrUpdtPaymentDetailDTO> instOrUpdtPaymentDetailDTOS) {

        //判断是否超过预付款金额
        //有预付款的付款明细
        List<InstOrUpdtPaymentDetailDTO> havePrepaymentsList = instOrUpdtPaymentDetailDTOS.stream()
                .filter(instOrUpdtPaymentDetailDTO -> StringUtils.isNotBlank(instOrUpdtPaymentDetailDTO.getPrepaymentsId()) && instOrUpdtPaymentDetailDTO.getType() == 0)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(havePrepaymentsList)) {
            //获取所有预付款的id
            List<String> prepaymentsIdList = havePrepaymentsList.stream()
                    .map(InstOrUpdtPaymentDetailDTO::getPrepaymentsId)
                    .distinct()
                    .collect(Collectors.toList());
            //查询所有预付款的金额
            List<XqPrepayments> xqPrepaymentsList = xqPrepaymentsService.listByIds(prepaymentsIdList);
            Map<String, XqPrepayments> xqPrepaymentsMap = xqPrepaymentsList.stream()
                    .collect(Collectors.toMap(XqPrepayments::getId, xqPrepayments -> xqPrepayments));
            //根据使用的预付款进行分组
            Map<String, List<InstOrUpdtPaymentDetailDTO>> map = havePrepaymentsList.stream()
                    .collect(Collectors.groupingBy(InstOrUpdtPaymentDetailDTO::getPrepaymentsId));
            //其它使用预付款的付款明细
            List<XqPaymentDetail> xqPaymentDetailList = this.list(new LambdaQueryWrapper<XqPaymentDetail>()
                    .ne(XqPaymentDetail::getSourceId, sourceId)
                    .in(XqPaymentDetail::getPrepaymentsId, prepaymentsIdList));
            Map<String, List<XqPaymentDetail>> map1 = xqPaymentDetailList.stream()
                    .collect(Collectors.groupingBy(XqPaymentDetail::getPrepaymentsId));
            for (String prepaymentsId : map.keySet()) {
                //实际的预付款
                XqPrepayments xqPrepayments = xqPrepaymentsMap.get(prepaymentsId);
                if (ObjectUtils.isEmpty(xqPrepayments)) {
                    throw new InterestingBootException("预付款不存在！");
                }
                //需要的预付款
                BigDecimal needAmount = BigDecimal.ZERO;
                List<InstOrUpdtPaymentDetailDTO> instOrUpdtPaymentDetailDTOList = map.get(prepaymentsId);
                if (!CollectionUtils.isEmpty(instOrUpdtPaymentDetailDTOList)) {
                    needAmount = instOrUpdtPaymentDetailDTOList.stream()
                            .map(InstOrUpdtPaymentDetailDTO::getPayMoney)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                }
                //已经使用的预付款
                BigDecimal usedAmount = BigDecimal.ZERO;
                List<XqPaymentDetail> xqPaymentDetails = map1.get(prepaymentsId);
                if (!CollectionUtils.isEmpty(xqPaymentDetails)) {
                    usedAmount = xqPaymentDetails.stream()
                            .map(XqPaymentDetail::getPayMoney)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                }
                if (needAmount.compareTo(xqPrepayments.getPrepaymentAmount().subtract(usedAmount)) > 0) {
                    throw new InterestingBootException("预付款余额不足！");
                }
            }
        }


        List<XqPaymentDetail> originalComs =
                this.list(new LambdaQueryWrapper<XqPaymentDetail>().eq(XqPaymentDetail::getSourceId,
                        sourceId));
        // 插入参数的id分组
        Map<String, InstOrUpdtPaymentDetailDTO> addComsMap = new LinkedHashMap<>();
        for (InstOrUpdtPaymentDetailDTO instOrUpdtPaymentDetailDTO : instOrUpdtPaymentDetailDTOS) {
            addComsMap.put(instOrUpdtPaymentDetailDTO.getId(), instOrUpdtPaymentDetailDTO);
        }

        List<XqPaymentDetail> toUpdateComs = new ArrayList<>();
        List<String> toDeleteIds = new ArrayList<>();
        List<XqPaymentDetail> toAddComs = new ArrayList<>();

        for (XqPaymentDetail originalCom : originalComs) {
            InstOrUpdtPaymentDetailDTO addCom = addComsMap.get(originalCom.getId());
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

        for (InstOrUpdtPaymentDetailDTO addCom : addComsMap.values()) {
            XqPaymentDetail newCom = new XqPaymentDetail();
            BeanUtils.copyProperties(addCom, newCom);
            newCom.setId(null);
            newCom.setSourceId(sourceId);
            toAddComs.add(newCom);
        }

        this.saveBatch(toAddComs);
    }


    @Autowired
    private XqWithholdDetailService xqWithholdDetailService;
    /**
     * 处理预扣款信息
     * @param sourceId
     * @param instOrUpdtPaymentDetailDTOS
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWithholdDetails(String sourceId, List<AddWithholdDTO> instOrUpdtPaymentDetailDTOS) {
        List<XqWithholdDetail> originalComs = xqWithholdDetailService.lambdaQuery().eq(XqWithholdDetail::getSourceId,
                sourceId).list();

        // 插入参数的id分组
        Map<String, AddWithholdDTO> addComsMap = new LinkedHashMap<>();

        for (AddWithholdDTO instOrUpdtPaymentDetailDTO : instOrUpdtPaymentDetailDTOS) {
            addComsMap.put(instOrUpdtPaymentDetailDTO.getId(), instOrUpdtPaymentDetailDTO);
        }

        List<XqWithholdDetail> toUpdateComs = new ArrayList<>();
        List<String> toDeleteIds = new ArrayList<>();
        List<XqWithholdDetail> toAddComs = new ArrayList<>();

        for (XqWithholdDetail originalCom : originalComs) {
            AddWithholdDTO addCom = addComsMap.get(originalCom.getId());
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

        xqWithholdDetailService.updateBatchById(toUpdateComs);
        xqWithholdDetailService.removeByIds(toDeleteIds);

        for (AddWithholdDTO addCom : addComsMap.values()) {
            XqWithholdDetail newCom = new XqWithholdDetail();
            BeanUtils.copyProperties(addCom, newCom);
            newCom.setId(null);
            newCom.setSourceId(sourceId);
            toAddComs.add(newCom);
        }

        xqWithholdDetailService.saveBatch(toAddComs);
    }


}




