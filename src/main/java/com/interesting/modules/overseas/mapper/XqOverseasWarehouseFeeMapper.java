package com.interesting.modules.overseas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.overseas.dto.QueryWarehouseFeeDTO;
import com.interesting.modules.overseas.entity.XqOverseasWarehouseFee;
import com.interesting.modules.overseas.vo.ListWarehouseFeeVO;
import com.interesting.modules.overseas.vo.OverseasWarehouseFeeVO;
import org.apache.ibatis.annotations.Param;

public interface XqOverseasWarehouseFeeMapper extends BaseMapper<XqOverseasWarehouseFee> {
    IPage<ListWarehouseFeeVO> queryByPage(@Param("dto") QueryWarehouseFeeDTO dto, @Param("page") Page<ListWarehouseFeeVO> page);

    OverseasWarehouseFeeVO queryById(@Param("id") String id);
}
