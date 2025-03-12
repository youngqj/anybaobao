package com.interesting.modules.warehouse.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.common.api.vo.Result;
import com.interesting.modules.accmaterial.dto.QueryXqAccInventoryDTO;
import com.interesting.modules.accmaterial.dto.QueryXqAccInventoryDTO2;
import com.interesting.modules.accmaterial.vo.AccInventoryPageVO;
import com.interesting.modules.accmaterial.vo.AccInventoryPageVO2;
import com.interesting.modules.reportform.dto.PayableDTO;
import com.interesting.modules.reportform.vo.PayableVO;
import com.interesting.modules.warehouse.dto.InstOrUpdtXqWarehouseDTO;
import com.interesting.modules.warehouse.dto.QueryXqWarehouseDTO;
import com.interesting.modules.warehouse.entity.XqWarehouse;
import com.interesting.modules.warehouse.vo.WarehouseIdNameAndAddressVO;
import com.interesting.modules.warehouse.vo.XqWarehouseVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
* @author 26773
* @description 针对表【xq_warehouse(仓库管理)】的数据库操作Service
* @createDate 2023-07-04 11:45:24
*/
public interface XqWarehouseService extends IService<XqWarehouse> {

    IPage<XqWarehouseVO> warehousePage(QueryXqWarehouseDTO dto);

    IPage<AccInventoryPageVO> listAccInventoryPage(QueryXqAccInventoryDTO dto);

    IPage<AccInventoryPageVO2> listAccInventoryPage2(QueryXqAccInventoryDTO2 dto);

    void exportTemplate(HttpServletResponse response)throws UnsupportedEncodingException;

    Result<?> importWarehouse(MultipartFile file)throws Exception;

    List<WarehouseIdNameAndAddressVO> listWarehouse();

    List<String> getWarehouseName();

    Boolean addWarehouse(InstOrUpdtXqWarehouseDTO dto);

    Boolean updateWarehouseById(InstOrUpdtXqWarehouseDTO dto);

    XqWarehouseVO getDetailById(String id);

}
