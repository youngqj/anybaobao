package com.interesting.modules.commission.service;

import com.interesting.modules.commission.entity.XqCommissionHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.commissioncompany.dto.AddCommissionHistoryTmpDTO2;
import com.interesting.modules.commissioncompany.dto.EditCommissionHistoryTmpDTO2;

/**
* @author 26773
* @description 针对表【xq_commission_history(佣金历史记录模板)】的数据库操作Service
* @createDate 2023-07-10 15:32:35
*/
public interface XqCommissionHistoryService extends IService<XqCommissionHistory> {

    boolean editCommissionTemp(EditCommissionHistoryTmpDTO2 dto);

    boolean addCommissionTemp(AddCommissionHistoryTmpDTO2 dto);
}
