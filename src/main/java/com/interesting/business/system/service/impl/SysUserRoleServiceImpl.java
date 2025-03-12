package com.interesting.business.system.service.impl;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.interesting.business.system.entity.SysRole;
import com.interesting.business.system.entity.SysUser;
import com.interesting.business.system.entity.SysUserRole;
import com.interesting.business.system.mapper.SysUserRoleMapper;
import com.interesting.business.system.service.ISysRoleService;
import com.interesting.business.system.service.ISysUserRoleService;
import com.interesting.business.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author: interesting-boot
 * @since 2018-12-21
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {


    /**
     * 根据userid 获取角色信息
     *
     * @param id
     * @return
     */
    @Override
    public List<SysRole> getRoleByUserId(String id) {
        return this.baseMapper.getRoleByUserId(id);
    }
}
