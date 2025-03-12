package com.interesting.modules.warehouse.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.accmaterial.dto.QueryXqAccInventoryDTO;
import com.interesting.modules.accmaterial.dto.QueryXqAccInventoryDTO2;
import com.interesting.modules.accmaterial.vo.AccInventoryPageVO;
import com.interesting.modules.accmaterial.vo.AccInventoryPageVO2;
import com.interesting.modules.warehouse.dto.QueryXqWarehouseDTO;
import com.interesting.modules.warehouse.entity.XqWarehouse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.warehouse.vo.WarehouseIdNameAndAddressVO;
import com.interesting.modules.warehouse.vo.XqWarehouseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_warehouse(仓库管理)】的数据库操作Mapper
* @createDate 2023-07-04 11:45:24
* @Entity com.interesting.modules.warehouse.entity.XqWarehouse
*/
public interface XqWarehouseMapper extends BaseMapper<XqWarehouse> {

    IPage<XqWarehouseVO> warehousePage(@Param("page") Page<XqWarehouseVO> page, @Param("dto") QueryXqWarehouseDTO dto);

    XqWarehouseVO getDetailById(String id);

    IPage<AccInventoryPageVO> listAccInventoryPage(@Param("page") Page<AccInventoryPageVO> page, @Param("dto") QueryXqAccInventoryDTO dto);

    IPage<AccInventoryPageVO2> listAccInventoryPage2(@Param("page") Page<AccInventoryPageVO2> page, @Param("dto") QueryXqAccInventoryDTO2 dto);

    List<WarehouseIdNameAndAddressVO> listWarehouse();

    List<String> getWarehouseName();

}




