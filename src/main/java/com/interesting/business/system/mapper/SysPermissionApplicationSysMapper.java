package com.interesting.business.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.business.system.model.SysPermissionApplicationSys;
import com.interesting.business.system.vo.SysPermissionApplicationSysVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 * <p>
 *
 * @author cc
 * @version 1.0
 * @date 2021/11/8
 */
@Mapper
public interface SysPermissionApplicationSysMapper extends BaseMapper<SysPermissionApplicationSys> {


    /**
     * 根据 用户名查找
     *
     * @param username
     * @return
     */
    List<SysPermissionApplicationSysVo> queryByUser(String username);

}
