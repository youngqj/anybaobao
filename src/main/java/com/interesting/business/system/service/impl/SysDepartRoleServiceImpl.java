package com.interesting.business.system.service.impl;

import com.interesting.business.system.entity.SysDepartRole;
import com.interesting.business.system.mapper.SysDepartRoleMapper;
import com.interesting.business.system.service.ISysDepartRoleService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 部门角色
 * @Author: interesting-boot
 * @Date:   2020-02-12
 * @Version: V1.0
 */
@Service
public class SysDepartRoleServiceImpl extends ServiceImpl<SysDepartRoleMapper, SysDepartRole> implements ISysDepartRoleService {

    @Override
    public List<SysDepartRole> queryDeptRoleByDeptAndUser(String orgCode, String userId) {
        return this.baseMapper.queryDeptRoleByDeptAndUser(orgCode,userId);
    }
}
