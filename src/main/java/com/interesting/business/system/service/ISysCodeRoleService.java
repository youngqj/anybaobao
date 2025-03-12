package com.interesting.business.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.business.system.entity.SysCodeRole;
import com.interesting.business.system.model.SysDepartTreeModel;

import java.util.List;

/**
 * @Description: 编码规则
 * @Author: zjk
 * @Date: 2021-07-23
 * @Version: V1.0
 */
public interface ISysCodeRoleService extends IService<SysCodeRole> {
    /**
     * 根据类型获取编码规则
     * @param type 1出货序号
     * @param save_flag 前端传0；后端保存时候必须传1
     *
     */
    String getCodeNumByType(Integer type, Integer save_flag);

    String getCodeNumByType2(Integer type, Integer saveFlag);
}
