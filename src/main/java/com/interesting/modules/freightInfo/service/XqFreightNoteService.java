package com.interesting.modules.freightInfo.service;

import com.interesting.modules.freightInfo.dto.AddFreightNoteDTO;

import java.util.List;

public interface XqFreightNoteService {

    void updateOrderFretNote(String orderId, List<AddFreightNoteDTO> freightNoteDTOList);
}
