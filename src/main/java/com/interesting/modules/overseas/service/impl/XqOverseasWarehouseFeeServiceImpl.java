package com.interesting.modules.overseas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.order.dto.sub.XqOrderExtraCostDTO;
import com.interesting.modules.order.entity.XqOrderExtraCost;
import com.interesting.modules.overseas.dto.AddOverseasWarehouseFeeDTO;
import com.interesting.modules.overseas.dto.AddSplitFeeDTO;
import com.interesting.modules.overseas.dto.EditOverseasWarehouseFeeDTO;
import com.interesting.modules.overseas.dto.QueryWarehouseFeeDTO;
import com.interesting.modules.overseas.entity.XqOverseasWarehouseFee;
import com.interesting.modules.overseas.entity.XqSplitFee;
import com.interesting.modules.overseas.mapper.XqOverseasWarehouseFeeMapper;
import com.interesting.modules.overseas.service.XqOverseasWarehouseFeeService;
import com.interesting.modules.overseas.service.XqSplitFeeService;
import com.interesting.modules.overseas.vo.ListWarehouseFeeVO;
import com.interesting.modules.overseas.vo.OverseasWarehouseFeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class XqOverseasWarehouseFeeServiceImpl extends ServiceImpl<XqOverseasWarehouseFeeMapper,XqOverseasWarehouseFee> implements XqOverseasWarehouseFeeService {

    @Autowired
    private XqSplitFeeService xqSplitFeeService;
    @Override
    public boolean addOverseasWarehouseFee(AddOverseasWarehouseFeeDTO dto) {
        XqOverseasWarehouseFee fee=new XqOverseasWarehouseFee();
        BeanUtils.copyProperties(dto,fee);
        this.save(fee);
        if (dto.getSplitFeeDTOList().size() > 0) {
            xqSplitFeeService.saveBatch(dto.getSplitFeeDTOList().stream().map(xqSplitFeeDTO -> {
                XqSplitFee xqSplitFee = new XqSplitFee();
                com.interesting.common.util.BeanUtils.copyProperties(xqSplitFeeDTO, xqSplitFee);
                xqSplitFee.setId(null);
                xqSplitFee.setWarehouseFeeId(fee.getId());
                return xqSplitFee;
            }).collect(Collectors.toList()));

        }
        return true;
    }

    @Override
    public boolean editOverseasWarehouseFee(EditOverseasWarehouseFeeDTO dto) {
        XqOverseasWarehouseFee fee = baseMapper.selectById(dto.getId());
        if (fee==null){
            throw new InterestingBootException("未找到记录");

        }
        BeanUtils.copyProperties(dto,fee);
        if (dto.getSplitFeeDTOList() != null && dto.getSplitFeeDTOList().size() > 0) {
            if (CollectionUtils.isEmpty(dto.getSplitFeeDTOList())) {
                xqSplitFeeService.remove(new LambdaQueryWrapper<XqSplitFee>().eq(XqSplitFee::getWarehouseFeeId, dto.getId()));
            }

            // 原有的列表
            List<XqSplitFee> xqSplitFees = xqSplitFeeService.list(new LambdaQueryWrapper<XqSplitFee>().eq(XqSplitFee::getWarehouseFeeId, dto.getId()));

            if (CollectionUtils.isEmpty(xqSplitFees)) {
                // 若原有的列表为空，则全部新增
                xqSplitFeeService.saveBatch(dto.getSplitFeeDTOList().stream().map(xqSplitFeeDTO -> {
                    XqSplitFee xqSplitFee = new XqSplitFee();
                    BeanUtils.copyProperties(xqSplitFeeDTO, xqSplitFee);
                    xqSplitFee.setId(null);
                    xqSplitFee.setWarehouseFeeId(dto.getId());
                    return xqSplitFee;
                }).collect(Collectors.toList()));

            } else {

                Map<String, AddSplitFeeDTO> addComsMap = new LinkedHashMap<>();
                for (AddSplitFeeDTO dto1 : dto.getSplitFeeDTOList()) {
                    addComsMap.put(dto1.getId(), dto1);
                }

                List<XqSplitFee> toUpdateComs = new ArrayList<>();
                List<String> toDeleteIds = new ArrayList<>();

                for (XqSplitFee originalCom : xqSplitFees) {
                    AddSplitFeeDTO addCom = addComsMap.get(originalCom.getId());
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

                xqSplitFeeService.updateBatchById(toUpdateComs);
                xqSplitFeeService.removeByIds(toDeleteIds);
            }

        }

        return this.updateById(fee);
    }

    @Override
    public IPage<ListWarehouseFeeVO> queryByPage(QueryWarehouseFeeDTO dto,Page<ListWarehouseFeeVO> page) {
        return baseMapper.queryByPage(dto,page);
    }

    @Override
    public OverseasWarehouseFeeVO queryById(String id) {
        return baseMapper.queryById(id);
    }
}
