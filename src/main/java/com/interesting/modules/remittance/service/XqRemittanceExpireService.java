package com.interesting.modules.remittance.service;

import com.interesting.modules.order.dto.sub.AddOrderRemiExpDateDTO;
import com.interesting.modules.remittance.entity.XqRemittanceExpire;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.remittance.vo.QueryDataNotInDetailVO;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_remittance_expire】的数据库操作Service
* @createDate 2023-08-15 15:37:10
*/
public interface XqRemittanceExpireService extends IService<XqRemittanceExpire> {

    void updateRemiExpDate(String id, List<AddOrderRemiExpDateDTO> remiExpDateVOS);

    List<QueryDataNotInDetailVO>  queryDataNotInDetail();

}
