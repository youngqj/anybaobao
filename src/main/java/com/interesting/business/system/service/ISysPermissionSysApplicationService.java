package com.interesting.business.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.business.system.model.SysPermissionApplicationSys;
import com.interesting.business.system.vo.SysPermissionApplicationSysVo;

import java.util.List;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 * <p>
 *
 * @author cc
 * @version 1.0
 * @date 2021/11/8
 */
public interface ISysPermissionSysApplicationService extends IService<SysPermissionApplicationSys> {

    /**
     *
     * @param username
     * @return
     */
    List<SysPermissionApplicationSysVo> queryByUser(String username);

}
