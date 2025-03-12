package com.interesting.modules.accmaterial.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.common.api.vo.Result;
import com.interesting.modules.accmaterial.dto.AddInventoryCheckDTO;
import com.interesting.modules.accmaterial.dto.EditStatusDTO;
import com.interesting.modules.accmaterial.dto.QueryInventoryCheckPageDTO;
import com.interesting.modules.accmaterial.entity.XqInventoryCheck;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.accmaterial.vo.InventoryCheckDetailVO;
import com.interesting.modules.accmaterial.vo.InventoryCheckPageVO;

/**
* @author 26773
* @description 针对表【xq_inventory_check(盘点录入)】的数据库操作Service
* @createDate 2023-07-11 15:28:29
*/
public interface XqInventoryCheckService extends IService<XqInventoryCheck> {

    IPage<InventoryCheckPageVO> pageInventoryCheck(QueryInventoryCheckPageDTO dto);

    InventoryCheckDetailVO getInventoryCheckDetById(String id);

    boolean deleteBatchInventoryCheck(String ids);

    boolean addInventoryCheck(AddInventoryCheckDTO dto);

    boolean auditInventoryCheck(EditStatusDTO dto);

//    boolean addInventoryCheck(AddInventoryCheckDTO dto);
}
