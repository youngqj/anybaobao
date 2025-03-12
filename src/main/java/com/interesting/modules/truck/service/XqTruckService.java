package com.interesting.modules.truck.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.truck.dto.AddTruckDTO;
import com.interesting.modules.truck.dto.EditTruckDTO;
import com.interesting.modules.truck.dto.QueryTruckDTO;
import com.interesting.modules.truck.entity.XqTruck;
import com.interesting.modules.truck.vo.XqTruckListVO;

public interface XqTruckService extends IService<XqTruck> {
    boolean add(AddTruckDTO dto);

    boolean deleteByIds(String ids);

    boolean edit(EditTruckDTO dto);

    IPage<XqTruckListVO> page(QueryTruckDTO dto);
}
