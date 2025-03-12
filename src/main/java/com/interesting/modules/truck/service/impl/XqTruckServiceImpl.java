package com.interesting.modules.truck.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.BeanUtils;
import com.interesting.modules.airports.entity.XqAirport;
import com.interesting.modules.truck.dto.AddTruckDTO;
import com.interesting.modules.truck.dto.EditTruckDTO;
import com.interesting.modules.truck.dto.QueryTruckDTO;
import com.interesting.modules.truck.entity.XqTruck;
import com.interesting.modules.truck.mapper.XqTruckMapper;
import com.interesting.modules.truck.service.XqTruckService;
import com.interesting.modules.truck.vo.XqTruckListVO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class XqTruckServiceImpl extends ServiceImpl<XqTruckMapper, XqTruck>
        implements XqTruckService {
    @Override
    public boolean add(AddTruckDTO dto) {
        XqTruck xqTruck = new XqTruck();
        BeanUtils.copyProperties(dto, xqTruck);
        return this.save(xqTruck);
    }

    @Override
    public boolean deleteByIds(String ids) {
        List<String> strings = Arrays.asList(ids.split(","));
        return this.removeByIds(strings);
    }

    @Override
    public boolean edit(EditTruckDTO dto) {

        XqTruck xqTruck = this.getById(dto.getId());
        if (xqTruck == null) {
            throw new InterestingBootException("未找到当前卡车信息");
        }
        BeanUtils.copyProperties(dto, xqTruck);
        return this.updateById(xqTruck);
    }


    @Override
    public IPage<XqTruckListVO> page(QueryTruckDTO dto) {
        Page<XqTruckListVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        return baseMapper.page(page, dto);
    }
}
