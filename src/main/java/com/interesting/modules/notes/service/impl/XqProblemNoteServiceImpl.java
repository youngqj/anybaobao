package com.interesting.modules.notes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.constant.CommonConstant;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.notes.dto.InstOrUpdtNotesDTO;
import com.interesting.modules.notes.entity.XqProblemNote;
import com.interesting.modules.notes.mapper.XqProblemNoteMapper;
import com.interesting.modules.notes.service.XqProblemNoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
* @author 26773
* @description 针对表【xq_problem_note(问题说明表)】的数据库操作Service实现
* @createDate 2023-06-28 17:34:35
*/
@Service
public class XqProblemNoteServiceImpl extends ServiceImpl<XqProblemNoteMapper, XqProblemNote>
    implements XqProblemNoteService{

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateNotes(String orderId, String moduleType, List<InstOrUpdtNotesDTO> notesDTOS) {

        notesDTOS.forEach(i -> {
            if (StringUtils.isBlank(i.getId())) {
                i.setId(UUID.randomUUID().toString());
            }
        });


        List<XqProblemNote> originalComs = this.list(new LambdaQueryWrapper<XqProblemNote>()
                        .eq(XqProblemNote::getOrderId, orderId)
                        .eq(XqProblemNote::getModuleType, moduleType));

        Map<String, InstOrUpdtNotesDTO> addComsMap = new LinkedHashMap<>();
        for (InstOrUpdtNotesDTO addOrderRawDTO : notesDTOS) {
            addComsMap.put(addOrderRawDTO.getId(), addOrderRawDTO);
        }

        List<XqProblemNote> toUpdateComs = new ArrayList<>();
        List<String> toDeleteIds = new ArrayList<>();
        List<XqProblemNote> toAddComs = new ArrayList<>();

        for (XqProblemNote originalCom : originalComs) {
            InstOrUpdtNotesDTO addCom = addComsMap.get(originalCom.getId());
            if (addCom != null) {
                // 更新记录
                BeanUtils.copyProperties(addCom, originalCom);
                toUpdateComs.add(originalCom);
                addComsMap.remove(originalCom.getId());
            } else {
                // 删除记录
                toDeleteIds.add(originalCom.getId());
            }
        }

        this.updateBatchById(toUpdateComs);
        this.removeByIds(toDeleteIds);

        for (InstOrUpdtNotesDTO addCom : addComsMap.values()) {
            XqProblemNote newCom = new XqProblemNote();
            BeanUtils.copyProperties(addCom, newCom);
            newCom.setModuleType(moduleType);
            newCom.setId(null);
            newCom.setOrderId(orderId);
            toAddComs.add(newCom);
        }

        this.saveBatch(toAddComs);
    }
}




