package com.interesting.modules.accmaterial.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.modules.accmaterial.entity.XqAccessoryInfo;
import com.interesting.modules.accmaterial.service.XqAccessoryInfoService;
import com.interesting.modules.accmaterial.mapper.XqAccessoryInfoMapper;
import com.interesting.modules.accmaterial.vo.XqAccessoryInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_accessory_info】的数据库操作Service实现
* @createDate 2023-06-09 14:41:01
*/
@Service
public class XqAccessoryInfoServiceImpl extends ServiceImpl<XqAccessoryInfoMapper, XqAccessoryInfo>
    implements XqAccessoryInfoService{

    @Override
    public XqAccessoryInfo getByCondition(String accessoryName, String size, String msf) {
        return baseMapper.getByCondition(accessoryName,size,msf);
    }

    @Override
    public List<XqAccessoryInfoVO> queryAccessInfo() {
        return baseMapper.queryAccessInfo();
    }

    @Override
    public IPage<XqAccessoryInfoVO> queryAccessInfoByPage(Integer pageNo, Integer pageSize) {
        Page<XqAccessoryInfoVO> page = new Page<>(pageNo, pageSize);
        return baseMapper.queryAccessInfoByPage(page);
    }
}




