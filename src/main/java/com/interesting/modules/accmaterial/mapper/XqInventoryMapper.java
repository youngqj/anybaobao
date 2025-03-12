package com.interesting.modules.accmaterial.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.accmaterial.entity.XqAccessoryInfo;
import com.interesting.modules.accmaterial.entity.XqInventory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.accmaterial.vo.AccInventoryDetails2VO;
import com.interesting.modules.accmaterial.vo.XqAccessoryInfoVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 26773
* @description 针对表【xq_inventory(库存管理)】的数据库操作Mapper
* @createDate 2023-07-05 16:19:33
* @Entity com.interesting.modules.accmaterial.entity.XqInventory
*/
public interface XqInventoryMapper extends BaseMapper<XqInventory> {

    IPage<AccInventoryDetails2VO> listInventoryDetails(@Param("page") Page<AccInventoryDetails2VO> page,
                                                       @Param("warehouseId") String warehouseId,
                                                       @Param("accessoryId") String accessoryId,
                                                       @Param("relativeTimeBegin") String relativeTimeBegin,
                                                       @Param("relativeTimeEnd") String relativeTimeEnd);

    void physicalDeleteBatch(List<String> ids);

    BigDecimal getSkuNum(@Param("accessoryId") String accessoryId);


}




