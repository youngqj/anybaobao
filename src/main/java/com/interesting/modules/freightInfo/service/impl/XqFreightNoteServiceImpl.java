package com.interesting.modules.freightInfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.freightInfo.dto.AddFreightNoteDTO;
import com.interesting.modules.freightInfo.entity.XqFreightNote;
import com.interesting.modules.freightInfo.mapper.XqFreightNoteMapper;
import com.interesting.modules.freightInfo.service.XqFreightNoteService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class XqFreightNoteServiceImpl extends ServiceImpl<XqFreightNoteMapper, XqFreightNote> implements XqFreightNoteService {
    @Override
    public void updateOrderFretNote(String orderId, List<AddFreightNoteDTO> freightNoteDTOList) {
        freightNoteDTOList.forEach(i -> {
            if (StringUtils.isBlank(i.getId())) {
                i.setId(UUID.randomUUID().toString());
            }
        });

        List<XqFreightNote> originalComs =
                this.list(new LambdaQueryWrapper<XqFreightNote>()
                        .eq(XqFreightNote::getOrderId, orderId));

        Map<String, AddFreightNoteDTO> addComsMap = new LinkedHashMap<>();
        for (AddFreightNoteDTO dto : freightNoteDTOList) {
            addComsMap.put(dto.getId(), dto);
        }

        List<XqFreightNote> toUpdateComs = new ArrayList<>();
        List<String> toDeleteIds = new ArrayList<>();
        List<XqFreightNote> toAddComs = new ArrayList<>();

        for (XqFreightNote originalCom : originalComs) {
            AddFreightNoteDTO addCom = addComsMap.get(originalCom.getId());
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

        for (AddFreightNoteDTO addCom : addComsMap.values()) {
            XqFreightNote newCom = new XqFreightNote();
            BeanUtils.copyProperties(addCom, newCom);
            newCom.setId(null);
            newCom.setOrderId(orderId);
            toAddComs.add(newCom);
        }
        this.saveBatch(toAddComs);
    }
}
