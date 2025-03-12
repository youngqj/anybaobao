package com.interesting.modules.accmaterial.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.accmaterial.entity.XqAccessoryInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.accmaterial.vo.XqAccessoryInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_accessory_info】的数据库操作Mapper
* @createDate 2023-06-09 14:41:01
* @Entity com.interesting.modules.accmaterial.entity.XqAccessoryInfo
*/
public interface XqAccessoryInfoMapper extends BaseMapper<XqAccessoryInfo> {

    XqAccessoryInfo getByCondition(@Param("accessoryName") String accessoryName, @Param("size") String size, @Param("msf") String msf);

    List<XqAccessoryInfoVO> queryAccessInfo();

    IPage<XqAccessoryInfoVO> queryAccessInfoByPage(@Param("page") Page<XqAccessoryInfoVO> page);
}




