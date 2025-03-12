package com.interesting.business.system.service;

import java.util.List;
import java.util.Map;

import com.interesting.business.system.entity.SysRole;
import com.interesting.business.system.entity.SysUserRole;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author: interesting-boot
 * @since 2018-12-21
 */
public interface ISysUserRoleService extends IService<SysUserRole> {
    /**
     * 根据userid 获取角色信息
     *
     * @param id
     * @return
     */
    List<SysRole> getRoleByUserId(String id);

}
