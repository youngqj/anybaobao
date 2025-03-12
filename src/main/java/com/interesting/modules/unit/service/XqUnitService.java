package com.interesting.modules.unit.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.unit.dto.XqUnitDTO;
import com.interesting.modules.unit.entity.XqUnit;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 26773
* @description 针对表【xq_unit(单位表)】的数据库操作Service
* @createDate 2023-06-07 17:32:49
*/
public interface XqUnitService extends IService<XqUnit> {
    IPage<XqUnit> pageList(XqUnitDTO dto);
}
