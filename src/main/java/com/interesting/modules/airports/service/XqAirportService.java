package com.interesting.modules.airports.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.airports.dto.AddXqAirportDTO;
import com.interesting.modules.airports.dto.EditXqAirportDTO;
import com.interesting.modules.airports.dto.QueryAirportsDTO;
import com.interesting.modules.airports.entity.XqAirport;
import com.interesting.modules.airports.vo.SumAirPortVO;
import com.interesting.modules.airports.vo.XqAirportListVO;

import java.util.List;

public interface XqAirportService extends IService<XqAirport> {

    IPage<XqAirportListVO> page(QueryAirportsDTO dto);

    List<SumAirPortVO> sumAirPort(QueryAirportsDTO dto);


    boolean add(AddXqAirportDTO dto);

    boolean deleteByIds(String ids);

    boolean edit(EditXqAirportDTO dto);
}
