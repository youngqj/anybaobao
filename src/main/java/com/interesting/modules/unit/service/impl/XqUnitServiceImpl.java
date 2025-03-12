package com.interesting.modules.unit.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.modules.unit.dto.XqUnitDTO;
import com.interesting.modules.unit.entity.XqUnit;
import com.interesting.modules.unit.service.XqUnitService;
import com.interesting.modules.unit.mapper.XqUnitMapper;
import org.springframework.stereotype.Service;

/**
* @author 26773
* @description 针对表【xq_unit(单位表)】的数据库操作Service实现
* @createDate 2023-06-07 17:32:49
*/
@Service
public class XqUnitServiceImpl extends ServiceImpl<XqUnitMapper, XqUnit>
    implements XqUnitService{

    @Override
    public IPage<XqUnit> pageList(XqUnitDTO dto) {
        Page<XqUnit> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        return baseMapper.pageList(dto, page);
    }
}




