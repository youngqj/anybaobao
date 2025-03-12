package com.interesting.modules.accmaterial.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.accmaterial.dto.QueryInventoryCheckPageDTO;
import com.interesting.modules.accmaterial.entity.XqInventoryCheck;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.accmaterial.vo.InventoryCheckPageVO;
import org.apache.ibatis.annotations.Param;

/**
* @author 26773
* @description 针对表【xq_inventory_check(盘点录入)】的数据库操作Mapper
* @createDate 2023-07-11 15:28:29
* @Entity com.interesting.modules.accmaterial.entity.XqInventoryCheck
*/
public interface XqInventoryCheckMapper extends BaseMapper<XqInventoryCheck> {

    IPage<InventoryCheckPageVO> pageInventoryCheck(@Param("page") Page<InventoryCheckPageVO> page, @Param("dto") QueryInventoryCheckPageDTO dto);
}




