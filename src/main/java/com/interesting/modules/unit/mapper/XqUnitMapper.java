package com.interesting.modules.unit.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.unit.dto.XqUnitDTO;
import com.interesting.modules.unit.entity.XqUnit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 26773
* @description 针对表【xq_unit(单位表)】的数据库操作Mapper
* @createDate 2023-06-07 17:32:49
* @Entity com.interesting.modules.unit.entity.XqUnit
*/
public interface XqUnitMapper extends BaseMapper<XqUnit> {
    IPage<XqUnit> pageList(@Param("dto") XqUnitDTO dto, @Param("page") IPage<XqUnit> page);
}




