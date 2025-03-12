package com.interesting.modules.commission.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.modules.commission.entity.XqCommissionHistory;
import com.interesting.modules.commission.service.XqCommissionHistoryService;
import com.interesting.modules.commission.mapper.XqCommissionHistoryMapper;
import com.interesting.modules.commissioncompany.dto.AddCommissionHistoryTmpDTO2;
import com.interesting.modules.commissioncompany.dto.EditCommissionHistoryTmpDTO2;
import org.springframework.stereotype.Service;

/**
* @author 26773
* @description 针对表【xq_commission_history(佣金历史记录模板)】的数据库操作Service实现
* @createDate 2023-07-10 15:32:35
*/
@Service
public class XqCommissionHistoryServiceImpl extends ServiceImpl<XqCommissionHistoryMapper, XqCommissionHistory>
    implements XqCommissionHistoryService{

    @Override
    public boolean editCommissionTemp(EditCommissionHistoryTmpDTO2 dto) {
        XqCommissionHistory byId = this.getById(dto.getId());
        String name = dto.getName();

        if (this.lambdaQuery().eq(XqCommissionHistory::getName, name)
                .ne(XqCommissionHistory::getId, dto.getId()).count() > 0) {
            throw new InterestingBootException("该模板名称已存在");
        }


        byId.setName(dto.getName());
        return this.updateById(byId);
    }

    @Override
    public boolean addCommissionTemp(AddCommissionHistoryTmpDTO2 dto) {
        XqCommissionHistory xqCommissionHistory = new XqCommissionHistory();
        String name = dto.getName();
        if (this.lambdaQuery().eq(XqCommissionHistory::getName, name).count() > 0) {
            throw new InterestingBootException("该模板名称已存在");
        }

        xqCommissionHistory.setCustomerId(dto.getCustomerId());
        xqCommissionHistory.setName(dto.getName());
        return this.save(xqCommissionHistory);
    }
}




