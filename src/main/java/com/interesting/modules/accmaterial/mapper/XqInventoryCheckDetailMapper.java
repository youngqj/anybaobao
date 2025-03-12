package com.interesting.modules.accmaterial.mapper;

import com.interesting.modules.accmaterial.entity.XqInventoryCheckDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.accmaterial.vo.InventoryCheckDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 26773
* @description 针对表【xq_inventory_check_detail(盘点录入)】的数据库操作Mapper
* @createDate 2023-07-13 09:50:34
* @Entity com.interesting.modules.accmaterial.entity.XqInventoryCheckDetail
*/
@Mapper
public interface XqInventoryCheckDetailMapper extends BaseMapper<XqInventoryCheckDetail> {

    InventoryCheckDetailVO getInventoryCheckDetById(@Param("id") String id);
}




