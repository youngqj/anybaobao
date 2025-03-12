package com.interesting.modules.notes.service;

import com.interesting.modules.notes.dto.InstOrUpdtNotesDTO;
import com.interesting.modules.notes.entity.XqProblemNote;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_problem_note(问题说明表)】的数据库操作Service
* @createDate 2023-06-28 17:34:35
*/
public interface XqProblemNoteService extends IService<XqProblemNote> {

    void updateNotes(String orderId, String moduleType, List<InstOrUpdtNotesDTO> notesDTOS);



}
