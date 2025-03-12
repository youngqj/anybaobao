package com.interesting.modules.overseas.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.overseas.dto.EditEnterOrderStatusDTO;
import com.interesting.modules.overseas.dto.InstOverSeasTransferDTO;
import com.interesting.modules.overseas.dto.QueryPageOverSeasInventoryTransferDTO;
import com.interesting.modules.overseas.entity.XqOverseasTransfer;
import com.interesting.modules.overseas.vo.OverSeasInventoryTransferDetailVO;
import com.interesting.modules.overseas.vo.OverSeasInventoryTransferPageVO;

public interface XqOverseasTransferService extends IService<XqOverseasTransfer> {
    boolean addInventoryTransfer(InstOverSeasTransferDTO dto);

    OverSeasInventoryTransferDetailVO getInventoryTransferDetById(String id);

    IPage<OverSeasInventoryTransferPageVO> pageInventoryTransfer(QueryPageOverSeasInventoryTransferDTO dto);

    boolean deleteBatchInventoryTransfer(String ids);

    boolean editCheckStatus(EditEnterOrderStatusDTO dto);


}
