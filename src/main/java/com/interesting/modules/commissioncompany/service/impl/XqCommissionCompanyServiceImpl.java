package com.interesting.modules.commissioncompany.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.BeanUtils;
import com.interesting.modules.commission.service.XqCommissionHistoryService;
import com.interesting.modules.commission.vo.CommissionHistoryTmpDetailsVO;
import com.interesting.modules.commissioncompany.dto.AddCommissionCompanyDTO;
import com.interesting.modules.commissioncompany.dto.AddCommissionHistoryTmpDTO;
import com.interesting.modules.commissioncompany.dto.EditCommissionHistoryTmpDTO;
import com.interesting.modules.commissioncompany.entity.XqCommissionCompany;
import com.interesting.modules.commissioncompany.service.XqCommissionCompanyService;
import com.interesting.modules.commissioncompany.mapper.XqCommissionCompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
* @author 26773
* @description 针对表【xq_commission_company(佣金公司表)】的数据库操作Service实现
* @createDate 2023-06-08 14:23:37
*/
@Service
public class XqCommissionCompanyServiceImpl extends ServiceImpl<XqCommissionCompanyMapper, XqCommissionCompany>
    implements XqCommissionCompanyService{

    @Autowired
    private XqCommissionHistoryService xqCommissionHistoryService;

    @Override
    public boolean addCommissionCompany(AddCommissionCompanyDTO dto) {
        XqCommissionCompany xqCommissionCompany = new XqCommissionCompany();
        BeanUtils.copyProperties(dto, xqCommissionCompany);
        return this.save(xqCommissionCompany);
    }

    @Override
    public boolean editCommissionCompany(AddCommissionCompanyDTO dto) {
        String id = dto.getId();
        if (id == null) {
            throw new InterestingBootException("id不能为空");
        }
        XqCommissionCompany byId = this.getById(id);
        if (byId == null) {
            throw new InterestingBootException("该记录不存在");
        }

        XqCommissionCompany xqCommissionCompany = new XqCommissionCompany();
        BeanUtils.copyProperties(dto, xqCommissionCompany);
        return this.updateById(xqCommissionCompany);
    }

    @Override
    public boolean editCommissionTemp(EditCommissionHistoryTmpDTO dto) {
        XqCommissionCompany byId = this.getById(dto.getId());
        byId.setName(dto.getCompanyName());
        byId.setPercentage(dto.getPercentage());
        byId.setRequirements(dto.getRequirements());
        return this.updateById(byId);
    }

    @Override
    public boolean addCommissionTemp(AddCommissionHistoryTmpDTO dto) {
        XqCommissionCompany xqCommissionCompany = new XqCommissionCompany();
        xqCommissionCompany.setName(dto.getCompanyName());
        xqCommissionCompany.setPercentage(dto.getPercentage());
        xqCommissionCompany.setRequirements(dto.getRequirements());
        xqCommissionCompany.setTemplateId(dto.getTemplateId());
        return this.save(xqCommissionCompany);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatchCommissionTempOuter(String ids) {
        List<String> strings = Arrays.asList(ids.split(","));
        xqCommissionHistoryService.removeByIds(strings);

        for (String string : strings) {
            this.lambdaUpdate().eq(XqCommissionCompany::getTemplateId, string).remove();
        }

        return true;
    }
}




