package com.interesting.modules.commissioncompany.service;

import com.interesting.modules.commission.vo.CommissionHistoryTmpDetailsVO;
import com.interesting.modules.commissioncompany.dto.AddCommissionCompanyDTO;
import com.interesting.modules.commissioncompany.dto.AddCommissionHistoryTmpDTO;
import com.interesting.modules.commissioncompany.dto.EditCommissionHistoryTmpDTO;
import com.interesting.modules.commissioncompany.entity.XqCommissionCompany;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 26773
* @description 针对表【xq_commission_company(佣金公司表)】的数据库操作Service
* @createDate 2023-06-08 14:23:37
*/
public interface XqCommissionCompanyService extends IService<XqCommissionCompany> {

    boolean addCommissionCompany(AddCommissionCompanyDTO dto);

    boolean editCommissionCompany(AddCommissionCompanyDTO dto);

    boolean editCommissionTemp(EditCommissionHistoryTmpDTO dto);

    boolean addCommissionTemp(AddCommissionHistoryTmpDTO dto);

    boolean deleteBatchCommissionTempOuter(String ids);
}
