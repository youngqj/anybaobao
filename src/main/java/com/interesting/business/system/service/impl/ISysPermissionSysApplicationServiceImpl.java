package com.interesting.business.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.business.system.mapper.SysPermissionApplicationSysMapper;
import com.interesting.business.system.model.SysPermissionApplicationSys;
import com.interesting.business.system.service.ISysPermissionSysApplicationService;
import com.interesting.business.system.vo.SysPermissionApplicationSysVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 * <p>
 *
 * @author cc
 * @version 1.0
 * @date 2021/11/8
 */
@Service
public class ISysPermissionSysApplicationServiceImpl extends ServiceImpl<SysPermissionApplicationSysMapper, SysPermissionApplicationSys> implements ISysPermissionSysApplicationService {
    @Override
    public List<SysPermissionApplicationSysVo> queryByUser(String username) {
        return this.baseMapper.queryByUser(username);
    }
}
