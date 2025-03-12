package com.interesting.modules.airports.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.airports.dto.QueryAirportsDTO;
import com.interesting.modules.airports.entity.XqAirport;
import com.interesting.modules.airports.vo.SumAirPortVO;
import com.interesting.modules.airports.vo.XqAirportListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XqAirportMapper extends BaseMapper<XqAirport> {

    IPage<XqAirportListVO> page(@Param("page") Page<XqAirportListVO> page,
                                @Param("dto") QueryAirportsDTO dto);

    List<SumAirPortVO> sumAirPort(@Param("dto") QueryAirportsDTO dto);
}
