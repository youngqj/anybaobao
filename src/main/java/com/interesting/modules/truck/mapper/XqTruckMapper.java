package com.interesting.modules.truck.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.truck.dto.QueryTruckDTO;
import com.interesting.modules.truck.entity.XqTruck;
import com.interesting.modules.truck.vo.XqTruckListVO;
import org.apache.ibatis.annotations.Param;

public interface XqTruckMapper extends BaseMapper<XqTruck> {
    IPage<XqTruckListVO> page(@Param("page") Page<XqTruckListVO> page,
                              @Param("dto") QueryTruckDTO dto);
}
