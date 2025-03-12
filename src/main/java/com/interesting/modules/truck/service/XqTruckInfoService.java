package com.interesting.modules.truck.service;

import com.interesting.modules.truck.dto.InstUptTruckInfoDTO;
import com.interesting.modules.truck.entity.XqTruckInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_truck_info(卡车信息表)】的数据库操作Service
* @createDate 2023-08-01 17:00:10
*/
public interface XqTruckInfoService extends IService<XqTruckInfo> {

    void updateTruckInfo(String id, List<InstUptTruckInfoDTO> truckInfos, Integer roleCode);

}
