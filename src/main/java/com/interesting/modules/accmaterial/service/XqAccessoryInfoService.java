package com.interesting.modules.accmaterial.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.accmaterial.entity.XqAccessoryInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.accmaterial.vo.XqAccessoryInfoVO;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_accessory_info】的数据库操作Service
* @createDate 2023-06-09 14:41:01
*/
public interface XqAccessoryInfoService extends IService<XqAccessoryInfo> {
    XqAccessoryInfo getByCondition( String accessoryName, String size, String msf);

    List<XqAccessoryInfoVO> queryAccessInfo();

    IPage<XqAccessoryInfoVO> queryAccessInfoByPage(Integer pageNo, Integer pageSize);

}
