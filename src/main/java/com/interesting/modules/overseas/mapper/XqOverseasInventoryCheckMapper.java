package com.interesting.modules.overseas.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.overseas.dto.QueryPageOverSeasInventoryCheckDTO;
import com.interesting.modules.overseas.entity.XqOverseasInventoryCheck;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.overseas.vo.OverSeasInventoryCheckPageVO;
import org.apache.ibatis.annotations.Param;

/**
* @author 26773
* @description 针对表【xq_overseas_inventory_check(盘点录入)】的数据库操作Mapper
* @createDate 2023-07-28 09:17:25
* @Entity com.interesting.modules.overseas.entity.XqOverseasInventoryCheck
*/
public interface XqOverseasInventoryCheckMapper extends BaseMapper<XqOverseasInventoryCheck> {

    IPage<OverSeasInventoryCheckPageVO> pageInventoryCheck(@Param("page") Page<OverSeasInventoryCheckPageVO> page,
                                                           @Param("dto") QueryPageOverSeasInventoryCheckDTO dto);
}




