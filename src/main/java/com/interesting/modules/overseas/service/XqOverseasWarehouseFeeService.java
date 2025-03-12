package com.interesting.modules.overseas.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.overseas.dto.AddOverseasWarehouseFeeDTO;
import com.interesting.modules.overseas.dto.EditOverseasWarehouseFeeDTO;
import com.interesting.modules.overseas.dto.QueryWarehouseFeeDTO;
import com.interesting.modules.overseas.entity.XqOverseasWarehouseFee;
import com.interesting.modules.overseas.vo.ListWarehouseFeeVO;
import com.interesting.modules.overseas.vo.OverseasWarehouseFeeVO;

public interface XqOverseasWarehouseFeeService extends IService<XqOverseasWarehouseFee> {
    boolean addOverseasWarehouseFee(AddOverseasWarehouseFeeDTO dto);
    boolean editOverseasWarehouseFee(EditOverseasWarehouseFeeDTO dto);
    IPage<ListWarehouseFeeVO> queryByPage(QueryWarehouseFeeDTO dto,Page<ListWarehouseFeeVO> page);

    OverseasWarehouseFeeVO queryById(String id);
}
