package com.interesting.modules.overseas.mapper;

import com.interesting.modules.overseas.entity.XqOverseasInventoryCheckDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.overseas.vo.OverSeasInventoryCheckDetailRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_overseas_inventory_check_detail(盘点录入)】的数据库操作Mapper
* @createDate 2023-07-28 09:17:39
* @Entity com.interesting.modules.overseas.entity.XqOverseasInventoryCheckDetail
*/
@Mapper
public interface XqOverseasInventoryCheckDetailMapper extends BaseMapper<XqOverseasInventoryCheckDetail> {

    List<OverSeasInventoryCheckDetailRecordVO> getInventoryCheckDetListById(@Param("id") String id);
}




