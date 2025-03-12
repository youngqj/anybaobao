package com.interesting.modules.overseas.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.overseas.dto.EditEnterOrderStatusDTO;
import com.interesting.modules.overseas.dto.InstOverSeasInventoryCheckDTO;
import com.interesting.modules.overseas.dto.QueryPageOverSeasInventoryCheckDTO;
import com.interesting.modules.overseas.entity.XqOverseasInventoryCheck;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.overseas.vo.OverSeasInventoryCheckDetailVO;
import com.interesting.modules.overseas.vo.OverSeasInventoryCheckPageVO;

/**
* @author 26773
* @description 针对表【xq_overseas_inventory_check(盘点录入)】的数据库操作Service
* @createDate 2023-07-28 09:17:25
*/
public interface XqOverseasInventoryCheckService extends IService<XqOverseasInventoryCheck> {

    IPage<OverSeasInventoryCheckPageVO> pageInventoryCheck(QueryPageOverSeasInventoryCheckDTO dto);

    OverSeasInventoryCheckDetailVO getInventoryCheckDetById(String id);

    boolean deleteBatchInventoryCheck(String ids);

    boolean addInventoryCheck(InstOverSeasInventoryCheckDTO dto);

    boolean editCheckStatus(EditEnterOrderStatusDTO dto);
}
